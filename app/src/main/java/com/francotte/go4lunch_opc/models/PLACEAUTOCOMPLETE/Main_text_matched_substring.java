
package com.francotte.go4lunch_opc.models.PLACEAUTOCOMPLETE;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Main_text_matched_substring implements Serializable, Parcelable
{


    private Integer length;
    private Integer offset;
    public final static Creator<Main_text_matched_substring> CREATOR = new Creator<Main_text_matched_substring>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Main_text_matched_substring createFromParcel(android.os.Parcel in) {
            return new Main_text_matched_substring(in);
        }

        public Main_text_matched_substring[] newArray(int size) {
            return (new Main_text_matched_substring[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4965422054984349169L;

    protected Main_text_matched_substring(android.os.Parcel in) {
        this.length = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.offset = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Main_text_matched_substring() {
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
