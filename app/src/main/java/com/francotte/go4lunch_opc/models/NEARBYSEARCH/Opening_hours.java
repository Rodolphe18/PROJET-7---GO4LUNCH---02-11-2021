
package com.francotte.go4lunch_opc.models.NEARBYSEARCH;

import java.io.Serializable;

import android.os.Parcelable;

public class Opening_hours implements Serializable, Parcelable
{


    private Boolean open_now;
    public final static Creator<Opening_hours> CREATOR = new Creator<Opening_hours>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Opening_hours createFromParcel(android.os.Parcel in) {
            return new Opening_hours(in);
        }

        public Opening_hours[] newArray(int size) {
            return (new Opening_hours[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4079972817749604224L;

    protected Opening_hours(android.os.Parcel in) {
        this.open_now = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Opening_hours() {
    }

    public Boolean getOpen_now() {
        return open_now;
    }

    public void setOpen_now(Boolean open_now) {
        this.open_now = open_now;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(open_now);
    }

    public int describeContents() {
        return  0;
    }

}
