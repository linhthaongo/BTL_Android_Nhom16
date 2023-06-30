package com.trangchu.blt.adapter;

import android.annotation.SuppressLint;
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
import com.trangchu.blt.dao.DaoDuAn;
import com.trangchu.blt.khaibao.DuAn;

import java.util.ArrayList;
import java.util.Locale;

public class DuAnAdapter extends BaseAdapter implements Filterable {
    Activity context;
    int layout;
    ArrayList<DuAn> dsDuAn;
    ArrayList<DuAn> dsDuANSort;
    private Filter DuAnFilter;
    DaoDuAn daoDuAn;

    public DuAnAdapter(Activity context, int layout, ArrayList<DuAn> dsDuAn) {
        this.context = context;
        this.layout = layout;
        this.dsDuAn = dsDuAn;
        this.dsDuANSort = dsDuAn;
    }

    @Override
    public int getCount() {
        return dsDuAn.size();
    }

    @Override
    public Object getItem(int i) {
        return dsDuAn.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataSet(ArrayList<DuAn> dsl) {
        this.dsDuAn = dsl;
        notifyDataSetChanged();
    }

    public void resetData() {
        this.dsDuAn = dsDuANSort;
    }

    @Override
    public Filter getFilter() {
        if (DuAnFilter == null){
            DuAnFilter = new CustomFilter();
        }
        return DuAnFilter;
    }

    public class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence == null || charSequence.length() == 0){
                results.values = dsDuANSort;
                results.count = dsDuANSort.size();
            } else {
                ArrayList<DuAn> dsDuAnMoi = new ArrayList<DuAn>();
                for (DuAn duAn : dsDuAn){
                    if (duAn.getTenDuAn().toUpperCase(Locale.ROOT).startsWith(charSequence.toString().toUpperCase()) ||
                            duAn.getMaDuAn().toUpperCase(Locale.ROOT).startsWith(charSequence.toString().toUpperCase())){
                        dsDuAnMoi.add(duAn);
                    }
                }
                results.values = dsDuAnMoi;
                results.count = dsDuAn.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.count == 0){
                notifyDataSetInvalidated();
            } else {
                dsDuAn = (ArrayList<DuAn>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }

    private class ViewHolder{
        TextView txtMaDuAn, txtTenDuAn, txtMoTa;
        ImageView ivDelete, ivEdit;
        LinearLayout linearLayout;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.linearLayout = view.findViewById(R.id.linear_SuaDuAn1);
            holder.txtMaDuAn = view.findViewById(R.id.txt_MaDuAnDong);
            holder.txtTenDuAn = view.findViewById(R.id.txt_TenDuAnDong);
            holder.txtMoTa = view.findViewById(R.id.txt_moTaDong);
            holder.ivEdit = view.findViewById(R.id.img_SuaDuAnDong);
            holder.ivDelete = view.findViewById(R.id.img_XoaDuAnDong);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final DuAn duAn = dsDuAn.get(i);

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daoDuAn = new DaoDuAn(context);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_suaduan);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                final EditText edtMaDuAn = dialog.findViewById(R.id.edit_SuaMaDuAn);
                final EditText edtTenDuAn = dialog.findViewById(R.id.edit_SuaTenDuAn);
                final EditText edtMoTaDuAn = dialog.findViewById(R.id.edit_SuaMotaDuAn);
                Button btnSua = dialog.findViewById(R.id.btn_LuuSuaDuAn);
                Button btnHuy = dialog.findViewById(R.id.btn_HuySuaDuAn);

                edtMaDuAn.setText(duAn.getMaDuAn());
                edtTenDuAn.setText(duAn.getTenDuAn());
                edtMoTaDuAn.setText(duAn.getMoTa());
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String maDuAn = edtMaDuAn.getText().toString();
                            String tenDuAn = edtTenDuAn.getText().toString();
                            String moTaDuAn = edtMoTaDuAn.getText().toString();
                            DuAn duAn1 = new DuAn(maDuAn, tenDuAn, moTaDuAn);
                            //Update vào sql
                            if (daoDuAn.suaDuAn(duAn1)) {
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                dsDuAn.clear();
                                dsDuAn.addAll(daoDuAn.getAll());
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
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                daoDuAn = new DaoDuAn(context);
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
                txt_Massage.setText("Bạn có chắc chắn xóa dự án " + duAn.getMaDuAn() + " không ?");
                final DuAn duAn = dsDuAn.get(i);
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (daoDuAn.xoaDuAn(duAn) == true) {
                            txt_Massage.setText("");
                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF478B, android.graphics.PorterDuff.Mode.MULTIPLY);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dsDuAn.clear();
                                    dsDuAn.addAll(daoDuAn.getAll());
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
        holder.txtMaDuAn.setText(duAn.getMaDuAn());
        holder.txtTenDuAn.setText(duAn.getTenDuAn());
        holder.txtMoTa.setText(duAn.getMoTa());

        return view;
    }
}
