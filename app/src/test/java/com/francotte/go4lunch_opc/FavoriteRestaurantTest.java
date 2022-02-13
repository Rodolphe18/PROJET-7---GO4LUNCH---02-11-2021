package com.francotte.go4lunch_opc;

import com.francotte.go4lunch_opc.models.FavoriteRestaurant;

import junit.framework.TestCase;

public class FavoriteRestaurantTest extends TestCase {

    private String idPlace = "idPlace";
    private String namePlace = "namePlace";
    private String urlPlace = "urlPlace";

    FavoriteRestaurant favoriteRestaurant;


    public void setUp() {
        favoriteRestaurant = new FavoriteRestaurant(idPlace, namePlace, urlPlace);
    }

    public void testGetNamePlace() {
        assertTrue(favoriteRestaurant.getNamePlace().equals(namePlace));
    }

    public void testGetIdPlace() {
        assertTrue(favoriteRestaurant.getIdPlace().equals(idPlace));
    }

    public void testGetUrlPlace() {
        assertTrue(favoriteRestaurant.getUrlPlace().equals(urlPlace));
    }
}

