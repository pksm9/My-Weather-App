package com.example.myweatherapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

    public long getDay() {
        //TODO: Must implement date conversion
        return this.dt;
    }

    public String getTemp() {
        try {
            return main.getString("temp");
        } catch (JSONException e) {
            Log.d("Hi", e.getMessage());
            return null;
        }
    }

    public String getHumidity() {
        try {
            return main.getString("humidity");
        } catch (JSONException e) {
            Log.d("Hi", e.getMessage());
            return null;
        }
    }

    public String getDescription() {
        try {
            return weather.getString("description");
        } catch (JSONException e) {
            Log.d("Hi", e.getMessage());
            return null;
        }
    }
}
