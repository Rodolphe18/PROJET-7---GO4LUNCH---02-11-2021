package com.francotte.go4lunch_opc;

import com.francotte.go4lunch_opc.models.NearbySearch.Location;
import com.francotte.go4lunch_opc.models.Restaurant;
import com.francotte.go4lunch_opc.models.User;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTest extends TestCase {

    private String name= "name";
    private String address = "adress";
    private double rating = 4.5;
    private String illustration = "illustration";
    private String placeId = "placeId";
    private List<User> userList = new ArrayList<>();
    private String phoneNumber = "0145720545";
    private String website = "www.francotte.com";
    private Boolean openNow = false;
    private Location location = null;
    private int distanceCurrentUser = 1000;


    Restaurant restaurant;

    public void setUp() throws Exception {
        super.setUp();
        restaurant = new Restaurant(name, address, illustration, placeId, rating, phoneNumber, website, location);
    }

    public void testGetPlaceId() {
        assertEquals(restaurant.getPlaceId(), placeId);
    }

    public void testGetName() {
        assertEquals(restaurant.getName(), name);
    }

    public void testGetAddress() {
        assertEquals(restaurant.getAddress(), address);
    }

    public void testGetIllustration() {
        assertEquals(restaurant.getIllustration(), illustration);
    }

    public void testGetRating() {
        assertEquals(restaurant.getRating(), rating);
    }

    public void testGetPhoneNumber() {
        assertEquals(restaurant.getPhoneNumber(), phoneNumber);
    }


}
