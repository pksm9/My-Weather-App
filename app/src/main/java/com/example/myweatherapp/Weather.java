package com.example.myweatherapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather {
    private long dt;
    private JSONObject main;
    private JSONObject weather;

    public Weather(JSONObject forecast) throws JSONException {
        this.main = forecast.getJSONObject("main");
        this.weather = forecast.getJSONArray("weather").getJSONObject(0);
        this.dt = forecast.getLong("dt");
    }

    public String getImageUrl() {
        try {
            return "https://openweathermap.org/img/w/"+ weather.getString("icon") +".png";
        } catch (JSONException e) {
            Log.d("Hi", e.getMessage());
            return null;
        }
    }

    public String getDay() {
        Date date = new Date(dt*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String day = simpleDateFormat.format(date);

        return day;
    }

    public String getTemp() {
        try {
            return main.getString("temp");
        } catch (JSONException e) {
            Log.d("Error", e.getMessage());
            return null;
        }
    }

    public String getHumidity() {
        try {
            return main.getString("humidity");
        } catch (JSONException e) {
            Log.d("Error", e.getMessage());
            return null;
        }
    }

    public String getDescription() {
        try {
            return weather.getString("description");
        } catch (JSONException e) {
            Log.d("Error", e.getMessage());
            return null;
        }
    }

    public String getDetails() {
        try {
            return weather.getString("description");
        } catch (JSONException e) {
            Log.d("Error", e.getMessage());
            return null;
        }
    }
}
