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
import com.example.starwars.Models.AllFilms;
import com.example.starwars.R;
import com.example.starwars.Adapter.SwapiAdapter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class FilmsCategory extends AppCompatActivity {
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    AllFilms allFilms;
    SwapiAdapter adapter;
    ArrayList<AdapterInterface> films;
    int pictureId = 3;

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
            films = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.r_view);
            GridLayoutManager layoutManager = new GridLayoutManager(FilmsCategory.this, 2);
            recyclerView.setLayoutManager(layoutManager);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }

        @Override
        protected void onPostExecute(final ArrayList<AdapterInterface> films) {
            super.onPostExecute(films);
            adapter = new SwapiAdapter(films);
            recyclerView.setAdapter(adapter);

            adapter.setListener(new SwapiAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(FilmsCategory.this, DetailActivityFilms.class);
                    intent.putExtra(DetailActivityFilms.NAME, films.get(position).getName());
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
                Call<AllFilms> call = apiInterface.getAllFilms(i);
                try {
                    allFilms = (AllFilms) call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                films.addAll(allFilms.getResults());
                i++;
            } while (allFilms.getNext() != null);
            return films;
        }
    }
}
