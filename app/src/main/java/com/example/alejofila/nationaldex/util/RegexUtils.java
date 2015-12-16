package com.example.alejofila.nationaldex.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alejandro on 15/12/2015.
 */
public class RegexUtils {

    public  static int extractNationalId(String resourceUri) {
       return extractURIAppendedId(resourceUri);
    }
    private static int extractURIAppendedId(String resourceUri){
        Pattern pattern = Pattern.compile("/(\\d+)/");
        Matcher matcher = pattern.matcher(resourceUri);

        if (matcher.find())
            return  Integer.parseInt(matcher.group(1));
        else
            return -1;

    }

    public static int extractMoveId(String resourceUri){
        return extractURIAppendedId(resourceUri);


    }


}
