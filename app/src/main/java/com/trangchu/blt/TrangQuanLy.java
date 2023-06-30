package com.trangchu.blt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import com.trangchu.blt.dao.DaoTaiKhoan;
import com.trangchu.blt.khaibao.TaiKhoaMatKhau;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrangQuanLy extends AppCompatActivity {
    CardView cardViewPhong, cardViewNV,cardViewDA,cardThongKe, cardThongTin,cardViewApp, cardViewLich, cardViewThoat;
    ImageView imageViewMenu;
    CircleImageView imageQuanLy;
    TextView tenQuanLy,emailQuanLy;
    ArrayList<TaiKhoaMatKhau> dsTaiKhoan = new ArrayList<>();
    DaoTaiKhoan daoTaiKhoan = new DaoTaiKhoan(TrangQuanLy.this);
    Intent intent;
    static boolean AllNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangquanly);

        AnhXa();
        Xuat();
        cardThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangQuanLy.this, ThongKe.class));
            }
        });
        cardViewPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangQuanLy.this, DanhSachPhong.class));
            }
        });
        cardThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getIntent().getExtras();
                String tenTaiKhoan = bundle.getString("key_1");
                dsTaiKhoan = daoTaiKhoan.getOne(tenTaiKhoan);
                TaiKhoaMatKhau QL = dsTaiKhoan.get(0);
                Intent intent = new Intent(TrangQuanLy.this, Thongtintaikhoan.class);
                // đóng gói kiểu dữ liệu String
                bundle.putString("key_1", QL.getTaiKhoan());
                bundle.putString("key_2", QL.getHoten());
                bundle.putString("key_3", QL.getSDT());
                bundle.putString("key_4", QL.getEmailTK());
                bundle.putByteArray("key_5", QL.getAnhDaiDien());
                bundle.putString("key_6", QL.getMatKhau());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

        cardViewNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllNV =true;
                DanhSachPhong.xetList =false;
                DanhSachDuAn.DuAnlist =false;
                startActivity(new Intent(TrangQuanLy.this, DanhSachNhanVien.class));
            }
        });

        cardViewDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangQuanLy.this, DanhSachDuAn.class));
            }
        });

        cardViewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangQuanLy.this, ThongTinapp.class));
            }
        });
//        cardGame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(TrangQuanLy.this, Game.class));
//            }
//        });

        cardViewThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TrangQuanLy.this);
                alertDialog.setTitle("Thông báo");
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setMessage("Bạn có muốn thoát chương trình không?");

                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(TrangQuanLy.this, DangNhap.class));
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        });

        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView view = (ImageView) v;
                final PopupMenu popupMenu = new PopupMenu(TrangQuanLy.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menuDoiMatKhau:
                                intent = new Intent(TrangQuanLy.this, DoiMatKhau.class);
                                startActivity(intent);
                                break;
                            case R.id.menuPhone:
                                try {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:0369491473"));
                                    startActivity(intent);
                                } catch (Exception e){
                                    Toast.makeText(TrangQuanLy.this, "Lỗi: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.menuEmail:
                                try {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                                    startActivity(intent);
                                }catch (Exception e){
                                    Toast.makeText(TrangQuanLy.this, "Lỗi: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        return true;
                    }
                });
            }
        });

        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        cardViewLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TrangQuanLy.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    }
                },nam, thang, ngay);
                datePickerDialog.show();
            }
        });
    }
    private void AnhXa(){
        cardViewPhong = findViewById(R.id.card_Phong);
        cardThongKe = findViewById(R.id.card_ThongKe);
        cardViewNV = findViewById(R.id.card_NhanVien);
        cardViewDA = findViewById(R.id.card_DuAn);
        cardViewApp = findViewById(R.id.card_App);
        cardViewLich = findViewById(R.id.card_XemLich);
        //cardGame=findViewById(R.id.card_Game);
        cardViewThoat = findViewById(R.id.card_DangXuat);
        cardThongTin=findViewById(R.id.card_ThongTin);
        imageViewMenu = findViewById(R.id.imageViewMenu);
        imageQuanLy = findViewById(R.id.img_AvataTQL);
        tenQuanLy = findViewById(R.id.txt_HoTenTQL);
        emailQuanLy = findViewById(R.id.txt_EmailTQL);
    }


    //Avarta Quan Ly
    private void Xuat(){
        Bundle bundle = getIntent().getExtras();
        String tenTaiKhoan = bundle.getString("key_1");
        dsTaiKhoan = daoTaiKhoan.getOne(tenTaiKhoan);
        TaiKhoaMatKhau QL = dsTaiKhoan.get(0);
        tenQuanLy = findViewById(R.id.txt_HoTenTQL);
        emailQuanLy = findViewById(R.id.txt_EmailTQL);
        imageQuanLy = findViewById(R.id.img_AvataTQL);

        tenQuanLy.setText(QL.getHoten());
        emailQuanLy.setText(QL.getEmailTK());
        if(QL.getAnhDaiDien() != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(QL.getAnhDaiDien(), 0, QL.getAnhDaiDien().length);
            imageQuanLy.setImageBitmap(bitmap);
        }
        else
        {
            BitmapDrawable bitmapDrawable1 = (BitmapDrawable) AppCompatResources.getDrawable(TrangQuanLy.this,R.drawable.nvmd);
            imageQuanLy.setImageDrawable(bitmapDrawable1);
        }
    }
}