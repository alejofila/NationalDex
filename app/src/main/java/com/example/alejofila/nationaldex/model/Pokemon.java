package com.example.alejofila.nationaldex.model;

import android.util.Log;

import com.example.alejofila.nationaldex.util.RegexUtils;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alejandro on 14/12/2015.
 */
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Pokemon implements Comparable<Pokemon> {

    private static final String TAG = Pokemon.class.getSimpleName();
    private String name;
    @SerializedName(value = "resource_uri")
    private String resourceUri;
    private int nationalID;


    public Pokemon() {

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

    public int getNationalID() {
        return nationalID;
    }

    public void setNationalID(int nationalID) {
        this.nationalID = nationalID;
    }

    public void calculateNationalID() {
        nationalID = RegexUtils.extractNationalId(resourceUri);

    }


    @Override
    public int compareTo(Pokemon another) {
        if (this.getNationalID() == another.getNationalID())
            return 0;
        else if (this.getNationalID() < another.getNationalID())
            return -1;
        else return 1;
    }
}
