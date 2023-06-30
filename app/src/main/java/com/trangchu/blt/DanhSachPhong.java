package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trangchu.blt.adapter.PhongApdapter;
import com.trangchu.blt.dao.DaoNhanVien;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.NhanVien;
import com.trangchu.blt.khaibao.Phong;

import java.util.ArrayList;

public class DanhSachPhong extends AppCompatActivity {
    FloatingActionButton  btnThemPhong,btnThoatDSP;
    EditText edTimKiem;
    TextView tvAnHien;
    ArrayList<Phong> danhSachPhong = new ArrayList<>();
    ArrayList<Phong> timKiem = new ArrayList<>();
    ArrayList<NhanVien> dsNhanVien;
    static ArrayList<NhanVien> dsNhanVienDuocLoc;
    public static  boolean xetList;
    ListView listViewPhong;
    PhongApdapter phongApdapter;
    DaoPhong daoPhong;
    DaoNhanVien daoNhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachphong);

        AnhXa();

        daoPhong = new DaoPhong(DanhSachPhong.this);

        danhSachPhong = daoPhong.getAll();
        timKiem = daoPhong.getAll();

        btnThemPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DanhSachPhong.this, ThemPhong.class));
                finish();
            }
        });

        btnThoatDSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        phongApdapter = new PhongApdapter(DanhSachPhong.this, R.layout.dong_phong, danhSachPhong);
        listViewPhong.setAdapter(phongApdapter);

        if (danhSachPhong.size() == 0){
            listViewPhong.setVisibility(View.INVISIBLE);
            tvAnHien.setVisibility(View.VISIBLE);
        } else {
            listViewPhong.setVisibility(View.VISIBLE);
            tvAnHien.setVisibility(View.INVISIBLE);
        }
        listViewPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String maPhong = danhSachPhong.get(i).getMaPhong();
                daoNhanVien = new DaoNhanVien(DanhSachPhong.this);
                dsNhanVien = daoNhanVien.getAll();
                int dem = 0;
                dsNhanVienDuocLoc = new ArrayList<>();
                for (int y = 0; y < dsNhanVien.size(); y++){

                    NhanVien nv = dsNhanVien.get(y);
                    if (maPhong.equals(nv.getMaPhong().toString())){
                        dsNhanVienDuocLoc.add(dsNhanVien.get(y));
                        dem++;
                    }
                    else
                    {
                        continue;
                    }
                }
                if (dem > 0){
                    Intent intent = new Intent(DanhSachPhong.this, DanhSachNhanVien.class);
                    xetList = true;
                    TrangQuanLy.AllNV =false;
                    DanhSachDuAn.DuAnlist = false;
                    startActivity(intent);
                }
                else {
                    Toast.makeText(DanhSachPhong.this, "Không có nhân viên nào thuộc mã phòng " + danhSachPhong.get(i), Toast.LENGTH_LONG).show();
                }
            }
        });


        edTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 < i1){
                    phongApdapter.resetData();
                } else {
                    phongApdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                phongApdapter.notifyDataSetChanged();
            }
        });
    }

    private void AnhXa(){
        btnThemPhong= findViewById(R.id.btn_ThemPhongDSP);
        btnThoatDSP=findViewById(R.id.btn_ThoatDSP);
        listViewPhong = findViewById(R.id.listview_Phong);
        edTimKiem = findViewById(R.id.edit_TimPhong);
        tvAnHien = findViewById(R.id.tvAnHien);
    }
}