package com.example.myweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DailyWeatherAdapter extends ArrayAdapter<Weather> {

    private List<Weather> weatherList;
    private Context context;

    public DailyWeatherAdapter(@NonNull Context context,@NonNull List<Weather> weatherList) {
        super(context, 0, weatherList);
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_weather_list, parent, false);

        TextView txtDay = convertView.findViewById(R.id.txtDay);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtTemp = convertView.findViewById(R.id.txtTemp);
        ImageView iconWeather = convertView.findViewById(R.id.iconWeather);

        Weather weather = weatherList.get(position);

        txtTemp.setText(weather.getTemp());
        txtDescription.setText(weather.getDescription());

        return convertView;
    }
}
