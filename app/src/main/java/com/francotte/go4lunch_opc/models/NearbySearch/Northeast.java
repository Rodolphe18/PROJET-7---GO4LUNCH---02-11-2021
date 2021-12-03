
package com.francotte.go4lunch_opc.models.NearbySearch;

import java.io.Serializable;

import android.os.Parcelable;


public class Northeast implements Serializable, Parcelable
{
    private Double lat;
    private Double lng;
    public final static Creator<Northeast> CREATOR = new Creator<Northeast>() {



        public Northeast createFromParcel(android.os.Parcel in) {
            return new Northeast(in);
        }

        public Northeast[] newArray(int size) {
            return (new Northeast[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2543707889065480377L;

    protected Northeast(android.os.Parcel in) {
        this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.lng = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    public Northeast() {
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
