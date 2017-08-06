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
import com.example.starwars.Models.AllVehicles;
import com.example.starwars.R;
import com.example.starwars.Adapter.SwapiAdapter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class VehiclesCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiInterface apiInterface;
    AllVehicles allVehicles;
    SwapiAdapter adapter;
    ArrayList<AdapterInterface> vehicles;
    int pictureId = 5;

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
            vehicles = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.r_view);
            GridLayoutManager layoutManager = new GridLayoutManager(VehiclesCategory.this, 2);
            recyclerView.setLayoutManager(layoutManager);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }

        @Override
        protected void onPostExecute(final ArrayList<AdapterInterface> vehicles) {
            super.onPostExecute(vehicles);
            adapter = new SwapiAdapter(vehicles);
            recyclerView.setAdapter(adapter);


            adapter.setListener(new SwapiAdapter.Listener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(VehiclesCategory.this, DetailActivityVehicles.class);
                    intent.putExtra(DetailActivityVehicles.NAME, vehicles.get(position).getName());
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
                Call<AllVehicles> call = apiInterface.getAllVehicles(i);
                try {
                    allVehicles = (AllVehicles) call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vehicles.addAll(allVehicles.getResults());
                i++;
            } while (allVehicles.getNext() != null);
            return vehicles;
        }
    }

}
