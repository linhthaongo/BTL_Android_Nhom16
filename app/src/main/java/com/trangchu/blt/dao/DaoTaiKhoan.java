package com.trangchu.blt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.trangchu.blt.Database;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;

import java.util.ArrayList;

public class DaoTaiKhoan {
    Database dtbDangNhap;

    public DaoTaiKhoan(Context context){
        dtbDangNhap = new Database(context);
    }

    public ArrayList<TaiKhoaMatKhau> getAll(){
        ArrayList<TaiKhoaMatKhau> listTK = new ArrayList<>();
        SQLiteDatabase dtb = dtbDangNhap.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("SELECT * FROM TAIKHOAN", null);
        cursor.moveToNext();
        while (!cursor.isAfterLast()){
            try{
                String taikhoan = cursor.getString(0);
                String matkhau  = cursor.getString(1);
                String hoten = cursor.getString(2);
                String emailTk = cursor.getString(3);
                byte[] anhDaiDien = cursor.getBlob(4);
                String SDT = cursor.getString(5);
                TaiKhoaMatKhau taiKhoaMatKhau = new TaiKhoaMatKhau(taikhoan, matkhau,hoten,emailTk,anhDaiDien,SDT);
                listTK.add(taiKhoaMatKhau);
                cursor.moveToNext();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        cursor.close();
        return listTK;
    }

    public ArrayList<TaiKhoaMatKhau> getOne(String tenTaiKhoan){
        ArrayList<TaiKhoaMatKhau> listTK = new ArrayList<>();
        SQLiteDatabase dtb = dtbDangNhap.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("SELECT * FROM TAIKHOAN Where tenTaiKhoan = '"+tenTaiKhoan+"'", null);
        cursor.moveToNext();
        while (!cursor.isAfterLast()){
            try{
                String taikhoan = cursor.getString(0);
                String matkhau  = cursor.getString(1);
                String hoten = cursor.getString(2);
                String emailTk = cursor.getString(3);
                byte[] anhDaiDien = cursor.getBlob(4);
                String SDT = cursor.getString(5);
                TaiKhoaMatKhau taiKhoaMatKhau = new TaiKhoaMatKhau(taikhoan, matkhau, hoten,emailTk,anhDaiDien,SDT);
                listTK.add(taiKhoaMatKhau);
                cursor.moveToNext();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        cursor.close();
        return listTK;
    }

    public  boolean Them(TaiKhoaMatKhau tk){
        SQLiteDatabase db = dtbDangNhap.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenTaiKhoan", tk.getTaiKhoan());
        values.put("matkhau", tk.getMatKhau());
        long r = db.insert("TAIKHOAN", null, values);
        if (r <= 0){
            return false;
        }
        return true;
    }

    public boolean SuaTK(TaiKhoaMatKhau tk){
        SQLiteDatabase db = dtbDangNhap.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTaiKhoan", tk.getTaiKhoan());
        contentValues.put("matKhau",tk.getMatKhau());
        contentValues.put("hoTen",tk.getHoten());
        contentValues.put("emailTK", tk.getEmailTK());
        contentValues.put("anhDaiDien", tk.getAnhDaiDien());
        contentValues.put("SDT", tk.getSDT());
        int r = db.update("TAIKHOAN", contentValues, "tenTaiKhoan=?", new String[]{tk.getTaiKhoan()});
        if (r <= 0){
            return false;
        }
        return true;
    }


    public boolean doiMatKhau(TaiKhoaMatKhau tk){
        SQLiteDatabase db = dtbDangNhap.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matkhau", tk.getMatKhau());
        int r = db.update("TAIKHOAN", contentValues, "tenTaiKhoan=?", new String[]{tk.getTaiKhoan()});
        if (r <= 0){
            return false;
        }
        return true;
    }
}
