package com.francotte.go4lunch_opc.repositories.user_repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.francotte.go4lunch_opc.models.FavoriteRestaurant;
import com.francotte.go4lunch_opc.models.PlaceDetails.PlaceDetails;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.google_api.GoogleMapPlacesService;
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

    public static final String API_KEY = "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA";


    /**
     * --- CALLBACK ---
     */
    public interface CallbackFirestoreUsers {
        void onSuccessGetUsers(List<User> users);

        void onFailureGetUsers(Exception e);
    }

    public interface CallbackFirestoreUser {
        void onSuccessGetCurrentUser(User user);

        void onFailureGetCurrentUser();
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

    /**
     * --- METHOD ---
     */
    // Notification
    public static void getAllInformationToConstructNotification(final CallbackGetAllInformationToConstructNotification callback) {
        UserHelper.getUser(FirebaseAuth.getInstance().getUid())
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
                                                        if (user.getLunchPlaceID() != null) {
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
                        } else {
                            callback.onSuccessGetAllInformationToConstructNotification(user.getUserName(), null, null);
                        }
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetAllInformationToConstructNotification();
                    }
                });

    }

    // User
    public static void getTokenAtCurrentUser(final CallbackGetTokenAtCurrentUser callback) {
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

    public static void getAllUsers(final CallbackFirestoreUsers callback) {

        UserHelper.getAllUsers()
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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetUsers(e);
                    }
                });
    }

    public static void getUsersOfAPlace(final CallbackFirestoreUsers callback, final String placeID) {

        UserHelper.getAllUsersByPlaceId(placeID)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot querySnap : querySnapshot) {
                            User user = querySnap.toObject(User.class);
                            if (user.getLunchPlaceID() != null) {
                                users.add(user);
                            }
                        }
                        callback.onSuccessGetUsers(users);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetUsers(e);
                    }
                });
    }


    public static void getCurrentUser(final CallbackFirestoreUser callback) {
        UserHelper.getUser(FirebaseAuth.getInstance().getUid())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        callback.onSuccessGetCurrentUser(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetCurrentUser();
                    }
                });
    }

    public static void setUpdateDataRealTime(final CallbackFirestoreUsers callback) {
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
    public static void getAllFavoritePlaceOfAUser(final CallbackFirestoreFavorite callback, String userID) {
        FavoriteHelper.getAllPlaceIDFavorite(userID)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        List<FavoriteRestaurant> favorites = new ArrayList<>();
                        for (QueryDocumentSnapshot snap : querySnapshot) {
                            FavoriteRestaurant favoriteRestaurant = snap.toObject(FavoriteRestaurant.class);
                            favorites.add(favoriteRestaurant);
                        }
                        callback.onSuccessGetAllFavoriteOfTheUser(favorites);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailureGetAllFavoriteOfTheUser();
                    }
                });
    }

}
