package com.francotte.go4lunch_opc.models;

import android.widget.ImageView;
import android.widget.TextView;

public class Restaurant {

    private String restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantType;
    private int numberWorkmates;
    private String restaurantSchedule;
    private double restaurantUserDistance;
    private String restaurantNote;
    private String restaurantIcon = "";

    public Restaurant(String restaurantId, String restaurantName, String restaurantAddress, String restaurantType, int numberWorkmates, String restaurantSchedule, double restaurantUserDistance, String restaurantNote, String restaurantIcon) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantType = restaurantType;
        this.numberWorkmates = numberWorkmates;
        this.restaurantSchedule = restaurantSchedule;
        this.restaurantUserDistance = restaurantUserDistance;
        this.restaurantNote = restaurantNote;
        this.restaurantIcon = restaurantIcon;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public int getNumberWorkmates() {
        return numberWorkmates;
    }

    public void setNumberWorkmates(int numberWorkmates) {
        this.numberWorkmates = numberWorkmates;
    }

    public String getRestaurantSchedule() {
        return restaurantSchedule;
    }

    public void setRestaurantSchedule(String restaurantSchedule) {
        this.restaurantSchedule = restaurantSchedule;
    }

    public double getRestaurantUserDistance() {
        return restaurantUserDistance;
    }

    public void setRestaurantUserDistance(double restaurantUserDistance) {
        this.restaurantUserDistance = restaurantUserDistance;
    }

    public String getRestaurantNote() {
        return restaurantNote;
    }

    public void setRestaurantNote(String restaurantNote) {
        this.restaurantNote = restaurantNote;
    }

    public String getRestaurantIcon() {
        return restaurantIcon;
    }

    public void setRestaurantIcon(String restaurantIcon) {
        this.restaurantIcon = restaurantIcon;
    }
}


