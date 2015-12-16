package com.example.alejofila.nationaldex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alejandro on 15/12/2015.
 */
public class Sprite {
    @SerializedName(value = "resource_uri")
    private
    String resourceUri;
    private String urlImg;

    public Sprite(String urlImg){
        this.urlImg = urlImg;
    }


    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
