package com.example.alejofila.nationaldex.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.model.Pokemon;
import com.example.alejofila.nationaldex.model.PokemonDetails;
import com.example.alejofila.nationaldex.ui.fragment.PokemonDetailsGeneralFragment;
import com.example.alejofila.nationaldex.ui.fragment.PokemonDetailsMoveFragment;
import com.example.alejofila.nationaldex.ui.fragment.PokemonDetailsStatsFragment;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonDetailsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = PokemonDetailsPagerAdapter.class.getSimpleName();
    private Context mContext;
    private PokemonDetails pokemonDetails;

    public PokemonDetailsPagerAdapter(FragmentManager fm, Context mContext, PokemonDetails pokemonDetails) {
        super(fm);
        this.mContext = mContext;
        this.pokemonDetails =pokemonDetails;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment ;
        switch (position) {
            case 0:
                fragment = PokemonDetailsGeneralFragment.getInstance(pokemonDetails);
                break;
            case 1:
                fragment = PokemonDetailsMoveFragment.getInstance(pokemonDetails);
                break;
            case 2:
                fragment = PokemonDetailsStatsFragment.getInstance(pokemonDetails);
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String pageTitle ;
        switch (position){
            case 0:
                pageTitle = mContext.getString(R.string.title_tab_general);
                break;
            case 1:
                pageTitle = mContext.getString(R.string.title_tab_moves);
                break;
            case 2:
                pageTitle = mContext.getString(R.string.title_tab_stats);
                break;
            default:
                pageTitle = null;
        }
        return pageTitle;

    }
}
