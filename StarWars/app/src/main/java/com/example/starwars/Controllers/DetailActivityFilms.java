package com.example.starwars.Controllers;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starwars.Adapter.ApiClient;
import com.example.starwars.Adapter.ApiInterface;
import com.example.starwars.Models.AllFilms;
import com.example.starwars.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivityFilms extends AppCompatActivity {

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

        apiInterface.getFilmSearch(searchName).enqueue(new Callback<AllFilms>() {
            @Override
            public void onResponse(Call<AllFilms> call, Response<AllFilms> response) {
                AllFilms allFilms = (AllFilms) response.body();

                tw_name.setText(allFilms.getResults().get(0).getName());
                tw_info.setText(allFilms.getResults().get(0).toString());
//                Drawable drawable = getResources().getDrawable(R.drawable.film);
//                imageView.setImageDrawable(drawable);
                Picasso.with(DetailActivityFilms.this).load("https://timedotcom.files.wordpress.com/2016/12/star-wars-lead-2.jpg?w=720&quality=85").into(imageView);
            }

            @Override
            public void onFailure(Call<AllFilms> call, Throwable t) {
                Toast.makeText(DetailActivityFilms.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
