package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.trangchu.blt.adapter.NhanVienAdapter;
import com.trangchu.blt.dao.DaoDuAn;
import com.trangchu.blt.dao.DaoNhanVien;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.DuAn;
import com.trangchu.blt.khaibao.NhanVien;
import com.trangchu.blt.khaibao.Phong;

import java.util.ArrayList;

public class ThongKe extends AppCompatActivity {
    Button btnThongKeNV,btnThongKeDA,btnThongKePhong,btnQuaylaiTK;
    TextInputEditText editThongKeNV,editThongKeDA,editThongKePhong;

    ArrayList<NhanVien> nv = new ArrayList<>();
    DaoNhanVien daoNhanVien = new DaoNhanVien(ThongKe.this);

    ArrayList<Phong> phong = new ArrayList<>();
    DaoPhong daoPhong = new DaoPhong(ThongKe.this);

    ArrayList<DuAn> duAn = new ArrayList<>();
    DaoDuAn daoDuAn = new DaoDuAn(ThongKe.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        AnhXa();

        btnThongKeNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nv = daoNhanVien.getAll();
                int dem = 0;
                for (int i = 0; i < nv.size(); i++){
                    dem = dem +1;
                }
                editThongKeNV.setText(dem+"");
            }
        });


        btnThongKeDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duAn = daoDuAn.getAll();
                int dem = 0;
                for (int i = 0; i < duAn.size(); i++){
                    dem = dem +1;
                }
                editThongKeDA.setText(dem+"");
            }
        });


        btnThongKePhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phong = daoPhong.getAll();
                int dem = 0;
                for (int i = 0; i < phong.size(); i++){
                    dem = dem +1;
                }
                editThongKePhong.setText(dem+"");
            }
        });
        btnQuaylaiTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
    private  void AnhXa(){
        btnThongKeNV= findViewById(R.id.btn_ThongKeNV);
        btnThongKeDA= findViewById(R.id.btn_ThongKeDA);
        btnThongKePhong= findViewById(R.id.btn_ThongKePhong);
        btnQuaylaiTK= findViewById(R.id.btn_QuaylaiTK);
        editThongKeNV= findViewById(R.id.edit_ThongKeNV);
        editThongKeDA= findViewById(R.id.edit_ThongKeDA);
        editThongKePhong= findViewById(R.id.edit_ThongKePhong);
    }
}