package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.Phong;

import java.util.ArrayList;

public class ThemPhong extends AppCompatActivity {
    LinearLayout linearLayout;
    EditText edtMaPhong, edtTenPhong,edtMoTaPhong;
    Button btnLuu, btnHuy;
    DaoPhong daoPhong;
    ArrayList<Phong> dsLop = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themphong);

        linearLayout = findViewById(R.id.linearLayoutThemPhong);
        edtMaPhong = findViewById(R.id.edit_MaPhongThem);
        edtTenPhong = findViewById(R.id.edit_TenPhongThem);
        edtMoTaPhong= findViewById(R.id.edit_MotaPhongThem);
        btnLuu = findViewById(R.id.btn_LuuPhongThem);
        btnHuy = findViewById(R.id.btn_HuyPhongThem);

        daoPhong = new DaoPhong(ThemPhong.this);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = edtMaPhong.getText().toString();
                String tenPhong = edtTenPhong.getText().toString();
                String moTaPhong = edtMoTaPhong.getText().toString();
                if (maPhong.equals("")){
                    Toast.makeText(ThemPhong.this, "Mã lớp không được để trống.", Toast.LENGTH_SHORT).show();
                } else if(tenPhong.equals("")){
                    Toast.makeText(ThemPhong.this, "Tên phòng không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    Phong phong= new Phong(maPhong, tenPhong,moTaPhong);
                    if (daoPhong.insert(phong)){
                        Toast.makeText(ThemPhong.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ThemPhong.this, DanhSachPhong.class));
                        finish();
                    } else {
                        Toast.makeText(ThemPhong.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThemPhong.this, DanhSachPhong.class));
                finish();
            }
        });
    }
}