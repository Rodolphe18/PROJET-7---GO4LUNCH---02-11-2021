
package com.francotte.go4lunch_opc.models.PlaceAutoComplete;

import java.io.Serializable;
import java.util.List;

import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Prediction implements Serializable, Parcelable
{


    private String description;
    private List<Matched_substring> matched_substrings = null;
    private String place_id;
    private String reference;
    private Structured_formatting structured_formatting;
    private List<Term> terms = null;
    private List<String> types = null;
    public final static Creator<Prediction> CREATOR = new Creator<Prediction>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Prediction createFromParcel(android.os.Parcel in) {
            return new Prediction(in);
        }

        public Prediction[] newArray(int size) {
            return (new Prediction[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4217909588736470617L;

    protected Prediction(android.os.Parcel in) {
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.place_id = ((String) in.readValue((String.class.getClassLoader())));
        this.reference = ((String) in.readValue((String.class.getClassLoader())));
        this.structured_formatting = ((Structured_formatting) in.readValue((Structured_formatting.class.getClassLoader())));
        in.readList(this.terms, (Term.class.getClassLoader()));
        in.readList(this.types, (String.class.getClassLoader()));
    }

    public Prediction() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Matched_substring> getMatched_substrings() {
        return matched_substrings;
    }

    public void setMatched_substrings(List<Matched_substring> matched_substrings) {
        this.matched_substrings = matched_substrings;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Structured_formatting getStructured_formatting() {
        return structured_formatting;
    }

    public void setStructured_formatting(Structured_formatting structured_formatting) {
        this.structured_formatting = structured_formatting;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(description);
        dest.writeList(matched_substrings);
        dest.writeValue(place_id);
        dest.writeValue(reference);
        dest.writeValue(structured_formatting);
        dest.writeList(terms);
        dest.writeList(types);
    }

    public int describeContents() {
        return  0;
    }

}
