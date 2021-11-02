package com.francotte.go4lunch_opc.ui;

import android.Manifest;
import android.content.Context;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.francotte.go4lunch_opc.DI.InjectionMain;
import com.francotte.go4lunch_opc.R;


import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Result;
import com.francotte.go4lunch_opc.viewmodel.MainViewModel;
import com.francotte.go4lunch_opc.DI.MainViewModelFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.francotte.go4lunch_opc.models.NEARBYSEARCH.GoogleMapPlace;


import java.util.List;

import service.GoogleMapPlacesCall;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MapViewFragment extends Fragment implements GoogleMapPlacesCall.CallbacksFetchNearbyPlace {

    //MAPS
    private GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int ACCESS_LOCATION_REQUEST_CODE = 1001;
    //VIEW MODEL
    private MainViewModel viewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map_view, container, false);
        configureToolbar(root);
        configureViewModel();
        init();
        initMaps();
        configureUI(root);
        zoomToUserLocation();
        return root;
         }


    // Configure information into toolbar
    private void configureToolbar(View root) {
        MainActivity activity = ((MainActivity) root.getContext());
        activity.getSupportActionBar().setTitle(R.string.ToolbarMainTitle);}

        // Configure
        private void configureViewModel() {
            MainViewModelFactory mMainViewModelFactory = InjectionMain.provideViewModelFactory(getActivity());
            this.viewModel = new ViewModelProvider(this, mMainViewModelFactory).get(MainViewModel.class);
        }


    private void init() {

        String API_KEY_PLACE = "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA";
        Places.initialize(getActivity().getApplicationContext(), API_KEY_PLACE);
        PlacesClient placesClient = Places.createClient(getContext());}

    // Init Place and show map into fragment
    private void initMaps() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view_fragment);
        mapsFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                map = googleMap;

                try {
                    boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style));
                    if (!success) {
                        Log.e("MapFragment", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("MapFragment", "Can't find style. Error: ", e);
                }


                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    zoomToUserLocation();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
                    }
                }
            }
        });

    }
    // Configure Fab
    private void configureUI(View root) {
        FloatingActionButton mFab = root.findViewById(R.id.fragment_map_view_fab_my_location);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomToUserLocation();
            }
        });
    }

    // REQUEST API WITH RETROFIT
    private void callAPI(String location) {
        viewModel.getNearbyPlaces(this, location);
    }



    private void zoomToUserLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
          //  return;
       }
        // map.setMyLocationEnabled(true);
       // if (statusCheck()) {
            Task<android.location.Location> locationTask = viewModel.getUserLocation();
            locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        callAPI(location.getLatitude() + "," + location.getLongitude());
                    }
                    //progressDialog.dismiss();
                }
            });
        // } else {

       // }
    }

    // Response of CallApi - GoogleMapPlace
    @Override
    public void onResponse(@Nullable GoogleMapPlace places) {
        if(places.getResults()!= null) {
            map.clear();
            List<Result> candidatesPlaces = places.getResults();
            for(int i = 0; i < candidatesPlaces.size(); i++){
                LatLng latLng = new LatLng(candidatesPlaces.get(i).getGeometry().getLocation().getLat(), candidatesPlaces.get(i).getGeometry().getLocation().getLng());
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("MapsViewFragment", "fail bro" + t.getMessage());
    }

    //Check Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                zoomToUserLocation();
            } else {
                getActivity().finish();
            }
        }
    }

    public boolean statusCheck() {
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }




}