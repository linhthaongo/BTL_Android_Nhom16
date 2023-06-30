package com.trangchu.blt;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.trangchu.blt.adapter.NhanVienAdapter;
import com.trangchu.blt.dao.DaoDuAn;
import com.trangchu.blt.dao.DaoNhanVien;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.DuAn;
import com.trangchu.blt.khaibao.NhanVien;
import com.trangchu.blt.khaibao.Phong;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class SuaNhanVien extends AppCompatActivity {


    DaoNhanVien daoNhanVien = new DaoNhanVien(SuaNhanVien.this);
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    EditText edtMaNhanVien,edtTenNhanVien,edtNgaySinh,edtGioiTinh,edtSDT,edtDiaChi,edtEmail,edtMaPhong,edtMaDuAn;
    ImageView hinhAnhNV;
    DaoPhong daoPhong = new DaoPhong(SuaNhanVien.this);
    DaoDuAn daoDuAn = new DaoDuAn(SuaNhanVien.this);
    ArrayList<Phong> phongList = new ArrayList<>();
    ArrayList<DuAn> duAnList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suanhanvien);
        duAnList = daoDuAn.getAll();
        phongList = daoPhong.getAll();


        edtMaNhanVien = findViewById(R.id.edit_MaNhanVienSua);
        edtTenNhanVien = findViewById(R.id.edit_TenNhanVienSua);
        edtNgaySinh = findViewById(R.id.edit_NgaySinhSuaNV);
        edtGioiTinh = findViewById(R.id.edit_GioiTinhSuaNV);
        edtSDT = findViewById(R.id.edit_SDTSuaNV);
        edtDiaChi = findViewById(R.id.edit_DiaChiSuaNV);
        edtEmail = findViewById(R.id.edit_EmailSuaNV);
        edtMaPhong = findViewById(R.id.edit_MaPhongSuaNV);
        edtMaDuAn = findViewById(R.id.edit_MaDuAnSuaNV);
        hinhAnhNV  = findViewById(R.id.img_AnhSuaNV);

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

        Button btnSua = findViewById(R.id.btn_LuuSuaNV);
        Button btnHuy = findViewById(R.id.btn_HuySuaNV);
        Button btnOpenCam = findViewById(R.id.btn_AnhNVSuaCam);
        Button btnOpenFolder = findViewById(R.id.btn_AnhNVSuaFolder);



        edtMaNhanVien.setText(MaNhanVien);
        edtTenNhanVien.setText(TenNhanVien);
        edtNgaySinh.setText(NgaySinh);
        edtGioiTinh.setText(GioiTinh);
        edtSDT.setText(SoDienThoai);
        edtDiaChi.setText(DiaChi);
        edtEmail.setText(Email);
        edtMaPhong.setText(MaPhong);
        edtMaDuAn.setText(MaDuAn);



        //byte->bitmap
        if(HinhAnh !=null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(HinhAnh, 0, HinhAnh.length);
            hinhAnhNV.setImageBitmap(bitmap);
        }
        else
        {
            BitmapDrawable bitmapDrawable1 = (BitmapDrawable) AppCompatResources.getDrawable(SuaNhanVien.this,R.drawable.nvmd);
            hinhAnhNV.setImageDrawable(bitmapDrawable1);
        }

        btnOpenCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });

        btnOpenFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
                String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
                String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                String maNhanVien = edtMaNhanVien.getText().toString();
                String tenNhanVien = edtTenNhanVien.getText().toString();
                String gioiTinh = edtGioiTinh.getText().toString();
                String ngaySinh = edtNgaySinh.getText().toString();
                String sdt = edtSDT.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                String email = edtEmail.getText().toString();
                String maPhong = edtMaPhong.getText().toString();
                String maDuAn = edtMaDuAn.getText().toString();

                //
                boolean checkDA = false;
                boolean checkP = false;
                for(int i = 0; i < duAnList.size(); i++){
                    if(duAnList.get(i).getMaDuAn().equals(maDuAn) || maDuAn.equals("")){
                        checkDA = true;
                        break;
                    }
                }
                for(int i = 0; i < phongList.size(); i++){
                    if(phongList.get(i).getMaPhong().equals(maPhong) || maPhong.equals("")){
                        checkP = true;
                        break;
                    }
                }


                if (maNhanVien.equals("")) {
                    Toast.makeText(SuaNhanVien.this, "Mã nhân viên không được để trống", Toast.LENGTH_LONG).show();
                } else if (tenNhanVien.equals("")) {
                    Toast.makeText(SuaNhanVien.this, "Tên nhân viên không được để trống", Toast.LENGTH_LONG).show();
                } else if (tenNhanVien.matches((".*[0-9].*"))) {
                    Toast.makeText(SuaNhanVien.this, "Tên chỉ được nhập chuỗi", Toast.LENGTH_LONG).show();
                } else if (email.equals("")) {
                    Toast.makeText(SuaNhanVien.this, "Email nhân viên không được để trống", Toast.LENGTH_LONG).show();
                } else if (email.matches(pattern) == false) {
                    Toast.makeText(SuaNhanVien.this, "Bạn nhập sai định dạng email", Toast.LENGTH_SHORT).show();
                }
                else if (gioiTinh.isEmpty()) {
                    Toast.makeText(SuaNhanVien.this, "Giới tính không được để trống", Toast.LENGTH_LONG).show();

                }
                else if (ngaySinh.isEmpty()) {
                    Toast.makeText(SuaNhanVien.this, "Ngày sinh không được để trống", Toast.LENGTH_LONG).show();

                }
                else if (sdt.isEmpty()) {
                    Toast.makeText(SuaNhanVien.this, "Số điện thoại không được để trống", Toast.LENGTH_LONG).show();

                }
                else if (sdt.matches(reg) == false) {
                    Toast.makeText(SuaNhanVien.this, "SDT không đúng định dạng", Toast.LENGTH_LONG).show();
                }
                else if (diaChi.isEmpty()) {
                    Toast.makeText(SuaNhanVien.this, "Địa chỉ không được để trống", Toast.LENGTH_LONG).show();

                }
                else if(hinhAnhNV.getDrawable() == null){
                    Toast.makeText(SuaNhanVien.this, "Hình ảnh không được để trống.", Toast.LENGTH_SHORT).show();
                }else if (!checkDA) {
                    Toast.makeText(SuaNhanVien.this, "Bạn nhập mã dự án không hợp lệ.", Toast.LENGTH_SHORT).show();
                }else if (!checkP) {
                    Toast.makeText(SuaNhanVien.this, "Bạn nhập mã phòng không hợp lệ.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //bitmap->byte
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) hinhAnhNV.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] hinh = byteArrayOutputStream.toByteArray();
                    if(gioiTinh.equals("Nam")  || gioiTinh.equals("Nữ"))
                    {
                        NhanVien s = new NhanVien(maNhanVien,tenNhanVien,gioiTinh,ngaySinh,sdt,diaChi,email,maPhong,maDuAn,hinh);
                        if (daoNhanVien.suaNV(s)) {
                            Toast.makeText(SuaNhanVien.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SuaNhanVien.this, DanhSachNhanVien.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SuaNhanVien.this, "Sửa thất bại!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(SuaNhanVien.this, "Giới tính chỉ được nhập Nam hoặc Nữ.", Toast.LENGTH_LONG).show();
                    }
                }
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(SuaNhanVien.this, "Lỗi : " + e, Toast.LENGTH_LONG).show();
//                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaNhanVien.this, DanhSachNhanVien.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode ==RESULT_OK && data !=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            hinhAnhNV.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                hinhAnhNV.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}