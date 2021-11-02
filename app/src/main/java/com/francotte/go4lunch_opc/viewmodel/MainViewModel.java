package com.francotte.go4lunch_opc.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.francotte.go4lunch_opc.repository.UserRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import service.GoogleMapPlacesCall;

public class MainViewModel extends ViewModel {

    private UserRepository userRepository;
    final FusedLocationProviderClient fusedLocationProviderClient;
    private final WeakReference<Context> context;

    public MainViewModel(Context context) {
        this.context = new WeakReference<>(context);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    // GET CURRENT USER
    @Nullable
    protected FirebaseUser getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    //SIGN OUT USER
    public void signOutUserFromFirebase(Context context) {
        userRepository.signOutUserFromFirebase(context);
    }

    // DELETE USER
    public void deleteUserFromFirebase(Context context) {
        if (getCurrentUser() != null) {
            userRepository.deleteUserFromFirebase();
        }
    }

    public void getNearbyPlaces(GoogleMapPlacesCall.CallbacksFetchNearbyPlace callbacksFetchNearbyPlace, String location) {
        GoogleMapPlacesCall.fetchNearbyPlaces(callbacksFetchNearbyPlace, location);
    }

    //GET LOCATION
    public Task<Location> getUserLocation() {
        if (ActivityCompat.checkSelfPermission(context.get(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context.get(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return fusedLocationProviderClient.getLastLocation();
    }
}