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
import com.example.starwars.Models.AllPeople;
import com.example.starwars.R;
import com.example.starwars.Adapter.SwapiAdapter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class PeoplesCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiInterface apiInterface;
    AllPeople allPeople;
    SwapiAdapter adapter;
    ArrayList<AdapterInterface> peoples;
    int pictureId = 1;

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
            peoples = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.r_view);
            GridLayoutManager layoutManager = new GridLayoutManager(PeoplesCategory.this, 2);
            recyclerView.setLayoutManager(layoutManager);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }

        @Override
        protected void onPostExecute(final ArrayList<AdapterInterface> peoples) {
            super.onPostExecute(peoples);
            adapter = new SwapiAdapter(peoples);
            recyclerView.setAdapter(adapter);

            adapter.setListener(new SwapiAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(PeoplesCategory.this, DetailActivityPeoples.class);
                    intent.putExtra(DetailActivityPeoples.NAME, peoples.get(position).getName());
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
                Call<AllPeople> call = apiInterface.getAllPeoples(i);
                try {
                    allPeople = (AllPeople) call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                peoples.addAll(allPeople.getResults());
                i++;
            } while (allPeople.getNext() != null);
            return peoples;
        }
    }

}
