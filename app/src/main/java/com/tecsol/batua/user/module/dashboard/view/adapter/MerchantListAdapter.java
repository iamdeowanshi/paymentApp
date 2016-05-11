package com.tecsol.batua.user.module.dashboard.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.batua.android.user.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.module.common.util.DecimalFormatUtil;
import com.tecsol.batua.user.module.payment.view.activity.PrePaymentConfirmationActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Arnold Laishram.
 */
public class MerchantListAdapter extends RecyclerView.Adapter<MerchantListAdapter.MerchantDetailViewHolder> {

    private List<Merchant> merchantDetails;
    private Context context;

    public MerchantListAdapter(List<Merchant> merchantDetails) {
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
        final Merchant merchant = merchantDetails.get(position);

        merchantDetailViewHolder.txtMerchantTitle.setText(merchant.getName());
        merchantDetailViewHolder.ratingReview.setRating(merchant.getAverageRating());
        Picasso.with(context).load(merchant.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).into(merchantDetailViewHolder.imgProfile);

        LayerDrawable stars = (LayerDrawable) merchantDetailViewHolder.ratingReview.getProgressDrawable();
        if (merchant.getAverageRating() != 5.0f) {
            stars.getDrawable(2).setColorFilter(Color.rgb(249, 173, 35), PorterDuff.Mode.SRC_ATOP);
        } else {
            stars.getDrawable(2).setColorFilter(Color.rgb(138, 211, 33), PorterDuff.Mode.SRC_ATOP);
        }

        //merchantDetailViewHolder.txtReviewedNum.setText("(" + merchant.getReviewedNum().toString() + ")");
        merchantDetailViewHolder.txtMerchantAddress.setText(merchant.getAddress());
        merchantDetailViewHolder.txtDistance.setText(DecimalFormatUtil.formatToExactTwoDecimal(merchant.getDistance())+" km");

        merchantDetailViewHolder.itemView.setOnClickListener(new MerchantClickListener(merchant));

        merchantDetailViewHolder.ratingReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent i = new Intent(context, ReviewActivity.class);
                i.putExtra("Galleries", Parcels.wrap(merchant.getGalleries()));
                i.putExtra("MerchantId", merchant.getId());
                context.startActivity(i);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return merchantDetails.size();
    }


    public class MerchantDetailViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_merchant_profile) CircularImageView imgProfile;
        @Bind(R.id.txt_merchant_title) TextView txtMerchantTitle;
        @Bind(R.id.rating_review) RatingBar ratingReview;
        @Bind(R.id.txt_reviewed_num) TextView txtReviewedNum;
        @Bind(R.id.txt_merchant_address) TextView txtMerchantAddress;
        @Bind(R.id.txt_distance) TextView txtDistance;

        public MerchantDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class MerchantClickListener implements View.OnClickListener {

        private Merchant merchant;

        public MerchantClickListener(Merchant merchant) {
            this.merchant = merchant;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, PrePaymentConfirmationActivity.class);
            i.putExtra("Merchant", Parcels.wrap(merchant));
            context.startActivity(i);
        }
    }

}
