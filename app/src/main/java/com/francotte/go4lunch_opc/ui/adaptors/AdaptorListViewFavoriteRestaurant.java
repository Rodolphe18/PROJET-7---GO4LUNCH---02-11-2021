package com.francotte.go4lunch_opc.ui.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.models.FavoriteRestaurant;
import com.francotte.go4lunch_opc.repositories.user_repository.FavoriteHelper;
import com.francotte.go4lunch_opc.ui.activities.DetailRestaurantActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdaptorListViewFavoriteRestaurant extends RecyclerView.Adapter<AdaptorListViewFavoriteRestaurant.viewHolderFavoriteRestaurant> {

    private List<FavoriteRestaurant> favorites = new ArrayList<>();
    private final String uid;


    public AdaptorListViewFavoriteRestaurant(String uid) {
        this.uid = uid;
    }

    @NotNull
    @Override
    public viewHolderFavoriteRestaurant onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_favorite, parent, false);
        return new viewHolderFavoriteRestaurant(view, uid);
    }

    @Override
    public void onBindViewHolder(viewHolderFavoriteRestaurant holder, int position) {
        holder.bind(favorites.get(position));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    // To update data of this adapter
    public void updateData(List<FavoriteRestaurant> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }


    protected class viewHolderFavoriteRestaurant extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView name;
        private final LinearLayout item;
        private final ImageButton buttonFavorite;

        private final String EXTRA_NAME = "place_id";
        private final String uid;

        public viewHolderFavoriteRestaurant(@NonNull View itemView, String uid) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_favorite_icon);
            name = itemView.findViewById(R.id.item_favorite_name_place);
            item = itemView.findViewById(R.id.item_favorite_item);
            buttonFavorite = itemView.findViewById(R.id.item_favorite_button_favorite);
            this.uid = uid;
        }

        public void bind(final FavoriteRestaurant favorite) {
            if (favorite.getUrlPlace() != null) {
                Glide.with(itemView)
                        .load(favorite.getUrlPlace())
                        .apply(RequestOptions.circleCropTransform())
                        .into(icon);
            }
            name.setText(favorite.getNamePlace());
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailRestaurantActivity.class);
                    intent.putExtra(EXTRA_NAME, favorite.getIdPlace());
                    itemView.getContext().startActivity(intent);
                }
            });
            buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavoriteHelper.deleteFavoritePlace(uid, favorite.getIdPlace());
                    notifyDataSetChanged();
                }
            });
        }

    }

}
