package com.trangchu.blt.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.trangchu.blt.DanhSachNhanVien;
import com.trangchu.blt.R;
import com.trangchu.blt.SuaNhanVien;
import com.trangchu.blt.ThemNhanVien;
import com.trangchu.blt.dao.DaoDuAn;
import com.trangchu.blt.dao.DaoNhanVien;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.DuAn;
import com.trangchu.blt.khaibao.NhanVien;
import com.trangchu.blt.khaibao.Phong;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienAdapter extends BaseAdapter implements Filterable {
    Context context;
    int layout;
    ArrayList<NhanVien> ds = new ArrayList<>();
    ArrayList<NhanVien> dsSortNhanVien;
    private CustomFilter nvFilter;
    DaoNhanVien daoNhanVien;
    ArrayList<Phong> dsMaPhong = new ArrayList<>();

    public NhanVienAdapter(Context context, int layout, ArrayList<NhanVien> ds){
        this.context = context;
        this.ds = ds;
        this.layout = layout;
        daoNhanVien = new DaoNhanVien(context);
        this.dsSortNhanVien = ds;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int i) {
        return ds.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataSet(ArrayList<NhanVien> dsl) {
        this.ds = dsl;
        notifyDataSetChanged();
    }

    public void resetData() {
        this.ds = dsSortNhanVien;
    }

    @Override
    public Filter getFilter() {
        if (nvFilter ==null){
            nvFilter = new CustomFilter();
        }
        return nvFilter;
    }

    public class CustomFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = dsSortNhanVien;
                results.count = dsSortNhanVien.size();
            } else {
                ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
                for (NhanVien nv : ds) {
                    if (nv.getTenNhanVien().toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            nv.getMaNhanVien().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        dsnv.add(nv);
                    }
                }
                results.values = dsnv;
                results.count = ds.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults.count == 0){
                notifyDataSetInvalidated();
            } else {
                ds = (ArrayList<NhanVien>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }

    private class ViewHolder extends AppCompatActivity{
        TextView txtMaNV, txtTenNV, txtngaysinh, txtgioitinh, txtemail, txtsdt, txtdiachi,txtmaphong,txtmaduan;
        ImageView ivDelete, ivEdit;
        CircleImageView ivHinhNv;
        LinearLayout linearLayout;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.linearLayout = view.findViewById(R.id.linear_SuaNhanVien);
            holder.txtMaNV = view.findViewById(R.id.txt_MaNVDong);
            holder.txtTenNV = view.findViewById(R.id.txt_TenNVDong);
            holder.txtngaysinh = view.findViewById(R.id.txt_NgaySinhDong);
            holder.txtgioitinh = view.findViewById(R.id.txt_GioiTinhNVDong);
            holder.txtsdt = view.findViewById(R.id.txt_SdtNVDong);
            holder.txtdiachi = view.findViewById(R.id.txt_DiaChiNVDong);
            holder.txtemail = view.findViewById(R.id.txt_EmailNVDong);

            holder.ivHinhNv = view.findViewById(R.id.img_NhanVienDong);

            holder.txtmaphong = view.findViewById(R.id.txt_MaPhongNVDong);
            holder.txtmaduan = view.findViewById(R.id.txt_MaDANVDong);

            holder.ivEdit = view.findViewById(R.id.img_SuaNVDong);
            holder.ivDelete = view.findViewById(R.id.img_XoaNVDong);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final NhanVien nhanVien = ds.get(i);
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this: là activity hiện tại
                Intent intent = new Intent(view.getContext(), SuaNhanVien.class);
                Bundle bundle = new Bundle();
                // đóng gói kiểu dữ liệu String, Boolean
                bundle.putString("key_1", nhanVien.getMaNhanVien());
                bundle.putString("key_2", nhanVien.getTenNhanVien());
                bundle.putString("key_3", nhanVien.getGioiTinh());
                bundle.putString("key_4", nhanVien.getNgaySinh());
                bundle.putString("key_5", nhanVien.getSdt());
                bundle.putString("key_6", nhanVien.getDiaChi());
                bundle.putString("key_7", nhanVien.getMaDuAn());
                bundle.putString("key_8", nhanVien.getMaPhong());
                bundle.putString("key_9", nhanVien.getEmail());
                bundle.putByteArray("key_10", nhanVien.getHinh());
                // đóng gói bundle vào intent
                intent.putExtras(bundle);
                // start SecondActivity
                view.getContext().startActivity(intent);
                ((Activity) context).finish();
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                daoNhanVien = new DaoNhanVien(context);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.luukhithoat);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                final TextView txt_Massage = dialog.findViewById(R.id.txt_Luu);
                Button btnXoa = dialog.findViewById(R.id.btn_Luu);
                Button btnHuy = dialog.findViewById(R.id.btn_Thoat);
                final ProgressBar progressBar = dialog.findViewById(R.id.progress_Luu);
                progressBar.setVisibility(View.INVISIBLE);
                txt_Massage.setText("Bạn có chắc chắn xóa nhân viên" + nhanVien.getMaNhanVien() + " không ?");
                final NhanVien nv = ds.get(i);
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (daoNhanVien.xoaNV(nv) == true) {
                            txt_Massage.setText("");
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF478B, android.graphics.PorterDuff.Mode.MULTIPLY);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ds.clear();
                                    ds.addAll(daoNhanVien.getAll());
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }, 2000);
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.txtMaNV.setText(nhanVien.getMaNhanVien());
        holder.txtTenNV.setText(nhanVien.getTenNhanVien());
        holder.txtngaysinh.setText(nhanVien.getNgaySinh());
        holder.txtgioitinh.setText(nhanVien.getGioiTinh());
        holder.txtsdt.setText(nhanVien.getSdt());
        holder.txtdiachi.setText(nhanVien.getDiaChi());
        holder.txtemail.setText(nhanVien.getEmail());

        //byte[]->bitmap
        byte[] hinhAnh = nhanVien.getHinh();
        if(hinhAnh !=null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            holder.ivHinhNv.setImageBitmap(bitmap);
        }
        else{
            BitmapDrawable bitmapDrawable1 = (BitmapDrawable) AppCompatResources.getDrawable(view.getContext(),R.drawable.nvmd);
            holder.ivHinhNv.setImageDrawable(bitmapDrawable1);
        }

        holder.txtmaphong.setText(nhanVien.getMaPhong());
        holder.txtmaduan.setText(nhanVien.getMaDuAn());

        return view;
    }
    
}

