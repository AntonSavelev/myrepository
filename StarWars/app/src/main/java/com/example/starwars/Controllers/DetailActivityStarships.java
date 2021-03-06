package com.example.starwars.Controllers;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starwars.Adapter.ApiClient;
import com.example.starwars.Adapter.ApiInterface;
import com.example.starwars.Models.AllStarships;
import com.example.starwars.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivityStarships extends AppCompatActivity {

    public static final String NAME = "name";
    ApiInterface apiInterface;
    TextView tw_name;
    TextView tw_info;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String searchName = (String) getIntent().getExtras().get(NAME);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        tw_name = (TextView) findViewById(R.id.name);
        tw_info = (TextView) findViewById(R.id.info);
        imageView = (ImageView) findViewById(R.id.detail_image);

        apiInterface.getStarshipSearch(searchName).enqueue(new Callback<AllStarships>() {
            @Override
            public void onResponse(Call<AllStarships> call, Response<AllStarships> response) {
                AllStarships allStarships = (AllStarships) response.body();

                tw_name.setText(allStarships.getResults().get(0).getName());
                tw_info.setText(allStarships.getResults().get(0).toString());
                Drawable drawable = getResources().getDrawable(R.drawable.starship_1080);
                imageView.setImageDrawable(drawable);
            }

            @Override
            public void onFailure(Call<AllStarships> call, Throwable t) {
                Toast.makeText(DetailActivityStarships.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
