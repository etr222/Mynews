package com.example.ad0517;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;



public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private NewsBean result;


    public MyAdapter(Context context,NewsBean result){
        this.context=context;
        this.result=result;
    }
    public MyAdapter(){

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        NewsBean.ResultBean.DataBean dataBean = result.getResult().getData().get(position);
        String url=dataBean.getThumbnail_pic_s();

        holder.tv_title.setText(dataBean.getTitle());
        holder.tv_author.setText(dataBean.getAuthor_name());
        holder.tv_data.setText(dataBean.getDate());

        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)//图片加载出来前，显示的图片
                .error(R.mipmap.ic_launcher)//图片加载失败后，显示的图片
                .into(holder.imageView);

        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                NewsBean.ResultBean.DataBean dataBean = result.getResult().getData().get(position);

                Intent intent=new Intent(context,WebViewActivity.class);

                intent.putExtra("url",dataBean.getUrl());
                intent.putExtra("title",dataBean.getTitle());
                intent.putExtra("dataBean",dataBean);
                intent.putExtra("newsId",dataBean.getUniquekey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.getResult().getData().size();
    }
}
