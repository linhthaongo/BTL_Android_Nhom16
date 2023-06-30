package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.TelephonyNetworkSpecifier;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Thongtintaikhoan extends AppCompatActivity {
    TextView tenTaiKhoanQL,hoTenQL,SDTQL,EmailQL;
    CircleImageView anhDaiDienQL;
    Button btnSuaTKTT,btnQuayLaiTKTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtintaikhoan);
        btnSuaTKTT=findViewById(R.id.btn_SuaTKTT);
        btnQuayLaiTKTT=findViewById(R.id.btn_QuayLaiTKTT);

        Bundle bundle = getIntent().getExtras();
        String tenTaiKhoan = bundle.getString("key_1");
        String hoTen = bundle.getString("key_2");
        String SDT = bundle.getString("key_3");
        String Email = bundle.getString("key_4");
        String matKhau = bundle.getString("key_6");
        byte[] anhDaiDien = bundle.getByteArray("key_5");


        tenTaiKhoanQL = findViewById(R.id.txt_TenTKTT);
        hoTenQL = findViewById(R.id.txt_HoTenTKTT);
        SDTQL = findViewById(R.id.txt_SDTTKTT);
        EmailQL = findViewById(R.id.txt_EmailTKTT);
        anhDaiDienQL =findViewById(R.id.img_AnhTT);

        tenTaiKhoanQL.setText(tenTaiKhoan);
        hoTenQL.setText(hoTen);
        SDTQL.setText(SDT);
        EmailQL.setText(Email);

        if(anhDaiDien != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhDaiDien, 0, anhDaiDien.length);
            anhDaiDienQL.setImageBitmap(bitmap);
        }
        else {
            BitmapDrawable bitmapDrawable1 = (BitmapDrawable) AppCompatResources.getDrawable(Thongtintaikhoan.this,R.drawable.nvmd);
            anhDaiDienQL.setImageDrawable(bitmapDrawable1);
        }

        btnSuaTKTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thongtintaikhoan.this, SuaThongTinTaiKhoan.class);
                // đóng gói kiểu dữ liệu String
                bundle.putString("key_1", tenTaiKhoan);
                bundle.putString("key_2", hoTen);
                bundle.putString("key_3", SDT);
                bundle.putString("key_4", Email);
                bundle.putByteArray("key_5", anhDaiDien);
                bundle.putString("key_6", matKhau);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                finish();
            }
        });

        btnQuayLaiTKTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Thongtintaikhoan.this, TrangQuanLy.class);
                // đóng gói kiểu dữ liệu String
                bundle.putString("key_1", tenTaiKhoan);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

    }
}