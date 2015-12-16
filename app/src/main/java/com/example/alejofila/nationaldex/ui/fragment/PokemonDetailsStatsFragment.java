package com.example.alejofila.nationaldex.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.model.PokemonDetails;

import java.text.AttributedCharacterIterator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonDetailsStatsFragment  extends Fragment{

    private static final String KEY_STAT_HP ="stat_hp";
    private static final String KEY_STAT_ATTACK = "stat_attack";
    private static final String KEY_STAT_DEFENSE = "stat_defense";
    private static final String KEY_STAT_SPEED = "stat_speed";
    private static final String KEY_STAT_CATCH_RATE = "stat_catch_rate";
    private static final String KEY_STAT_WEIGHT = "stat_weight";
    private static final String KEY_STAT_HEIGHT = "stat_height";

    @Bind(value = R.id.txt_hp)
    TextView txtHP;
    @Bind(value = R.id.txt_attack)
    TextView txtAttack;
    @Bind(value = R.id.txt_catch_rate)
    TextView txtCatchRate;
    @Bind(value = R.id.txt_defense)
    TextView txtDefense;
    @Bind(value = R.id.txt_height)
    TextView txtHeight;
    @Bind(value = R.id.txt_weight)
    TextView txtWeight;
    @Bind(value = R.id.txt_speed)
    TextView txtSpeed;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokedex_details_stats,container,false);
        ButterKnife.bind(this,rootView);
        loadUI(getArguments());
        return rootView;

    }

    private void loadUI(Bundle arguments) {

        txtAttack.setText(""+arguments.getInt(KEY_STAT_ATTACK));
        txtDefense.setText(""+(arguments.getInt(KEY_STAT_DEFENSE)));
        txtHeight.setText(arguments.getString(KEY_STAT_HEIGHT));
        txtHP.setText(""+arguments.getInt(KEY_STAT_HP));
        txtSpeed.setText(""+arguments.getInt(KEY_STAT_SPEED));
        txtWeight.setText(arguments.getString(KEY_STAT_WEIGHT));
        txtCatchRate.setText(""+arguments.getInt(KEY_STAT_CATCH_RATE));

    }

    public static Fragment getInstance(PokemonDetails pkmonDetails){
        Bundle info = new Bundle();
        Fragment fragment = new PokemonDetailsStatsFragment();
        info.putInt(KEY_STAT_HP,pkmonDetails.getHp());
        info.putInt(KEY_STAT_ATTACK, pkmonDetails.getAttack());
        info.putInt(KEY_STAT_DEFENSE,pkmonDetails.getDefense());
        info.putInt(KEY_STAT_SPEED, pkmonDetails.getSpeed());
        info.putInt(KEY_STAT_CATCH_RATE, pkmonDetails.getCatchRate());
        info.putString(KEY_STAT_WEIGHT, pkmonDetails.getWeight());
        info.putString(KEY_STAT_HEIGHT, pkmonDetails.getHeight());
        fragment.setArguments(info);
        return fragment;

    }
}
