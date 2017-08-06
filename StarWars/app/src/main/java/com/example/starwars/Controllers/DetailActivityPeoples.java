package com.example.starwars.Controllers;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starwars.Adapter.ApiClient;
import com.example.starwars.Adapter.ApiInterface;
import com.example.starwars.Models.AllPeople;
import com.example.starwars.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivityPeoples extends AppCompatActivity {

    public static final String NAME = "name";
    ApiInterface apiInterface;
    TextView tw_name;
    TextView tw_info;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String searchName = (String) getIntent().getExtras().get(NAME);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        tw_name = (TextView) findViewById(R.id.name);
        tw_info = (TextView) findViewById(R.id.info);
        imageView = (ImageView) findViewById(R.id.detail_image);

        apiInterface.getPeopleSearch(searchName).enqueue(new Callback<AllPeople>() {
            @Override
            public void onResponse(Call<AllPeople> call, Response<AllPeople> response) {
                AllPeople allPeople = (AllPeople) response.body();

                tw_name.setText(allPeople.getResults().get(0).getName());
                tw_info.setText(allPeople.getResults().get(0).toString());
                Drawable drawable = getResources().getDrawable(R.drawable.r2d2);
                imageView.setImageDrawable(drawable);
            }

            @Override
            public void onFailure(Call<AllPeople> call, Throwable t) {
                Toast.makeText(DetailActivityPeoples.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}