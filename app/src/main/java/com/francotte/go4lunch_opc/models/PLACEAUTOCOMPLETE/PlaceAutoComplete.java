
package com.francotte.go4lunch_opc.models.PLACEAUTOCOMPLETE;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PlaceAutoComplete implements Serializable, Parcelable
{


    private List<Prediction> predictions = null;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<PlaceAutoComplete> CREATOR = new Creator<PlaceAutoComplete>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PlaceAutoComplete createFromParcel(android.os.Parcel in) {
            return new PlaceAutoComplete(in);
        }

        public PlaceAutoComplete[] newArray(int size) {
            return (new PlaceAutoComplete[size]);
        }

    }
    ;
    private final static long serialVersionUID = 5785903390273594019L;

    protected PlaceAutoComplete(android.os.Parcel in) {
        in.readList(this.predictions, (Prediction.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PlaceAutoComplete() {
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(predictions);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
