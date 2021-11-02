package com.francotte.go4lunch_opc.repository;

import android.content.Context;

import com.firebase.ui.auth.AuthUI;
import com.francotte.go4lunch_opc.models.UsersWithoutPlaceId;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Date;

import static com.squareup.okhttp.internal.Internal.instance;

public class UserRepository {

    private static final String COLLECTION_NAME = "users";
    private static UserRepository instance;
    private UserRepository() {}
    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UserRepository.class) {
            if (instance == null) {
                instance = new UserRepository();
            }
            return instance;
        }
    }

    public FirebaseUser getCurrentUser() { return FirebaseAuth.getInstance().getCurrentUser(); }

    public String getCurrentUserUID() {
        FirebaseUser user = getCurrentUser();
        return (user != null)? user.getUid() : null;
    }

    public Task<Void> signOutUserFromFirebase (Context context) {return AuthUI.getInstance().signOut(context); }




    public static CollectionReference getUsersCollection(){return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);}

    //CREATE
    public static Task<Void> createUser(String uid, String username, String urlPicture, String token){
        UsersWithoutPlaceId usersWithoutPlaceId = new UsersWithoutPlaceId(uid,username, urlPicture, token);
        //Date date = new Date();
        //Users users = new Users(uid, username, urlPicture, token, null, date);
        return UserRepository.getUsersCollection().document(uid).set(usersWithoutPlaceId, SetOptions.merge());
    }
    //GET
    public static Task<DocumentSnapshot> getUser(String uid){
        return UserRepository.getUsersCollection().document(uid).get();
    }

    //GET
    public static Task<QuerySnapshot> getAllUsers(){
        return UserRepository.getUsersCollection().get();
    }

    //DELETE
    public void deleteUserFromFirebase() {
        String uid = this.getCurrentUserUID();
        if (uid != null){
            this.getUsersCollection().document(uid).delete();
        }
    }
}


