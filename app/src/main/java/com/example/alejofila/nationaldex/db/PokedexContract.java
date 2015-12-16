package com.example.alejofila.nationaldex.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokedexContract  {
    public static final String CONTENT_AUTHORITY ="com.example.alejofila.nationaldex";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class PokemonEntry implements BaseColumns {
        public static final String TABLE_NAME = "pokemon";
        public static final String CN_NAME = "name";
        public static final String CN_RESOURCE_URI = "resource_uri";
        public static final String CN_NATIONAL_ID = "national_id";
        public static final String CN_IS_FAVORITE ="is_favorite";// 0 true 1 false
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                "("+CN_NATIONAL_ID + " INTEGER PRIMARY KEY,"
                + CN_NAME + " TEXT NOT NULL,"
                + CN_RESOURCE_URI + " TEXT NOT NULL,"
                + CN_IS_FAVORITE +" INTEGER NOT NULL"
                + ");";

        public static final String PATH_POKEMON = PokemonEntry.TABLE_NAME;

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POKEMON;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POKEMON).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POKEMON;

        public static Uri buildPokemonUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class PokemonDetailsEntry implements  BaseColumns{
        public static final String TABLE_NAME ="pokemon_details";
        public static final String CN_TYPE_1 = "type1";
        public static final String CN_TYPE_2 = "type2";
        public static final String CN_ABILITY_1 = "ability1";
        public static final String CN_ABILITY_2 = "ability2";
        public static final String CN_NATIONAL_ID ="national_id";
        public static final String CN_HP ="hp";
        public static final String CN_ATTACK ="attack";
        public static final String CN_DEFENSE ="defense";
        public static final String CN_SPEED ="speed";
        public static final String CN_HEIGHT ="height";
        public static final String CN_WEIGHT ="weight";
        public static final String CN_CATCH_RATE ="catch_rate";
        public static final String CN_IMAGE ="image";
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                "("+CN_NATIONAL_ID + " INTEGER PRIMARY KEY,"
                + CN_IMAGE + " TEXT,"
                + CN_TYPE_1 + " TEXT NOT NULL, "
                + CN_TYPE_2 + " TEXT, "
                + CN_ABILITY_1 +" TEXT NOT NULL, "
                + CN_ABILITY_2 +" TEXT, "
                + CN_HP +" INTEGER NOT NULL, "
                + CN_ATTACK+" INTEGER NOT NULL, "
                +CN_DEFENSE+ " INTEGER NOT NULL, "
                +CN_SPEED+ " INTEGER NOT NULL, "
                +CN_WEIGHT+" INTEGER NOT NULL, "
                +CN_HEIGHT+" INTEGER NOT NULL, "
                +CN_CATCH_RATE+ " INTEGER NOT NULL"
        + ");";

        public static final String PATH_POKEMON_DETAILS = TABLE_NAME;



        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POKEMON_DETAILS;


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POKEMON_DETAILS).build();


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POKEMON_DETAILS;

        public static Uri buildPokemonDetailsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class Evolution  implements BaseColumns{
        public final static String TABLE_NAME ="evolution";
        public final static String CN_TO ="to_pokemon";
        public final static String CN_RESOURCE_URI="resource_uri";
        public final static String CN_PRE_EVOLUTION_NATIONAL_ID="pre_evolution_national_id";

        public final static String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME
                +"("+CN_PRE_EVOLUTION_NATIONAL_ID+" INTEGER, "
                +CN_TO+ " TEXT NOT NULL, "
                +CN_RESOURCE_URI+ " TEXT NOT NULL"
                +");";

        public static final String PATH_POKEMON_EVOLUTION = TABLE_NAME;


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POKEMON_EVOLUTION;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POKEMON_EVOLUTION).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POKEMON_EVOLUTION;

        public static Uri buildPokemonEvolutionUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
    public static final class Move  implements BaseColumns{
        public final static String TABLE_NAME ="move";
        public final static String CN_NAME ="name";
        public final static String CN_LEARN_TYPE="learn_type";
        public final static String CN_ID="id";


        public final static String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME
                +"("+CN_NAME+" TEXT NOT NULL,"
                +CN_LEARN_TYPE+ " TEXT NOT NULL,"
                +CN_ID + " INTEGER PRIMARY KEY"
                +");";

        public static final String PATH_MOVE = TABLE_NAME;


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVE;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVE).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVE;

        public static Uri buildMoveUri(long id) {

            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class Description implements BaseColumns{

        public final static String TABLE_NAME ="description";
        public final static String CN_POKE_ID ="poke_id";
        public final static String CN_ID="id";
        public final static String CN_INFORMATION ="information";

        public final static String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME
                +"("+CN_POKE_ID+" INTEGER NOT NULL,"
                +CN_INFORMATION+ " TEXT NOT NULL,"
                +CN_ID + " INTEGER PRIMARY KEY"
                +");";

        public static final String PATH_DESCRIPTION = TABLE_NAME;


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DESCRIPTION;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DESCRIPTION).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DESCRIPTION;

        public static Uri buildDescriptionUri(long id) {

            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }
    public static final class MoveXPokemon  implements BaseColumns{
        public final static String TABLE_NAME ="move_x_pokemon";
        public final static String CN_NATIONAL_ID ="national_id";
        public final static String CN_MOVE_ID="move_id";
        public final static String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME
                +"("+CN_NATIONAL_ID+" INTEGER,"
                +CN_MOVE_ID+ " INTEGER "
                +");";
        public static final String PATH_MOVE_X_POKEMON =TABLE_NAME ;

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVE_X_POKEMON;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVE_X_POKEMON).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVE_X_POKEMON;

        public static Uri buildPokemonMoveXPokemonUri(long id) {

            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MoveForPokemon {
        public static final String PATH_MOVE_FOR_POKEMON="move_for_pokemon";
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVE_FOR_POKEMON;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVE_FOR_POKEMON).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVE_FOR_POKEMON;

        public static Uri buildMoveForPokemon(long id) {

            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }



}
