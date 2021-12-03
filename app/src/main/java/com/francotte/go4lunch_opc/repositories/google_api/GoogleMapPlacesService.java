package com.francotte.go4lunch_opc.repositories.google_api;


import com.francotte.go4lunch_opc.models.NearbySearch.ResultsPlaces;
import com.francotte.go4lunch_opc.models.PlaceAutoComplete.PlaceAutoComplete;
import com.francotte.go4lunch_opc.models.PlaceDetails.PlaceDetails;
import com.francotte.go4lunch_opc.utils.Utility;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GoogleMapPlacesService {


    @GET("maps/api/place/nearbysearch/json")
    Call<ResultsPlaces> getGoogleMapPlaces(@Query("location") String location, @Query("type") String type, @Query("radius") String radius, @Query("key") String API_KEY);

    Retrofit retrofitGetGoogleMapPlaces = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .client(Utility.getClientHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("maps/api/place/details/json")
    Call<PlaceDetails> getPlaceDetails(@Query("place_id") String place_id, @Query("key") String API_KEY);

    Retrofit retrofitGetPlaceDetails = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .client(Utility.getClientHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("maps/api/place/autocomplete/json")
    Call<PlaceAutoComplete> getPlaceAutoComplete(@Query("input") String input, @Query("type") String type, @Query("key") String API_KEY);

    Retrofit retrofitGetPlaceAutoComplete = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
