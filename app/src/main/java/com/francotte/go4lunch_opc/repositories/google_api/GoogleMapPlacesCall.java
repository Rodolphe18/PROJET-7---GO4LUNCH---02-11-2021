package com.francotte.go4lunch_opc.repositories.google_api;


import android.location.Location;
import android.util.Log;

import androidx.annotation.Nullable;


import com.francotte.go4lunch_opc.models.NearbySearch.ResultsPlaces;
import com.francotte.go4lunch_opc.models.PlaceAutoComplete.PlaceAutoComplete;
import com.francotte.go4lunch_opc.models.PlaceDetails.PlaceDetails;
import com.francotte.go4lunch_opc.models.User;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleMapPlacesCall {


    public static final String API_KEY = "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA";


    /**
     * --- CALLBACK ---
     */
    public interface CallbacksFetchNearbyPlace {
        void onResponseFetchNearbyPlace(@Nullable ResultsPlaces places);

        void onFailureFetchNearbyPlace(Throwable t);
    }

    public interface GetDetailOfPlaceCallbacks {
        void onResponseGetDetailOfPlace(PlaceDetails result);

        void onFailureGetDetailOfPlace(Throwable t);
    }

    public interface GetAllPredictionOfSearchPlace {
        void onResponseGetAllPredictionsOfSearchPlace(PlaceAutoComplete result, String input);

        void onFailureGetAllPredictionsOfSearchPlace(Throwable t);
    }

    /**
     * --- METHOD ---
     */
    public static void fetchNearbyPlaces(CallbacksFetchNearbyPlace callbacksFetchNearbyPlace, String location) {

        GoogleMapPlacesService service = GoogleMapPlacesService.retrofitGetGoogleMapPlaces.create(GoogleMapPlacesService.class);

        Call<ResultsPlaces> call = service.getGoogleMapPlaces(location, "restaurant", "1300", API_KEY);

        call.enqueue(new Callback<ResultsPlaces>() {
            @Override
            public void onResponse(@NotNull Call<ResultsPlaces> call, @NotNull Response<ResultsPlaces> response) {
                callbacksFetchNearbyPlace.onResponseFetchNearbyPlace(response.body());
            }


            @Override
            public void onFailure(@NotNull Call<ResultsPlaces> call, @NotNull Throwable t) {
                callbacksFetchNearbyPlace.onFailureFetchNearbyPlace(t);
            }
        });
    }

    public static void getDetailOfAPlace(final GetDetailOfPlaceCallbacks callbacksGetDetailOfAPlace, String place_id) {
        GoogleMapPlacesService service = GoogleMapPlacesService.retrofitGetPlaceDetails.create(GoogleMapPlacesService.class);

        Call<PlaceDetails> call = service.getPlaceDetails(place_id, API_KEY);
        call.enqueue(new Callback<PlaceDetails>() {
            @Override
            public void onResponse(@NotNull Call<PlaceDetails> call, @NotNull Response<PlaceDetails> response) {
                callbacksGetDetailOfAPlace.onResponseGetDetailOfPlace(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<PlaceDetails> call, @NotNull Throwable t) {
                callbacksGetDetailOfAPlace.onFailureGetDetailOfPlace(t);
            }
        });
    }

    public static void getAllPredictionOfSearchPlace(final GetAllPredictionOfSearchPlace callbacksGetAllPredictions, final String input, final Location location) {
        GoogleMapPlacesService service = GoogleMapPlacesService.retrofitGetPlaceAutoComplete.create(GoogleMapPlacesService.class);
        Call<PlaceAutoComplete> call = service.getPlaceAutoComplete(input, "establishment", location.getLatitude() + "," + location.getLongitude(), "1300", API_KEY);
        call.enqueue(new Callback<PlaceAutoComplete>() {
            @Override
            public void onResponse(@NotNull Call<PlaceAutoComplete> call, @NotNull Response<PlaceAutoComplete> response) {
                callbacksGetAllPredictions.onResponseGetAllPredictionsOfSearchPlace(response.body(), input);
            }

            @Override
            public void onFailure(@NotNull Call<PlaceAutoComplete> call, @NotNull Throwable t) {
                callbacksGetAllPredictions.onFailureGetAllPredictionsOfSearchPlace(t);
            }
        });
    }
}

