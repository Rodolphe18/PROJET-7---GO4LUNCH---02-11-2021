package com.francotte.go4lunch_opc.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.firestore.FirestoreCall;
import com.francotte.go4lunch_opc.models.FavoriteRestaurant;

import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Result;

import com.francotte.go4lunch_opc.models.PLACEDETAILS.PlaceDetails;
import com.francotte.go4lunch_opc.models.User;

import com.francotte.go4lunch_opc.service.UserHelper;
import com.francotte.go4lunch_opc.service.FavoriteHelper;
import com.francotte.go4lunch_opc.service.GoogleMapPlacesCall;
import com.francotte.go4lunch_opc.ui.adaptor.AdaptorListViewWorkmates;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailRestaurantActivity extends AppCompatActivity implements GoogleMapPlacesCall.GetDetailOfPlaceCallbacks, View.OnClickListener, FirestoreCall.CallbackFirestore, FirestoreCall.CallbackFirestoreFavorite {

    /** ----UI ---*/
    ImageView mPhotoPlaceImage;
    TextView mNameOfPlaceText;
    TextView mInformationOfPlaceText;

    FloatingActionButton mFavoritesPlaceFab;

    LinearLayout mCallPlaceButton;
    LinearLayout mFavoritePlaceButton;
    LinearLayout mWebSitePlaceButton;

    ImageView mIconFavoriteButton;

    RecyclerView mRecyclerViewWorkmates;

    private ProgressDialog progressDialog;
    /** -----------*/
    AdaptorListViewWorkmates adapter;

    private String place_id;
    private Result place;

    private Boolean isCurrentUserEatHer = false;
    private Boolean isThisPlaceIsFavorite = false;

    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.configureToolbar();
        this.configureUI();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                place_id = null;
            } else {
                place_id = extras.getString("place_id");
                getDetailsOfAPlace();
            }
        } else {
            place_id = (String) savedInstanceState.getSerializable("place_id");
            getDetailsOfAPlace();
        }
    }

    // Configure information into toolbar
    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
    }
    // Setup UI
    private void configureUI() {

        //Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        //General detail place init
        mFavoritesPlaceFab = (FloatingActionButton) findViewById(R.id.detail_activity_fab_favorites);
        mNameOfPlaceText = findViewById(R.id.details_activity_name_of_restaurant);
        mInformationOfPlaceText = findViewById(R.id.details_activity_information_of_restaurant);
        mCallPlaceButton = findViewById(R.id.detail_activity_call_place_button);
        mFavoritePlaceButton = findViewById(R.id.detail_activity_favorite_place_button);
        mIconFavoriteButton = findViewById(R.id.detail_activity_favorite_place_icon);
        mWebSitePlaceButton = findViewById(R.id.detail_activity_website_button);
        mPhotoPlaceImage = findViewById(R.id.details_activity_image_place);

        //Recycler View
        mRecyclerViewWorkmates = (RecyclerView) findViewById(R.id.detail_activity_workmates_of_place_recycler_view);
        adapter = new AdaptorListViewWorkmates(this, false);
        mRecyclerViewWorkmates.setAdapter(adapter);
        mRecyclerViewWorkmates.setLayoutManager(new LinearLayoutManager(this));
    }
    // Get details of a place with place id
    private void getDetailsOfAPlace() {
        GoogleMapPlacesCall.getDetailOfAPlace(this, place_id);
    }

    // Get all favorite to check if this place is a favorite
    private void getAllFavoritePlaceOfThisUser() {
        FirestoreCall.getAllFavoritePlaceOfAUser(this, getCurrentUser().getUid());
    }

    // Update UI with information of this place - 'getAllUserOfThisPlace'
    private void updateUI() {
        //GENERAL
        mNameOfPlaceText.setText(place.getName());
        mInformationOfPlaceText.setText(place.getVicinity());
        //PHONE
        if (place.getInternational_phone_number() != null) {
            mCallPlaceButton.setOnClickListener(this);
        } else {
            mCallPlaceButton.setEnabled(false);
        }
        // WEBSITE
        if (place.getWebsite() != null) {
            mWebSitePlaceButton.setOnClickListener(this);
        } else {
            mWebSitePlaceButton.setEnabled(false);
        }
        // FAVORITE
        if(isThisPlaceIsFavorite){
            mIconFavoriteButton.setImageResource(R.drawable.ic_baseline_star_24);
        }else{
            mIconFavoriteButton.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        mFavoritePlaceButton.setOnClickListener(this);

        // CHOSEN PLACE
        mFavoritesPlaceFab.setColorFilter(Color.LTGRAY);
        mFavoritesPlaceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCurrentUserEatHer) {
                    mFavoritesPlaceFab.setColorFilter(Color.LTGRAY);
                    UserHelper.updateUserLunchPlace(getCurrentUser().getUid(), null, null);
                    getAllUsersOfThisPlace();
                    isCurrentUserEatHer = false;
                } else {
                    mFavoritesPlaceFab.setColorFilter(Color.GREEN);
                    Date date = new Date();
                    UserHelper.updateUserLunchPlace(getCurrentUser().getUid(), place_id, date);
                    getAllUsersOfThisPlace();
                    Snackbar.make(view, getResources().getString(R.string.alert_msg_today_eat) + " " + place.getName(), Snackbar.LENGTH_LONG).show();
                    isCurrentUserEatHer = true;
                }
            }
        });
        //PHOTO
        if (place.getPhotos().size() > 0) {
            String baseUrlPhoto = "https://maps.googleapis.com/maps/api/place/photo";
            String widthPhoto = "?maxwidth=" + place.getPhotos().get(0).getWidth();
            String referencePhoto = "&photoreference=" + place.getPhotos().get(0).getPhotoReference();
            String url = baseUrlPhoto + widthPhoto + referencePhoto + "&key=" + "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA";

            Glide.with(this)
                    .load(url)
                    .into(mPhotoPlaceImage);
        }

        getAllUsersOfThisPlace();
    }

    // Get all users jointed of this place
    private void getAllUsersOfThisPlace() {
        FirestoreCall.getUsersOfAPlace(this, place_id);
    }

    // Check if this user eat in this place
    private void checkIfCurrentUserEatHere(List<User> users) {
        for (User user : users) {
            if (user.getLunchPlaceID() != null) {
                if (user.getLunchPlaceID().equals(place_id) && user.getUserId().equals(getCurrentUser().getUid())) {
                    isCurrentUserEatHer = true;
                    mFavoritesPlaceFab.setColorFilter(Color.GREEN);
                    break;
                }
            }
        }
    }

    // Response of Method 'getDetailOfAPlace' - call 'getAllFavoriteOfThisUser' to check if this place is a favorite
    @Override
    public void onResponseGetDetailOfPlace(PlaceDetails result) {
        if (result.getResult() != null) {
            place = result.getResult();
            updateUI();
            getAllFavoritePlaceOfThisUser();
        } else {
            Log.d("DetailActivity", "nothing result");
        }
    }


    @Override
    public void onFailureGetDetailOfPlace(Throwable t) {
        Log.e("DetailActivity", t.getMessage());
    }

    // Response of Method 'getAllFavoriteOfThisUser' - call 'updateUI'
    @Override
    public void onSuccessGetAllFavoriteOfTheUser(List<FavoriteRestaurant> favorites) {
        for(FavoriteRestaurant favoriteRestaurant : favorites){
            if (favoriteRestaurant.getIdPlace().equals(place_id)) {
                isThisPlaceIsFavorite = true;
                break;
            }
        }

        progressDialog.dismiss();
    }
    @Override
    public void onFailureGetAllFavoriteOfTheUser() {

    }

    // Response of Method 'getAllUserOfThisPlace' - update date adapter && call 'checkIfCurrentUserEatHer'
    @Override
    public void onSuccessGetUsers(List<User> users) {
        adapter.updateData(users);
        checkIfCurrentUserEatHere(users);
    }
    @Override
    public void onFailureGetUsers(Exception e) {
        Log.e("DetailActivity", "Error firestore call " + e.getMessage());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_activity_call_place_button:
                Log.d("DetailActivity", "call phone number" + place.getInternational_phone_number());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + place.getInternational_phone_number()));
                startActivity(intent);
                break;
            case R.id.detail_activity_favorite_place_button:
                if(!isThisPlaceIsFavorite) {
                    String url = null;
                    if (place.getPhotos().size() > 0) {
                        String baseUrlPhoto = "https://maps.googleapis.com/maps/api/place/photo";
                        String widthPhoto = "?maxwidth=" + place.getPhotos().get(0).getWidth();
                        String referencePhoto = "&photoreference=" + place.getPhotos().get(0).getPhotoReference();
                        url = baseUrlPhoto + widthPhoto + referencePhoto + "&key=" + "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA";
                    }
                    FavoriteHelper.addFavoritePlace(getCurrentUser().getUid(), place.getPlace_id(), place.getName(), url);
                    mIconFavoriteButton.setImageResource(R.drawable.ic_baseline_star_24);
                    isThisPlaceIsFavorite = true;
                }else{
                    FavoriteHelper.deleteFavoritePlace(getCurrentUser().getUid(), place_id);
                    mIconFavoriteButton.setImageResource(R.drawable.ic_baseline_star_border_24);
                    isThisPlaceIsFavorite = false;
                }
                break;
            case R.id.detail_activity_website_button:
                Log.d("DetailActivity", "run website " + place.getWebsite());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(place.getWebsite()));
                startActivity(browserIntent);
                break;
        }
    }

    @Nullable
    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


}