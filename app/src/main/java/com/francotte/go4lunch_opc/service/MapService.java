package com.francotte.go4lunch_opc.service;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface MapService {
    void addMarker(GoogleMap googleMap, LatLng position, String nameOfPlace, Context context, boolean isBusy);
}
