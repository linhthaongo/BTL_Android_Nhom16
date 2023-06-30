package com.trangchu.blt.khaibao;

import androidx.annotation.NonNull;

public class Phong {
    private String maPhong;
    private String tenPhong;
    private String moTaPhong;

    public Phong(String maPhong, String tenPhong, String moTaPhong) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.moTaPhong=moTaPhong;

    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getMoTaPhong() {
        return moTaPhong;
    }

    public void setMoTaPhong(String moTaPhong) {
        this.moTaPhong = moTaPhong;
    }

    @NonNull
    @Override
    public String toString(){return getMaPhong();}
}
