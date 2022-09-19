package com.inventors.jd.keeper.models;

/**
 * Created by jd on 04-Aug-18.
 */

public class Post {
    private int id;
    private String title;
    private String url;
    private String is_active;

    public Post(int id, String title, String url, String is_active) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.is_active = is_active;
    }

    public Post() {

    }

    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }


    //GETTERS


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getIs_active() {
        return is_active;
    }
}
