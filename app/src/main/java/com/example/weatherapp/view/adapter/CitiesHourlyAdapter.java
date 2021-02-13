package com.example.weatherapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ItemCitiesHourlyBinding;
import com.example.weatherapp.model.citylocationmodel.CityWeatherInfo;

import java.util.List;

public class CitiesHourlyAdapter extends RecyclerView.Adapter<CitiesHourlyAdapter.ViewHolder> {

    private List<CityWeatherInfo> item;

    public CitiesHourlyAdapter(List<CityWeatherInfo> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public CitiesHourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCitiesHourlyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_cities_hourly, parent, false);
        return new CitiesHourlyAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final CitiesHourlyAdapter.ViewHolder holder, int position) {
        holder.itemBinding.setData(item.get(position));

    }




    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCitiesHourlyBinding itemBinding;

        public ViewHolder(@NonNull ItemCitiesHourlyBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }


    }


}


