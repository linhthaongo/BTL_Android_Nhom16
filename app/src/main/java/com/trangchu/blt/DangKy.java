package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trangchu.blt.dao.DaoTaiKhoan;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;

import java.util.ArrayList;

public class DangKy extends AppCompatActivity {

    Button btnDangKy, btnHuy;
    EditText editTaiKhoan, editMatKhau,editXacNhanMK;
    ArrayList<TaiKhoaMatKhau> dsTaiKhoan = new ArrayList<>();
    DaoTaiKhoan daoTaiKhoan;
    boolean passVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        AnhXa();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daoTaiKhoan = new DaoTaiKhoan(DangKy.this);

                String taikhoan = editTaiKhoan.getText().toString();
                String matkhau = editMatKhau.getText().toString();
                String xnmatkhau = editXacNhanMK.getText().toString();
                boolean xetTk = true, xetMk = false;
                TaiKhoaMatKhau taiKhoaMatKhau = new TaiKhoaMatKhau(taikhoan, matkhau);

                dsTaiKhoan = daoTaiKhoan.getAll();
                if (matkhau.matches(xnmatkhau)){
                    xetMk = true;
                } else {
                    xetMk = false;
                }

                for (int i = 0; i<dsTaiKhoan.size(); i++){
                    TaiKhoaMatKhau taiKhoaMatKhau1 = dsTaiKhoan.get(i);
                    if (taiKhoaMatKhau1.getTaiKhoan().matches(taikhoan)){
                        xetTk = false;
                        break;
                    }
                }
                //tối thiểu tám ký tự, ít nhất một chữ cái và một số
                String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

                if (taikhoan.isEmpty()){
                    Toast.makeText(DangKy.this, "Tên tài khoản không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    if (matkhau.isEmpty() || xnmatkhau.isEmpty()){
                        Toast.makeText(DangKy.this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!matkhau.matches(regex)){
                        Toast.makeText(DangKy.this, "Mật khẩu phải bao gồm: tối thiểu 8 ký tự, ít nhất một chữ cái và một số", Toast.LENGTH_LONG).show();
                    }else {
                        if (xetTk == true){
                            if (xetMk == true){
                                daoTaiKhoan.Them(taiKhoaMatKhau);
                                Toast.makeText(DangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("taikhoan", taikhoan);
                                intent.putExtra("matkhau", matkhau);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                Toast.makeText(DangKy.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DangKy.this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKy.this, DangNhap.class));
                editTaiKhoan.setText("");
                editMatKhau.setText("");
                editXacNhanMK.setText("");
            }
        });

        editMatKhau.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=editMatKhau.getRight()-editMatKhau.getCompoundDrawables()[Right].getBounds().width()){
                        int selection= editMatKhau.getSelectionEnd();
                        if(passVisible){
                            editMatKhau.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatkhoa,0);
                            editMatKhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible=false;
                        }
                        else {
                            editMatKhau.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatmo,0);
                            editMatKhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible=true;
                        }
                        editMatKhau.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });

        editXacNhanMK.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=editXacNhanMK.getRight()-editXacNhanMK.getCompoundDrawables()[Right].getBounds().width()){
                        int selection= editXacNhanMK.getSelectionEnd();
                        if(passVisible){
                            editXacNhanMK.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatkhoa,0);
                            editXacNhanMK.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible=false;
                        }
                        else {
                            editXacNhanMK.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatmo,0);
                            editXacNhanMK.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible=true;
                        }
                        editXacNhanMK.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });
    }

    private void AnhXa(){
        btnDangKy =findViewById(R.id.btn_DangKyDK);
        btnHuy = findViewById(R.id.btn_HuyDK);
       editTaiKhoan= findViewById(R.id.edit_TaiKhoanDK);
       editMatKhau=findViewById(R.id.edit_MatKhauDK);
       editXacNhanMK=findViewById(R.id.edit_XacNhanMKDK);
    }
}