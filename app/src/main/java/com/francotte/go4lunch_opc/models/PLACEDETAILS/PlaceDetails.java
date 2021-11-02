
package com.francotte.go4lunch_opc.models.PLACEDETAILS;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;

import com.google.android.gms.common.api.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PlaceDetails implements Serializable, Parcelable
{

    @SerializedName("html_attributions")
    @Expose
    private List<Object> html_attributions = null;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<PlaceDetails> CREATOR = new Creator<PlaceDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PlaceDetails createFromParcel(android.os.Parcel in) {
            return new PlaceDetails(in);
        }

        public PlaceDetails[] newArray(int size) {
            return (new PlaceDetails[size]);
        }

    }
    ;
    private final static long serialVersionUID = 127717134142870259L;

    protected PlaceDetails(android.os.Parcel in) {
        in.readList(this.html_attributions, (java.lang.Object.class.getClassLoader()));
        this.result = ((Result) in.readValue((Result.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PlaceDetails() {
    }

    public List<Object> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<Object> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(html_attributions);
        dest.writeValue(result);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
