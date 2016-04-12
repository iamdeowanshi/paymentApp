package com.batua.android.merchant.module.merchant.view.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.batua.android.merchant.data.model.BaseModel;

import java.util.List;

/**
 * Created by Maria on 04/04/16.
 */
public class SpinAdapter<T extends BaseModel> extends ArrayAdapter<String> {

    private List<T> list;
    private Context context;

    public SpinAdapter(Context context, int textViewResourceId, List<T> list) {
        super(context, textViewResourceId);
        this.list = list;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomTextView(position);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomTextView(position);

    }

    public int getCount() {
        return list.size();
    }

    public String getItem(int position) {
        return list.get(position).toString();
    }

    public long getItemId(int position) {
        return 0;
    }

    private TextView getCustomTextView(int position) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(dpToPixel(5), dpToPixel(5), dpToPixel(5), 0);
        textView.setText(list.get(position).toString());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        return textView;
    }

    private int dpToPixel(float dp) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        return (int) (fpixels + 0.5f);
    }
}
