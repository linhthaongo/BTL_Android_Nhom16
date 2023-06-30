package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Load extends AppCompatActivity {
    RelativeLayout relaLoad;
    Animation rotateAnimation;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        relaLoad=findViewById(R.id.rela_Load);

        imageView = findViewById(R.id.img_logo);
        rotateAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Load.this, GioiThieu.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }

    private void rotateAnimation(){
        rotateAnimation = AnimationUtils.loadAnimation(this,R.anim.xoay_hinh);
        imageView.startAnimation(rotateAnimation);
    }
}