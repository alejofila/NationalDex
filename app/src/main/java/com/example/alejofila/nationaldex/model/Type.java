package com.example.alejofila.nationaldex.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class Type {

    /**
     * TYPE CONSTANTS
     */
    public static final String NORMAL = "normal";
    public static final String FIRE  = "fire"   ;
    public static final String WATER  = "water" ;
    public static final String ELECTRIC  = "electric";
    public static final String GRASS  = "grass";
    public static final String ICE  = "ice";
    public static final String FIGHTING  = "fighting";
    public static final String POISON  = "poison";
    public static final String GROUND  = "ground";
    public static final String FLYING  = "flying";
    public static final String PSYCHIC  = "psychic";
    public static final String BUG  = "bug";
    public static final String ROCK  = "rock";
    public static final String GHOST  = "ghost";
    public static final String DRAGON  = "dragon";
    public static final String DARK  = "dark";
    public static final String STEEL  = "steel";
    public static final String FAIRY = "fairy";

    private String name;
    @SerializedName(value = "resource_uri")
    private String resourceUri;

    public Type(String name){
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
