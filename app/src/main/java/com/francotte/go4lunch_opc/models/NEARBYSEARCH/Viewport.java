
package com.francotte.go4lunch_opc.models.NEARBYSEARCH;

import java.io.Serializable;
import android.os.Parcelable;


public class Viewport implements Serializable, Parcelable
{


    private Northeast northeast;
    private Southwest southwest;
    public final static Creator<Viewport> CREATOR = new Creator<Viewport>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Viewport createFromParcel(android.os.Parcel in) {
            return new Viewport(in);
        }

        public Viewport[] newArray(int size) {
            return (new Viewport[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2542703769206540703L;

    protected Viewport(android.os.Parcel in) {
        this.northeast = ((Northeast) in.readValue((Northeast.class.getClassLoader())));
        this.southwest = ((Southwest) in.readValue((Southwest.class.getClassLoader())));
    }

    public Viewport() {
    }

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(northeast);
        dest.writeValue(southwest);
    }

    public int describeContents() {
        return  0;
    }

}
