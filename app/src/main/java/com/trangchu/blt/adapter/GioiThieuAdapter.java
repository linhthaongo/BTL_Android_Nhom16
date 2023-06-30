package com.trangchu.blt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.trangchu.blt.DangNhap;
import com.trangchu.blt.GioiThieu;
import com.trangchu.blt.R;

public class GioiThieuAdapter extends PagerAdapter {

    Context ctx;

    public GioiThieuAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.activity_gioithieu,container,false);


        ImageView logo=view.findViewById(R.id.logo);

        ImageView ind1=view.findViewById(R.id.ind1);
        ImageView ind2=view.findViewById(R.id.ind2);
        ImageView ind3=view.findViewById(R.id.ind3);


        TextView title=view.findViewById(R.id.title);
        TextView desc=view.findViewById(R.id.desc);
        TextView txtthoatLoad=view.findViewById(R.id.txt_thoatLoad);

        ImageView next=view.findViewById(R.id.next);
        ImageView back=view.findViewById(R.id.back);

        TextView btnGetStarted=view.findViewById(R.id.btnGetStarted);
        txtthoatLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx, DangNhap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,DangNhap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioiThieu.viewPager.setCurrentItem(position+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioiThieu.viewPager.setCurrentItem(position-1);
            }
        });

        switch (position)
        {
            case 0:
                logo.setImageResource(R.drawable.imgbanner);
                ind1.setImageResource(R.drawable.bg_ui);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);
                btnGetStarted.setBackgroundResource(R.color.white);

                title.setText("Giao Diện Thân Thiện");
                desc.setText("Giao diện của APP được thiết kế thân thiện và dễ dàng sử dụng dựa trên trải nghiệm người dùng.");
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                break;
            case 1:
                logo.setImageResource(R.drawable.gt02);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.bg_ui);
                ind3.setImageResource(R.drawable.unselected);
                btnGetStarted.setBackgroundResource(R.color.white);

                title.setText("Bảo Mật Tuyệt Đối");
                desc.setText("Mọi thông tin trên APP đều sẽ được bảo mật tuyệt đối cho người dùng.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 2:
                logo.setImageResource(R.drawable.gt01);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.bg_ui);

                title.setText("Tính Năng Mạnh Mẽ");
                desc.setText("Quản lý nhân sự, phòng, dự án một cách chính xác, hiệu quả và nhanh chóng.");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
