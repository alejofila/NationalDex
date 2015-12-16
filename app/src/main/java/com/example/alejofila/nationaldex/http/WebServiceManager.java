package com.example.alejofila.nationaldex.http;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class WebServiceManager {
    private static final String TAG = WebServiceManager.class.getSimpleName();
    /**
     * Constants for HTTP REST calls
     */
    public static final String BASE_URL = "http://pokeapi.co/";
    private static final String API_URL ="api/v1/";
    private static final String ALL_POKEMON ="pokedex/1/";
    private static final String SINGLE_POKEMON="pokemon/";

    public static void getAllPokemons(Callback callback){
        String strUrl = BASE_URL+API_URL+ALL_POKEMON;
        Request request = new Request.Builder()
                .url(strUrl)
                .build();
        generalRequest(request, callback);
    }
    public static void getPokemonInfo(Callback callback, int national_id){
        String strUrl = BASE_URL+API_URL+SINGLE_POKEMON+national_id;
        Request request = new Request.Builder()
                .url(strUrl)
                .build();
        generalRequest(request, callback);
    }

    public static void getPokemonImage(Callback callback, String urlImage){
        String strUrl = BASE_URL+urlImage;
        Request request = new Request.Builder()
                .url(strUrl)
                .build();
        generalRequest(request, callback);
    }

    private static void generalRequest(Request request, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Log.i(TAG, "This is the target URL " + request.urlString());
        client.newCall(request).enqueue(callback);
    }
}
