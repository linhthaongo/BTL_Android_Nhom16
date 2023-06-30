package com.trangchu.blt.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
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

import com.trangchu.blt.R;
import com.trangchu.blt.dao.DaoPhong;
import com.trangchu.blt.khaibao.Phong;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Locale;

public class PhongApdapter extends BaseAdapter implements Filterable {

    Activity context;
    int layout;
    ArrayList<Phong> danhSachPhong;
    ArrayList<Phong> danhSachPhongSort;
    private Filter phongFilter;
    DaoPhong daoPhong;

    // hàm tạo
    public PhongApdapter(Activity context, int layout, ArrayList<Phong> danhSachPhong) {
        this.context = context;
        this.layout = layout;
        this.danhSachPhong = danhSachPhong;
        this.danhSachPhongSort = danhSachPhong;
    }

    @Override
    public int getCount() {
        return danhSachPhong.size();
    }

    @Override
    public Object getItem(int i) {
        return danhSachPhong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataSet(ArrayList<Phong> dsl) {
        this.danhSachPhong = dsl;
        notifyDataSetChanged();
    }

    public void resetData() {
        this.danhSachPhong = danhSachPhongSort;
    }
    //lọc tìm kiếm phòng
    @Override
    public Filter getFilter() {
        if (phongFilter == null) {
            phongFilter = new CustomFilter();
        }
        return phongFilter;
    }
    // tìm kiếm
    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();// rỗng thì trả về danh sách ban đầu còn không thì trả về kết quả
            if (constraint == null || constraint.length() == 0) {
                results.values = danhSachPhongSort;
                results.count = danhSachPhongSort.size();
            } else {
                ArrayList<Phong> danhsachphongmoi = new ArrayList<Phong>();
                for (Phong phong :danhSachPhong ) {
                    if (phong.getTenPhong().toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                            phong.getMaPhong().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        danhsachphongmoi.add(phong);
                    }
                }
                results.values = danhsachphongmoi;
                results.count = danhSachPhong.size();
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                danhSachPhong = (ArrayList<Phong>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    private class ViewHolder {
        TextView txtMaPhong, txtTenPhong,txtMoTaPhong;
        ImageView imgXoa, imgSua;
        LinearLayout linearLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.linearLayout = convertView.findViewById(R.id.linearLayout_SuaPhong);
            holder.txtMaPhong = convertView.findViewById(R.id.txt_MaPhongDong);
            holder.txtTenPhong = convertView.findViewById(R.id.txt_TenPhongDong);
            holder.txtMoTaPhong = convertView.findViewById(R.id.txt_MoTaPhongDong);
            holder.imgXoa = convertView.findViewById(R.id.img_XoaPhongDong);
            holder.imgSua = convertView.findViewById(R.id.img_SuaPhongDong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Phong phong = danhSachPhong.get(position);

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoPhong = new DaoPhong(context);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_suaphong);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                final EditText editMaPhong = dialog.findViewById(R.id.edit_SuaMaPhong);
                final EditText edtTenPhong = dialog.findViewById(R.id.edit_SuaTenPhong);
                final EditText edtMoTaPhong = dialog.findViewById(R.id.edit_SuaMotaPhong);
                Button btnSua = dialog.findViewById(R.id.btn_LuuSuaPhong);
                Button btnHuy = dialog.findViewById(R.id.btn_HuySuaPhong);
                //Đổ dữ liệu
                editMaPhong.setText(phong.getMaPhong());
                edtTenPhong.setText(phong.getTenPhong());
                edtMoTaPhong.setText(phong.getMoTaPhong());
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String maPhong = editMaPhong.getText().toString();
                            String tenPhong = edtTenPhong.getText().toString();
                            String moTaPhong = edtMoTaPhong.getText().toString();
                            Phong phong1 = new Phong(maPhong, tenPhong,moTaPhong);
                            //Update vào sql
                            if (daoPhong.update(phong1)) {
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                danhSachPhong.clear();
                                danhSachPhong.addAll(daoPhong.getAll());
                                notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoPhong = new DaoPhong(context);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.luukhithoat);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;//>??????

                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                final TextView txt_Massage = dialog.findViewById(R.id.txt_Luu);
                Button btnXoa = dialog.findViewById(R.id.btn_Luu);
                Button btnHuy = dialog.findViewById(R.id.btn_Thoat);
                final ProgressBar progressBar = dialog.findViewById(R.id.progress_Luu);
                progressBar.setVisibility(View.INVISIBLE);
                txt_Massage.setText("Bạn có chắc chắn xóa phòng " + phong.getMaPhong() + " không ?");
                final Phong phong = danhSachPhong.get(position);
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (daoPhong.delete(phong.getMaPhong()) == true) {
                            txt_Massage.setText("");
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF478B, android.graphics.PorterDuff.Mode.MULTIPLY);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    danhSachPhong.clear();
                                    danhSachPhong.addAll(daoPhong.getAll());
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
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.txtMaPhong.setText(phong.getMaPhong());
        holder.txtTenPhong.setText(phong.getTenPhong());
        holder.txtMoTaPhong.setText(phong.getMoTaPhong());
        return convertView;
    }
}
