
package com.francotte.go4lunch_opc.models.NearbySearch;

import java.io.Serializable;

import android.os.Parcelable;


public class Southwest implements Serializable, Parcelable
{


    private Double lat;

    private Double lng;
    public final static Creator<Southwest> CREATOR = new Creator<Southwest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Southwest createFromParcel(android.os.Parcel in) {
            return new Southwest(in);
        }

        public Southwest[] newArray(int size) {
            return (new Southwest[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4025258812108208690L;

    protected Southwest(android.os.Parcel in) {
        this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.lng = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Southwest() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(lat);
        dest.writeValue(lng);
    }

    public int describeContents() {
        return  0;
    }

}
