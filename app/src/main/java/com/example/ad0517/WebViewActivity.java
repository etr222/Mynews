package com.example.ad0517;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class WebViewActivity extends AppCompatActivity {

    private String NewsId="";
    private EditText editText;
    private Boolean is_collect=false;
    private Button btnCollect;
    private String ObjectId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        NewsBean.ResultBean.DataBean dataBean= (NewsBean.ResultBean.DataBean) intent.getSerializableExtra("dataBean");
        NewsId=intent.getStringExtra("newsId");
//        WebView的使用：
//        获取WebView的实例。
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
//        设置目标网页在当前WebView中显示。通过WebView对象.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new WebViewClient());
//        设置WebView展示的网页的地址。通过WebView对象.loadUrl()
        webView.loadUrl(url);

        setTitle(title.substring(0, 5));

//        设置返回按钮。
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        editText = findViewById(R.id.comment_text);
        Button btnComment = findViewById(R.id.btn_comment);
        btnCollect = findViewById(R.id.btn_collect);


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser = BmobUser.getCurrentUser();
                if (bmobUser == null) {
                    checkUser();
                } else {

                    Comment comment=new Comment();
                    comment.setUser_id(BmobUser.getCurrentUser().getObjectId());
                    comment.setNews_id(NewsId);
                    comment.setIs_del(false);
                    comment.setContent(editText.getText().toString());
                    comment.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(WebViewActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
            }
        });


        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BmobUser.getCurrentUser() == null) {
                    checkUser();
                } else {

                    if (!ObjectId.equals("")){
                        Log.e("updateCollect","updateCollect");
                        updateCollect();
                    }else {
                        Log.e("addCollect","addCollect");
                        addCollect();
                    }
                }
            }
        });

    }

    private void updateCollect() {
        final Collect collect=new Collect();
        collect.setIs_collect(!is_collect);

        collect.update(ObjectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.e("bmob","更新成功");
                    if (!collect.getIs_collect()){
                        btnCollect.setText("收藏");
                        is_collect=collect.getIs_collect();
                        Log.e("is_collect",is_collect+"");
                    }else {
                        btnCollect.setText("已收藏");
                        is_collect=collect.getIs_collect();
                        Log.e("is_collect",is_collect+"");
                    }
                }else{
                    Log.e("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void addCollect() {
        final Collect collect=new Collect();
        collect.setUser_id(BmobUser.getCurrentUser().getObjectId());
        collect.setNews_id(NewsId);
        collect.setIs_collect(!is_collect);
        collect.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    btnCollect.setText("已收藏");
                    is_collect=true;
                    ObjectId=collect.getObjectId();
                }else{
                    Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void checkUser() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(WebViewActivity.this);
        dialog.setTitle("您是否已经拥有账号？");
        dialog.setMessage("您是否已经拥有账号？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WebViewActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WebViewActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
    private void queryIsCollect(){
        BmobQuery<Collect> query = new BmobQuery<Collect>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("news_id", NewsId);
//执行查询方法
        query.findObjects(new FindListener<Collect>() {
            @Override
            public void done(List<Collect> object, BmobException e) {
                if(e==null){

                    for (Collect collect : object) {
                        //获得playerName的信息
                        if (collect.getIs_collect()){
                            btnCollect.setText("已收藏");
                        }
                    }
                }else{
                    Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        BmobUser user=BmobUser.getCurrentUser();
        if (user!=null){
            queryIsCollect();
        }
    }
}
