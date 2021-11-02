package service;


import androidx.annotation.Nullable;

import com.francotte.go4lunch_opc.BuildConfig;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.models.NEARBYSEARCH.GoogleMapPlace;
import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Result;
import com.francotte.go4lunch_opc.models.PLACEDETAILS.PlaceDetails;
import com.francotte.go4lunch_opc.viewmodel.Utility;
import com.google.android.gms.common.api.internal.ApiKey;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapPlacesCall {


    public static final String API_KEY = "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA";

    /** --- CALLBACK --- */
    public interface CallbacksFetchNearbyPlace {
        void onResponse(@Nullable GoogleMapPlace places);
        void onFailure(Throwable t);
    }
    public interface GetDetailOfPlaceCallbacks {
        void onResponseGetDetailOfPlace (PlaceDetails result);
        void onFailureGetDetailOfPlace (Throwable t);
    }

    /** --- METHOD --- */
    public static void fetchNearbyPlaces (CallbacksFetchNearbyPlace callbacksFetchNearbyPlace, String location){
        final WeakReference<CallbacksFetchNearbyPlace> cbRef = new WeakReference<>(callbacksFetchNearbyPlace);

        GoogleMapPlacesService service = GoogleMapPlacesService.retrofitGetGoogleMapPlaces.create(GoogleMapPlacesService.class);

        Call<GoogleMapPlace> call = service.getGoogleMapPlaces(location, "restaurant", "1000", API_KEY);

        call.enqueue(new Callback<GoogleMapPlace>() {
            @Override
            public void onResponse(@NotNull Call<GoogleMapPlace> call, @NotNull Response<GoogleMapPlace> response) {
                if(cbRef.get() != null){
                    cbRef.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GoogleMapPlace> call, @NotNull Throwable t) {
                if (cbRef.get() != null){
                    cbRef.get().onFailure(t);
                }
            }
        });
    }
    public static void getDetailOfAPlace (final GetDetailOfPlaceCallbacks callbacks, String placeID){
        GoogleMapPlacesService service = GoogleMapPlacesService.retrofitGetPlaceDetails.create(GoogleMapPlacesService.class);

        Call<PlaceDetails> call = service.getPlaceDetails(placeID, API_KEY);
        call.enqueue(new Callback<PlaceDetails>() {
            @Override
            public void onResponse(@NotNull Call<PlaceDetails> call, @NotNull Response<PlaceDetails> response) {
                callbacks.onResponseGetDetailOfPlace(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<PlaceDetails> call, @NotNull Throwable t) {
                callbacks.onFailureGetDetailOfPlace(t);
            }
        });
    }
}

