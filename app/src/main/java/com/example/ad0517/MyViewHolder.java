package com.example.ad0517;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {
     ImageView imageView;
     TextView tv_title;
     TextView tv_author;
     TextView tv_data;
     View newsView;


    public MyViewHolder(View itemView) {
        super(itemView);
        newsView=itemView;
        imageView=itemView.findViewById(R.id.iv_img);
        tv_title=itemView.findViewById(R.id.tv_title);
        tv_author=itemView.findViewById(R.id.tv_author);
        tv_data=itemView.findViewById(R.id.tv_data);
    }
}
