package com.example.myweatherapp;

public class Weather {

    private long day;
    public String temp;
    public String description;
    private String icon;

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Weather(String temp, String icon) {
        this.temp = temp;
        this.icon = icon;
    }

    public Weather(long day, String temp, String description, String icon) {
        this.day = day;
        this.temp = temp;
        this.description = description;
        this.icon = icon;
    }
}
