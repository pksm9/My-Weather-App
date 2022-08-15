package com.example.myweatherapp;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.function.Predicate;

public class WeatherSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_settings);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        SettingsFragment fragment_pref = new SettingsFragment();
        fragmentTransaction.replace(android.R.id.content, fragment_pref);
        fragmentTransaction.commit();

    }





}