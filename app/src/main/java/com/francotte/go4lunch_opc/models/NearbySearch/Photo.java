
package com.francotte.go4lunch_opc.models.NearbySearch;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;



public class Photo implements Serializable, Parcelable
{


    private Integer height;

    private List<String> html_attributions = null;

    private String photo_reference;

    private Integer width;
    public final static Creator<Photo> CREATOR = new Creator<Photo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Photo createFromParcel(android.os.Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return (new Photo[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3049341499952841722L;

    protected Photo(android.os.Parcel in) {
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.html_attributions, (String.class.getClassLoader()));
        this.photo_reference = ((String) in.readValue((String.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Photo() {
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getPhotoReference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(height);
        dest.writeList(html_attributions);
        dest.writeValue(photo_reference);
        dest.writeValue(width);
    }

    public int describeContents() {
        return  0;
    }

}
