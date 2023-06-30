package com.trangchu.blt;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.trangchu.blt.dao.DaoDuAn;
import com.trangchu.blt.dao.DaoNhanVien;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.DuAn;
import com.trangchu.blt.khaibao.NhanVien;
import com.trangchu.blt.khaibao.Phong;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


import de.hdodenhof.circleimageview.CircleImageView;

public class ThemNhanVien extends AppCompatActivity {

    EditText edtTennv, edtManv ,edtGT , edtemail,edtSDT,edtNgaySinh,edtdiachi,edtMaPhong, edtMaDA ;
    CircleImageView ImgHinhNV;
    Button btnThem,btnHuy,ibtnCam,ibtnFolder;
    DaoNhanVien daoSach;
    ArrayList<Phong> phongList = new ArrayList<>();
    ArrayList<DuAn> duAnList = new ArrayList<>();
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themnhanvien);
        DaoPhong daoPhong = new DaoPhong(ThemNhanVien.this);
        DaoDuAn daoDuAn = new DaoDuAn(ThemNhanVien.this);
        duAnList = daoDuAn.getAll();
        phongList = daoPhong.getAll();

        edtManv = findViewById(R.id.edit_MaNhanVienThem);
        edtTennv = findViewById(R.id.edit_TenNhanVienThem);
        edtGT = findViewById(R.id.edit_SDTThemGT);
        edtNgaySinh = findViewById(R.id.edit_SDTThemNS);
        edtSDT = findViewById(R.id.edit_SDTThemNV);
        edtemail = findViewById(R.id.edit_SDTThemEmail);
        ImgHinhNV = findViewById(R.id.img_AnhNV);
        edtdiachi = findViewById(R.id.edit_DiaChiNV);
        btnThem =findViewById(R.id.btn_ThemNV);
        btnHuy = findViewById(R.id.btn_HuyThemNV);
        edtMaPhong = findViewById(R.id.edtMaPhong);
        edtMaDA = findViewById(R.id.edtMaDuAn);

        ibtnCam = findViewById(R.id.btn_ThemAnhCAM);
        ibtnFolder = findViewById(R.id.btn_ThemAnhNV);
        BitmapDrawable bitmapDrawable1 = (BitmapDrawable) AppCompatResources.getDrawable(ThemNhanVien.this,R.drawable.nvmd);
        ImgHinhNV.setImageDrawable(bitmapDrawable1);

        daoSach = new DaoNhanVien(ThemNhanVien.this);

        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemNhanVien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //i: năm , i1: tháng, i2: ngày
                        calendar.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam, thang, ngay);
                datePickerDialog.show();
            }
        });

        //open camera
        ibtnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });


        //open folder
        ibtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
                    String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    String maNhanVien = edtManv.getText().toString();
                    String tenNhanVien = edtTennv.getText().toString();
                    String gioiTinh = edtGT.getText().toString();
                    String ngaySinh = edtNgaySinh.getText().toString();
                    String sdt =edtSDT.getText().toString();;
                    String diaChi = edtdiachi.getText().toString();
                    String email = edtemail.getText().toString();
                    String maPhong = edtMaPhong.getText().toString();
                    String maDuAn =edtMaDA.getText().toString();

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

                    //data-> byte[]
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) ImgHinhNV.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
                    byte[] hinh = byteArrayOutputStream.toByteArray();

                    if (maNhanVien.equals("")) {
                        Toast.makeText(ThemNhanVien.this, "Mã nhân viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (tenNhanVien.isEmpty()) {
                        Toast.makeText(ThemNhanVien.this, "Tên nhân viên không được để trống", Toast.LENGTH_LONG).show();

                    }
                    else if (gioiTinh.isEmpty()) {
                        Toast.makeText(ThemNhanVien.this, "Giới tính không được để trống", Toast.LENGTH_LONG).show();

                    }
                    else if (ngaySinh.isEmpty()) {
                        Toast.makeText(ThemNhanVien.this, "Ngày sinh không được để trống", Toast.LENGTH_LONG).show();

                    }
                    else if (sdt.isEmpty()) {
                        Toast.makeText(ThemNhanVien.this, "Số điện thoại không được để trống", Toast.LENGTH_LONG).show();

                    }
                    else if (sdt.matches(reg) == false) {
                        Toast.makeText(ThemNhanVien.this, "SDT không đúng định dạng", Toast.LENGTH_LONG).show();
                    }
                    else if (diaChi.isEmpty()) {
                        Toast.makeText(ThemNhanVien.this, "Địa chỉ không được để trống", Toast.LENGTH_LONG).show();
                    }
                    else if (tenNhanVien.matches((".*[0-9].*"))) {
                        Toast.makeText(ThemNhanVien.this, "Tên chỉ được nhập chuỗi", Toast.LENGTH_LONG).show();
                    } else if (email.equals("")) {
                        Toast.makeText(ThemNhanVien.this, "Email nhân viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (email.matches(pattern) == false) {
                        Toast.makeText(ThemNhanVien.this, "Bạn nhập sai định dạng email", Toast.LENGTH_SHORT).show();
                    }else if (!checkDA) {
                        Toast.makeText(ThemNhanVien.this, "Bạn nhập mã dự án không hợp lệ.", Toast.LENGTH_SHORT).show();
                    }else if (!checkP) {
                        Toast.makeText(ThemNhanVien.this, "Bạn nhập mã phòng không hợp lệ.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(gioiTinh.equals("Nam")  || gioiTinh.equals("Nữ"))
                        {
                            NhanVien s = new NhanVien(maNhanVien,tenNhanVien,gioiTinh,ngaySinh,sdt,diaChi,email,maPhong,maDuAn,hinh);
                            if (daoSach.themNV(s)) {
                                Toast.makeText(ThemNhanVien.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ThemNhanVien.this, DanhSachNhanVien.class));
                                finish();
                            } else {
                                Toast.makeText(ThemNhanVien.this, " Không được trùng mã nhân viên ", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(ThemNhanVien.this, "Giới tính chỉ được nhập Nam hoặc Nữ.", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ThemNhanVien.this, ImgHinhNV.getResources().toString() + e, Toast.LENGTH_LONG).show();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(ThemNhanVien.this, DanhSachNhanVien.class));
                    finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ThemNhanVien.this, "Lỗi : " + e, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //result image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode ==RESULT_OK && data !=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ImgHinhNV.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ImgHinhNV.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}