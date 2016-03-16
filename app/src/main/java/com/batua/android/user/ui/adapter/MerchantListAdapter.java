package com.batua.android.user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.data.model.MerchantDetail;
import com.batua.android.user.ui.activity.MakePaymentActivity;
import com.batua.android.user.ui.activity.ReviewActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author Arnold Laishram.
 */
public class MerchantListAdapter extends RecyclerView.Adapter<MerchantListAdapter.MerchantDetailViewHolder> {

    private List<MerchantDetail> merchantDetails;
    private Context context;

    public MerchantListAdapter(List<MerchantDetail> merchantDetails) {
        this.merchantDetails = merchantDetails;
    }

    @Override
    public MerchantDetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_merchant, viewGroup, false);
        MerchantDetailViewHolder merchantDetailViewHolder = new MerchantDetailViewHolder(v);

        return merchantDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(MerchantDetailViewHolder merchantDetailViewHolder, int position) {
        MerchantDetail merchantDetail = merchantDetails.get(position);

        merchantDetailViewHolder.txtMerchantTitle.setText(merchantDetail.getMerchantTitle());
        merchantDetailViewHolder.ratingReview.setRating(merchantDetail.getRatingReview());

        LayerDrawable stars = (LayerDrawable) merchantDetailViewHolder.ratingReview.getProgressDrawable();
        if (merchantDetail.getRatingReview() != 5.0f) {
            stars.getDrawable(2).setColorFilter(Color.rgb(249, 173, 35), PorterDuff.Mode.SRC_ATOP);
        } else {
            stars.getDrawable(2).setColorFilter(Color.rgb(138, 211, 33), PorterDuff.Mode.SRC_ATOP);
        }

        merchantDetailViewHolder.txtReviewedNum.setText(merchantDetail.getReviewedNum().toString());
        merchantDetailViewHolder.txtMerchantAddress.setText(merchantDetail.getMerchantAddress());
        merchantDetailViewHolder.txtDistance.setText(merchantDetail.getDistance());

        merchantDetailViewHolder.itemView.setOnClickListener(new MerchantClickListener(position));
    }

    @Override
    public int getItemCount() {
        return merchantDetails.size();
    }


    public class MerchantDetailViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_merchant_profile)
        ImageView imgProfile;
        @Bind(R.id.txt_merchant_title)
        TextView txtMerchantTitle;
        @Bind(R.id.rating_review)
        RatingBar ratingReview;
        @Bind(R.id.txt_reviewed_num)
        TextView txtReviewedNum;
        @Bind(R.id.txt_merchant_address)
        TextView txtMerchantAddress;
        @Bind(R.id.txt_distance)
        TextView txtDistance;

        @OnClick({R.id.txt_reviewed_num, R.id.rating_layout})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.txt_reviewed_num:
                case R.id.rating_layout:
                    Intent i = new Intent(context, ReviewActivity.class);
                    context.startActivity(i);
                    break;
            }
        }

        public MerchantDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class MerchantClickListener implements View.OnClickListener {

        private int position;

        public MerchantClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Timber.d(position + "");
            Intent i = new Intent(context, MakePaymentActivity.class);
            i.putExtra("position", position);
            context.startActivity(i);
        }
    }

}
