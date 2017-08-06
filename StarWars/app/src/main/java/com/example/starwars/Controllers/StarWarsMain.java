package com.example.starwars.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.starwars.R;

public class StarWarsMain extends AppCompatActivity {

    ListView listView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_wars_main);

        listView = (ListView) findViewById(R.id.root_list);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent = new Intent(StarWarsMain.this, PeoplesCategory.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(StarWarsMain.this, PlanetsCategory.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(StarWarsMain.this, FilmsCategory.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(StarWarsMain.this, SpeciesCategory.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(StarWarsMain.this, VehiclesCategory.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(StarWarsMain.this, StarshipsCategory.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }
}
