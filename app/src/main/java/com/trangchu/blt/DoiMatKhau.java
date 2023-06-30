package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.trangchu.blt.dao.DaoTaiKhoan;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;

import java.util.ArrayList;

public class DoiMatKhau extends AppCompatActivity {

    EditText edtCtk, edtCmk, edtMkm;
    Button btnDoiMatKhau, btnHuy;
    DaoTaiKhoan daoTaiKhoan;
    ProgressBar progressBarDoiMK;
    ArrayList<TaiKhoaMatKhau> dsTaiKhoan = new ArrayList<>();
    boolean passVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);

        AnhXa();
        progressBarDoiMK.setVisibility(View.INVISIBLE);
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean xetTk = false, xetMk = true;
                daoTaiKhoan = new DaoTaiKhoan(DoiMatKhau.this);
                String taikhoan = edtCtk.getText().toString();
                String matkhau = edtCmk.getText().toString();
                String matkhaumoi = edtMkm.getText().toString();
                TaiKhoaMatKhau tkmkMoi = new TaiKhoaMatKhau(taikhoan, matkhaumoi);
                dsTaiKhoan = daoTaiKhoan.getAll();
                //Kiểm tra tài khoản mật khẩu có khớp vs tk trong danh sách không
                for (int i = 0; i < dsTaiKhoan.size(); i++){
                    TaiKhoaMatKhau tkx = dsTaiKhoan.get(i);
                    if (tkx.getTaiKhoan().matches(taikhoan) && tkx.getMatKhau().matches(matkhau)){
                        xetTk = true;
                        break;
                    }
                }
                String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

                if (matkhau.matches(matkhaumoi)){
                    xetMk = false;
                }

                else {
                    xetMk = true;
                } if (taikhoan.isEmpty()){
                    Toast.makeText(DoiMatKhau.this, "Tên tài khoản không được để trống!", Toast.LENGTH_SHORT).show();
                } else{
                    if (matkhau.isEmpty() || matkhaumoi.isEmpty()){
                        Toast.makeText(DoiMatKhau.this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!matkhaumoi.matches(regex)){
                        Toast.makeText(DoiMatKhau.this, "Mật khẩu mới phải bao gồm: tối thiểu 8 ký tự, ít nhất một chữ cái và một số", Toast.LENGTH_LONG).show();
                    }else {
                        if (xetTk == true){
                            if (xetMk == true){
                                progressBarDoiMK.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        daoTaiKhoan.doiMatKhau(tkmkMoi);
                                        Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DoiMatKhau.this, DangNhap.class ));
                                        finish();
                                    }
                                }, 500);
                            } else {
                                Toast.makeText(DoiMatKhau.this, "Mật khẩu cũ không được trùng với mật khẩu mới!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DoiMatKhau.this, "Tên tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        edtCmk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=edtCmk.getRight()-edtCmk.getCompoundDrawables()[Right].getBounds().width()){
                        int selection= edtCmk.getSelectionEnd();
                        if(passVisible){
                            edtCmk.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatkhoa,0);
                            edtCmk.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible=false;
                        }
                        else {
                            edtCmk.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatmo,0);
                            edtCmk.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible=true;
                        }
                        edtCmk.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });
        edtMkm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=edtMkm.getRight()-edtMkm.getCompoundDrawables()[Right].getBounds().width()){
                        int selection= edtMkm.getSelectionEnd();
                        if(passVisible){
                            edtMkm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatkhoa,0);
                            edtMkm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible=false;
                        }
                        else {
                            edtMkm.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatmo,0);
                            edtMkm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible=true;
                        }
                        edtMkm.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });
    }

    private void AnhXa(){
        edtCtk = findViewById(R.id.edit_NhapTKDMK);
        edtCmk = findViewById(R.id.edit_MatKhauDMK);
        edtMkm = findViewById(R.id.edit_XacNhanDMK);
        btnDoiMatKhau = findViewById(R.id.btn_XacNhanDMK);
        btnHuy = findViewById(R.id.btn_HuyDMK);
        progressBarDoiMK=findViewById(R.id.progress_Luu);
    }
}