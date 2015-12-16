package com.example.alejofila.nationaldex.model;

import com.example.alejofila.nationaldex.util.RegexUtils;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class Move {
    private String learn_type;
    private String name;
    @SerializedName(value = "resource_uri")
    private String resourceUri;

    public Move(String name){
        this.name = name;
    }

    private int id;

    public String getLearn_type() {
        return learn_type;
    }

    public void setLearn_type(String learn_type) {
        this.learn_type = learn_type;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void calculateMoveId(){
        id = RegexUtils.extractMoveId(resourceUri);
    }
}
