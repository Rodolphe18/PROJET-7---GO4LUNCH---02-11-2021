
package com.francotte.go4lunch_opc.models.PlaceDetails;

import java.io.Serializable;

import android.os.Parcelable;

import com.francotte.go4lunch_opc.Close;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Period implements Serializable, Parcelable {

    @SerializedName("close")
    @Expose
    private Close close;
    @SerializedName("open")
    @Expose
    private Open open;
    public final static Creator<Period> CREATOR = new Creator<Period>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Period createFromParcel(android.os.Parcel in) {
            return new Period(in);
        }

        public Period[] newArray(int size) {
            return (new Period[size]);
        }

    };
    private final static long serialVersionUID = -818853519053302272L;

    protected Period(android.os.Parcel in) {
        this.close = ((com.francotte.go4lunch_opc.Close) in.readValue((com.francotte.go4lunch_opc.Close.class.getClassLoader())));
        this.open = ((Open) in.readValue((Open.class.getClassLoader())));
    }

    public Period() {
    }

    public com.francotte.go4lunch_opc.Close getClose() {
        return close;
    }

    public void setClose(com.francotte.go4lunch_opc.Close close) {
        this.close = close;
    }

    public Open getOpen() {
        return open;
    }

    public void setOpen(Open open) {
        this.open = open;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(close);
        dest.writeValue(open);
    }

    public int describeContents() {
        return 0;
    }

}
