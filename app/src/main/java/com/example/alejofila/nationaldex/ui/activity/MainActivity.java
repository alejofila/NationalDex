package com.example.alejofila.nationaldex.ui.activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.alejofila.nationaldex.adapter.PokemonAdapter;
import com.example.alejofila.nationaldex.db.CrudManager;
import com.example.alejofila.nationaldex.db.DBHelper;
import com.example.alejofila.nationaldex.http.WebServiceManager;
import com.example.alejofila.nationaldex.model.Move;
import com.example.alejofila.nationaldex.model.PokemonDetails;
import com.example.alejofila.nationaldex.model.SpriteDetail;
import com.example.alejofila.nationaldex.ui.fragment.ListPokedexFragment;
import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.ui.fragment.PokemonDetailsContainerFragment;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonClicked {
    private static final String TAG = MainActivity.class.getSimpleName();
    private CrudManager manager;
    public Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        deleteDatabase(DBHelper.DATABASE_NAME);
        setSupportActionBar(toolbar);
        manager = new CrudManager(getContentResolver());
        if (savedInstanceState == null) {
            setUpHomeFragment();
        }
    }

    private void setUpHomeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, new ListPokedexFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPokemonDetails(int national_id,final String pokemonName) {
        if (!manager.hasPokemonInfo(national_id)) {
            WebServiceManager.getPokemonInfo(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.e(TAG,e.getMessage());
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    PokemonDetails pokemonInfo = gson.fromJson(json, PokemonDetails.class);
                    requestPokemonImage(pokemonInfo, pokemonName);
                }
            }, national_id);
        }
        else
            showDetailsFragmentContainer(national_id,pokemonName);
    }
    private void requestPokemonImage(final PokemonDetails pokeDetails, final String pokemonName) {
        String imgUrl;
        try {
             imgUrl = pokeDetails.getSprites()[0].getResourceUri();
        }
        catch (ArrayIndexOutOfBoundsException e){
            imgUrl=null;
        }
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG,e.getMessage());
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                SpriteDetail spriteInfo = gson.fromJson(json, SpriteDetail.class);
                spriteInfo.setImage(WebServiceManager.BASE_URL + spriteInfo.getImage());
                savePokemonInformation(pokeDetails,spriteInfo);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showDetailsFragmentContainer(pokeDetails.getNational_id(),pokemonName);
                    }
                });
            }
        };
        if(imgUrl != null)
            WebServiceManager.getPokemonImage(callback,imgUrl);
        else{
            savePokemonInformation(pokeDetails, null);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    showDetailsFragmentContainer(pokeDetails.getNational_id(),pokemonName);
                }
            });
        }
    }
    private void savePokemonInformation(PokemonDetails pokeDetails, SpriteDetail spriteInfo){
        manager.insertPokemonDetails(pokeDetails, spriteInfo);
        //Extract Move ID
        for(Move move: pokeDetails.getMoves())
            move.calculateMoveId();
        manager.insertMoves(pokeDetails.getMoves());
        manager.insertEvolutions(pokeDetails.getEvolutions(), pokeDetails.getNational_id());
        manager.insertMovesXPokemon(pokeDetails.getMoves(), pokeDetails.getNational_id());
        }
    private void showDetailsFragmentContainer(int national_id, String pokeName){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container,
                PokemonDetailsContainerFragment.getInstance(national_id,pokeName)).
                addToBackStack(null).commit();
    }

}
