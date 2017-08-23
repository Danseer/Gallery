package com.example.konstantin.gallery;

/**
 * Created by Konstantin on 16.07.2017.
 */

public class gallery_item {
    private String caption;
    private String id;
    private String urls;
    private String urlm;
    private String owner_name;


    public String getUrlm() {
        return urlm;
    }

    public void setUrlm(String urlm) {
        this.urlm = urlm;
    }


    public String getCaption() {

        return caption;
    }

    public void setCaption(String caption) {

        this.caption = caption;
    }

    public String getOwner_name() {

        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrls() {

        return urls;
    }

    public void setUrls(String url) {
        this.urls = url;
    }
}
