package com.batua.android.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.batua.android.R;
import com.batua.android.app.di.Injector;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Aaditya Deowanshi.
 */
public class GalleryViewPagerAdapter extends PagerAdapter {

    @Inject Context context;
    @Inject LayoutInflater inflater;

    private int index;
    private List<String> images;

    public GalleryViewPagerAdapter(List<String> images, int position) {
        Injector.instance().inject(this);
        this.images = images;
        this.index = position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View pageView = inflater.inflate(R.layout.page_item_gallery, view, false);

        ViewHolder viewHolder = new ViewHolder(pageView);
        viewHolder.bindData(position);

        view.addView(pageView, 0);

        return pageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public class ViewHolder {
        @Bind(R.id.image_view) ImageView imageView;

        int position;

        public ViewHolder(View pageView) {
            ButterKnife.bind(this, pageView);
        }

        public void bindData(int position) {
            this.position = position;

            Glide.with(context).load(images.get(position)).fitCenter().into(imageView);
        }
    }

}
