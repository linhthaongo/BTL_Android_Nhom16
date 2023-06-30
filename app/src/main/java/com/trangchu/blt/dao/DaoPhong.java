package com.trangchu.blt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trangchu.blt.Database;
import com.trangchu.blt.khaibao.DuAn;
import com.trangchu.blt.khaibao.Phong;

import java.util.ArrayList;

public class DaoPhong {
    Database db;
    public DaoPhong(Context context){
        db = new Database(context);
    }
    public ArrayList<Phong> getAll(){
        ArrayList<Phong> lsList = new ArrayList<>();
        SQLiteDatabase dtb = db. getReadableDatabase();
        Cursor cs = dtb.rawQuery("SELECT * FROM PHONG",null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            String maPhong = cs.getString(0);
            String tenPhong = cs.getString(1);
            String moTaPhong = cs.getString(2);
            Phong s = new Phong(maPhong,tenPhong,moTaPhong);
            lsList.add(s);
            cs.moveToNext();
        }
        cs.close();
        return lsList;
    }
    public  boolean insert(Phong phong){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maPhong", phong.getMaPhong());
        contentValues.put("tenPhong",phong.getTenPhong());
        contentValues.put("moTaPhong",phong.getMoTaPhong());
        long r = sqLiteDatabase.insert("PHONG",null,contentValues);
        if (r<= 0){
            return false;
        }
        return true;
    }

    public  boolean update(Phong phong){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maPhong", phong.getMaPhong());
        contentValues.put("tenPhong",phong.getTenPhong());
        contentValues.put("moTaPhong",phong.getMoTaPhong());
        long r = sqLiteDatabase.update("PHONG",contentValues, "maPhong=?",new String[]{phong.getMaPhong()});
        if (r<= 0){
            return false;
        }
        return true;
    }
    public boolean delete(String maphong) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        int r = sqLiteDatabase.delete("PHONG", "maPhong=?", new String[]{maphong});
        if (r <= 0) {
            return false;
        }
        return true;
    }
}
