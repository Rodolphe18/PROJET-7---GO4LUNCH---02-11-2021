package com.francotte.go4lunch_opc;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.francotte.go4lunch_opc.models.FavoriteRestaurant;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.user_repository.FavoriteHelper;
import com.francotte.go4lunch_opc.repositories.user_repository.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FirestoreHelperTest {

    /**
     * This is test CRUD on collection Users
     */
    //CREATE
    @Test
    public void createAUser (){

        UserHelper.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                // GET USERS BEFORE CREATE THE USER
                final List<User> userListBeforeCreate = new ArrayList<>();

                for(QueryDocumentSnapshot snap : querySnapshot){
                    User user = snap.toObject(User.class);
                    userListBeforeCreate.add(user);
                }
                Log.d("FirebaseInstrumentedTest", "size : " + userListBeforeCreate.size());

                //CREATE A USER
                final String uid = "user_test_id" + userListBeforeCreate.size();
                final String username = "username_test" + userListBeforeCreate.size();
                final String urlPicture = "https://www.istockphoto.com/fr/photo/coin-en-bois-de-cuisine-et-de-salle-%C3%A0-manger-gm1302319417-394098815";


                UserHelper.createUser(uid, username, urlPicture, null);

                // GET USERS AFTER CREATE THE USER
                UserHelper.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {

                        List<User> userListAfterCreate = new ArrayList<>();

                        for(QueryDocumentSnapshot snap : querySnapshot){
                            User user = snap.toObject(User.class);
                            userListAfterCreate.add(user);
                        }
                        Log.d("FirebaseInstrumentedTest", "size : " + userListAfterCreate.size());

                        assertEquals(userListBeforeCreate.size() + 1, userListAfterCreate.size());

                        User user = userListAfterCreate.get(userListAfterCreate.size() - 1);

                        assertEquals(user.getUserId(), uid);
                        assertEquals(user.getUserName(), username);
                        assertEquals(user.getUrlPicture(), urlPicture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseInstrumentedTest", e.getMessage());
                        fail();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseInstrumentedTest", e.getMessage());
                fail();
            }
        });

    }
    //READ
    @Test
    public void getAllUsers() {

        UserHelper.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                List<User> userList = new ArrayList<>();
                for(QueryDocumentSnapshot snap : querySnapshot){
                    User user = snap.toObject(User.class);
                    userList.add(user);
                }
                Log.d("FirebaseInstrumentedTest", "size : " + userList.size());
                assertTrue(userList.size() > 0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseInstrumentedTest", e.getMessage());
                fail();
            }
        });
    }
    //UPDATE
    @Test
    public void updateAUser (){

        UserHelper.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                //CREATE A USER
                final String uid = "user_test_id";
                final String username = "username_test";
                final String urlPicture = "https://www.istockphoto.com/fr/photo/coin-en-bois-de-cuisine-et-de-salle-%C3%A0-manger-gm1302319417-394098815";

                UserHelper.createUser(uid, username, urlPicture, null);

                // GET USERS BEFORE UPDATE THE USER
                final List<User> userListBeforeUpdate = new ArrayList<>();

                for(QueryDocumentSnapshot snap : querySnapshot){
                    User users = snap.toObject(User.class);
                    userListBeforeUpdate.add(users);
                }

                // GET USERS AFTER CREATE THE USER
                UserHelper.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {

                        final String placeId = "placeID";
                        final Date date = new Date();

                        UserHelper.updateUserLunchPlace(uid, placeId, date);

                        List<User> userListAfterUpdate = new ArrayList<>();

                        for(QueryDocumentSnapshot snap : querySnapshot){
                            User user = snap.toObject(User.class);
                            userListAfterUpdate.add(user);
                        }

                        User user = userListAfterUpdate.get(userListAfterUpdate.size() - 1);

                        assertEquals(user.getUserId(), uid);
                        assertEquals(user.getLunchPlaceID(), placeId);
                        assertNotEquals(user.getDateLunchPlace(), null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseInstrumentedTest", e.getMessage());
                        fail();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseInstrumentedTest", e.getMessage());
                fail();
            }
        });

    }
    //DELETE
    @Test
    public void deleteAUser (){

        UserHelper.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                final String uid = "user_test_id_to_delete";
                //CREATE A USER
                UserHelper.createUser(uid ,"username_test_to_delete" , "https://www.istockphoto.com/fr/photo/coin-en-bois-de-cuisine-et-de-salle-%C3%A0-manger-gm1302319417-394098815", null);
                // GET USERS BEFORE DELETE THE USER
                final List<User> userListBeforeDelete = new ArrayList<>();

                for(QueryDocumentSnapshot snap : querySnapshot){
                    User user = snap.toObject(User.class);
                    userListBeforeDelete.add(user);
                }
                //DELETE THE USER
                UserHelper.deleteUser(uid);
                // GET USERS AFTER DELETE THE USER
                UserHelper.getAllUsers().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {

                        List<User> userListAfterDelete = new ArrayList<>();

                        for(QueryDocumentSnapshot snap : querySnapshot){
                            User users = snap.toObject(User.class);
                            userListAfterDelete.add(users);
                        }
                        assertEquals(userListBeforeDelete.size(), userListAfterDelete.size()  + 1);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseInstrumentedTest", e.getMessage());
                        fail();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseInstrumentedTest", e.getMessage());
                fail();
            }
        });
    }

    /**
     * This is test to add en suppress favorite at a user
     */
    // CREATE
    @Test
    public void addAFavorite (){

        //CREATE A USER
        final String uid = "user_test_id";
        final String username = "username_test";
        final String urlPicture = "https://www.istockphoto.com/fr/photo/coin-en-bois-de-cuisine-et-de-salle-%C3%A0-manger-gm1302319417-394098815";

        UserHelper.createUser(uid, username, urlPicture, null);

        final String placeId = "placeID";
        final String placeName = "placeName";
        final String placeUrl = "placeUrl";

        FavoriteHelper.addFavoritePlace(uid, placeId, placeName, placeUrl);

        FavoriteHelper.getAllPlaceIDFavorite(uid).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                List<FavoriteRestaurant> favorites = new ArrayList<>();
                for(QueryDocumentSnapshot snap : querySnapshot){
                    FavoriteRestaurant favorite = snap.toObject(FavoriteRestaurant.class);
                    favorites.add(favorite);
                }

                FavoriteRestaurant favoriteAdded = favorites.get(favorites.size() - 1);

                assertEquals(favoriteAdded.getIdPlace(), placeId);
                assertEquals(favoriteAdded.getNamePlace(), placeName);
                assertEquals(favoriteAdded.getUrlPlace(), placeUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fail();
            }
        });

    }
    @Test
    public void removeAFavorite (){

        //CREATE A USER
        final String uid = "user_test_id";
        final String username = "username_test";
        final String urlPicture = "https://www.istockphoto.com/fr/photo/coin-en-bois-de-cuisine-et-de-salle-%C3%A0-manger-gm1302319417-394098815";

        UserHelper.createUser(uid, username, urlPicture, null);

        final String placeId = "placeID";
        final String placeName = "placeName";
        final String placeUrl = "placeUrl";

        FavoriteHelper.addFavoritePlace(uid, placeId, placeName, placeUrl);

        FavoriteHelper.getAllPlaceIDFavorite(uid).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                for(QueryDocumentSnapshot snap : querySnapshot){
                    FavoriteRestaurant favorite = snap.toObject(FavoriteRestaurant.class);
                    FavoriteHelper.deleteFavoritePlace(uid, favorite.getIdPlace());
                }

                FavoriteHelper.getAllPlaceIDFavorite(uid).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        assertEquals(querySnapshot.size(), 0);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fail();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fail();
            }
        });
    }


}