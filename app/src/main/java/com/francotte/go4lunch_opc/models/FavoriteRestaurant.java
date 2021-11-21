package com.francotte.go4lunch_opc.models;


public class FavoriteRestaurant {

    private String idPlace;
    private String namePlace;
    private String urlPlace;

    public FavoriteRestaurant() {
    }
    public FavoriteRestaurant(String idPlace, String namePlace, String urlPlace) {
        this.idPlace = idPlace;
        this.namePlace = namePlace;
        this.urlPlace = urlPlace;
    }

    // --- GETTER --- //
    public String getNamePlace() {
        return namePlace;
    }
    public String getIdPlace() {
        return idPlace;
    }
    public String getUrlPlace() {
        return urlPlace;
    }
    // --- SETTER --- //
    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }
    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }
    public void setUrlPlace(String urlPlace) {
        this.urlPlace = urlPlace;
    }

}
