package com.francotte.go4lunch_opc.ui.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.models.PlaceDetails.PlaceDetails;
import com.francotte.go4lunch_opc.models.User;
import com.francotte.go4lunch_opc.repositories.google_api.GoogleMapPlacesCall;
import com.francotte.go4lunch_opc.ui.activities.DetailRestaurantActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class AdaptorListViewWorkmates extends RecyclerView.Adapter<AdaptorListViewWorkmates.WorkmatesViewHolder> {

    private List<User> users = new ArrayList<>();
    private final Context context;
    private final boolean isUsingInWorkmatesFragment;


    public AdaptorListViewWorkmates(Context context, boolean isUsingInWorkmatesFragment) {
        this.context = context;
        this.isUsingInWorkmatesFragment = isUsingInWorkmatesFragment;
    }

    @NotNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_workmate, parent, false);
        return new WorkmatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkmatesViewHolder holder, int position) {
        holder.bind(context, users.get(position), isUsingInWorkmatesFragment);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    // To update data of this adapter
    public void updateData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }


    protected static class WorkmatesViewHolder extends RecyclerView.ViewHolder implements GoogleMapPlacesCall.GetDetailOfPlaceCallbacks {

        private final LinearLayout item;
        private final ImageView icon;
        private final TextView text;

        private User user;

        private boolean isUsingInWorkmatesFragment;


        public WorkmatesViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_workmate_icon);
            text = itemView.findViewById(R.id.item_workmate_text);
            item = itemView.findViewById(R.id.item_workmate_item);

        }

        public void bind(Context context, final User user, boolean isUsingInWorkmatesFragment) {
            this.user = user;
            this.isUsingInWorkmatesFragment = isUsingInWorkmatesFragment;
            if (user.getUrlPicture() != null) {
                Glide.with(context)
                        .load(user.getUrlPicture())
                        .apply(RequestOptions.circleCropTransform())
                        .into(icon);
            }
            if (user.getLunchPlaceID() != null) {
                GoogleMapPlacesCall.getDetailOfAPlace(this, user.getLunchPlaceID());

            } else {
                text.setText(user.getUserName() + " " + context.getString(R.string.not_decided_state_workmates));
                text.setEnabled(false);
            }

            this.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!user.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        Intent intent = new Intent(view.getContext(), DetailRestaurantActivity.class);
                        intent.putExtra("place_id", user.getLunchPlaceID());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }

        @Override
        public void onResponseGetDetailOfPlace(PlaceDetails result) {
            if (isUsingInWorkmatesFragment) {
                text.setText(user.getUserName() + " " + itemView.getResources().getString(R.string.item_is_eating) + " " + result.getResult().getName());
            } else {
                text.setText(user.getUserName() + " " + itemView.getResources().getString(R.string.joined_state_workmates));
            }
            text.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailureGetDetailOfPlace(Throwable t) {
            text.setText(user.getUserName());
            text.setEnabled(false);
        }

    }

}
