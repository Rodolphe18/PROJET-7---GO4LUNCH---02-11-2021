
package com.francotte.go4lunch_opc.models.PlaceDetails;

import java.io.Serializable;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Review implements Serializable, Parcelable {

    @SerializedName("author_name")
    @Expose
    private String author_name;
    @SerializedName("author_url")
    @Expose
    private String author_url;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("profile_photo_url")
    @Expose
    private String profile_photo_url;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("relative_time_description")
    @Expose
    private String relative_time_description;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time")
    @Expose
    private Integer time;
    public final static Creator<Review> CREATOR = new Creator<Review>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Review createFromParcel(android.os.Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return (new Review[size]);
        }

    };
    private final static long serialVersionUID = 8457850026292395473L;

    protected Review(android.os.Parcel in) {
        this.author_name = ((String) in.readValue((String.class.getClassLoader())));
        this.author_url = ((String) in.readValue((String.class.getClassLoader())));
        this.language = ((String) in.readValue((String.class.getClassLoader())));
        this.profile_photo_url = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.relative_time_description = ((String) in.readValue((String.class.getClassLoader())));
        this.text = ((String) in.readValue((String.class.getClassLoader())));
        this.time = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Review() {
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRelative_time_description() {
        return relative_time_description;
    }

    public void setRelative_time_description(String relative_time_description) {
        this.relative_time_description = relative_time_description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(author_name);
        dest.writeValue(author_url);
        dest.writeValue(language);
        dest.writeValue(profile_photo_url);
        dest.writeValue(rating);
        dest.writeValue(relative_time_description);
        dest.writeValue(text);
        dest.writeValue(time);
    }

    public int describeContents() {
        return 0;
    }

}
