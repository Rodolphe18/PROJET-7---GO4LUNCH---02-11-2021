package com.francotte.go4lunch_opc.firestore;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.francotte.go4lunch_opc.models.Discussion;
import com.francotte.go4lunch_opc.models.FavoriteRestaurant;
import com.francotte.go4lunch_opc.models.PLACEDETAILS.PlaceDetails;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.service.GoogleMapPlacesService;
import com.francotte.go4lunch_opc.service.UserHelper;
import com.francotte.go4lunch_opc.service.FavoriteHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirestoreCall {

    public static final String API_KEY = "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA" ;


    /** --- CALLBACK --- */
    public interface CallbackFirestore {
        void onSuccessGetUsers(List<User> users);
        void onFailureGetUsers(Exception e);
    }
    public interface CallbackFirestoreUser {
        void onSuccessGetCurrentUser(User user);
        void onFailureGetCurrentUser();
    }
    public interface CallbackFirestoreUsersOfDiscussion {
        void onSuccessGetUsersOfTheDiscussion (User uid1, User uid2);
        void onFailureGetUsersOfTheDiscussion (Exception e);
    }
    public interface CallbackFirestoreDiscussion {
        void onSuccessGetAllDiscussions(List<Discussion> discussions);
        void onFailureGetAllDiscussions(Exception e);
    }
    public interface CallbackFirestoreFavorite {
        void onSuccessGetAllFavoriteOfTheUser(List<FavoriteRestaurant> favorites);
        void onFailureGetAllFavoriteOfTheUser();
    }
    public interface CallbackGetTokenAtCurrentUser {
        void onFailureGetCurrentToken(Exception e);
        void onSuccessGetCurrentToken(String token);
    }
    public interface CallbackGetAllInformationToConstructNotification {
        void onSuccessGetAllInformationToConstructNotification(String name, String nameRestaurant, List<User> usersLunchByUser);
        void onFailureGetAllInformationToConstructNotification();
    }

    /** --- METHOD --- */
    // Notification
    public static void getAllInformationToConstructNotification (final CallbackGetAllInformationToConstructNotification callback){
        UserHelper.getUser(FirebaseAuth.getInstance().getUid())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetAllInformationToConstructNotification();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        final User user = documentSnapshot.toObject(User.class);

                        if (user.getLunchPlaceID() != null) {
                            GoogleMapPlacesService service = GoogleMapPlacesService.retrofitGetPlaceDetails.create(GoogleMapPlacesService.class);

                            Call<PlaceDetails> call = service.getPlaceDetails(user.getLunchPlaceID(), API_KEY);
                            call.enqueue(new Callback<PlaceDetails>() {
                                @Override
                                public void onResponse(@NotNull Call<PlaceDetails> call, @NotNull final Response<PlaceDetails> response) {

                                    final PlaceDetails detailPlace = response.body();

                                    UserHelper.getAllUsersByPlaceId(user.getLunchPlaceID())
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            })
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot querySnapshot) {
                                                    List<User> users = new ArrayList<>();
                                                    for (QueryDocumentSnapshot querySnap : querySnapshot) {
                                                        User user = querySnap.toObject(User.class);
                                                        if(user.getLunchPlaceID() != null) {
                                                            users.add(user);
                                                        }
                                                    }
                                                    callback.onSuccessGetAllInformationToConstructNotification(user.getUserName(), detailPlace.getResult().getName(), users);
                                                }
                                            });

                                }

                                @Override
                                public void onFailure(@NotNull Call<PlaceDetails> call, @NotNull Throwable t) {
                                    callback.onSuccessGetAllInformationToConstructNotification(user.getUserName(), null, null);
                                    Log.e("FirestoreCall", t.getMessage());
                                }
                            });
                        }else{
                            callback.onSuccessGetAllInformationToConstructNotification(user.getUserName(), null, null);
                        }
                    }
                });
    }
    // User
    public static void getTokenAtCurrentUser (final CallbackGetTokenAtCurrentUser callback){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {

                        callback.onSuccessGetCurrentToken(s);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetCurrentToken(e);
                    }
                });
    }
    public static void getAllUsers(final CallbackFirestore callback) {

        UserHelper.getAllUsers()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetUsers(e);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot querySnap : querySnapshot) {
                            User user = querySnap.toObject(User.class);
                            users.add(user);
                        }
                        callback.onSuccessGetUsers(users);
                    }
                });
    }
    public static void getUsersOfAPlace(final CallbackFirestore callback, final String placeID) {

        UserHelper.getAllUsersByPlaceId(placeID)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetUsers(e);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot querySnap : querySnapshot) {
                            User user = querySnap.toObject(User.class);
                            if(user.getLunchPlaceID() != null) {
                                users.add(user);
                            }
                        }
                        callback.onSuccessGetUsers(users);
                    }
                });
    }
    public static void getCurrentUser(final CallbackFirestoreUser callback) {
        UserHelper.getUser(FirebaseAuth.getInstance().getUid())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetCurrentUser();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        callback.onSuccessGetCurrentUser(user);
                    }
                });
    }
    public static void getUsersOfDiscussionByID (final CallbackFirestoreUsersOfDiscussion callback, final String uid1, final String uid2){
        UserHelper.getUsersOfADiscussionByID(uid1, uid2).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                List<User> users = new ArrayList<>();
                for(QueryDocumentSnapshot querySnap : querySnapshot){
                    users.add(querySnap.toObject(User.class));
                }
                callback.onSuccessGetUsersOfTheDiscussion(users.get(0), users.get(1));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailureGetUsersOfTheDiscussion(e);
            }
        });
    }
    public static void setUpdateDataRealTime(final CallbackFirestore callback) {
        UserHelper.getUsersCollection().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    callback.onFailureGetUsers(e);
                } else if (querySnapshot != null & !querySnapshot.isEmpty()) {
                    List<User> users = new ArrayList<>();
                    for (QueryDocumentSnapshot querySnap : querySnapshot) {
                        User user = querySnap.toObject(User.class);
                        users.add(user);
                    }
                    callback.onSuccessGetUsers(users);
                }
            }
        });
    }
    // FAVORITE
    public static void getAllFavoritePlaceOfAUser (final CallbackFirestoreFavorite callback, String userID){
        FavoriteHelper.getAllPlaceIDFavorite(userID)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<FavoriteRestaurant> favorites = new ArrayList<>();
                        for(QueryDocumentSnapshot snap : querySnapshot){
                            FavoriteRestaurant favoriteRestaurant = snap.toObject(FavoriteRestaurant.class);
                            favorites.add(favoriteRestaurant);
                        }
                        callback.onSuccessGetAllFavoriteOfTheUser(favorites);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailureGetAllFavoriteOfTheUser();
            }
        });
    }

}
