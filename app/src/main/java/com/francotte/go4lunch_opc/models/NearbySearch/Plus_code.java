
package com.francotte.go4lunch_opc.models.NearbySearch;

import java.io.Serializable;

import android.os.Parcelable;


public class Plus_code implements Serializable, Parcelable
{


    private String compound_code;

    private String global_code;
    public final static Creator<Plus_code> CREATOR = new Creator<Plus_code>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Plus_code createFromParcel(android.os.Parcel in) {
            return new Plus_code(in);
        }

        public Plus_code[] newArray(int size) {
            return (new Plus_code[size]);
        }

    }
    ;
    private final static long serialVersionUID = 1026342706680562091L;

    protected Plus_code(android.os.Parcel in) {
        this.compound_code = ((String) in.readValue((String.class.getClassLoader())));
        this.global_code = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Plus_code() {
    }

    public String getCompound_code() {
        return compound_code;
    }

    public void setCompound_code(String compound_code) {
        this.compound_code = compound_code;
    }

    public String getGlobal_code() {
        return global_code;
    }

    public void setGlobal_code(String global_code) {
        this.global_code = global_code;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(compound_code);
        dest.writeValue(global_code);
    }

    public int describeContents() {
        return  0;
    }

}
