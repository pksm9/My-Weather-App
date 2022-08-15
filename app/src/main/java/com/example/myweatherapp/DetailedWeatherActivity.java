package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        String temp = getIntent().getExtras().getString("temp");
        detailTemp.setText(temp);

        String description = getIntent().getExtras().getString("description");
        detailDescription.setText(description);

        String humidity = getIntent().getExtras().getString("humidity");
        detailHumidity.setText("Humidity: "+humidity+"%");

        String city = getIntent().getExtras().getString("humidity");
        String country = getIntent().getExtras().getString("humidity");
        detailHumidity.setText(city+","+country);

        String icon = getIntent().getExtras().getString("icon");
        try {
            URL imageUrl = new URL(icon);
            Glide.with(this).load(imageUrl).into(detailIcon);
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
}