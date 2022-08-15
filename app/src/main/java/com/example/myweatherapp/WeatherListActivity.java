package com.example.myweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.function.Predicate;

public class WeatherListActivity extends AppCompatActivity {
    TextView txtDescription, txtTemp, txtDay;
    ImageView iconWeather;
    ListView dailyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchData fetchData = new FetchData();
        fetchData.execute();

        txtTemp = findViewById(R.id.txtTemp);
        txtDescription = findViewById(R.id.txtDescription);
        iconWeather = findViewById(R.id.iconWeather);
        txtDay = findViewById(R.id.txtDay);
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
                    JSONObject forecast = list.getJSONObject(i);
                    weatherList.add(new Weather(forecast));
                    i = i+8;
                }

                DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(WeatherListActivity.this,weatherList);
                dailyList.setAdapter(dailyWeatherAdapter);

                dailyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Weather detailedWeather = (Weather) dailyList.getAdapter().getItem(position);
                        Intent detailedWeatherIntent = new Intent(getApplicationContext(), DetailedWeatherActivity.class);

                        detailedWeatherIntent.putExtra("temp", detailedWeather.getTemp());
                        detailedWeatherIntent.putExtra("description", detailedWeather.getDetails());
                        detailedWeatherIntent.putExtra("icon", detailedWeather.getImageUrl());
                        detailedWeatherIntent.putExtra("humidity", detailedWeather.getHumidity());
                        startActivity(detailedWeatherIntent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String city = sharedPreferences.getString("search_city", "London");
                String units = sharedPreferences.getString("change_units", "Celsius");

                final String BASE_URL ="https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+"bf5e6047a46ad2469dced210d31f972e"+"&units="+units;
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