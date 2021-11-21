
package com.francotte.go4lunch_opc.models.PLACEDETAILS;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Open implements Serializable, Parcelable
{

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("time")
    @Expose
    private String time;
    public final static Creator<Open> CREATOR = new Creator<Open>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Open createFromParcel(android.os.Parcel in) {
            return new Open(in);
        }

        public Open[] newArray(int size) {
            return (new Open[size]);
        }

    }
    ;
    private final static long serialVersionUID = 7023212706691135713L;

    protected Open(android.os.Parcel in) {
        this.day = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Open() {
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(day);
        dest.writeValue(time);
    }

    public int describeContents() {
        return  0;
    }

}
