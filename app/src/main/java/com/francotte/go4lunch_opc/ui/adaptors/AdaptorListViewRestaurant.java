package com.francotte.go4lunch_opc.ui.adaptors;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.francotte.go4lunch_opc.DI.InjectionMain;
import com.francotte.go4lunch_opc.DI.MainViewModelFactory;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.models.NearbySearch.Result;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.user_repository.UserHelper;
import com.francotte.go4lunch_opc.ui.activities.DetailRestaurantActivity;
import com.francotte.go4lunch_opc.utils.LocationRepository;
import com.francotte.go4lunch_opc.utils.UtilsListRestaurant;
import com.francotte.go4lunch_opc.viewmodel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdaptorListViewRestaurant extends RecyclerView.Adapter<AdaptorListViewRestaurant.RestaurantViewHolder> {

    private List<Result> places = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();



    @NotNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view, map, context);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.bind(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    // To update data of this adapter
    public void updateData(List<Result> data, Map<String, Integer> map) {
        this.map = map;
        this.places = data;
        notifyDataSetChanged();
    }


    protected static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout item;
        private final ImageView icon;
        private final TextView name;
        private final TextView address;
        private final TextView hour;
        private final TextView distanceRestaurant;
        private final TextView numberWorkmates;
        private final ImageView star1;
        private final ImageView star2;
        private final ImageView star3;

        private Context context;

        private LocationRepository locationRepository;

        private final Map<String, Integer> map;

        private final String EXTRA_NAME = "place_id";

        public RestaurantViewHolder(View itemView, Map<String, Integer> map, Context context) {
            super(itemView);
            this.map = map;
            this.context = context;
            item = itemView.findViewById(R.id.item_restaurant);
            name = itemView.findViewById(R.id.item_restaurant_name);
            icon = itemView.findViewById(R.id.item_restaurant_icon);
            address = itemView.findViewById(R.id.item_restaurant_address);
            hour = itemView.findViewById(R.id.item_restaurant_open_hour);
            distanceRestaurant = itemView.findViewById(R.id.item_restaurant_distance);
            numberWorkmates = itemView.findViewById(R.id.item_restaurant_nb_workmate);
            star1 = itemView.findViewById(R.id.item_list_restaurant_star_1_image);
            star2 = itemView.findViewById(R.id.item_list_restaurant_star_2_image);
            star3 = itemView.findViewById(R.id.item_list_restaurant_star_3_image);
        }

        public void bind(final Result place) {
            name.setText(place.getName());
            if (place.getVicinity() != null) {
                address.setText(place.getVicinity());
            }
            if (place.getOpening_hours() != null) {

                Calendar calendar = Calendar.getInstance();

                int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

                String openingHour = "";
                if (place.getOpening_hours().getWeekdayText() != null) {
                    openingHour += (String) place.getOpening_hours().getWeekdayText().get(currentDay);
                }
                String isOpen;
                if (place.getOpening_hours().getOpenNow()) {
                    isOpen = itemView.getContext().getResources().getString(R.string.place_opening);
                } else {
                    isOpen = itemView.getContext().getResources().getString(R.string.place_closed);
                }
                openingHour += " " + isOpen;
                hour.setText(openingHour);

                // NOMBRE DE STARS

             //   UtilsListRestaurant.updateRating(star1, star2, star3, place);

                // DISTANCE DE l'UTILISATEUR PAR RAPPORT AU RESTAURANT

                float distance;
                float results[] = new float[10];
                double restaurantLat = Objects.requireNonNull(place.getGeometry().getLocation().getLat());
                double restaurantLng = Objects.requireNonNull(place.getGeometry().getLocation().getLng());
                double myLatitude = getLocation().getLatitude();
                double myLongitude = getLocation().getLongitude();
                Location.distanceBetween(myLatitude, myLongitude, restaurantLat, restaurantLng, results);
                distance = results[0];
                String dist = Math.round(distance) + "m";
                distanceRestaurant.setText(dist);




            if (place.getPhotos() != null && place.getPhotos().size() > 0) {
                String baseUrlPhoto = "https://maps.googleapis.com/maps/api/place/photo";
                String widthPhoto = "?maxwidth=" + place.getPhotos().get(0).getWidth();
                String referencePhoto = "&photoreference=" + place.getPhotos().get(0).getPhotoReference();
                String url = baseUrlPhoto + widthPhoto + referencePhoto + "&key=" + "AIzaSyDDNE6N-Ltg6BZQTyLt5Rfcs6ogJO0ZBGA";
                Glide.with(itemView.getContext())
                        .load(url)
                        .into(icon);
            }

            item.setOnClickListener(new View.OnClickListener() {
                 @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailRestaurantActivity.class);
                    intent.putExtra(EXTRA_NAME, place.getPlace_id());
                    view.getContext().startActivity(intent);
                }
            });

            int nbWorkmates = 0;
            if (map.get(place.getPlace_id()) != null) {
                nbWorkmates = map.get(place.getPlace_id());
            }
            numberWorkmates.setText("(" + nbWorkmates + ")");
        }
    }


        private LocationRepository getLocation() {
            locationRepository = new LocationRepository(context);
            if (locationRepository.canGetLocation()) {
            } else {
                locationRepository.showSettingsAlert();
            }
            return locationRepository;
        }

}
}
