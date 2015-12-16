package com.example.alejofila.nationaldex.db;

/**
 * Created by Alejandro on 14/12/2015.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;


public class PokedexContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = PokedexContentProvider.class.getSimpleName();
    private DBHelper dbHelper;

    private static final int MOVE_FOR_POKEMON_ITEM =900 ;
    static final int POKEMON_ITEM = 100;
    static final int POKEMON_LIST = 101;
    static final int EVOLUTION_ITEM = 200;
    static final int EVOLUTION_LIST = 201;
    static final int MOVE_ITEM = 300;
    static final int MOVE_LIST = 301;
    static final int MOVES_X_POKEMON_ITEM = 400;
    static final int MOVES_X_POKEMON_LIST = 401;
    static final int DESCRIPTION_ITEM = 700;
    static final int DESCRIPTION_LIST = 701;
    static final int POKEMON_DETAILS_ITEM = 601;
    static final int POKEMON_DETAILS_LIST = 602;


    @Override
    public boolean onCreate() {
        dbHelper = DBHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case POKEMON_LIST:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.PokemonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case POKEMON_ITEM:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.PokemonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case POKEMON_DETAILS_ITEM:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.PokemonDetailsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case POKEMON_DETAILS_LIST:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.PokemonDetailsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case EVOLUTION_LIST:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.Evolution.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case EVOLUTION_ITEM:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.Evolution.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case DESCRIPTION_ITEM:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.Description.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case DESCRIPTION_LIST:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.Description.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVES_X_POKEMON_ITEM:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.MoveXPokemon.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVES_X_POKEMON_LIST:
                retCursor = dbHelper.getReadableDatabase().query(
                        PokedexContract.MoveXPokemon.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVE_FOR_POKEMON_ITEM:
                String tables=PokedexContract.Move.TABLE_NAME+" t1 INNER JOIN "
                        +PokedexContract.MoveXPokemon.TABLE_NAME+" t2 ON "
                        +"t1."+PokedexContract.Move.CN_ID+"=t2."+PokedexContract.MoveXPokemon.CN_MOVE_ID;
                retCursor = dbHelper.getReadableDatabase().query(
                        tables,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case POKEMON_LIST:
                return PokedexContract.PokemonEntry.CONTENT_TYPE;
            case POKEMON_ITEM:
                return PokedexContract.PokemonEntry.CONTENT_ITEM_TYPE;
            case MOVE_LIST:
                return PokedexContract.Move.CONTENT_TYPE;
            case MOVE_ITEM:
                return PokedexContract.Move.CONTENT_ITEM_TYPE;
            case EVOLUTION_ITEM:
                return PokedexContract.Evolution.CONTENT_ITEM_TYPE;
            case EVOLUTION_LIST:
                return PokedexContract.Evolution.CONTENT_TYPE;
            case POKEMON_DETAILS_ITEM:
                return PokedexContract.PokemonDetailsEntry.CONTENT_ITEM_TYPE;
            case POKEMON_DETAILS_LIST:
                return PokedexContract.PokemonDetailsEntry.CONTENT_TYPE;
            case DESCRIPTION_LIST:
                return PokedexContract.Description.CONTENT_TYPE;
            case DESCRIPTION_ITEM:
                return PokedexContract.Description.CONTENT_ITEM_TYPE;
            case MOVES_X_POKEMON_ITEM:
                return PokedexContract.MoveXPokemon.CONTENT_ITEM_TYPE;
            case MOVES_X_POKEMON_LIST:
                return PokedexContract.MoveXPokemon.CONTENT_TYPE;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case POKEMON_ITEM:
                long _id = db.insert(PokedexContract.PokemonEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = PokedexContract.PokemonEntry.buildPokemonUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case POKEMON_DETAILS_ITEM:
                long id = db.insert(PokedexContract.PokemonDetailsEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = PokedexContract.PokemonDetailsEntry.buildPokemonDetailsUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            case MOVE_ITEM:
                long id2 = db.insert(PokedexContract.Move.TABLE_NAME, null, values);
                if (id2 > 0)
                    returnUri = PokedexContract.Move.buildMoveUri(id2);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case EVOLUTION_ITEM:
                long id3 = db.insert(PokedexContract.Evolution.TABLE_NAME, null, values);
                if (id3 > 0)
                    returnUri = PokedexContract.Evolution.buildPokemonEvolutionUri(id3);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case DESCRIPTION_ITEM:
                long id4 = db.insert(PokedexContract.Description.TABLE_NAME, null, values);
                if (id4 > 0)
                    returnUri = PokedexContract.Description.buildDescriptionUri(id4);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            case MOVES_X_POKEMON_ITEM:
                long id5 = db.insert(PokedexContract.MoveXPokemon.TABLE_NAME, null, values);
                if (id5 > 0)
                    returnUri = PokedexContract.MoveXPokemon.buildPokemonMoveXPokemonUri(id5);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;


        }

        Log.d(TAG, "URI INSERTED " + returnUri.toString());
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case POKEMON_ITEM:
                rowsDeleted = db.delete(
                        PokedexContract.PokemonEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case POKEMON_ITEM:
                rowsUpdated = db.update(PokedexContract.PokemonEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                Log.d(TAG, "************ ROWS UPDATE" + rowsUpdated);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case POKEMON_ITEM:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PokedexContract.PokemonEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case MOVE_ITEM:
                db.beginTransaction();
                int returnCount2 = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PokedexContract.Move.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount2++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount2;

            case DESCRIPTION_ITEM:
                db.beginTransaction();
                int returnCount3 = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PokedexContract.Description.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount3++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount3;
            case MOVES_X_POKEMON_ITEM:
                db.beginTransaction();
                int returnCount4 = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(PokedexContract.MoveXPokemon.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount4++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount4;
            default:
                return super.bulkInsert(uri, values);
        }

    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PokedexContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PokedexContract.PokemonEntry.PATH_POKEMON, POKEMON_ITEM);
        matcher.addURI(authority, PokedexContract.PokemonDetailsEntry.PATH_POKEMON_DETAILS, POKEMON_DETAILS_ITEM);
        matcher.addURI(authority, PokedexContract.Move.PATH_MOVE, MOVE_ITEM);
        matcher.addURI(authority, PokedexContract.Evolution.PATH_POKEMON_EVOLUTION, EVOLUTION_ITEM);
        matcher.addURI(authority, PokedexContract.MoveXPokemon.PATH_MOVE_X_POKEMON, MOVES_X_POKEMON_ITEM);
        matcher.addURI(authority, PokedexContract.Description.PATH_DESCRIPTION, DESCRIPTION_ITEM);
        matcher.addURI(authority, PokedexContract.MoveForPokemon.PATH_MOVE_FOR_POKEMON, MOVE_FOR_POKEMON_ITEM);

        return matcher;
    }
}
