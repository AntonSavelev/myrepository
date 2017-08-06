package com.example.starwars.Adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.starwars.R;

import java.util.List;


public class SwapiAdapter extends RecyclerView.Adapter<SwapiAdapter.ViewHolder>{


    private List<AdapterInterface> results;
    private Listener listener;
    private int id_picture;
    Drawable drawable;

    public static interface Listener {
        public void onClick(int position);
    }

    public SwapiAdapter(List<AdapterInterface> results) {
        this.results = results;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;



        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public void setPictureId(int id_picture){
        this.id_picture = id_picture;
    }

    @Override
    public SwapiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new SwapiAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(SwapiAdapter.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.image);
        switch(id_picture){
            case 1:
                drawable = cardView.getResources().getDrawable(R.drawable.r2d2);
                break;
            case 2:
                drawable = cardView.getResources().getDrawable(R.drawable.planet_1080);
                break;
            case 3:
                drawable = cardView.getResources().getDrawable(R.drawable.film);
                break;
            case 4:
                drawable = cardView.getResources().getDrawable(R.drawable.species_1080);
                break;
            case 5:
                drawable = cardView.getResources().getDrawable(R.drawable.vehicle_1080);
                break;
            case 6:
                drawable = cardView.getResources().getDrawable(R.drawable.starship_1080);
                break;
        }

        imageView.setImageDrawable(drawable);
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        AdapterInterface people = results.get(position);
        textView.setText(people.getName());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (results == null)
            return 0;
        return results.size();
    }
}

