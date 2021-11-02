
package com.francotte.go4lunch_opc.models.PLACEAUTOCOMPLETE;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Structured_formatting implements Serializable, Parcelable
{

    @SerializedName("main_text")
    @Expose
    private String main_text;
    @SerializedName("main_text_matched_substrings")
    @Expose
    private List<Main_text_matched_substring> main_text_matched_substrings = null;
    @SerializedName("secondary_text")
    @Expose
    private String secondary_text;
    public final static Creator<Structured_formatting> CREATOR = new Creator<Structured_formatting>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Structured_formatting createFromParcel(android.os.Parcel in) {
            return new Structured_formatting(in);
        }

        public Structured_formatting[] newArray(int size) {
            return (new Structured_formatting[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6102385977073936649L;

    protected Structured_formatting(android.os.Parcel in) {
        this.main_text = ((String) in.readValue((String.class.getClassLoader())));
        this.secondary_text = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Structured_formatting() {
    }

    public String getMain_text() {
        return main_text;
    }

    public void setMain_text(String main_text) {
        this.main_text = main_text;
    }

    public List<Main_text_matched_substring> getMain_text_matched_substrings() {
        return main_text_matched_substrings;
    }

    public void setMain_text_matched_substrings(List<Main_text_matched_substring> main_text_matched_substrings) {
        this.main_text_matched_substrings = main_text_matched_substrings;
    }

    public String getSecondary_text() {
        return secondary_text;
    }

    public void setSecondary_text(String secondary_text) {
        this.secondary_text = secondary_text;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(main_text);
        dest.writeList(main_text_matched_substrings);
        dest.writeValue(secondary_text);
    }

    public int describeContents() {
        return  0;
    }

}
