package com.trangchu.blt.khaibao;

import androidx.annotation.NonNull;

public class DuAn {
    private String maDuAn, tenDuAn, moTa;

    public DuAn(String maDuAn, String tenDuAn, String moTa) {
        this.maDuAn = maDuAn;
        this.tenDuAn = tenDuAn;
        this.moTa = moTa;
    }

    public String getMaDuAn() {
        return maDuAn;
    }

    public void setMaDuAn(String maDuAn) {
        this.maDuAn = maDuAn;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @NonNull
    @Override
    public String toString() {
        return getMaDuAn();
    }
}
