package com.example.fds75.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WEManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public WEManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB2_NAME;
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void addAll(List<WeatherItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (WeatherItem item : list) {
            ContentValues values = new ContentValues();
            values.put("curcity", item.getCurCity());
            values.put("curday", item.getCurDay());
            values.put("curnight", item.getCurNight());
            values.put("curmax", item.getCurMax());
            values.put("curmin", item.getCurMin());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public List<WeatherItem> listAll(){
        List<WeatherItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<WeatherItem>();
            while(cursor.moveToNext()){
                WeatherItem item = new WeatherItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurCity(cursor.getString(cursor.getColumnIndex("CURCITY")));
                item.setCurDay(cursor.getString(cursor.getColumnIndex("CURDAY")));
                item.setCurNight(cursor.getString(cursor.getColumnIndex("CURNIGHT")));
                item.setCurMax(cursor.getString(cursor.getColumnIndex("CURMAX")));
                item.setCurMin(cursor.getString(cursor.getColumnIndex("CURMIN")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;
    }

    public String findDay(String curCity){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "curcity=?", new String[]{String.valueOf(curCity)}, null,
                null, null);
        String day = null;
        if(cursor!=null && cursor.moveToFirst()){
            if (curCity.equals(cursor.getString(cursor.getColumnIndex("CURCITY")))){
                day = cursor.getString(cursor.getColumnIndex("CURDAY"));
            }
            cursor.close();
        }
        db.close();
        return day;
    }

    public String findNight(String curCity){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "curcity=?", new String[]{String.valueOf(curCity)}, null,
                null, null);
        String night = null;
        if(cursor!=null && cursor.moveToFirst()){
            if (curCity.equals(cursor.getString(cursor.getColumnIndex("CURCITY")))){
                night = cursor.getString(cursor.getColumnIndex("CURNIGHT"));
            }
            cursor.close();
        }
        db.close();
        return night;
    }

    public String findMAX(String curCity){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "curcity=?", new String[]{String.valueOf(curCity)}, null,
                null, null);
        String max = null;
        if(cursor!=null && cursor.moveToFirst()){
            if (curCity.equals(cursor.getString(cursor.getColumnIndex("CURCITY")))){
                max = cursor.getString(cursor.getColumnIndex("CURMAX"));
            }
            cursor.close();
        }
        db.close();
        return max;
    }

    public String findMIN(String curCity){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "curcity=?", new String[]{String.valueOf(curCity)}, null,
                null, null);
        String min = null;
        if(cursor!=null && cursor.moveToFirst()){
            if (curCity.equals(cursor.getString(cursor.getColumnIndex("CURCITY")))){
                min = cursor.getString(cursor.getColumnIndex("CURMIN"));
            }
            cursor.close();
        }
        db.close();
        return min;
    }
}

