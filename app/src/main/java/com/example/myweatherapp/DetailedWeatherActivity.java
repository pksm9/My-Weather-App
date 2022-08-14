package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    }
}