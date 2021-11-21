package com.francotte.go4lunch_opc.models;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Calendar;
import java.util.Date;

public class User {

    //USER INFORMATION
    private String userId;
    private String username;
    @Nullable
    private String userUrlPicture;
    //TOKEN
    private String token;
    //PLACE INFORMATION
    @Nullable
    private String userLunchPlaceID;
    @ServerTimestamp
    @Nullable
    Date userDateLunchPlaceChoice;


    public User(){

    }
    public User(String userId, String username, @Nullable String urlPicture, String token, @Nullable String userLunchPlaceID, @Nullable Date userDateLunchPlaceChoice) {
        this.userId = userId;
        this.username = username;
        this.userUrlPicture = urlPicture;
        this.token = token;
        this.userLunchPlaceID = userLunchPlaceID;
        this.userDateLunchPlaceChoice = userDateLunchPlaceChoice;
    }

    //GETTER
    public String getUserId() {
        return userId;
    }
    public String getUserName() {
        return username;
    }
    @Nullable
    public String getUrlPicture() {
        return userUrlPicture;
    }
    @Nullable
    public String getLunchPlaceID() {

        if(userDateLunchPlaceChoice != null){

            Calendar currentCal= Calendar.getInstance();
            Calendar calLunchPlace = Calendar.getInstance();
            calLunchPlace.setTime(userDateLunchPlaceChoice);

            if(currentCal.get(Calendar.DAY_OF_MONTH) == calLunchPlace.get(Calendar.DAY_OF_MONTH)){
                return userLunchPlaceID;
            }else{
                return null;
            }
        }else{
            return null;
        }
        //return lunchPlaceID;

    }
    @Nullable
    public Date getDateLunchPlace(){
        return userDateLunchPlaceChoice;
    }
    public String getToken (){ return token; }
    //SETTER
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUserName(String username) {
        this.username = username;
    }
    public void setUrlPicture(@Nullable String urlPicture) {
        this.userUrlPicture = urlPicture;
    }
    public void setLunchPlaceID(@Nullable String lunchPlaceID) {
        this.userLunchPlaceID = lunchPlaceID;
    }
    public void setDateLunchPlace(@Nullable Date dateLunchPlaceChoice){ this.userDateLunchPlaceChoice = dateLunchPlaceChoice; }
    public void setToken (String token){ this.token = token; }

}
