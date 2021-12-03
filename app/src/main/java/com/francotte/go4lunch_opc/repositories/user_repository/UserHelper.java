package com.francotte.go4lunch_opc.repositories.user_repository;

import com.francotte.go4lunch_opc.models.UserWithoutPlaceId;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Date;

public class UserHelper {

    private static final String COLLECTION_NAME = "user";


    // GET ALL
    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    //CREATE
    public static Task<Void> createUser(String uid, String username, String urlPicture, String token) {
        UserWithoutPlaceId userWithoutPlaceId = new UserWithoutPlaceId(uid, username, urlPicture, token);
        //Date date = new Date();
        //Users users = new Users(uid, username, urlPicture, token, null, date);
        return UserHelper.getUsersCollection().document(uid).set(userWithoutPlaceId, SetOptions.merge());
    }

    //GET
    public static Task<DocumentSnapshot> getUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).get();
    }

    //GET
    public static Task<QuerySnapshot> getUsersOfADiscussionByID(String uid1, String uid2) {
        return UserHelper.getUsersCollection().whereIn("userId", Arrays.asList(uid1, uid2)).get();
    }

    //GET
    public static Task<QuerySnapshot> getAllUsers() {
        return UserHelper.getUsersCollection().get();
    }

    //GET
    public static Task<QuerySnapshot> getAllUsersByPlaceId(String placeID) {
        return UserHelper.getUsersCollection().whereEqualTo("lunchPlaceID", placeID).get();
    }

    //UPDATE
    public static Task<Void> updateUserLunchPlace(String uid, String placeId, Date timestampLunch) {
        return UserHelper.getUsersCollection().document(uid).update("lunchPlaceID", placeId, "dateLunchPlace", timestampLunch);
    }

    //UPDATE
    public static Task<Void> updateToken(String uid, String token) {
        return UserHelper.getUsersCollection().document(uid).update("token", token);
    }

    //DELETE
    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}
