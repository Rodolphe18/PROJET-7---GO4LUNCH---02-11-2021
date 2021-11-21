
package com.francotte.go4lunch_opc.models.PLACEDETAILS;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Address_component implements Serializable, Parcelable
{

    @SerializedName("long_name")
    @Expose
    private String long_name;
    @SerializedName("short_name")
    @Expose
    private String short_name;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    public final static Creator<Address_component> CREATOR = new Creator<Address_component>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Address_component createFromParcel(android.os.Parcel in) {
            return new Address_component(in);
        }

        public Address_component[] newArray(int size) {
            return (new Address_component[size]);
        }

    }
    ;
    private final static long serialVersionUID = -8605357617118779677L;

    protected Address_component(android.os.Parcel in) {
        this.long_name = ((String) in.readValue((String.class.getClassLoader())));
        this.short_name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.types, (java.lang.String.class.getClassLoader()));
    }

    public Address_component() {
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(long_name);
        dest.writeValue(short_name);
        dest.writeList(types);
    }

    public int describeContents() {
        return  0;
    }

}
