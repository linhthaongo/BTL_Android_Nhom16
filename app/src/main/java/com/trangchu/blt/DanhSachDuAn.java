package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trangchu.blt.adapter.DuAnAdapter;
import com.trangchu.blt.adapter.PhongApdapter;
import com.trangchu.blt.dao.DaoDuAn;
import com.trangchu.blt.dao.DaoNhanVien;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.DuAn;
import com.trangchu.blt.khaibao.NhanVien;
import com.trangchu.blt.khaibao.Phong;

import java.util.ArrayList;

public class DanhSachDuAn extends AppCompatActivity {
    FloatingActionButton btnThemDuAn,btnThoatDuAn;
    EditText edTimKiem;
    TextView tvAnHien;
    ArrayList<DuAn> dsDuAn = new ArrayList<>();
    ArrayList<DuAn> timkiem = new ArrayList<>();

    ArrayList<NhanVien> dsNhanVien;
    static ArrayList<NhanVien> nvlistDuocLoc;
    public static boolean DuAnlist;

    ListView lvDuAn;
    DuAnAdapter duAnAdapter;

    DaoDuAn daoDuAn;
    DaoNhanVien daoNhanVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachduan);

        AnhXa();
        daoDuAn = new DaoDuAn(DanhSachDuAn.this);
        dsDuAn = daoDuAn.getAll();
        timkiem = daoDuAn.getAll();

        btnThemDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DanhSachDuAn.this, ThemDuAn.class));
                finish();
            }
        });

        btnThoatDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DanhSachDuAn.this, TrangQuanLy.class));
                onBackPressed();
            }
        });


        duAnAdapter = new DuAnAdapter(DanhSachDuAn.this, R.layout.dong_duan, dsDuAn);
        lvDuAn.setAdapter(duAnAdapter);

        if (dsDuAn.size() == 0){
            lvDuAn.setVisibility(View.INVISIBLE);
            tvAnHien.setVisibility(View.VISIBLE);
        } else {
            lvDuAn.setVisibility(View.VISIBLE);
            tvAnHien.setVisibility(View.INVISIBLE);
        }

        lvDuAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String maDuAn = dsDuAn.get(i).toString();
                daoNhanVien = new DaoNhanVien(DanhSachDuAn.this);
                dsNhanVien = daoNhanVien.getAll();
                int dem = 0;
                nvlistDuocLoc = new ArrayList<>();
                for (int y = 0; y < dsNhanVien.size(); y++){
                    NhanVien nv = dsNhanVien.get(y);
                    if (maDuAn.matches(nv.getMaDuAn())){
                        nvlistDuocLoc.add(dsNhanVien.get(y));
                        dem++;
                    }
                }
                if (dem > 0){
                    Intent intent = new Intent(DanhSachDuAn.this, DanhSachNhanVien.class);
                    DuAnlist = true;
                    DanhSachPhong.xetList = false;
                    TrangQuanLy.AllNV =false;
                    startActivity(intent);
                } else {
                    Toast.makeText(DanhSachDuAn.this, "Không có nhân viên nào thuộc mã dự án này " + dsDuAn.get(i), Toast.LENGTH_LONG).show();
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
                    duAnAdapter.resetData();
                } else {
                    duAnAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                duAnAdapter.notifyDataSetChanged();
            }
        });
    }

    private void AnhXa(){
        btnThemDuAn = findViewById(R.id.btn_ThemDuAn);
        btnThoatDuAn= findViewById(R.id.btn_ThoatDSDA);
        edTimKiem = findViewById(R.id.edit_TimDuAN);
        tvAnHien = findViewById(R.id.tvAnHien);
        lvDuAn = findViewById(R.id.list_DuAn);
    }
}