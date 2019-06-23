package com.example.fds75.weather;

public class WeatherItem {
    private int id;
    private String curCity;
    private String curDay;
    private String curNight;
    private String curMax;
    private String curMin;

    public WeatherItem() {
        this.curCity = "";
        this.curDay = "";
        this.curNight = "";
        this.curMax = "";
        this.curMin = "";
    }

    public WeatherItem(String curCity, String curDay, String curNight, String curMax, String curMin) {
        this.curCity = curCity;
        this.curDay = curDay;
        this.curNight = curNight;
        this.curMax = curMax;
        this.curMin = curMin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurCity() {
        return curCity;
    }

    public void setCurCity(String curCity) {
        this.curCity = curCity;
    }

    public String getCurDay() {
        return curDay;
    }

    public void setCurDay(String curDay) {
        this.curDay = curDay;
    }

    public String getCurNight() {
        return curNight;
    }

    public void setCurNight(String curNight) {
        this.curNight = curNight;
    }

    public String getCurMax() {
        return curMax;
    }

    public void setCurMax(String curMax) {
        this.curMax = curMax;
    }

    public String getCurMin() {
        return curMin;
    }

    public void setCurMin(String curMin) {
        this.curMin = curMin;
    }
}
