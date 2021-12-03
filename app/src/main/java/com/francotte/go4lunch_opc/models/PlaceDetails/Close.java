
package com.francotte.go4lunch_opc;

import java.io.Serializable;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Close implements Serializable, Parcelable {

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("time")
    @Expose
    private String time;
    public final static Creator<Close> CREATOR = new Creator<Close>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Close createFromParcel(android.os.Parcel in) {
            return new Close(in);
        }

        public Close[] newArray(int size) {
            return (new Close[size]);
        }

    };
    private final static long serialVersionUID = -7131373640554035469L;

    protected Close(android.os.Parcel in) {
        this.day = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Close() {
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
        return 0;
    }

}
