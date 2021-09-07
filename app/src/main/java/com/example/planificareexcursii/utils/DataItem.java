package com.example.planificareexcursii.utils;

public class DataItem {

    int resIdThumbnail;
    String cityName;
    String description;
    public DataItem(int resIdThumbnail, String cityName) {
        this.resIdThumbnail = resIdThumbnail;
        this.cityName = cityName;
    }

    public DataItem(int resIdThumbnail, String cityName, String description) {
        this.resIdThumbnail = resIdThumbnail;
        this.cityName = cityName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResIdThumbnail() {
        return resIdThumbnail;
    }

    public void setResIdThumbnail(int resIdThumbnail) {
        this.resIdThumbnail = resIdThumbnail;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


}
