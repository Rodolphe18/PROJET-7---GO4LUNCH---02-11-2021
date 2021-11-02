
package com.francotte.go4lunch_opc.models.NEARBYSEARCH;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable.Creator;

public class GoogleMapPlace implements Serializable
{


    private List<Object> html_attributions = null;
    private String next_page_token;
    private List<Result> results = null;
    private String status;
    public final static Creator<GoogleMapPlace> CREATOR = new Creator<GoogleMapPlace>() {

        public GoogleMapPlace createFromParcel(android.os.Parcel in) {
            return new GoogleMapPlace(in);
        }

        public GoogleMapPlace[] newArray(int size) {
            return (new GoogleMapPlace[size]);
        }

    }
    ;
    private final static long serialVersionUID = -355421996691157913L;

    protected GoogleMapPlace(android.os.Parcel in) {
        in.readList(this.html_attributions, (Object.class.getClassLoader()));
        this.next_page_token = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.results, (Result.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public GoogleMapPlace() {
    }

    public List<Object> getHtml_attributions() {
        return html_attributions;
    }



    public void setHtml_attributions(List<Object> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(html_attributions);
        dest.writeValue(next_page_token);
        dest.writeList(results);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
