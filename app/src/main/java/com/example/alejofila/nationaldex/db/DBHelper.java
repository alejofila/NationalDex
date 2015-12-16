package com.example.alejofila.nationaldex.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    /**
     * Constants
     */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db.sqlite3";
    public static final String TAG = DBHelper.class.getSimpleName();

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context){
        if(instance == null)
            instance = new DBHelper(context.getApplicationContext());
        return instance;

    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating database");
        db.execSQL(PokedexContract.PokemonEntry.CREATE_TABLE);
        db.execSQL(PokedexContract.PokemonDetailsEntry.CREATE_TABLE);
        db.execSQL(PokedexContract.Evolution.CREATE_TABLE);
        db.execSQL(PokedexContract.Move.CREATE_TABLE);
        db.execSQL(PokedexContract.MoveXPokemon.CREATE_TABLE);

    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}