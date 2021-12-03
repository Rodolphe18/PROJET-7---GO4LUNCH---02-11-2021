package com.francotte.go4lunch_opc.utils;

import android.location.Location;
import android.view.View;
import android.widget.ImageView;

import com.francotte.go4lunch_opc.models.NearbySearch.Result;

import java.util.List;

public class UtilsListRestaurant {

    public static void updateRating(ImageView star1, ImageView star2, ImageView star3, Result place)
    {
        double rating = place.getRating();

        if (rating > 4)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);

        }
        else if (rating > 3)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.INVISIBLE);

        }
        else if (rating > 2)
        {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
        }
        else
        {
            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Update the attribute DistanceCurrentUser for each restaurant
     */
    public static void updateDistanceToCurrentLocation(Location currentLocation, List<Result> restaurantList)
    {
        Location restaurantLocation = new Location("fusedLocationProvider");
        int size = restaurantList.size();
        for (int i = 0; i < size; i++)
        {
            //Get the restaurant's location
            restaurantLocation.setLatitude(restaurantList.get(i).getLocation().getLat());
            restaurantLocation.setLongitude(restaurantList.get(i).getLocation().getLng());
            //Get the distance between currentLocation and restaurantLocation
            int distanceLocation = (int) currentLocation.distanceTo(restaurantLocation);
            restaurantList.get(i).setDistanceCurrentUser(distanceLocation);
        }
    }
}
