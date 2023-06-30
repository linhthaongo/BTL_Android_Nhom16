package com.trangchu.blt.khaibao;

import java.io.Serializable;

public class TaiKhoaMatKhau implements Serializable {

    private String taiKhoan,matKhau,hoten,emailTK,SDT;
    byte[] anhDaiDien;

    public TaiKhoaMatKhau(){

    }

    public TaiKhoaMatKhau(String taiKhoan,String matKhau)
    {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public TaiKhoaMatKhau(String taiKhoan, String matKhau, String hoten, String emailTK, byte[] anhDaiDien, String SDT) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.hoten = hoten;
        this.emailTK = emailTK;
        this.anhDaiDien = anhDaiDien;
        this.SDT = SDT;
    }


    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmailTK() {
        return emailTK;
    }

    public void setEmailTK(String emailTK) {
        this.emailTK = emailTK;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public byte[] getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(byte[] anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }
}
