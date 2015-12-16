package com.example.alejofila.nationaldex.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.adapter.PokemonAdapter;
import com.example.alejofila.nationaldex.db.CrudManager;
import com.example.alejofila.nationaldex.db.PokedexContract;
import com.example.alejofila.nationaldex.http.WebServiceManager;
import com.example.alejofila.nationaldex.model.Pokemon;
import com.example.alejofila.nationaldex.model.PokemonDeserializer;
import com.example.alejofila.nationaldex.util.DividerItemDecoration;
import com.example.alejofila.nationaldex.util.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class ListPokedexFragment  extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = ListPokedexFragment.class.getSimpleName();

    public static final int LOADER_POKEMON_RESULTS = 300;

    @Bind(value = R.id.list_poke)
    RecyclerView list;

    Handler mHandler;
    CrudManager manager;
    PokemonAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnPokemonClicked = (PokemonAdapter.OnPokemonClicked) context;
        }
        catch (ClassCastException e){
            Log.e(TAG,"The hosting activity does not implemente OnFavClicked");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokedex_list,container,false);
        ButterKnife.bind(this, rootView);
        mHandler = new Handler();
        manager = new CrudManager(getActivity().getContentResolver());
        if(!manager.anyPokemon()){
            requestAllPokemons();
        }
        getLoaderManager().initLoader(LOADER_POKEMON_RESULTS, null, this);
        getActivity().setTitle(R.string.app_name);

        setUpRecyclerView();

        return rootView;
    }



    private void setUpRecyclerView(){
        adapter = new PokemonAdapter(getContext(),mOnPokemonClicked,mOnFavClicked);
        list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        list.setItemAnimator(new ScaleInTopAnimator());
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);

    }
    private void calculateNationalDex(Pokemon[] pokemons){
        for(Pokemon poke : pokemons)
            poke.calculateNationalID();
    }

    private void requestAllPokemons(){
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String resp = response.body().string();
                Gson gson = new GsonBuilder().registerTypeAdapter(Pokemon[].class, new PokemonDeserializer())
                        .create();
                Pokemon[] tmp_pokemons = gson.fromJson(resp, Pokemon[].class);
                calculateNationalDex(tmp_pokemons);
                Arrays.sort(tmp_pokemons);
                final Pokemon[] pokemons = tmp_pokemons.clone();
                manager.inserPokemons(pokemons);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //setUpRecyclerView(pokemons);

                    }
                });

            }
        };
        WebServiceManager.getAllPokemons(callback);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri pokemonUri = PokedexContract.PokemonEntry.CONTENT_URI;
        String[] columns = {PokedexContract.PokemonEntry.CN_NAME,
                PokedexContract.PokemonEntry.CN_NATIONAL_ID,
                PokedexContract.PokemonEntry.CN_IS_FAVORITE
                };
        return new CursorLoader(getContext(), pokemonUri, columns, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Loader Finished callback");
        switch (loader.getId()) {
            case LOADER_POKEMON_RESULTS:
                this.adapter.swapCursor(data);
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG,"Loader Reset Callback");
        switch (loader.getId()) {
            case LOADER_POKEMON_RESULTS:
                this.adapter.swapCursor(null);
                break;
        }

    }
    PokemonAdapter.OnPokemonClicked mOnPokemonClicked;

    PokemonAdapter.OnFavClicked mOnFavClicked = new PokemonAdapter.OnFavClicked() {
        @Override
        public void onFavToggled(int national_id, int newFavorite) {
            manager.toggleFavoritePokemon(national_id,newFavorite);
        }
    };
}
