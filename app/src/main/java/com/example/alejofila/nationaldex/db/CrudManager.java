package com.example.alejofila.nationaldex.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.alejofila.nationaldex.model.Ability;
import com.example.alejofila.nationaldex.model.Evolution;
import com.example.alejofila.nationaldex.model.Move;
import com.example.alejofila.nationaldex.model.Pokemon;
import com.example.alejofila.nationaldex.model.PokemonDetails;
import com.example.alejofila.nationaldex.model.Sprite;
import com.example.alejofila.nationaldex.model.SpriteDetail;
import com.example.alejofila.nationaldex.model.Type;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class CrudManager {

    private static final String TAG = CrudManager.class.getSimpleName();
    private static final Uri POKEMON_URI = PokedexContract.PokemonEntry.CONTENT_URI;
    private static final Uri POKEMON_DETAILS_URI = PokedexContract.PokemonDetailsEntry.CONTENT_URI;
    private static final Uri MOVE_URI = PokedexContract.Move.CONTENT_URI;
    private static final Uri MOVE_X_POKEMON_URI = PokedexContract.MoveXPokemon.CONTENT_URI;
    private static final Uri EVOLUTION_URI = PokedexContract.Evolution.CONTENT_URI;
    private static final Uri MOVE_FOR_POKEMON_URI =PokedexContract.MoveForPokemon.CONTENT_URI;
    ContentResolver contentResolver;

    public CrudManager(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;

    }

    public static ContentValues pokemonToContentValue(Pokemon pokemon) {
        ContentValues cv = new ContentValues();
        cv.put(PokedexContract.PokemonEntry.CN_NAME, pokemon.getName());
        cv.put(PokedexContract.PokemonEntry.CN_RESOURCE_URI, pokemon.getResourceUri());
        cv.put(PokedexContract.PokemonEntry.CN_NATIONAL_ID, pokemon.getNationalID());
        cv.put(PokedexContract.PokemonEntry.CN_IS_FAVORITE, 1);
        return cv;
    }

    private static ContentValues moveToContentValue(Move move) {
        ContentValues cv = new ContentValues();
        cv.put(PokedexContract.Move.CN_NAME, move.getName());
        cv.put(PokedexContract.Move.CN_LEARN_TYPE, move.getLearn_type());
        cv.put(PokedexContract.Move.CN_ID,move.getId());
        return cv;
    }

    public boolean anyPokemon() {
        String[] columns = {"COUNT(*)"};
        Cursor cursor = contentResolver.query(POKEMON_URI, columns, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            Log.e(TAG, "THE CURSOR IS NULL");
        int quantity = cursor.getInt(0);
        cursor.close();
        return !(quantity == 0);
    }

    public void inserPokemons(Pokemon[] pokemons) {
        ContentValues values[] = new ContentValues[pokemons.length];
        for (int i = 0; i < pokemons.length; i++) {
            values[i] = CrudManager.pokemonToContentValue(pokemons[i]);
        }
        try {
            contentResolver.bulkInsert(POKEMON_URI, values);
        } catch (Exception e) {
            Log.d(TAG, "Exception while bulk inserting");
        }

    }


    public void toggleFavoritePokemon(int national_id, int newFavorite) {
        Log.d(TAG, "National id prior to the update " + national_id);
        ContentValues cv = new ContentValues();
        cv.put(PokedexContract.PokemonEntry.CN_IS_FAVORITE, newFavorite);
        String args[] = {"" + national_id};
        contentResolver.update(POKEMON_URI, cv,
                PokedexContract.PokemonEntry.CN_NATIONAL_ID + "=?", args);
    }

    public boolean hasPokemonInfo(int national_id) {
        String[] columns = {"COUNT(*)"};
        String whereClause = PokedexContract.PokemonDetailsEntry.CN_NATIONAL_ID + "=?";
        String[] args = {"" + national_id};
        Cursor cursor = contentResolver.query(PokedexContract.PokemonDetailsEntry.CONTENT_URI, columns, whereClause, args, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            Log.e(TAG, "THE CURSOR IS NULL");
        int quantity = cursor.getInt(0);
        cursor.close();
        return !(quantity == 0);
    }

    public void insertPokemonDetails(PokemonDetails pokeDetails, SpriteDetail spriteInfo) {
        ContentValues cv = new ContentValues();
        cv.put(PokedexContract.PokemonDetailsEntry.CN_ABILITY_1, pokeDetails.getAbilities()[0].getName());
        try {
            cv.put(PokedexContract.PokemonDetailsEntry.CN_ABILITY_2, pokeDetails.getAbilities()[1].getName());
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, e.getMessage());
        }
        cv.put(PokedexContract.PokemonDetailsEntry.CN_ATTACK, pokeDetails.getAttack());
        cv.put(PokedexContract.PokemonDetailsEntry.CN_TYPE_1, pokeDetails.getTypes()[0].getName());
        try {
            cv.put(PokedexContract.PokemonDetailsEntry.CN_TYPE_2, pokeDetails.getTypes()[1].getName());
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, e.getMessage());
        }
        cv.put(PokedexContract.PokemonDetailsEntry.CN_CATCH_RATE, pokeDetails.getCatchRate());
        cv.put(PokedexContract.PokemonDetailsEntry.CN_DEFENSE, pokeDetails.getDefense());
        cv.put(PokedexContract.PokemonDetailsEntry.CN_HP, pokeDetails.getHp());
        cv.put(PokedexContract.PokemonDetailsEntry.CN_SPEED, pokeDetails.getSpeed());
        cv.put(PokedexContract.PokemonDetailsEntry.CN_NATIONAL_ID, pokeDetails.getNational_id());
        cv.put(PokedexContract.PokemonDetailsEntry.CN_HEIGHT, pokeDetails.getHeight());
        cv.put(PokedexContract.PokemonDetailsEntry.CN_WEIGHT, pokeDetails.getWeight());
        if (spriteInfo != null)
            cv.put(PokedexContract.PokemonDetailsEntry.CN_IMAGE, spriteInfo.getImage());

        Uri insertedItem = contentResolver.insert(POKEMON_DETAILS_URI, cv);

        Log.d(TAG, "Inserted row URI" + insertedItem.toString());

    }

    public PokemonDetails getPokemonDetails(int national_id) {

        String[] columns = {"*"};
        String whereClause = PokedexContract.PokemonDetailsEntry.CN_NATIONAL_ID + "=?";
        String[] args = {"" + national_id};
        Cursor cursor = contentResolver.query(PokedexContract.PokemonDetailsEntry.CONTENT_URI, columns, whereClause, args, null);
        return cursorToPokemonDetails(cursor);
    }

    private PokemonDetails cursorToPokemonDetails(Cursor cursor) {
        PokemonDetails pokeDetails = new PokemonDetails();
        Ability[] abilities = new Ability[2];
        cursor.moveToFirst();
        abilities[0] = new Ability(cursor.getString(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_ABILITY_1)));
        abilities[1] = new Ability(cursor.getString(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_ABILITY_2)));
        pokeDetails.setAbilities(abilities);
        pokeDetails.setAttack(cursor.getInt(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_ATTACK)));
        Type[] types = new Type[2];
        types[0] = new Type(cursor.getString(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_TYPE_1)));
        types[1] = new Type(cursor.getString(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_TYPE_2)));
        pokeDetails.setTypes(types);
        pokeDetails.setCatchRate(cursor.getInt(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_CATCH_RATE)));
        pokeDetails.setDefense(cursor.getInt(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_DEFENSE)));
        pokeDetails.setHp(cursor.getInt(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_HP)));
        pokeDetails.setSpeed(cursor.getInt(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_SPEED)));
        pokeDetails.setNational_id(cursor.getInt(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_NATIONAL_ID)));
        pokeDetails.setHeight(cursor.getString(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_HEIGHT)));
        pokeDetails.setWeight(cursor.getString(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_WEIGHT)));
        Sprite[] sprites = new Sprite[1];
        sprites[0] = new Sprite(cursor.getString(cursor.getColumnIndex(PokedexContract.PokemonDetailsEntry.CN_IMAGE)));
        pokeDetails.setSprites(sprites);
        cursor.close();
        return pokeDetails;

    }

    public void insertMoves(Move[] moves) {
        ContentValues values[] = new ContentValues[moves.length];
        for (int i = 0; i < moves.length; i++) {
            values[i] = CrudManager.moveToContentValue(moves[i]);
        }
        try {
            contentResolver.bulkInsert(MOVE_URI, values);
        } catch (Exception e) {
            Log.d(TAG, "Exception while bulk inserting");
        }

    }

    public void insertEvolutions(Evolution[] evolutions, int pre_evolution_national_id) {

        ContentValues values[] = new ContentValues[evolutions.length];
        for (int i = 0; i < evolutions.length; i++) {
            values[i] = evolutionsToContentValue(evolutions[i], pre_evolution_national_id);
        }
        contentResolver.bulkInsert(EVOLUTION_URI, values);
    }

    private ContentValues evolutionsToContentValue(Evolution evolution, int pre_evolution_national_id) {

        ContentValues values = new ContentValues();
        values.put(PokedexContract.Evolution.CN_TO, evolution.getTo());
        values.put(PokedexContract.Evolution.CN_RESOURCE_URI, evolution.getResourceUri());
        values.put(PokedexContract.Evolution.CN_PRE_EVOLUTION_NATIONAL_ID, pre_evolution_national_id);
        return values;
    }

    public Evolution[] getPokemonEvolutions(int pre_evolution_national_id) {
        String[] columns = {"*"};
        String whereClause = PokedexContract.Evolution.CN_PRE_EVOLUTION_NATIONAL_ID + "=?";
        String[] args = {"" + pre_evolution_national_id};
        Cursor cursor = contentResolver.query(PokedexContract.Evolution.CONTENT_URI, columns, whereClause, args, null);
        Evolution[] evos = new Evolution[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            String resourceUri = cursor.getString(cursor.getColumnIndex(PokedexContract.Evolution.CN_RESOURCE_URI));
            String toPokemon = cursor.getString(cursor.getColumnIndex(PokedexContract.Evolution.CN_TO));
            Log.i(TAG, "+++++Resource Uri :" + resourceUri + " toPokemon " + toPokemon);
            evos[i] = new Evolution(toPokemon, resourceUri);
            i++;
        }
        cursor.close();
        return evos;
    }

    public void insertDescriptions(){

    }

    public void insertMovesXPokemon(Move[] moves, int national_id) {
        ContentValues values[] = new ContentValues[moves.length];
        for (int i = 0; i < moves.length; i++) {
            values[i] = CrudManager.moveToContentValue(moves[i],national_id);
        }
        try {
            contentResolver.bulkInsert(MOVE_X_POKEMON_URI, values);
        } catch (Exception e) {
            Log.d(TAG, "Exception while bulk inserting");
        }

    }
    public Move[] getPokemonMoves(int nationalID){
        String[] projection={PokedexContract.Move.CN_NAME};
        String whereClause =PokedexContract.MoveXPokemon.CN_NATIONAL_ID+"=?";
        String[] args={""+nationalID};
        Cursor cursor = contentResolver.query(MOVE_FOR_POKEMON_URI,projection,whereClause,args,null);
        Move[] moves = new Move[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()){
            Log.d(TAG,"MOVE NAME: "+cursor.getString(0));
            moves[i] = new Move(cursor.getString(0));
            i++;
        }
        return moves;

    }

    /**
     *  This is for the MoveXPokemon
     * @param move
     * @param national_id
     * @return
     */

    private static ContentValues moveToContentValue(Move move, int national_id) {
        ContentValues cv = new ContentValues();
        cv.put(PokedexContract.MoveXPokemon.CN_MOVE_ID, move.getId());
        cv.put(PokedexContract.MoveXPokemon.CN_NATIONAL_ID,national_id);
        return cv;
    }
}
