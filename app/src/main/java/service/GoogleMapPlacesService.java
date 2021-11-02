package service;



import com.francotte.go4lunch_opc.models.NEARBYSEARCH.GoogleMapPlace;
import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Result;
import com.francotte.go4lunch_opc.models.PLACEAUTOCOMPLETE.PlaceAutoComplete;
import com.francotte.go4lunch_opc.models.PLACEDETAILS.PlaceDetails;
import com.francotte.go4lunch_opc.viewmodel.Utility;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public interface GoogleMapPlacesService {


    @GET("maps/api/place/nearbysearch/")
    Call<GoogleMapPlace> getGoogleMapPlaces(@Query("location") String location, @Query("type") String type, @Query("radius") String radius, @Query("key") String API_KEY);

    Retrofit retrofitGetGoogleMapPlaces = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .client(Utility.getClientHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("maps/api/place/details")
    Call<PlaceDetails> getPlaceDetails(@Query("place_id") String placeID, @Query("key") String API_KEY);


    Retrofit retrofitGetPlaceDetails = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("maps/api/place/autocomplete")
    Call<PlaceAutoComplete> getPlaceAutoComplete(@Query("input") String input, @Query("type") String type, @Query("key") String API_KEY);

    Retrofit retrofitGetPlaceAutoComplete = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
