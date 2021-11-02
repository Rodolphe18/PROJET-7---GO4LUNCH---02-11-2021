
package com.francotte.go4lunch_opc.models.NEARBYSEARCH;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;


public class Result implements Serializable, Parcelable
{

    private String business_status;
    private Geometry geometry;
    private String icon;
    private String icon_background_color;
    private String icon_mask_base_uri;
    private String name;
    private Opening_hours opening_hours;
    private List<Photo> photos = null;
    private String place_id;
    private Plus_code plus_code;
    private Integer price_level;
    private Double rating;
    private String reference;
    private String scope;
    private List<String> types = null;
    private Integer user_ratings_total;
    private String vicinity;
    public final static Creator<Result> CREATOR = new Creator<Result>() {


        public Result createFromParcel(android.os.Parcel in) {
            return new Result();
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7881254647711832149L;

    public Result() {
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
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

    public String getIcon_background_color() {
        return icon_background_color;
    }

    public void setIcon_background_color(String icon_background_color) {
        this.icon_background_color = icon_background_color;
    }

    public String getIcon_mask_base_uri() {
        return icon_mask_base_uri;
    }

    public void setIcon_mask_base_uri(String icon_mask_base_uri) {
        this.icon_mask_base_uri = icon_mask_base_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Opening_hours getOpeningHours() {
        return opening_hours;
    }

    public void setOpeningHours(Opening_hours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Plus_code getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(Plus_code plus_code) {
        this.plus_code = plus_code;
    }

    public Integer getPrice_level() {
        return price_level;
    }

    public void setPrice_level(Integer price_level) {
        this.price_level = price_level;
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

    public Integer getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(Integer user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(business_status);
        dest.writeValue(geometry);
        dest.writeValue(icon);
        dest.writeValue(icon_background_color);
        dest.writeValue(icon_mask_base_uri);
        dest.writeValue(name);
        dest.writeValue(opening_hours);
        dest.writeList(photos);
        dest.writeValue(place_id);
        dest.writeValue(plus_code);
        dest.writeValue(price_level);
        dest.writeValue(rating);
        dest.writeValue(reference);
        dest.writeValue(scope);
        dest.writeList(types);
        dest.writeValue(user_ratings_total);
        dest.writeValue(vicinity);
    }

    public int describeContents() {
        return  0;
    }

}
