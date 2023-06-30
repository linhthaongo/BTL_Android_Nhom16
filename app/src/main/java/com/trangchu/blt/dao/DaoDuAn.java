package com.trangchu.blt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trangchu.blt.Database;
import com.trangchu.blt.adapter.DuAnAdapter;
import com.trangchu.blt.khaibao.DuAn;

import java.util.ArrayList;

public class DaoDuAn {
    Database database;
    public  DaoDuAn(Context context){
        database = new Database(context);
    }

    public ArrayList<DuAn> getAll(){
        ArrayList<DuAn> da = new ArrayList<>();
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DUAN", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            try {
                String maDuAn = cursor.getString(0);
                String tenDuAn = cursor.getString(1);
                String moTa = cursor.getString(2);
                DuAn duAn = new DuAn(maDuAn, tenDuAn, moTa);
                da.add(duAn);
            }catch (Exception e){
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();
        return da;
    }

    public boolean themDuAn(DuAn da){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maDuAn", da.getMaDuAn());
        contentValues.put("tenDuAn", da.getTenDuAn());
        contentValues.put("moTa", da.getMoTa());
        long r = db.insert("DUAN", null, contentValues);
        if (r <= 0){
            return false;
        }
        return true;
    }

    public boolean suaDuAn(DuAn da){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maDuAn", da.getMaDuAn());
        contentValues.put("tenDuAn", da.getTenDuAn());
        contentValues.put("moTa", da.getMoTa());
        long r = db.update("DUAN", contentValues, "maDuAn=?", new String[]{da.getMaDuAn()});
        if (r <= 0){
            return false;
        }
        return true;
    }

    public boolean xoaDuAn(DuAn da){
        SQLiteDatabase db = database.getWritableDatabase();
        long r = db.delete("DUAN", "maDuAn=?", new String[]{da.getMaDuAn()});
        if (r <= 0){
            return false;
        }
        return true;
    }
}
