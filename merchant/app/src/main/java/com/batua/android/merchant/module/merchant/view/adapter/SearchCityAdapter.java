package com.batua.android.merchant.module.merchant.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.City;
import com.batua.android.merchant.module.merchant.view.listener.CitySelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Arnold Laishram.
 */

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.SearchCityViewHolder> implements Filterable {

    private List<City> cities;
    private CitySelectedListener citySelectedListener;
    private List<City> searchedCities = new ArrayList<City>(); // to be displayed
    private Context context;
    private String filterString;

    public SearchCityAdapter(List<City> cities, CitySelectedListener citySelectedListener) {
        this.cities = cities;
        this.citySelectedListener = citySelectedListener;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchedCities = (List<City>) results.values;// has the filtered value
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<City> filteredArrList = new ArrayList<City>();
                filterString = constraint.toString();

                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = cities.size();
                    results.values = cities;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < cities.size(); i++) {
                        String data = cities.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(cities.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
        return filter;

    }

    @Override
    public SearchCityViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.search_city, viewGroup, false);
        SearchCityViewHolder searchCityViewHolder = new SearchCityViewHolder(v);

        return searchCityViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchCityViewHolder searchCityViewHolder, final int position) {
        searchCityViewHolder.txtSearchCity.setText(searchedCities.get(position).getName());

        searchCityViewHolder.txtSearchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citySelectedListener.displayCity(searchedCities.get(position).getId(), searchedCities.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        boolean searchedCityMatched = false;
        if (searchedCities.size() == 0) {
            citySelectedListener.displayError();
        } else {
            for (City city: searchedCities){
                if (city.getName().equalsIgnoreCase(filterString)){
                    searchedCityMatched = true;
                }
            }

            if (!searchedCityMatched){
                citySelectedListener.displayError();
            }
        }
        return searchedCities.size();
    }

    public class SearchCityViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txt_search_city) TextView txtSearchCity;

        public SearchCityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
