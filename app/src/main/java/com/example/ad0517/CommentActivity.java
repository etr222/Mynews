package com.example.ad0517;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class CommentActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        listView=findViewById(R.id.comment_listView);

        if (BmobUser.getCurrentUser()==null){
            Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            BmobQuery<Comment> query = new BmobQuery<Comment>();
//查询playerName叫“比目”的数据
            query.addWhereEqualTo("user_id", BmobUser.getCurrentUser().getUsername());
//执行查询方法
            query.findObjects(new FindListener<Comment>() {
                @Override
                public void done(List<Comment> object, BmobException e) {
                    if(e==null){
                        Toast.makeText(CommentActivity.this, object.size(), Toast.LENGTH_SHORT).show();
                        for (Comment comment : object) {
                            comment.getContent();
                            comment.getNews_id();
                        }

                    }else{
                        Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
    }
}
