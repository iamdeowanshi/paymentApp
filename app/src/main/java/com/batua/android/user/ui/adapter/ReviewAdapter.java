package com.batua.android.user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.data.model.Review;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Aaditya Deowanshi.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textName.setText(reviewList.get(position).getName());
        holder.textDate.setText(reviewList.get(position).getDate());
        holder.expandableTextReview.setText(reviewList.get(position).getReview());
        holder.rating.setRating(reviewList.get(position).getStars());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_name) TextView textName;
        @Bind(R.id.rating) RatingBar rating;
        @Bind(R.id.text_date) TextView textDate;
        @Bind(R.id.expandable_text_review) ExpandableTextView expandableTextReview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
