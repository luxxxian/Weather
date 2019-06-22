package com.example.fds75.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(DailyItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("curdate", item.getCurDate());
        values.put("curdetail", item.getCurDetail());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<DailyItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (DailyItem item : list) {
            ContentValues values = new ContentValues();
            values.put("curname", item.getCurName());
            values.put("curdate", item.getCurDate());
            values.put("curdetail", item.getCurDetail());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }
    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void update(DailyItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("curdate", item.getCurDate());
        values.put("curdetail", item.getCurDetail());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<DailyItem> listAll(){
        List<DailyItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<DailyItem>();
            while(cursor.moveToNext()){
                DailyItem item = new DailyItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurDate(cursor.getString(cursor.getColumnIndex("CURDATE")));
                item.setCurDetail(cursor.getString(cursor.getColumnIndex("CURDETAIL")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;
    }

    public DailyItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null,
                null, null);
        DailyItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new DailyItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurDate(cursor.getString(cursor.getColumnIndex("CURDATE")));
            rateItem.setCurDetail(cursor.getString(cursor.getColumnIndex("CURDETAIL")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }
}

