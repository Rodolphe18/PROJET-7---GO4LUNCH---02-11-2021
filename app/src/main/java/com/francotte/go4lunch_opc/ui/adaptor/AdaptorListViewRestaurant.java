package com.francotte.go4lunch_opc.ui.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.models.NEARBYSEARCH.Result;
import com.francotte.go4lunch_opc.ui.DetailRestaurantActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptorListViewRestaurant extends RecyclerView.Adapter<AdaptorListViewRestaurant.RestaurantViewHolder> {

    private List<Result> places = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();


    @NotNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view, map);
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
        private final TextView information;
        private final TextView hour;
        private final TextView numberWorkmates;

        private final Map<String, Integer> map;

        private final String EXTRA_NAME = "place_id";

        public RestaurantViewHolder(View itemView, Map<String, Integer> map) {
            super(itemView);
            this.map = map;
            item = itemView.findViewById(R.id.item_restaurant);
            icon = itemView.findViewById(R.id.item_restaurant_icon);
            name = itemView.findViewById(R.id.item_restaurant_name);
            information = itemView.findViewById(R.id.item_restaurant_information);
            hour = itemView.findViewById(R.id.item_restaurant_open_hour);
            TextView distance = itemView.findViewById(R.id.item_restaurant_distance);
            numberWorkmates = itemView.findViewById(R.id.item_restaurant_nb_workmate);
        }

        public void bind(final Result place) {
            name.setText(place.getName());
            if (place.getScope() != null) {
                information.setText(place.getScope());
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
            }
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
            if(map.get(place.getPlace_id()) != null) {
                nbWorkmates = map.get(place.getPlace_id());
            }
            numberWorkmates.setText("("+nbWorkmates+")");
        }
    }
}
