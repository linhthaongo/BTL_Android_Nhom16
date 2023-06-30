package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.trangchu.blt.adapter.DuAnAdapter;
import com.trangchu.blt.dao.DaoDuAn;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.DuAn;
import com.trangchu.blt.khaibao.Phong;

import java.util.ArrayList;

public class ThemDuAn extends AppCompatActivity {
    LinearLayout linearLayout;
    EditText edtMaDuAn, edtTenDuAn, edtMoTa;
    Button btnLuu, btnHuy;
    DaoDuAn daoDuAn;
    DuAnAdapter duAnAdapter;
    ArrayList<Phong> dsLop = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themduan);

        linearLayout = findViewById(R.id.linearLayoutThemPhong);
        edtMaDuAn = findViewById(R.id.edit_MaDuAnThem);
        edtTenDuAn = findViewById(R.id.edit_TenDuAnThem);
        edtMoTa = findViewById(R.id.edit_TenMotaThem);
        btnLuu = findViewById(R.id.btn_ThemDuAn);
        btnHuy = findViewById(R.id.btn_HuyDuAnThem);

        daoDuAn = new DaoDuAn(ThemDuAn.this);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maDuAn = edtMaDuAn.getText().toString();
                String tenDuAn = edtTenDuAn.getText().toString();
                String moTa = edtMoTa.getText().toString();
                if (maDuAn.equals("")){
                    Toast.makeText(ThemDuAn.this, "Mã lớp không được để trống.", Toast.LENGTH_SHORT).show();
                } else if(tenDuAn.equals("")){
                    Toast.makeText(ThemDuAn.this, "Tên phòng không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else {
                    DuAn duAn= new DuAn(maDuAn, tenDuAn, moTa);
                    if (daoDuAn.themDuAn(duAn)){
                        Toast.makeText(ThemDuAn.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ThemDuAn.this, DanhSachDuAn.class));
                        finish();
                    } else {
                        Toast.makeText(ThemDuAn.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThemDuAn.this, DanhSachDuAn.class));
                finish();
            }
        });
    }
}