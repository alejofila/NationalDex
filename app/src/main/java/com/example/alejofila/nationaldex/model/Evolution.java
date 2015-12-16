package com.example.alejofila.nationaldex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class Evolution {

    public Evolution(String to, String resourceUri){
        this.to = to;
        this.resourceUri = resourceUri;
    }

    private String detail;
    private String method;
    @SerializedName(value = "resource_uri")


    private String resourceUri;
    private String to;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
