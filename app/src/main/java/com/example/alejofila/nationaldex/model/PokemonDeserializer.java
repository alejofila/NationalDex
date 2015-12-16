package com.example.alejofila.nationaldex.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonDeserializer implements JsonDeserializer<Pokemon[]> {
    @Override
    public Pokemon[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement content = json.getAsJsonObject().get("pokemon").getAsJsonArray();

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, Pokemon[].class);
    }
}