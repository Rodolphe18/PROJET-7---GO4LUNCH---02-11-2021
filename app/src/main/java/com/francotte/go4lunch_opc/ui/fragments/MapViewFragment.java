package com.francotte.go4lunch_opc.ui.fragments;

import android.Manifest;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import android.content.res.Resources;
import android.location.Location;
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

import com.francotte.go4lunch_opc.DI.DI;
import com.francotte.go4lunch_opc.DI.InjectionMain;
import com.francotte.go4lunch_opc.R;


import com.francotte.go4lunch_opc.models.NearbySearch.Result;
import com.francotte.go4lunch_opc.models.NearbySearch.ResultsPlaces;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.user_repository.FirestoreCall;
import com.francotte.go4lunch_opc.service.markers.MapService;
import com.francotte.go4lunch_opc.ui.activities.DetailRestaurantActivity;
import com.francotte.go4lunch_opc.ui.activities.MainActivity;
import com.francotte.go4lunch_opc.utils.LocationRepository;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.francotte.go4lunch_opc.repositories.google_api.GoogleMapPlacesCall;


public class MapViewFragment extends Fragment implements GoogleMapPlacesCall.CallbacksFetchNearbyPlace, FirestoreCall.CallbackFirestoreUsers {


    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
    //MAPS
    private GoogleMap map;
    private LocationRepository locationRepository;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private MapService mapService;
    //VIEW MODEL
    private MainViewModel viewModel;
    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult == null) {
                return;
            }
            for (android.location.Location location : locationResult.getLocations()) {
            }
        }
    };
    List<User> users;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map_view, container, false);
        configureToolbar(root);
        configureViewModel();
        getAllUsers();
        init();
        initMap();
        configureFabButton(root);
        return root;
    }


    private void configureToolbar(View root) {
        MainActivity activity = ((MainActivity) root.getContext());
        activity.getSupportActionBar().setTitle(R.string.main_activity_title);
    }

    private void configureViewModel() {
        MainViewModelFactory mMainViewModelFactory = InjectionMain.provideViewModelFactory(getActivity());
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) this, mMainViewModelFactory).get(MainViewModel.class);
    }


    private void configureFabButton(View root) {
        FloatingActionButton mFab = root.findViewById(R.id.fragment_map_view_fab_my_location);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserCurrentLocation();
            }
        });
    }

    // Init Place
    private void init() {
        mapService = DI.getMapApiService();
    }

    private void initMap() {
        SupportMapFragment mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view_fragment);
        mapsFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                map = googleMap;

            getUserCurrentLocation();

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Intent i = new Intent(getContext(), DetailRestaurantActivity.class);
                        i.putExtra("place_id", marker.getSnippet());
                        startActivity(i);
                        return false;
            }
        });
    }
        });
    }

    private LocationRepository getUserCurrentLocation() {

        locationRepository = new LocationRepository(getContext());
        if (locationRepository.canGetLocation()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000000);
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
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12);
                        map.moveCamera(cameraUpdate);
                        callNearbyPlacesAPI(location.getLatitude() + " " + location.getLongitude());
                    }
                }
            };
        } else {
            locationRepository.showSettingsAlert();
        }
        return locationRepository;
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


    private void callNearbyPlacesAPI(String location) {
        viewModel.getNearbyPlaces(this, location);
    }


    @Override
    public void onResponseFetchNearbyPlace(@Nullable ResultsPlaces places) {
        if (places.getResults() != null) {
            map.clear();
            List<Result> candidatesPlaces = places.getResults();
            for (int i = 0; i < candidatesPlaces.size(); i++) {
                LatLng latLng = new LatLng(candidatesPlaces.get(i).getGeometry().getLocation().getLat(), candidatesPlaces.get(i).getGeometry().getLocation().getLng());
                mapService.addMarker(map, latLng, candidatesPlaces.get(i).getName(), getActivity(), isPlaceIdBusy(candidatesPlaces.get(i).getPlace_id(), this.users), candidatesPlaces.get(i).getPlace_id());
            }
        }
    }


    @Override
    public void onFailureFetchNearbyPlace(Throwable t) {
        Log.e("MapsViewFragment", "failure" + t.getMessage());
    }

    private void getAllUsers() {
        FirestoreCall.getAllUsers(this);
        FirestoreCall.setUpdateDataRealTime(this);
    }

    @Override
    public void onSuccessGetUsers(List<User> users) {
        this.users = users;
        }


    @Override
    public void onFailureGetUsers(Exception e) {
    }


    public boolean isPlaceIdBusy (String placeId, List<User> users) {
        for (User user : users) {
            if (placeId.equals(user.getLunchPlaceID())) {
                return true;
            }
        }
        return false;
    }


}