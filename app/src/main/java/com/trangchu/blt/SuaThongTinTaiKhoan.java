package com.trangchu.blt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trangchu.blt.dao.DaoTaiKhoan;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuaThongTinTaiKhoan extends AppCompatActivity {
    EditText tenTKSua,hoTenSua,SDTSua,emailSua;
    CircleImageView anhSua;
    Button btnLuuSuaTK,btnHuySuaTK,openFolder,openCamera;
    byte[] anhDaiDien;
    String matKhau,tenTaiKhoan,hoTen,SDT,Email;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    DaoTaiKhoan daoTaiKhoan = new DaoTaiKhoan(SuaThongTinTaiKhoan.this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suathongtintaikhoan);

        AnhXa();
        HienThi();

        btnLuuSuaTK=findViewById(R.id.btn_LuuSuaTK);
        btnHuySuaTK=findViewById(R.id.btn_HuySuaTK);

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });

        openFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });


        btnLuuSuaTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
                    String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    String tenTKNew = tenTKSua.getText().toString();
                    String hoTenNew = hoTenSua.getText().toString();
                    String SDTNew = SDTSua.getText().toString();
                    String emailNew = emailSua.getText().toString();
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) anhSua.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] hinh = byteArrayOutputStream.toByteArray();

                    if (tenTKNew.equals("")) {
                        Toast.makeText(SuaThongTinTaiKhoan.this, "Tên tài khoản không được để trống!", Toast.LENGTH_LONG).show();
                    } else if (hoTenNew.equals("")) {
                        Toast.makeText(SuaThongTinTaiKhoan.this, "Tên không được để trống!", Toast.LENGTH_LONG).show();
                    } else if (SDTNew.equals("")) {
                        Toast.makeText(SuaThongTinTaiKhoan.this, "SDT không được để trống!", Toast.LENGTH_LONG).show();
                    } else if (SDTNew.matches(reg) == false) {
                        Toast.makeText(SuaThongTinTaiKhoan.this, "SDT không đúng định dạng", Toast.LENGTH_LONG).show();
                    } else if (emailNew.equals("")) {
                        Toast.makeText(SuaThongTinTaiKhoan.this, "Email không được để trống!", Toast.LENGTH_LONG).show();
                    } else if (emailNew.matches(pattern) == false) {
                        Toast.makeText(SuaThongTinTaiKhoan.this, "Bạn nhập sai định dạng email!", Toast.LENGTH_SHORT).show();
                    } else {
                        TaiKhoaMatKhau tk = new TaiKhoaMatKhau(tenTKNew, matKhau, hoTenNew, emailNew, hinh, SDTNew);
                        if (daoTaiKhoan.SuaTK(tk)) {
                            Toast.makeText(SuaThongTinTaiKhoan.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(SuaThongTinTaiKhoan.this, Thongtintaikhoan.class));
                            Bundle bundle = getIntent().getExtras();
                            Intent intent = new Intent(SuaThongTinTaiKhoan.this, Thongtintaikhoan.class);
                            // đóng gói kiểu dữ liệu String
                            bundle.putString("key_1", tenTKNew);
                            bundle.putString("key_2", hoTenNew);
                            bundle.putString("key_3", SDTNew);
                            bundle.putString("key_4", emailNew);
                            bundle.putByteArray("key_5", hinh);
                            bundle.putString("key_6", matKhau);
                            intent.putExtras(bundle);
                            view.getContext().startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SuaThongTinTaiKhoan.this, "Sửa thất bại!", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e){
                    Toast.makeText(SuaThongTinTaiKhoan.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuySuaTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                Intent intent = new Intent(SuaThongTinTaiKhoan.this, Thongtintaikhoan.class);
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
    }


    private void HienThi(){
        Bundle bundle = getIntent().getExtras();
        tenTaiKhoan = bundle.getString("key_1");
        hoTen = bundle.getString("key_2");
        SDT = bundle.getString("key_3");
        Email = bundle.getString("key_4");
        anhDaiDien = bundle.getByteArray("key_5");
        matKhau = bundle.getString("key_6");


        tenTKSua.setText(tenTaiKhoan);
        hoTenSua.setText(hoTen);
        SDTSua.setText(SDT);
        emailSua.setText(Email);
        if(anhDaiDien != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhDaiDien, 0, anhDaiDien.length);
            anhSua.setImageBitmap(bitmap);
        }
        else {
            BitmapDrawable bitmapDrawable1 = (BitmapDrawable) AppCompatResources.getDrawable(SuaThongTinTaiKhoan.this,R.drawable.nvmd);
            anhSua.setImageDrawable(bitmapDrawable1);
        }

    }

    private void AnhXa(){
        tenTKSua = findViewById(R.id.edit_TenSuaTK);
        hoTenSua = findViewById(R.id.edit_HoTenSuaTK);
        SDTSua = findViewById(R.id.edit_SDTSuaTK);
        emailSua = findViewById(R.id.edit_EmailSuaTK);
        anhSua = findViewById(R.id.img_AnhSuaTK);
        openFolder =findViewById(R.id.btn_AnhTKSuaFolder);
        openCamera = findViewById(R.id.btn_AnhTKSuaCam);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode ==RESULT_OK && data !=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            anhSua.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                anhSua.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}