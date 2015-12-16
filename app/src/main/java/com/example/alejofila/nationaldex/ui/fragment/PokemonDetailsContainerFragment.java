package com.example.alejofila.nationaldex.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.adapter.PokemonDetailsPagerAdapter;
import com.example.alejofila.nationaldex.db.CrudManager;
import com.example.alejofila.nationaldex.model.PokemonDetails;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonDetailsContainerFragment extends Fragment{

    private static final String KEY_NATIONAL_ID ="national_id" ;
    public static final String KEY_POKEMON_NAME ="pokemon_name";
    private static final String TAG = PokemonDetailsContainerFragment.class.getSimpleName();
    private PokemonDetails pokeInfo;
    @Bind(value = R.id.details_pager)
    ViewPager pager;
    @Bind(value = R.id.slidingTabs)
    TabLayout tabLayout;

    CrudManager manager;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send_mail) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
            intent.putExtra(Intent.EXTRA_SUBJECT, getActivity().getTitle());
            intent.putExtra(Intent.EXTRA_TEXT,pokeInfo.toString());
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(),getString(R.string.no_email_client),Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokemon_details_container, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        manager = new CrudManager(getActivity().getContentResolver());
        int national_id = getArguments().getInt(KEY_NATIONAL_ID);
        pokeInfo = manager.getPokemonDetails(national_id);
        pokeInfo.setEvolutions(manager.getPokemonEvolutions(national_id));
        pokeInfo.setMoves(manager.getPokemonMoves(national_id));

        PokemonDetailsPagerAdapter adapter = new PokemonDetailsPagerAdapter(getChildFragmentManager(),getContext(),
                pokeInfo);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        getActivity().setTitle("#"+getArguments().getInt(KEY_NATIONAL_ID)+" "+getArguments().getString(KEY_POKEMON_NAME));
        return rootView;
    }
    public static Fragment getInstance(int nationalID,String pokemonName){
        Fragment fragment = new PokemonDetailsContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NATIONAL_ID, nationalID);
        bundle.putString(KEY_POKEMON_NAME,pokemonName);

        fragment.setArguments(bundle);
        return fragment;

    }
}
