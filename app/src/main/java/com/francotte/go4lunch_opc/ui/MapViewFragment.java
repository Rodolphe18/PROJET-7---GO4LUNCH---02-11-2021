package com.francotte.go4lunch_opc.ui;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.francotte.go4lunch_opc.DI.DI;
import com.francotte.go4lunch_opc.DI.InjectionMain;
import com.francotte.go4lunch_opc.R;


import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Result;
import com.francotte.go4lunch_opc.models.NEARBYSEARCH.ResultsPlaces;
import com.francotte.go4lunch_opc.service.MapService;
import com.francotte.go4lunch_opc.viewmodel.MainViewModel;
import com.francotte.go4lunch_opc.DI.MainViewModelFactory;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

import com.francotte.go4lunch_opc.service.GoogleMapPlacesCall;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MapViewFragment extends Fragment implements GoogleMapPlacesCall.CallbacksFetchNearbyPlace  {


    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE= 1234;
    private Boolean mLocationPermissionGranted = false;
    //MAPS
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private MapService mapService;
    //VIEW MODEL
    private MainViewModel viewModel;

    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(locationResult == null) {
                return;
            }
            for (android.location.Location location: locationResult.getLocations()){
                Log.d(TAG, "onLocationResult: " + location.toString());
            }
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map_view, container, false);
        getLocationPermission();
        configureViewModel();
        mapService = DI.getMapApiService();
        initMap();
        return root;
    }

    private void configureViewModel() {
        MainViewModelFactory mMainViewModelFactory = InjectionMain.provideViewModelFactory(getActivity());
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) this, mMainViewModelFactory).get(MainViewModel.class);
    }


    private void initMap() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view_fragment);
        mapsFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                map = googleMap;
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                locationRequest = LocationRequest.create();
                locationRequest.setInterval(100000);
                locationRequest.setFastestInterval(2000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                checkSettingsAndStartLocationUpdates();

                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        if (locationResult == null) {
                            return;
                        }
                        for (Location location : locationResult.getLocations()) {
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
                            map.moveCamera(cameraUpdate);
                            Log.d(TAG, "onAPIResult: " + location.toString());
                            Log.d(TAG, "onAPIResult2" + location.getLatitude() + "," + location.getLongitude());
                            callNearbyPlacesAPI(location.getLatitude() + " " + location.getLongitude());
                        }
                    }
                };
            }
        });
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    // Initialize map
                    initMap();
                }
            }
        }
    }


    private void callNearbyPlacesAPI(String location) {
        viewModel.getNearbyPlaces(this, location);
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(getActivity(), 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    @Override
    public void onResponseFetchNearbyPlace(@Nullable ResultsPlaces places) {
        if(places.getResults()!= null) {
            map.clear();
            List<Result> candidatesPlaces = places.getResults();
            for(int i = 0; i < candidatesPlaces.size(); i++){
                LatLng latLng = new LatLng(candidatesPlaces.get(i).getGeometry().getLocation().getLat(), candidatesPlaces.get(i).getGeometry().getLocation().getLng());
                mapService.addMarker(map,latLng, candidatesPlaces.get(i).getName(), getActivity(), false);
            }
        }
    }

    @Override
    public void onFailureFetchNearbyPlace(Throwable t)  {
        Log.e("MapsViewFragment", "fail bro" + t.getMessage());
    }

}