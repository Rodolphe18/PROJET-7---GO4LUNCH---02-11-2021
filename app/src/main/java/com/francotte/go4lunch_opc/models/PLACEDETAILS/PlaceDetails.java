
package com.francotte.go4lunch_opc.models.PLACEDETAILS;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;

import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Geometry;
import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Photo;
import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PlaceDetails implements Serializable
{

    @SerializedName("adr_address")
    private String adr_address;

    @SerializedName("business_status")
    private String business_status;

    @SerializedName("formatted_address")
    private String formatted_address;

    @SerializedName("icon")
    private String icon;

    @SerializedName("icon_background_color")
    private String icon_background_color;

    @SerializedName("icon_mask_base_uri")
    private String icon_mask_base_uri;

    @SerializedName("international_phone_number")
    private String international_phone_number;

    @SerializedName("opening_hours")
    private String opening_hours;

    @SerializedName("price_level")
    private double price_level;

    @SerializedName("plus_code")
    private String plus_code;

    @SerializedName("place_id")
    private String place_id;

    @SerializedName("name")
    private String name;

    @SerializedName("rating")
    private Float rating;

    @SerializedName("reference")
    private String reference;

    @SerializedName("reviews")
    private String reviews;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("photos")
    private List<Photo> photos;

    @SerializedName("types")
    private List<String> types;

    @SerializedName("html_attributions")
    private List<Object> html_attributions = null;

    @SerializedName("result")
    private Result result;

    @SerializedName("status")
    private String status;

    @SerializedName("url")
    private String url;

    @SerializedName("user_ratings_total")
    private Float user_ratings_total;

    @SerializedName("utc_offset")
    private Float utc_offset;

    @SerializedName("website")
    private Float website;


    public final static Parcelable.Creator<PlaceDetails> CREATOR = new Parcelable.Creator<PlaceDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PlaceDetails createFromParcel(android.os.Parcel in) {
            return new PlaceDetails(in);
        }

        public PlaceDetails[] newArray(int size) {
            return (new PlaceDetails[size]);
        }

    }
    ;
    private final static long serialVersionUID = 127717134142870259L;

    protected PlaceDetails(android.os.Parcel in) {
        in.readList(this.html_attributions, (java.lang.Object.class.getClassLoader()));
        this.result = ((Result) in.readValue((Result.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PlaceDetails() {
    }

    public String getAdr_address() {
        return adr_address;
    }

    public void setAdr_address(String adr_address) {
        this.adr_address = adr_address;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
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

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public double getPrice_level() {
        return price_level;
    }

    public void setPrice_level(double price_level) {
        this.price_level = price_level;
    }

    public String getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(String plus_code) {
        this.plus_code = plus_code;
    }

    public String getPlaceId() {
        return place_id;
    }

    public void setPlaceId(String placeId) {
        this.place_id = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<Object> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<Object> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(Float user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public Float getUtc_offset() {
        return utc_offset;
    }

    public void setUtc_offset(Float utc_offset) {
        this.utc_offset = utc_offset;
    }

    public Float getWebsite() {
        return website;
    }

    public void setWebsite(Float website) {
        this.website = website;
    }

    public static Parcelable.Creator<PlaceDetails> getCREATOR() {
        return CREATOR;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
