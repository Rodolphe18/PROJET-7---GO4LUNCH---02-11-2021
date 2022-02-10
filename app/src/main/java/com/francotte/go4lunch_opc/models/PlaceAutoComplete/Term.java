
package com.francotte.go4lunch_opc.models.PlaceAutoComplete;

import java.io.Serializable;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Term implements Serializable, Parcelable
{


    private Integer offset;
    private String value;
    public final static Creator<Term> CREATOR = new Creator<Term>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Term createFromParcel(android.os.Parcel in) {
            return new Term(in);
        }

        public Term[] newArray(int size) {
            return (new Term[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6505553022489939585L;

    protected Term(android.os.Parcel in) {
        this.offset = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Term() {
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(offset);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}
