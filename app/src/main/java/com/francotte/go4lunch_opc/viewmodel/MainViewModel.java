package com.francotte.go4lunch_opc.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.auth.AuthUI;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.user_repository.UserHelper;
import com.francotte.go4lunch_opc.ui.activities.LogActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;

import com.francotte.go4lunch_opc.repositories.google_api.GoogleMapPlacesCall;

public class MainViewModel extends ViewModel {

    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;

    final FusedLocationProviderClient fusedLocationProviderClient;
    private final WeakReference<Context> context;

    public MainViewModel(Context context) {
        this.context = new WeakReference<>(context);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    // GET CURRENT USER
    @Nullable
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    //SIGN OUT USER
    public void signOutUserFromFirebase(Context context) {
        AuthUI.getInstance()
                .signOut(context)
                .addOnSuccessListener((Activity) context, updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK, context));
    }

    // DELETE USER
    public void deleteUserFromFirebase(Context context) {
        if (getCurrentUser() != null) {
            UserHelper.deleteUser(getCurrentUser().getUid());
            AuthUI.getInstance().delete(context).addOnSuccessListener((Activity) context, updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK, context));
        }
    }

    // UPDATE UI
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin, final Context context) {
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin) {
                    case SIGN_OUT_TASK:
                        ((Activity) context).finish();
                        break;
                    case DELETE_USER_TASK:
                        Intent intent = new Intent(context, LogActivity.class);
                        ((Activity) context).startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    //REQUEST API NEARBY PLACES
    public void getNearbyPlaces(GoogleMapPlacesCall.CallbacksFetchNearbyPlace callbacksFetchNearbyPlace, String location) {
        GoogleMapPlacesCall.fetchNearbyPlaces(callbacksFetchNearbyPlace, location);
    }

    //REQUEST API AUTOCOMPLETE PLACE
    public void getPlaceAutoComplete(GoogleMapPlacesCall.GetAllPredictionOfSearchPlace callbacksGetPlaceAutoComplete, String input) {
        GoogleMapPlacesCall.getAllPredictionOfSearchPlace(callbacksGetPlaceAutoComplete, input);
    }


    //GET LOCATION
    public Task<Location> getUserLastLocation() {
        if (ActivityCompat.checkSelfPermission(context.get(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context.get(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return fusedLocationProviderClient.getLastLocation();
    }

}