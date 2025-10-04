package com.example.cityhealth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Context context;
    private List<City> cityList;
    private List<City> cityListFull; // For search functionality

    public CityAdapter(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
        this.cityListFull = new ArrayList<>(cityList);
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = cityList.get(position);

        holder.tvCityName.setText(city.getCityName());
        holder.tvCityState.setText(city.getState());
        holder.tvHealthScore.setText("Health Score: " + String.format("%.1f", city.getHealthIndex()));

        // Click listener to open dashboard
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DashboardActivity.class);
                intent.putExtra("CITY_DATA", city);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    // Filter method for search
    public void filter(String searchText) {
        cityList.clear();

        if (searchText.isEmpty()) {
            cityList.addAll(cityListFull);
        } else {
            searchText = searchText.toLowerCase();
            for (City city : cityListFull) {
                if (city.getCityName().toLowerCase().contains(searchText) ||
                        city.getState().toLowerCase().contains(searchText)) {
                    cityList.add(city);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvCityName, tvCityState, tvHealthScore;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewCity);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            tvCityState = itemView.findViewById(R.id.tvCityState);
            tvHealthScore = itemView.findViewById(R.id.tvHealthScore);
        }
    }
}