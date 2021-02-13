package com.example.weatherapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ItemCityDailyBinding;
import com.example.weatherapp.model.citylocationmodel.CityWeatherInfo;
import com.example.weatherapp.utils.Utils;


import java.util.ArrayList;
import java.util.List;

import static com.example.weatherapp.utils.Constants.SIZE_DAILY_ADAPTER;


public class CityLocationAdapter extends RecyclerView.Adapter<CityLocationAdapter.ViewHolder> {

    private List<CityWeatherInfo> item;
    private Context context;

    public CityLocationAdapter(Context context, List<CityWeatherInfo> item) {
        this.context = context;
        this.item = item;

    }

    @NonNull
    @Override
    public CityLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCityDailyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_city_daily, parent, false);
        return new CityLocationAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final CityLocationAdapter.ViewHolder holder, int position) {
        String date = Utils.increaseDate(Utils.getCurrentDate(),position);

        holder.itemBinding.day.setText(Utils.getDayOfWeek(date));

        List<CityWeatherInfo> items = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            if (item.get(i).getDate().equals(date)) {
                items.add(item.get(i));
            }
        }

        CitiesHourlyAdapter adapter = new CitiesHourlyAdapter(items);
        holder.itemBinding.recycler.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        holder.itemBinding.recycler.addItemDecoration(dividerItemDecoration);
        holder.itemBinding.recycler.setLayoutManager(new GridLayoutManager(context,2));


    }

    public void updateAll(List<CityWeatherInfo> data) {
        item.clear();
        item.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return SIZE_DAILY_ADAPTER;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCityDailyBinding itemBinding;

        public ViewHolder(@NonNull ItemCityDailyBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }


}



