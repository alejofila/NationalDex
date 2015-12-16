package com.example.alejofila.nationaldex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class Ability {
    private String name;
    @SerializedName(value = "resource_uri")
    private String resourceUri;

    public Ability (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
