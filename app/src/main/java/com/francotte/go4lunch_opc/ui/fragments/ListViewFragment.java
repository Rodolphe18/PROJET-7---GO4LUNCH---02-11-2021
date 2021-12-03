package com.francotte.go4lunch_opc.ui.fragments;


import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francotte.go4lunch_opc.DI.InjectionMain;
import com.francotte.go4lunch_opc.DI.MainViewModelFactory;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.repositories.user_repository.FirestoreCall;
import com.francotte.go4lunch_opc.models.NearbySearch.Result;
import com.francotte.go4lunch_opc.models.NearbySearch.ResultsPlaces;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.google_api.GoogleMapPlacesCall;
import com.francotte.go4lunch_opc.ui.activities.MainActivity;
import com.francotte.go4lunch_opc.ui.adaptors.AdaptorListViewRestaurant;
import com.francotte.go4lunch_opc.utils.UtilsListRestaurant;
import com.francotte.go4lunch_opc.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewFragment extends Fragment implements GoogleMapPlacesCall.CallbacksFetchNearbyPlace, FirestoreCall.CallbackFirestore {

    //UI
    private TextView mTextNoRestaurant;
    private ProgressDialog progressDialog;
    //ADAPTER
    private AdaptorListViewRestaurant mAdapterListView;
    //VIEW MODEL
    private MainViewModel viewModel;
    //MAPS USER, String = idPlace, Integer = nb user jointed
    private final Map<String, Integer> mapUserByPlaceID = new HashMap<>();

   // private List<Result> restaurantListFromPlaces;

   //private Location currentLocation;

    public ListViewFragment() {
    }

 // private ListViewFragment(Location location) {
   //    this.currentLocation = location;
  // }

  // public static ListViewFragment newInstance(Location location) {
    //    return new ListViewFragment(location);
   // }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_view, container, false);

        configureToolbar(root);
        configureViewModel();
        initPlaces();
        initUI(root);
        getAllUsers();
        return root;
    }

    // configure information into toolbar
    private void configureToolbar(View root) {
        MainActivity activity = ((MainActivity) root.getContext());
        activity.getSupportActionBar().setTitle(R.string.main_activity_title);
    }

    // Configuring the view model to using
    private void configureViewModel() {
        MainViewModelFactory mMainViewModelFactory = InjectionMain.provideViewModelFactory(getActivity());
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) this, mMainViewModelFactory).get(MainViewModel.class);

    }


    // Configure UI with information
    private void initUI(View root) {
        // SHOW LOADING DIALOG
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();

        RecyclerView mRecyclerViewRestaurant = root.findViewById(R.id.fragment_list_view_recycler_view);

        mAdapterListView = new AdaptorListViewRestaurant();
        mRecyclerViewRestaurant.setAdapter(mAdapterListView);
        mRecyclerViewRestaurant.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTextNoRestaurant = root.findViewById(R.id.fragment_list_view_no_restaurant_text);
        mTextNoRestaurant.setEnabled(true);
    }

    // Get all users to show number jointed a place
    private void getAllUsers() {
        FirestoreCall.getAllUsers(this);
        FirestoreCall.setUpdateDataRealTime(this);
    }

    // Get current location of and call Api Place with current location
    private void initPlaces() {
        Task<Location> locationTask = viewModel.getUserLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                callAPIPlaces(location.getLatitude() + " " + location.getLongitude());
            }
        });
    }

    // Get All place proximity
    private void callAPIPlaces(String location) {
        viewModel.getNearbyPlaces(this, location);
    }

    // Response of Method 'getAllUsers' - init mapUserByPlaceID with users.getLunchPlaceID && Call initPlace()
    @Override
    public void onSuccessGetUsers(List<User> users) {
        mapUserByPlaceID.clear();
        for (User user : users) {
            final Integer val = mapUserByPlaceID.get(user.getLunchPlaceID());
            final Integer newVal = val == null ? 1 : val + 1;
            mapUserByPlaceID.put(user.getLunchPlaceID(), newVal);
        }
        initPlaces();
    }

    @Override
    public void onFailureGetUsers(Exception e) {

    }

    // Response of Method callApiPlace - get place && update adapter with place
    @Override
    public void onResponseFetchNearbyPlace(@Nullable ResultsPlaces places) {
        if (places != null) {
            if (places.getResults().size() > 0) {
                mTextNoRestaurant.setEnabled(false);
            } else {
                mTextNoRestaurant.setEnabled(true);
            }
            List<Result> restaurantList = places.getResults();

        //    UtilsListRestaurant.updateDistanceToCurrentLocation(currentLocation, restaurantList);


           // Location restaurantLocation = new Location("fusedLocationProvider");
           // int size = restaurantList.size();
           // for (int i = 0; i < size; i++)
           // {
                //Get the restaurant's location
              //  currentLocation = viewModel.getUserLastLocation();
               // restaurantLocation.setLatitude(restaurantList.get(i).getGeometry().getLocation().getLat());
               // restaurantLocation.setLongitude(restaurantList.get(i).getGeometry().getLocation().getLat());
                //Get the distance between currentLocation and restaurantLocation
               // int distanceLocation = (int) currentLocation.distanceTo(restaurantLocation);
               // restaurantList.get(i).setDistanceCurrentUser(distanceLocation);
           // }

            mAdapterListView.updateData(restaurantList, mapUserByPlaceID);
            // SHOW LOADING DIALOG
            progressDialog.dismiss();
        }
    }

    @Override
    public void onFailureFetchNearbyPlace(Throwable t) {
        Log.e("ListView", t.getMessage());
    }

}