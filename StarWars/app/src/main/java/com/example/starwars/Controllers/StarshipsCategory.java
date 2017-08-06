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
import com.example.starwars.Models.AllStarships;
import com.example.starwars.R;
import com.example.starwars.Adapter.SwapiAdapter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class StarshipsCategory extends AppCompatActivity {
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    AllStarships allStarships;
    SwapiAdapter adapter;
    ArrayList<AdapterInterface> starships;
    int pictureId = 6;

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
            starships = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.r_view);
            GridLayoutManager layoutManager = new GridLayoutManager(StarshipsCategory.this, 2);
            recyclerView.setLayoutManager(layoutManager);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }

        @Override
        protected void onPostExecute(final ArrayList<AdapterInterface> starships) {
            super.onPostExecute(starships);
            adapter = new SwapiAdapter(starships);
            recyclerView.setAdapter(adapter);


            adapter.setListener(new SwapiAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(StarshipsCategory.this, DetailActivityStarships.class);
                    intent.putExtra(DetailActivityStarships.NAME, starships.get(position).getName());
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
                Call<AllStarships> call = apiInterface.getAllStarships(i);
                try {
                    allStarships = (AllStarships) call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                starships.addAll(allStarships.getResults());
                i++;
            } while (allStarships.getNext() != null);
            return starships;
        }
    }


}
