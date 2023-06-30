package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trangchu.blt.adapter.NhanVienAdapter;
import com.trangchu.blt.dao.DaoNhanVien;
import com.trangchu.blt.khaibao.NhanVien;

import java.util.ArrayList;

public class DanhSachNhanVien extends AppCompatActivity {

    Context context;
    ArrayList<NhanVien> dsnhanvien;
    ArrayList<NhanVien> timKiem = new ArrayList<>();
    public static ArrayList<NhanVien> dsnvloc;
    ListView lvNhanvien;
    FloatingActionButton btnThemNV,btnThoatNV;
    NhanVienAdapter nhanVienAdapter;
    DaoNhanVien daoNhanVien;

    EditText edtTimKiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachnhanvien);

        btnThemNV = findViewById(R.id.btn_ThemNVDSDA);
        btnThoatNV = findViewById(R.id.btn_ThoatDSNV);
        lvNhanvien = findViewById(R.id.list_NhanVien);
        edtTimKiem = findViewById(R.id.edit_TimNhanVien);

        daoNhanVien = new DaoNhanVien(DanhSachNhanVien.this);
        dsnhanvien = daoNhanVien.getAll();

        btnThoatNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DanhSachNhanVien.this, TrangQuanLy.class));
                onBackPressed();
            }
        });

        btnThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(DanhSachNhanVien.this, ThemNhanVien.class));
            }
        });
        dsnvloc = new ArrayList<>();
        daoNhanVien = new DaoNhanVien(DanhSachNhanVien.this);
        if (DanhSachPhong.xetList == true) {
            dsnvloc = DanhSachPhong.dsNhanVienDuocLoc;
        }else if(DanhSachDuAn.DuAnlist ==true){
            dsnvloc = DanhSachDuAn.nvlistDuocLoc;
        }
        else if(TrangQuanLy.AllNV ==true){
            dsnvloc = daoNhanVien.getAll();
        }
        timKiem = daoNhanVien.getAll();

        nhanVienAdapter = new NhanVienAdapter(DanhSachNhanVien.this, R.layout.dong_nhanvien, dsnvloc);
        lvNhanvien.setAdapter(nhanVienAdapter);
        lvNhanvien.setTextFilterEnabled(true);
        lvNhanvien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final NhanVien nhanVien = dsnvloc.get(i);
                Intent intent = new Intent(view.getContext(), ChiTietNhanVien.class);
                Bundle bundle = new Bundle();
                // đóng gói kiểu dữ liệu String, Boolean
                bundle.putString("key_1", nhanVien.getMaNhanVien());
                bundle.putString("key_2", nhanVien.getTenNhanVien());
                bundle.putString("key_3", nhanVien.getGioiTinh());
                bundle.putString("key_4", nhanVien.getNgaySinh());
                bundle.putString("key_5", nhanVien.getSdt());
                bundle.putString("key_6", nhanVien.getDiaChi());
                bundle.putString("key_7", nhanVien.getMaDuAn());
                bundle.putString("key_8", nhanVien.getMaPhong());
                bundle.putString("key_9", nhanVien.getEmail());
                bundle.putByteArray("key_10", nhanVien.getHinh());
                // đóng gói bundle vào intent
                intent.putExtras(bundle);
                // start SecondActivity
                view.getContext().startActivity(intent);
            }
        });
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 < i1){
                    nhanVienAdapter.resetData();
                } else {
                    nhanVienAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                nhanVienAdapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    protected void onResume() {
        if (DanhSachPhong.xetList == true) {
            dsnhanvien = DanhSachPhong.dsNhanVienDuocLoc;
        } else {
            dsnhanvien.clear();
            dsnhanvien.addAll(daoNhanVien.getAll());
        }
        nhanVienAdapter.notifyDataSetChanged();
        super.onResume();
    }
}