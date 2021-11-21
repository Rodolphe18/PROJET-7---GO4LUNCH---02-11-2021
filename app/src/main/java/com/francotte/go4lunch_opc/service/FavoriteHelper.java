package com.francotte.go4lunch_opc.service;



import com.francotte.go4lunch_opc.models.FavoriteRestaurant;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;



public class FavoriteHelper {

    public static final String NAME_COLLECTION = "favorite";

    public static Task<QuerySnapshot> getAllPlaceIDFavorite (String idUser){
        return UserHelper.getUsersCollection()
                .document(idUser)
                .collection(NAME_COLLECTION).get();
    }
    public static Task<Void> addFavoritePlace (String idUser, String idPlace, String namePlace, String urlPlace){
        FavoriteRestaurant favoriteRestaurant = new FavoriteRestaurant(idPlace, namePlace, urlPlace);
        return UserHelper.getUsersCollection()
                .document(idUser)
                .collection(NAME_COLLECTION)
                .document(idPlace)
                .set(favoriteRestaurant, SetOptions.merge());
    }
    public static Task<Void> deleteFavoritePlace (String idUser, String idPlace){
        return  UserHelper.getUsersCollection()
                .document(idUser)
                .collection(NAME_COLLECTION)
                .document(idPlace)
                .delete();
    }
}
