package com.francotte.go4lunch_opc.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.repositories.user_repository.FirestoreCall;
import com.francotte.go4lunch_opc.models.FavoriteRestaurant;
import com.francotte.go4lunch_opc.ui.adaptors.AdaptorListViewFavoriteRestaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FavoriteRestaurantActivity extends AppCompatActivity implements FirestoreCall.CallbackFirestoreFavorite {

    private AdaptorListViewFavoriteRestaurant adapter;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        configureToolbar();
        configureUI();
        getMyPlacesFavorites();
    }

    // Configure information into toolbar
    private void configureToolbar() {
        // --- Toolbar --- //
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_favorite_toolbar);
        toolbar.setTitle(R.string.favorite_activity_title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Setup UI
    private void configureUI() {
        // --- UI --- //
        RecyclerView recyclerViewFavoriteRestaurant = findViewById(R.id.favorite_activity_recycler_view);
        recyclerViewFavoriteRestaurant.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdaptorListViewFavoriteRestaurant(getCurrentUser().getUid());
        recyclerViewFavoriteRestaurant.setAdapter(adapter);
        // --- Progress --- //
        progress = new ProgressDialog(this);
        progress.setTitle(R.string.fui_progress_dialog_loading);
        progress.show();
    }

    // This is to get all favorites of the user / getAllFavoritePlaceOfAUser -> Task : success and failure -> this
    private void getMyPlacesFavorites() {
        if (getCurrentUser() != null)
            FirestoreCall.getAllFavoritePlaceOfAUser(this, getCurrentUser().getUid());
    }

    // Response of get all favorites
    @Override
    public void onSuccessGetAllFavoriteOfTheUser(List<FavoriteRestaurant> favorites) {
        adapter.updateData(favorites);
        progress.dismiss();
    }

    @Override
    public void onFailureGetAllFavoriteOfTheUser() {
        progress.dismiss();
        Toast.makeText(this, R.string.error_loading_firebase, Toast.LENGTH_LONG).show();
    }

    // Get current user in with FirebaseAuth
    @Nullable
    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
