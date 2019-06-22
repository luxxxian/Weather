package com.example.fds75.weather;

public class DailyItem {
    private int id;
    private String curName;
    private String curDate;
    private String curDetail;

    public DailyItem() {
        this.curName = "";
        this.curDate = "";
        this.curDetail = "";
    }

    public DailyItem(String curName, String curDate, String curDetail) {
        this.curName = curName;
        this.curDate = curDate;
        this.curDetail = curDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public String getCurDetail() {
        return curDetail;
    }

    public void setCurDetail(String curDetail) {
        this.curDetail = curDetail;
    }
}
