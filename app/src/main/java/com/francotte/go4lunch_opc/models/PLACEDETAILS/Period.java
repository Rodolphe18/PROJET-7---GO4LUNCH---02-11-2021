
package com.francotte.go4lunch_opc.models.PLACEDETAILS;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Period implements Serializable, Parcelable
{

    @SerializedName("close")
    @Expose
    private com.francotte.go4lunch_opc.Close close;
    @SerializedName("open")
    @Expose
    private com.francotte.go4lunch_opc.Open open;
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

    }
    ;
    private final static long serialVersionUID = -818853519053302272L;

    protected Period(android.os.Parcel in) {
        this.close = ((com.francotte.go4lunch_opc.Close) in.readValue((com.francotte.go4lunch_opc.Close.class.getClassLoader())));
        this.open = ((com.francotte.go4lunch_opc.Open) in.readValue((com.francotte.go4lunch_opc.Open.class.getClassLoader())));
    }

    public Period() {
    }

    public com.francotte.go4lunch_opc.Close getClose() {
        return close;
    }

    public void setClose(com.francotte.go4lunch_opc.Close close) {
        this.close = close;
    }

    public com.francotte.go4lunch_opc.Open getOpen() {
        return open;
    }

    public void setOpen(com.francotte.go4lunch_opc.Open open) {
        this.open = open;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(close);
        dest.writeValue(open);
    }

    public int describeContents() {
        return  0;
    }

}
