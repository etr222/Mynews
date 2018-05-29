package com.example.ad0517;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CollectActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        listView=findViewById(R.id.collect_listView);



        if (BmobUser.getCurrentUser()==null){
            Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            BmobQuery<Collect> query = new BmobQuery<Collect>();
//查询playerName叫“比目”的数据
            query.addWhereEqualTo("is_collect", true);
//执行查询方法
            query.findObjects(new FindListener<Collect>() {
                @Override
                public void done(List<Collect> object, BmobException e) {
                    if(e==null){
                        for (Collect collect : object) {
                            collect.getIs_collect();
                        }
                    }else{
                        Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
    }



}
