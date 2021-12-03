package com.francotte.go4lunch_opc.models.NearbySearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable {


    private Geometry geometry;

    private String icon;

    private String international_phone_number;

    private String id;

    private String name;

    private OpeningHours opening_hours;

    private List<Photo> photos = new ArrayList<>();

    private String place_id;

    private Double rating;

    private String illustration;

    private String reference;

    private String scope;

    private List<String> types = new ArrayList<>();

    private String vicinity;

    private Integer priceLevel;

    private String website;

    private int distanceCurrentUser;

    private Location location;

    public Result() {
    }

    public Result(String name, String vicinity, String illustration, String place_id, double rating, String international_phone_number, String website, Location location) {

        this.name = name;
        this.vicinity = vicinity;
        this.illustration = illustration;
        this.place_id = place_id;
        this.rating = rating;
        this.international_phone_number = international_phone_number;
        this.website = website;
        this.location = location;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public int getDistanceCurrentUser() {
        return distanceCurrentUser;
    }

    public void setDistanceCurrentUser(int distanceCurrentUser) {
        this.distanceCurrentUser = distanceCurrentUser;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpeningHours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Result{" +
                "geometry=" + geometry +
                ", icon='" + icon + '\'' +
                ", international_phone_number='" + international_phone_number + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", opening_hours=" + opening_hours +
                ", photos=" + photos +
                ", place_id='" + place_id + '\'' +
                ", rating=" + rating +
                ", reference='" + reference + '\'' +
                ", scope='" + scope + '\'' +
                ", types=" + types +
                ", vicinity='" + vicinity + '\'' +
                ", priceLevel=" + priceLevel +
                ", website='" + website + '\'' +
                '}';
    }
}


