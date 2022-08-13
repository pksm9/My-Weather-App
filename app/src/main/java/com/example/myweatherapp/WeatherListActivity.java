package com.example.myweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherListActivity extends AppCompatActivity {

    TextView txtDescription;
    TextView txtTemp;
    ImageView iconWeather;
    ListView dailyList;
    String city = "Colombo";
    String units = "metric";
    String icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchData fetchData = new FetchData();
        fetchData.execute();

        txtTemp = findViewById(R.id.txtTemp);
        txtDescription = findViewById(R.id.txtDescription);
        iconWeather = findViewById(R.id.iconWeather);
        dailyList = findViewById(R.id.dailyList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.settings) {
            Intent settingsActivity = new Intent(getApplicationContext(), WeatherSettings.class);
            startActivity(settingsActivity);
        }
        else if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }



    public class FetchData extends AsyncTask<String, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                List<Weather> weatherList = new ArrayList<>();

                JSONObject weatherData = new JSONObject(forecastJsonStr);
                JSONArray list = weatherData.getJSONArray("list");

                for (int i = 0; i < 40;) {
                    JSONObject day = list.getJSONObject(i);
                    JSONObject main = day.getJSONObject("main");
                    JSONArray weather = day.getJSONArray("weather");
                    JSONObject weather_obj = weather.getJSONObject(0);

                    String temp = main.getString("temp").toString();
                    String description = weather_obj.getString("main").toString();
                    icon = weather_obj.getString("icon");
                    String date = main.getString("dt").toString();



                    weatherList.add(new Weather(temp, description));

                    i = i+8;
                }

                DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(WeatherListActivity.this,weatherList);
                dailyList.setAdapter(dailyWeatherAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String BASE_URL ="https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+"bf5e6047a46ad2469dced210d31f972e"+"&units="+units;
                String IMAGE_URL = "https://openweathermap.org/img/w/"+ icon +".png";
                URL url = new URL(BASE_URL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) { return null; }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line1;

                while ((line1 = reader.readLine()) != null) { buffer.append(line1 + "\n"); }
                if (buffer.length() == 0) { return null; }
                forecastJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("Hi", "Error ", e);
                return null;
            } finally{
                if (urlConnection != null) { urlConnection.disconnect(); }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Hi", "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }
}