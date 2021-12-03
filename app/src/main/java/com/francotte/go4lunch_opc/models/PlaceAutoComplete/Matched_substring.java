
package com.francotte.go4lunch_opc.models.PlaceAutoComplete;

import java.io.Serializable;

import android.os.Parcelable;


public class Matched_substring implements Serializable, Parcelable
{

    private Integer length;
    private Integer offset;
    public final static Creator<Matched_substring> CREATOR = new Creator<Matched_substring>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Matched_substring createFromParcel(android.os.Parcel in) {
            return new Matched_substring(in);
        }

        public Matched_substring[] newArray(int size) {
            return (new Matched_substring[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2042449409444173932L;

    protected Matched_substring(android.os.Parcel in) {
        this.length = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.offset = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Matched_substring() {
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(length);
        dest.writeValue(offset);
    }

    public int describeContents() {
        return  0;
    }

}
