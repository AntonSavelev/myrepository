package com.example.starwars.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.starwars.Adapter.AdapterInterface;
import com.example.starwars.Adapter.ApiClient;
import com.example.starwars.Adapter.ApiInterface;
import com.example.starwars.Models.AllSpecies;
import com.example.starwars.R;
import com.example.starwars.Adapter.SwapiAdapter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class SpeciesCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiInterface apiInterface;
    AllSpecies allSpecies;
    SwapiAdapter adapter;
    ArrayList<AdapterInterface> species;
    int pictureId = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        new MyAsyncTask().execute();

    }

    private class MyAsyncTask extends AsyncTask<Void, Void, ArrayList<AdapterInterface>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            species = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.r_view);
            GridLayoutManager layoutManager = new GridLayoutManager(SpeciesCategory.this, 2);
            recyclerView.setLayoutManager(layoutManager);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }

        @Override
        protected void onPostExecute(final ArrayList<AdapterInterface> species) {
            super.onPostExecute(species);
            adapter = new SwapiAdapter(species);
            recyclerView.setAdapter(adapter);


            adapter.setListener(new SwapiAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(SpeciesCategory.this, DetailActivitySpecies.class);
                    intent.putExtra(DetailActivitySpecies.NAME, species.get(position).getName());
                    startActivity(intent);
                }
            });
            adapter.setPictureId(pictureId);

            recyclerView.getAdapter().notifyDataSetChanged();

        }

        @Override
        protected ArrayList<AdapterInterface> doInBackground(Void... params) {

            int i = 1;
            do {
                Call<AllSpecies> call = apiInterface.getAllSpecies(i);
                try {
                    allSpecies = (AllSpecies) call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                species.addAll(allSpecies.getResults());
                i++;
            } while (allSpecies.getNext() != null);
            return species;
        }
    }
}
