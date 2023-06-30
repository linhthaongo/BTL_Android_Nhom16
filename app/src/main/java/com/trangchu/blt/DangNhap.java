package com.trangchu.blt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trangchu.blt.dao.DaoTaiKhoan;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;



import java.util.ArrayList;

public class DangNhap extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String TAG = "DangNhapActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button btnDangNhap;
    TextView txt_DangKy;
    EditText edTaiKhoan, edMatKhau;
    CheckBox cbLuuThongTin;
    DaoTaiKhoan tkDao;
    ArrayList<TaiKhoaMatKhau> listTK = new ArrayList<>();
    View topViewDN;
    ImageView viewWaveDN;

    boolean passVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_dangnhap);

        AnhXa();

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btn_DangNhapFB);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in"+ user.getUid());
                }
                else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        layThongTin();


        edMatKhau.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=edMatKhau.getRight()-edMatKhau.getCompoundDrawables()[Right].getBounds().width()){
                        int selection= edMatKhau.getSelectionEnd();
                        if(passVisible){
                            edMatKhau.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatkhoa,0);
                            edMatKhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passVisible=false;
                        }
                        else {
                            edMatKhau.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_lock,0,R.drawable.icmatmo,0);
                            edMatKhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passVisible=true;
                        }
                        edMatKhau.setSelection(selection);
                        return true;
                    }

                }

                return false;
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean xetTK = false;
                tkDao = new DaoTaiKhoan(DangNhap.this);
                String taikhoan = edTaiKhoan.getText().toString();
                String matkhau = edMatKhau.getText().toString();
                listTK = tkDao.getAll();
                for (int i = 0; i< listTK.size(); i++){
                    TaiKhoaMatKhau taiKhoaMatKhau = listTK.get(i);
                    if (taiKhoaMatKhau.getTaiKhoan().matches(taikhoan) && taiKhoaMatKhau.getMatKhau().matches(matkhau)){
                        xetTK = true;
                        break;
                    }
                }
                if (xetTK == true){
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    luuThongTin();
                    Intent intent = new Intent(DangNhap.this, TrangQuanLy.class);
                    Bundle bundle = new Bundle();
                    // đóng gói kiểu dữ liệu String
                    bundle.putString("key_1", taikhoan);
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                } else {
                    Toast.makeText(DangNhap.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivityForResult(intent, 999);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void handleFacebookAccessToken(AccessToken token){
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential authCredential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithCredential:success");
                    Intent intent = new Intent(DangNhap.this, TrangQuanLy.class);
                    startActivity(intent);

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
            }
        });
    }

    private void AnhXa(){
        btnDangNhap  = findViewById(R.id.btn_DangNhapDN);
        txt_DangKy  = findViewById(R.id.txt_DangKy1DN);
        edTaiKhoan = findViewById(R.id.edit_TaiKhoanDN);
        edMatKhau = findViewById(R.id.edit_MatKhauDN);
        cbLuuThongTin = findViewById(R.id.cb_LuuThongTin);
        topViewDN = findViewById(R.id.top_ViewDN);
        viewWaveDN = findViewById(R.id.view_WaveDN);
    }

    private void luuThongTin(){
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String taikhoan = edTaiKhoan.getText().toString();
        String matkhau = edMatKhau.getText().toString();
        boolean check = cbLuuThongTin.isChecked();
        if (!check){
            editor.clear();
        } else{
            editor.putString("taikhoan", taikhoan);
            editor.putString("matkhau", matkhau);
            editor.putBoolean("checkstatus", check);
        }
        editor.commit();
    }

    private void layThongTin(){
        SharedPreferences sharedPreferences = getSharedPreferences("taikhoan", MODE_PRIVATE);

        boolean check = sharedPreferences.getBoolean("checkstatus", false);
        if (check){
            String taikhoan = sharedPreferences.getString("taikhoan","");
            String matkhau = sharedPreferences.getString("matkhau","");
            edTaiKhoan.setText(taikhoan);
            edMatKhau.setText(matkhau);
        } else {
            edTaiKhoan.setText("");
            edMatKhau.setText("");
        }
        cbLuuThongTin.setChecked(check);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK){
            String taikhoan = data.getStringExtra("tenTaiKhoan");
            String matkhau = data.getStringExtra("matKhau");
            edTaiKhoan.setText(taikhoan);
            edMatKhau.setText(matkhau);
        }
    }
}