package com.trangchu.blt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {
    TextView txtDiem;
    CheckBox cBox1,cBox2,cBox3;
    SeekBar sk1,sk2,sk3;
    ImageButton imgButton;
    ImageView imgThoat;
    int soDiem=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AnhXa();
        EnableSeekBar();
        txtDiem.setText(soDiem + "");

        CountDownTimer countDownTimer= new CountDownTimer(60000,300) {
            @Override
            public void onTick(long l) {
                int khoangthem=10;
                Random random= new Random();
                int khoangMot = random.nextInt(khoangthem);
                int khoangHai = random.nextInt(khoangthem);
                int khoangBa = random.nextInt(khoangthem);
                // kiểm tra Win
                if(sk1.getProgress()>=sk1.getMax()){
                    this.cancel(); // dừng lại khi chạm đích
                    imgButton.setVisibility(View.VISIBLE);// sau khi dừng lại sẽ hiển thị button
                    Toast.makeText(Game.this,"Rắn chiến thắng!", Toast.LENGTH_SHORT).show();
                    // kiểm tra có dc check hay khong
                    if(cBox1.isChecked()){
                        soDiem +=10;
                        Toast.makeText(Game.this, "Bạn đoán chính xác!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        soDiem-=5;
                        Toast.makeText(Game.this, "Bạn đoán sai rồi!", Toast.LENGTH_SHORT).show();
                    }
                    txtDiem.setText(soDiem+"");
                    EnableCheckBox();
                }
                if(sk2.getProgress()>=sk2.getMax()){
                    this.cancel();
                    imgButton.setVisibility(View.VISIBLE);
                    Toast.makeText(Game.this,"Chuột chiến thắng!", Toast.LENGTH_SHORT).show();
                    if(cBox2.isChecked()){
                        soDiem +=10;
                        Toast.makeText(Game.this, "Bạn đoán chính xác!", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        soDiem-=5;
                        Toast.makeText(Game.this, "Bạn đoán sai rồi!", Toast.LENGTH_SHORT).show();

                    }
                    txtDiem.setText(soDiem+"");
                    EnableCheckBox();
                }
                if(sk3.getProgress()>=sk3.getMax()){
                    this.cancel();
                    imgButton.setVisibility(View.VISIBLE);

                    Toast.makeText(Game.this,"Rùa chiến thắng!", Toast.LENGTH_SHORT).show();
                    if(cBox3.isChecked()){
                        soDiem +=10;
                        Toast.makeText(Game.this, "Bạn đoán chính xác!", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        soDiem-=5;
                        Toast.makeText(Game.this, "Bạn đoán sai rồi!", Toast.LENGTH_SHORT).show();

                    }
                    txtDiem.setText(soDiem+"");
                    EnableCheckBox();
                }
                sk1.setProgress(sk1.getProgress() + khoangMot);
                sk2.setProgress(sk2 .getProgress() + khoangHai);
                sk3.setProgress(sk3.getProgress() + khoangBa);

            }

            @Override
            public void onFinish() {

            }
        };
        imgThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cBox1.isChecked()||cBox2.isChecked()||cBox3.isChecked())
                {
                    sk1.setProgress(0);// sau khi chiến thắng reset lại về 0
                    sk2.setProgress(0);
                    sk3.setProgress(0);
                    imgButton.setVisibility(View.INVISIBLE);// ẩn icon
                    countDownTimer.start();

                    DisableCheckBox();
                }
                else {
                    Toast.makeText(Game.this, "Vui lòng đặt cược trước khi chơi", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cBox2.setChecked(false);
                cBox3.setChecked(false);
            }
        });
        cBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cBox1.setChecked(false);
                cBox3.setChecked(false);
            }
        });
        cBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cBox2.setChecked(false);
                cBox1.setChecked(false);
            }
        });
    }
    private void AnhXa(){
        txtDiem         = (TextView)    findViewById(R.id.txtDiem);
        imgButton       =(ImageButton) findViewById(R.id.imgBtn);
        cBox1           = (CheckBox) findViewById(R.id.cbMot);
        cBox2           = (CheckBox) findViewById(R.id.cbHai);
        cBox3           = (CheckBox) findViewById(R.id.cbBa);
        sk1             =(SeekBar) findViewById(R.id.sneekMot);
        sk2             =(SeekBar) findViewById(R.id.sneekHai);
        sk3             =(SeekBar) findViewById(R.id.sneekBa);
        imgThoat        = findViewById(R.id.img_Thoat);
    }
    // cho checkbox tương tác
    private  void EnableCheckBox(){
        cBox1.setEnabled(true);
        cBox2.setEnabled(true);
        cBox3.setEnabled(true);
    }
    // không cho tưởng tác khi đua
    private void DisableCheckBox(){
        cBox1.setEnabled(false);
        cBox2.setEnabled(false);
        cBox3.setEnabled(false);
    }
    private  void EnableSeekBar(){
        sk1.setEnabled(false);
        sk2.setEnabled(false);
        sk3.setEnabled(false);
    }
}