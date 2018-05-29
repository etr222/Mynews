package com.example.ad0517;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {

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

    public Boolean getIs_del() {
        return is_del;
    }

    public void setIs_del(Boolean is_del) {
        this.is_del = is_del;
    }

    private String user_id;
    private String news_id;
    private Boolean is_del;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    private String Content;
}
