package com.example.alejofila.nationaldex.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.adapter.PokemonAdapter;
import com.example.alejofila.nationaldex.db.CrudManager;
import com.example.alejofila.nationaldex.model.PokemonDetails;
import com.example.alejofila.nationaldex.model.Type;
import com.example.alejofila.nationaldex.util.RegexUtils;
import com.squareup.picasso.Picasso;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonDetailsGeneralFragment extends Fragment {

    private static final String KEY_NATIONAL_ID = "national_id";
    private static final String TAG = PokemonDetailsGeneralFragment.class.getSimpleName();
    private static final String KEY_IMAGE = "image";
    private static final String KEY_TYPE_1 = "type_1";
    private static final String KEY_TYPE_2 = "type_2";
    private static final String KEY_ABILITY_1 = "ability_1";
    private static final String KEY_ABILITY_2 = "ability_2";
    private static final String KEY_EVOLUTION = "evolution";
    private static final String KEY_EVOLTION_RESOURCE_URI = "evolution_resource_uri";

    @Bind(R.id.lst_evolutions)
    ListView listEvolution;
    @Bind(R.id.img_pokemon)
    ImageView imgPokemon;
    @Bind(R.id.type1)
    TextView txtType1;
    @Bind(R.id.type2)
    TextView txtType2;
    @Bind(R.id.txt_ability1)
    TextView txtAbility1;
    @Bind(R.id.txt_ability2)
    TextView txtAbility2;


    // NON UI
    private CrudManager manager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mOnPokemonClicked = (PokemonAdapter.OnPokemonClicked) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "The hosting activity does not implemente OnPokemonClicked");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokedex_details_general, container, false);
        manager = new CrudManager(getActivity().getContentResolver());
        ButterKnife.bind(this, rootView);
        loadUI(getArguments());
        return rootView;

    }

    private void loadUI(Bundle pkmonInfo) {
        String strType1 = pkmonInfo.getString(KEY_TYPE_1);
        String strType2 = pkmonInfo.getString(KEY_TYPE_2, null);
        String ability1 = pkmonInfo.getString(KEY_ABILITY_1);
        String ability2 = pkmonInfo.getString(KEY_ABILITY_2, null);
        String img = pkmonInfo.getString(KEY_IMAGE);

        if (img != null) {
            Picasso.with(getActivity()).
                    load(img)
                    .placeholder(R.drawable.ic_pokemon_place_holder)
                    .into(imgPokemon);
            txtType1.setText(strType1);
        }

        if (strType2 != null)
            txtType2.setText(strType2);
        else
            txtType2.setVisibility(View.GONE);
        colorTypeTxt(strType1, strType2);
        txtAbility1.setText(ability1);
        if (ability2 != null)
            txtAbility2.setText(ability2);

        loadEvolutions(pkmonInfo);

    }

    private void loadEvolutions(Bundle pkmonInfo) {

        final String[] evosName = pkmonInfo.getStringArray(KEY_EVOLUTION);
        final String[] evosResourceUri = pkmonInfo.getStringArray(KEY_EVOLTION_RESOURCE_URI);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getContext(), R.layout.evolution_item, evosName);
        listEvolution.setAdapter(itemsAdapter);

        listEvolution.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnPokemonClicked.onPokemonDetails(RegexUtils.extractNationalId(evosResourceUri[position]), evosName[position]);

            }
        });


    }

    PokemonAdapter.OnPokemonClicked mOnPokemonClicked;

    private void colorTypeTxt(String strType1, String strType2) {

        applyColorToTextView(strType1, txtType1);
        if (strType2 != null)
            applyColorToTextView(strType2, txtType2);

    }

    private void applyColorToTextView(String strType, TextView txtType) {
        switch (strType) {
            case Type.BUG:
                txtType.setBackgroundResource(R.drawable.drawable_type_bug);
                break;
            case Type.DARK:
                txtType.setBackgroundResource(R.drawable.drawable_type_dark);
                break;
            case Type.DRAGON:
                txtType.setBackgroundResource(R.drawable.drawable_type_dragon);
                break;
            case Type.ELECTRIC:
                txtType.setBackgroundResource(R.drawable.drawable_type_electric);
                break;
            case Type.FAIRY:
                txtType.setBackgroundResource(R.drawable.drawable_type_fairy);
                break;
            case Type.FIGHTING:
                txtType.setBackgroundResource(R.drawable.drawable_type_fighting);
                break;
            case Type.FIRE:
                txtType.setBackgroundResource(R.drawable.drawable_type_fire);
                break;
            case Type.FLYING:
                txtType.setBackgroundResource(R.drawable.drawable_type_flying);
                break;
            case Type.GHOST:
                txtType.setBackgroundResource(R.drawable.drawable_type_ghost);
                break;
            case Type.GRASS:
                txtType.setBackgroundResource(R.drawable.drawable_type_grass);
                break;
            case Type.GROUND:
                txtType.setBackgroundResource(R.drawable.drawable_type_ground);
                break;
            case Type.ICE:
                txtType.setBackgroundResource(R.drawable.drawable_type_ice);
                break;
            case Type.NORMAL:
                txtType.setBackgroundResource(R.drawable.drawable_type_normal);
                break;
            case Type.POISON:
                txtType.setBackgroundResource(R.drawable.drawable_type_poison);
                break;
            case Type.PSYCHIC:
                txtType.setBackgroundResource(R.drawable.drawable_type_psychic);
                break;
            case Type.ROCK:
                txtType.setBackgroundResource(R.drawable.drawable_type_rock);
                break;
            case Type.STEEL:
                txtType.setBackgroundResource(R.drawable.drawable_type_steel);
                break;
            case Type.WATER:
                txtType.setBackgroundResource(R.drawable.drawable_type_water);
                break;
        }

    }


    public static Fragment getInstance(PokemonDetails pokemonDetails) {
        Fragment fragment = new PokemonDetailsGeneralFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGE, pokemonDetails.getSprites()[0].getUrlImg());
        bundle.putString(KEY_TYPE_1, pokemonDetails.getTypes()[0].getName());
        bundle.putString(KEY_ABILITY_1, pokemonDetails.getAbilities()[0].getName());
        try {
            bundle.putString(KEY_ABILITY_2, pokemonDetails.getAbilities()[1].getName());
            bundle.putString(KEY_TYPE_2, pokemonDetails.getTypes()[1].getName());
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, e.getMessage());
        }
        String[] evoNames = new String[pokemonDetails.getEvolutions().length];
        String[] evoResourceUri = new String[pokemonDetails.getEvolutions().length];
        Log.e(TAG, "AMOUNT OF EVOS IN THE FRAGMENT" + pokemonDetails.getEvolutions().length);

        for (int i = 0; i < pokemonDetails.getEvolutions().length; i++) {
            evoNames[i] = pokemonDetails.getEvolutions()[i].getTo();
            evoResourceUri[i] = pokemonDetails.getEvolutions()[i].getResourceUri();
        }
        bundle.putStringArray(KEY_EVOLUTION, evoNames);
        bundle.putStringArray(KEY_EVOLTION_RESOURCE_URI, evoResourceUri);


        fragment.setArguments(bundle);
        return fragment;

    }

}
