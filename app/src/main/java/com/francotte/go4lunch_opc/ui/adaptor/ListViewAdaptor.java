package com.francotte.go4lunch_opc.ui.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.models.Restaurant;
import com.francotte.go4lunch_opc.ui.DetailRestaurantActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewAdaptor extends RecyclerView.Adapter<ListViewAdaptor.RestaurantViewHolder> {

    private final List<Restaurant> restaurants;
    private Context context;

    public ListViewAdaptor(List<Restaurant> restaurants, Context context) {
        this.restaurants = restaurants;
        this.context = context;
    }


    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_view, parent, false);
        return new RestaurantViewHolder(view);
 }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant neighbour = restaurants.get(position);
        holder.restaurantName.setText(neighbour.getRestaurantName());
        holder.restaurantOpenHour.setText(neighbour.getRestaurantName());
        holder.restaurantNumberWorkmates.setText(neighbour.getRestaurantName());
        Glide.with(holder.restaurantIcon.getContext())
                .load(neighbour.getRestaurantIcon())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.restaurantIcon);

        holder.itemRestaurantListLayout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (context, DetailRestaurantActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_restaurant)
            public ConstraintLayout itemRestaurantListLayout;
            @BindView(R.id.item_restaurant_icon)
            public ImageView restaurantIcon;
            @BindView(R.id.item_restaurant_name)
            public TextView restaurantName;
            @BindView(R.id.item_restaurant_open_hour)
            public TextView restaurantOpenHour;
            @BindView(R.id.item_restaurant_nb_workmate)
            public TextView restaurantNumberWorkmates;


        public RestaurantViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
