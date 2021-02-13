package com.example.weatherapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ItemCitiesBinding;
import com.example.weatherapp.model.CitiesWeather;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private List<CitiesWeather> item;

    public CitiesAdapter(List<CitiesWeather> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCitiesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_cities, parent, false);
        return new CitiesAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final CitiesAdapter.ViewHolder holder, int position) {
        holder.itemBinding.setData(item.get(position));

    }

    public void updateAll(List<CitiesWeather> data) {
        item.clear();
        item.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCitiesBinding itemBinding;

        public ViewHolder(@NonNull ItemCitiesBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }


    }


}


