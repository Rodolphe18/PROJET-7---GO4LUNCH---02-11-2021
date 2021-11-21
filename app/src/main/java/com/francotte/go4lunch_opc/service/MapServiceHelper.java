package com.francotte.go4lunch_opc.service;

import android.content.Context;

import com.francotte.go4lunch_opc.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapServiceHelper implements MapService {

    @Override
    public void addMarker(GoogleMap googleMap, LatLng position, String nameOfPlace, Context context, boolean isBusy) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title(nameOfPlace);
        if(isBusy) {
            markerOptions.icon(BitMapMarker.getBitmapDescriptorFromResourceDrawable(context, R.drawable.ic_baseline_restaurant_24, R.drawable.ic_location_ping_green));
        }else{
            markerOptions.icon(BitMapMarker.getBitmapDescriptorFromResourceDrawable(context, R.drawable.ic_baseline_restaurant_24, R.drawable.ic_location_ping_orange));
        }
        googleMap.addMarker(markerOptions);
    }
}
