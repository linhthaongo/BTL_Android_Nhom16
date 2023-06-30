package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

public class ChiTietNhanVien extends AppCompatActivity {
    TextView txtSdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietnhanvien);

        Bundle bundle = getIntent().getExtras();
        String MaNhanVien = bundle.getString("key_1");
        String TenNhanVien = bundle.getString("key_2");
        String GioiTinh = bundle.getString("key_3");
        String NgaySinh = bundle.getString("key_4");
        String SoDienThoai = bundle.getString("key_5");
        String DiaChi = bundle.getString("key_6");
        String MaDuAn = bundle.getString("key_7");
        String MaPhong = bundle.getString("key_8");
        String Email = bundle.getString("key_9");
        byte[] HinhAnh = bundle.getByteArray("key_10");


        TextView tvMaNhanVien = findViewById(R.id.txt_MaNVCT);
        TextView tvTenNhanVien = findViewById(R.id.txt_tenNVCT);
        TextView tvNgaySinh = findViewById(R.id.txt_NgaySinhNVCT);
        TextView tvGioiTinh = findViewById(R.id.txt_GioiTinhNVCT);
        TextView tvSDT = findViewById(R.id.txt_SdtNVCT);
        TextView tvDiaChi = findViewById(R.id.txt_diachiNVCT);
        TextView tvEmail = findViewById(R.id.txt_EmailNVCT);
        TextView tvMaPhong = findViewById(R.id.txt_PhongNVCT);
        TextView tvMaDuAn= findViewById(R.id.txt_DuAnNVCT);
        ImageView hinhAnhCT = findViewById(R.id.img_AnhNV);


        tvMaNhanVien.setText(MaNhanVien);
        tvTenNhanVien.setText(TenNhanVien);
        tvNgaySinh.setText(NgaySinh);
        tvGioiTinh.setText(GioiTinh);
        tvSDT.setText(SoDienThoai);
        tvDiaChi.setText(DiaChi);
        tvEmail.setText(Email);
        tvMaPhong.setText(MaPhong);
        tvMaDuAn.setText(MaDuAn);


        if(HinhAnh !=null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(HinhAnh, 0, HinhAnh.length);
            hinhAnhCT.setImageBitmap(bitmap);
        }




        Button btnQuayLai = findViewById(R.id.btn_QuayLaiNVCT);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtSdt = findViewById(R.id.txt_SdtNVCT);
        txtSdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    String Sdt ="tel:"+tvSDT.getText().toString().trim();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(Sdt));
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(ChiTietNhanVien.this, "Lỗi: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}