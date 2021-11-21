package com.francotte.go4lunch_opc.ui.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.francotte.go4lunch_opc.R;
import com.francotte.go4lunch_opc.models.PLACEAUTOCOMPLETE.Prediction;
import com.francotte.go4lunch_opc.ui.DetailRestaurantActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdaptorListViewSuggestions extends RecyclerView.Adapter<AdaptorListViewSuggestions.viewHolderSuggestion> {

        List<Prediction> predictions = new ArrayList<>();
        String input = "";


@NotNull
@Override
public viewHolderSuggestion onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.item_suggestion_restaurant, parent, false);
        return new viewHolderSuggestion(view);
        }
@Override
public void onBindViewHolder(viewHolderSuggestion holder, int position) {
        holder.bind(predictions.get(position), input);
        }
@Override
public int getItemCount() {
        return predictions.size();
        }
// To update data of this adapter
public void updateSuggestions(List<Prediction> predictions, String input) {
        this.input = input;
        this.predictions = predictions;
        notifyDataSetChanged();
        }


protected static class viewHolderSuggestion extends RecyclerView.ViewHolder {

    private final LinearLayout item;
    private final TextView textSuggestion;

    private final String EXTRA_NAME = "placeID";

    public viewHolderSuggestion(@NonNull View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.item_suggestion_restaurant);
        textSuggestion = itemView.findViewById(R.id.item_suggestion_restaurant_text);
    }

    public void bind(final Prediction prediction, String input) {
        final SpannableStringBuilder sb = new SpannableStringBuilder(prediction.getDescription());

        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        final StyleSpan iss = new StyleSpan(Typeface.NORMAL); //Span to make text normal
        sb.setSpan(bss, 0, input.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        sb.setSpan(iss, input.length(), prediction.getDescription().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textSuggestion.setText(sb);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailRestaurantActivity.class);
                intent.putExtra(EXTRA_NAME, prediction.getPlace_id());
                itemView.getContext().startActivity(intent);
            }
        });
    }

}}


