package com.trangchu.blt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trangchu.blt.Database;
import com.trangchu.blt.khaibao.NhanVien;

import java.util.ArrayList;

public class DaoNhanVien {
    Database database;
    public DaoNhanVien(Context context){
        database = new Database(context);
    }

    public ArrayList<NhanVien> getAll(){
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NHANVIEN", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            try{
                String maNV = cursor.getString(0);
                String tenNV = cursor.getString(1);
                String gioiTinh = cursor.getString(2);
                String ngaySinh = cursor.getString(3);
                String sdt = cursor.getString(4);
                String diaChi = cursor.getString(5);
                String email = cursor.getString(6);
                String maPhong = cursor.getString(7);
                String maDuAn = cursor.getString(8);
                byte[] HinhAnh = cursor.getBlob(9);
                NhanVien nhanVien = new NhanVien(maNV, tenNV, gioiTinh, ngaySinh, sdt, diaChi, email, maPhong, maDuAn,HinhAnh);
                dsNhanVien.add(nhanVien);
            } catch (Exception e){
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();
        return dsNhanVien;
    }

    public boolean themNV(NhanVien n){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maNhanVien", n.getMaNhanVien());
        contentValues.put("tenNhanVien", n.getTenNhanVien());
        contentValues.put("gioiTinh", n.getGioiTinh());
        contentValues.put("ngaySinh", n.getNgaySinh());
        contentValues.put("SDT", n.getSdt());
        contentValues.put("diaChi", n.getDiaChi());
        contentValues.put("emailNhanVien", n.getEmail());
        contentValues.put("maPhong", n.getMaPhong());
        contentValues.put("maDuAn", n.getMaDuAn());
        contentValues.put("HinhAnh", n.getHinh());
        long r = db.insert("NHANVIEN", null, contentValues);
        if (r <= 0){
            return false;
        }
        return true;
    }

    public boolean suaNV(NhanVien n){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maNhanVien", n.getMaNhanVien());
        contentValues.put("tenNhanVien", n.getTenNhanVien());
        contentValues.put("gioiTinh", n.getGioiTinh());
        contentValues.put("ngaySinh", n.getNgaySinh());
        contentValues.put("SDT", n.getSdt());
        contentValues.put("diaChi", n.getDiaChi());
        contentValues.put("emailNhanVien", n.getEmail());
        contentValues.put("maPhong", n.getMaPhong());
        contentValues.put("maDuAn", n.getMaDuAn());
        contentValues.put("HinhAnh", n.getHinh());
        long r = db.update("NHANVIEN", contentValues, "maNhanVien=?", new String[]{n.getMaNhanVien()});
        if (r <= 0){
            return false;
        }
        return true;
    }

    public boolean xoaNV(NhanVien n){
        SQLiteDatabase db = database.getWritableDatabase();
        int r = db.delete("NHANVIEN", "maNhanVien=?", new String[]{n.getMaNhanVien()});
        if (r <= 0){
            return false;
        }
        return true;
    }


}
