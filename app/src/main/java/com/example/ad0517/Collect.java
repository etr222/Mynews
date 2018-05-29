package com.example.ad0517;

import cn.bmob.v3.BmobObject;

public class Collect extends BmobObject{
    public Boolean getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(Boolean is_collect) {
        this.is_collect = is_collect;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    private Boolean is_collect;
    private String user_id;
    private String news_id;
}
