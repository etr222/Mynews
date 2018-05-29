package com.example.ad0517;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobUser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int UNICODE =1001 ;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private String newsResultData;
    private Handler handler;
    private NewsBean newsBean;
    private RecyclerView.LayoutManager layoutManager;

    private TextView top;
    private TextView shihui;
    private TextView guonei;
    private TextView guoji;
    private TextView yule;
    private TextView tiyu;
    private TextView junshi;
    private TextView keji;
    private TextView caijing;
    private TextView shishang;
    private String type="top";

    private DrawerLayout drawerLayout;
    private TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTextView();
        initHandler();
        sendRequestWithOkhttp();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);


        NavigationView navigationView=findViewById(R.id.nav_view);
        userName=findViewById(R.id.userName);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_collect:
                        Intent collectIntent=new Intent(MainActivity.this,CollectActivity.class);
//                        collectIntent.putExtra("collect_data",newsResultData);
                        startActivity(collectIntent);
                        break;
                    case R.id.my_comment:
                        Intent CommentIntent=new Intent(MainActivity.this,CommentActivity.class);
//                        CommentIntent.putExtra("comment_data",newsResultData);
                        startActivity(CommentIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initHandler() {

        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what==UNICODE){
                    newsResultData= (String) msg.obj;
                    gsonNewsData();
                    return true;
                }
                return false;
            }
        });
    }

    private void initTextView() {
        top=findViewById(R.id.tv_type_top);
        shihui=findViewById(R.id.tv_type_shehui);
        guonei=findViewById(R.id.tv_type_guonei);
        guoji=findViewById(R.id.tv_type_guoji);
        yule=findViewById(R.id.tv_type_yule);
        tiyu=findViewById(R.id.tv_type_tiyu);
        junshi=findViewById(R.id.tv_type_junshi);
        keji=findViewById(R.id.tv_type_keji);
        caijing=findViewById(R.id.tv_type_caijing);
        shishang=findViewById(R.id.tv_type_shishang);

        top.setOnClickListener(this);
        shihui.setOnClickListener(this);
        guonei.setOnClickListener(this);
        guoji.setOnClickListener(this);
        yule.setOnClickListener(this);
        tiyu.setOnClickListener(this);
        junshi.setOnClickListener(this);
        keji.setOnClickListener(this);
        caijing.setOnClickListener(this);
        shishang.setOnClickListener(this);


    }

    private void setRecyclerView() {
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyAdapter(MainActivity.this,newsBean);
        recyclerView.setAdapter(adapter);
    }


    private void gsonNewsData() {
        Gson gson=new Gson();
        newsBean = gson.fromJson(newsResultData, NewsBean.class);
        setRecyclerView();

    }

    private void sendRequestWithOkhttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("http://v.juhe.cn/toutiao/index?type="+type+"&key=f7e6167290ae6bc8929f057e6308a325")
                        .build();
                try {
                    Response response=client.newCall(request).execute();

                    Message message=new Message();
                    message.what=UNICODE;
                    message.obj=response.body().string();
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_type_top:{
                type="top";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_guonei:{
                type="guonei";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_guoji:{
                type="guoji";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_shehui:{
                type="shehui";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_yule:{
                type="yule";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_tiyu:{
                type="tiyu";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_junshi:{
                type="junshi";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_keji:{
                type="keji";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_caijing:{
                type="caijing";
                sendRequestWithOkhttp();
                break;
            }case R.id.tv_type_shishang:{
                type="shishang";
                sendRequestWithOkhttp();
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BmobUser.getCurrentUser()!=null){
//            userName.setText(BmobUser.getCurrentUser().getUsername());
        }
    }
}
