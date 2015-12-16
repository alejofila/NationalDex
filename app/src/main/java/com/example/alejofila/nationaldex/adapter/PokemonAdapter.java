package com.example.alejofila.nationaldex.adapter;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejofila.nationaldex.R;
import com.example.alejofila.nationaldex.db.PokedexContract;
import com.example.alejofila.nationaldex.model.Pokemon;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 14/12/2015.
 */
public class PokemonAdapter  extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    private static final String TAG = PokemonAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;
    private OnPokemonClicked mOnPokemonClicked;
    private OnFavClicked mOnFavClicked;
    public PokemonAdapter (Context mContext, OnPokemonClicked mOnPokemonClicked, OnFavClicked mOnFavClicked){
        this.mOnPokemonClicked = mOnPokemonClicked;
        this.mOnFavClicked = mOnFavClicked;
        this.mContext = mContext;
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new PokemonViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindData(mCursor);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null) return 0;
        return mCursor.getCount();
    }
    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.card_holder)
        CardView cardHolder;

        @Bind(R.id.lst_name)
        TextView txtName;
        @Bind(R.id.lst_number)
        TextView txtID;
        @Bind(R.id.lst_fav)
        ImageView imgFav;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgFav.setOnClickListener(this);
            cardHolder.setOnClickListener(this);
        }
        public void bindData(Cursor mCursor){
            txtName.setText(mCursor.getString(mCursor.getColumnIndex(PokedexContract.PokemonEntry.CN_NAME)));
            txtID.setText("" + mCursor.getInt(mCursor.getColumnIndex(PokedexContract.PokemonEntry.CN_NATIONAL_ID)));
            if(mCursor.getInt(mCursor.getColumnIndex(PokedexContract.PokemonEntry.CN_IS_FAVORITE)) == 0)
                imgFav.setImageResource(R.drawable.ic_action_fav_true);
            else
                imgFav.setImageResource(R.drawable.ic_action_fav_false);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            Log.d(TAG, "Pokemon click detected");


            switch (v.getId()){
                case R.id.card_holder:
                    pokemonClicked();
                    break;
                case R.id.lst_fav:
                    favToggle();
                    break;
            }
        }

        private void pokemonClicked() {
            Log.d(TAG,"Pokemon click detected");
            int national_id = mCursor.getInt(mCursor.getColumnIndex(PokedexContract.PokemonEntry.CN_NATIONAL_ID));
            mOnPokemonClicked.onPokemonDetails(national_id,
                    mCursor.getString(mCursor.getColumnIndex(PokedexContract.PokemonEntry.CN_NAME)));
        }

        private void favToggle() {
            int national_id = mCursor.getInt(mCursor.getColumnIndex(PokedexContract.PokemonEntry.CN_NATIONAL_ID));
            if(mCursor.getInt(mCursor.getColumnIndex(PokedexContract.PokemonEntry.CN_IS_FAVORITE)) == 0)
                mOnFavClicked.onFavToggled(national_id,1);
            else
                mOnFavClicked.onFavToggled(national_id,0);

        }
    }



    public void swapCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();
    }
    public Cursor getCursor(){
        return mCursor;
    }


    public static interface OnFavClicked{
        /**
         *
         * @param national_id the national_id column value
         * @param newFavorite 0 if is going to be set as favorite 1 otherwise
         */
        void onFavToggled(int national_id, int newFavorite);
    }
    public static interface OnPokemonClicked{
        /**
         *
         * @param national_id the national_id column value
         */
        void onPokemonDetails(int national_id, String pokemonName);
    }


}
