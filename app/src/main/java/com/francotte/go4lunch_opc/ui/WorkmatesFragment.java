package com.francotte.go4lunch_opc.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.firestore.FirestoreCall;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.ui.adaptor.AdaptorListViewWorkmates;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class WorkmatesFragment extends Fragment implements FirestoreCall.CallbackFirestore, FirestoreCall.CallbackFirestoreUser {

    // ADAPTER
    private AdaptorListViewWorkmates mAdapterListView;
    // PROGRESS DIALOG
    ProgressDialog progressDialog;
    //JUST FOR PRESENTATION
    ExtendedFloatingActionButton eFab;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workmates, container, false);

        configureToolbar(root);
        initUI(root);
        getAllUsers();
        //JUST FOR PRESENTATION
        getObjCurrentUser();
        return root;
    }

    // Configure information into toolbar
    private void configureToolbar (View root){
        MainActivity activity = ((MainActivity)root.getContext());
        activity.getSupportActionBar().setTitle(R.string.main_activity_title_workmates);
    }

    // Configure UI with information
    private void initUI(View root) {
        // SHOW LOADING DIALOG
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        // Recycler view
        RecyclerView mRecyclerViewWorkmates = root.findViewById(R.id.fragment_workmates_recycler_view);
        //JUST FOR PRESENTATION
        eFab = root.findViewById(R.id.fragment_workmates_send_notification);

        mAdapterListView = new AdaptorListViewWorkmates(getActivity(), true);
        mRecyclerViewWorkmates.setAdapter(mAdapterListView);
        mRecyclerViewWorkmates.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // Get all user of this application
    private void getAllUsers(){
        FirestoreCall.getAllUsers(this);
        FirestoreCall.setUpdateDataRealTime(this);
    }

    private void getObjCurrentUser() {
        FirestoreCall.getCurrentUser(this);
    }


    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // Response of Method 'getAllUsers' - List user
    @Override
    public void onSuccessGetUsers(List<User> users) {
        String currentUserID = getCurrentUser().getUid();
        List<User> workmatesList = new ArrayList<>();
        for(User user : users){
            if(!user.getUserId().equals(currentUserID)){
                workmatesList.add(user);
            }
        }
        mAdapterListView.updateData(workmatesList);
        progressDialog.dismiss();
    }
    @Override
    public void onFailureGetUsers(Exception e) {
        Log.e("WorkmatesFragment", e.getMessage());
    }

    //JUST FOR TEST
    @Override
    public void onSuccessGetCurrentUser(final User user) {
        eFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void onFailureGetCurrentUser() {
    }
}