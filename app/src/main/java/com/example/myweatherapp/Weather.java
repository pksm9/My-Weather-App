package com.example.myweatherapp;

public class Weather {

    private long day;
    public String temp;
    public String description;
    private String icon;
    public String imageUrl;

    public String getImageUrl() {return imageUrl;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

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

    public Weather(String temp,String description, String imageUrl) {
        this.temp = temp;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Weather(long day, String temp, String description, String icon) {
        this.day = day;
        this.temp = temp;
        this.description = description;
        this.icon = icon;
    }
}
