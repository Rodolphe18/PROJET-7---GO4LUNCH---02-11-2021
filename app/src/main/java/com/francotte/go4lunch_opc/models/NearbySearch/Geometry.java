
package com.francotte.go4lunch_opc.models.NearbySearch;

import java.io.Serializable;
import android.os.Parcelable.Creator;


public class Geometry implements Serializable
{


    private Location location;
    private Viewport viewport;
    public final static Creator<Geometry> CREATOR = new Creator<Geometry>() {


        public Geometry createFromParcel(android.os.Parcel in) {
            return new Geometry(in);
        }

        public Geometry[] newArray(int size) {
            return (new Geometry[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7739014047611192221L;

    protected Geometry(android.os.Parcel in) {
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
        this.viewport = ((Viewport) in.readValue((Viewport.class.getClassLoader())));
    }

    public Geometry() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(location);
        dest.writeValue(viewport);
    }

    public int describeContents() {
        return  0;
    }

}
