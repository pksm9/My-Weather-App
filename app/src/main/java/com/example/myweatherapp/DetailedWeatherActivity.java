package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class DetailedWeatherActivity extends AppCompatActivity {

    TextView detailDate, detailLocation, detailTemp, detailDescription, detailHumidity;
    ImageView detailIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);

        detailDate = findViewById(R.id.detailDate);
        detailLocation = findViewById(R.id.detailLocation);
        detailIcon = findViewById(R.id.detailIcon);
        detailDate = findViewById(R.id.detailDate);
        detailTemp = findViewById(R.id.detailTemp);
        detailDescription = findViewById(R.id.detailDescription);
        detailHumidity = findViewById(R.id.detailHumidity);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String units = sharedPreferences.getString("change_units", "metric");

        String temp = "";
        if (units.equals("metric")) {
            String tempValue = getIntent().getExtras().getString("temp");
            temp = tempValue + " °C";
        }
        else if (units.equals("imperial")) {
            String tempValue = getIntent().getExtras().getString("temp");
            temp = tempValue + " °F";
        }

        detailTemp.setText(temp);

        String description = getIntent().getExtras().getString("description");
        detailDescription.setText(description);

        String humidity = getIntent().getExtras().getString("humidity");
        detailHumidity.setText("Humidity: "+humidity+"%");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String city = sharedPreferences.getString("search_city", "London");
        detailLocation.setText(city);

        String date = getIntent().getExtras().getString("date");
        detailDate.setText(date);

        String icon = getIntent().getExtras().getString("icon");
        try {
            URL imageUrl = new URL(icon);
            Glide.with(this).load(imageUrl).into(detailIcon);
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
}