package com.example.ad0517;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CollectAdapter extends BaseAdapter {
    private List<Collect> list;
    private Context context;

    class ViewHolder{
        ImageView imageView;
        TextView tv_title;
        TextView tv_author;
        TextView tv_data;
    }

    public CollectAdapter(){

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.imageView=convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title=convertView.findViewById(R.id.tv_title);
            viewHolder.tv_author=convertView.findViewById(R.id.tv_author);
            viewHolder.tv_data=convertView.findViewById(R.id.tv_data);

            convertView.setTag(viewHolder);
        }



        Collect collect=new Collect();
        

        return convertView;
    }

    public void changeData(List<Collect> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
