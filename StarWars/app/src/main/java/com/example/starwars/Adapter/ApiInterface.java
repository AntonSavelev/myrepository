package com.example.starwars.Adapter;

import com.example.starwars.Models.AllFilms;
import com.example.starwars.Models.AllPeople;
import com.example.starwars.Models.AllPlanet;
import com.example.starwars.Models.AllSpecies;
import com.example.starwars.Models.AllStarships;
import com.example.starwars.Models.AllVehicles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("people/")
    Call<AllPeople> getAllPeoples(@Query("page") int page);

    @GET("people/")
    Call<AllPeople> getPeopleSearch(@Query("search") String searchName);

    @GET("planets/")
    Call<AllPlanet> getAllPlanets(@Query("page") int page);

    @GET("planets/")
    Call<AllPlanet> getPlanetSearch(@Query("search") String searchName);

    @GET("films/")
    Call<AllFilms> getAllFilms(@Query("page") int page);

    @GET("films/")
    Call<AllFilms> getFilmSearch(@Query("search") String searchName);

    @GET("species/")
    Call<AllSpecies> getAllSpecies(@Query("page") int page);

    @GET("species/")
    Call<AllSpecies> getSpeciesSearch(@Query("search") String searchName);

    @GET("vehicles/")
    Call<AllVehicles> getAllVehicles(@Query("page") int page);

    @GET("vehicles/")
    Call<AllVehicles> getVehicleSearch(@Query("search") String searchName);

    @GET("starships/")
    Call<AllStarships> getAllStarships(@Query("page") int page);

    @GET("starships/")
    Call<AllStarships> getStarshipSearch(@Query("search") String searchName);
}