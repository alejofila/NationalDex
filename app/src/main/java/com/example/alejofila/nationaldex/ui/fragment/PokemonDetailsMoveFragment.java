package com.example.alejofila.nationaldex.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.model.Move;
import com.example.alejofila.nationaldex.model.PokemonDetails;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonDetailsMoveFragment extends Fragment {

    private static final String KEY_POKEMON_MOVES = "pokemon_moves";
    private static final String TAG = PokemonDetailsMoveFragment.class.getSimpleName() ;

    @Bind(value= R.id.lst_moves)
    ListView listMoves;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokedex_details_moves,container,false);
        ButterKnife.bind(this,rootView);
        loadMoves(getArguments());
        return rootView;
    }

    private void loadMoves(Bundle arguments) {
        String[] moves = arguments.getStringArray(KEY_POKEMON_MOVES);
        ArrayAdapter<String> movesAdapter =
                new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, moves);
        listMoves.setAdapter(movesAdapter);
    }

    public static Fragment getInstance(PokemonDetails pokemonDetails) {
        Fragment fragment = new PokemonDetailsMoveFragment();
        Bundle info = new Bundle();
        String[] moves = new String[pokemonDetails.getMoves().length];
        for(int i=0;i<pokemonDetails.getMoves().length;i++) {
            String moveName =  pokemonDetails.getMoves()[i].getName();
            Log.d(TAG,"Move name in Move Fragment"+ moveName);
            moves[i] = moveName;
        }
        info.putStringArray(KEY_POKEMON_MOVES,moves);
        fragment.setArguments(info);
        return fragment;


    }
}
