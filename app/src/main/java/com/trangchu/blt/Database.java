package com.trangchu.blt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "QUANLYNHANVIEN.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String  sql = "CREATE TABLE IF NOT EXISTS TAIKHOAN(tenTaiKhoan TEXT PRIMARY KEY NOT NULL, matKhau TEXT NOT NULL,hoTen TEXT, emailTK TEXT, anhDaiDien  BLOB, SDT TEXT)";
        db.execSQL(sql);
        sql = " INSERT INTO TAIKHOAN VALUES ('admin111','admin11','Ngô Thảo Linh','linh@gmail.com',null,'0337999999')";
        db.execSQL(sql);
        sql = " INSERT INTO TAIKHOAN VALUES ('admin222','admin111','Nguyễn Thị Tú Anh','tuanh@gmail.com',null,'0337888888')";
        db.execSQL(sql);
        sql = " INSERT INTO TAIKHOAN VALUES ('admin333','admin333','Nguyễn Văn Tiệp','tiep@gmail.com',null,'0337555555')";
        db.execSQL(sql);



        sql = " CREATE TABLE PHONG(maPhong TEXT PRIMARY KEY NOT NULL, tenPhong TEXT NOT NULL,moTaPhong TEXT NOT NULL)";
        db.execSQL(sql);
        sql = " INSERT INTO PHONG VALUES ('P01','Phòng IT','Đây là phòng 01')";
        db.execSQL(sql);
        sql = " INSERT INTO PHONG VALUES ('P02','Phòng Sale','Đây là phòng 02')";
        db.execSQL(sql);
        sql = " INSERT INTO PHONG VALUES ('P03','Phòng Desgin','Đây là phòng 03')";
        db.execSQL(sql);
        sql = " INSERT INTO PHONG VALUES ('P04','Phòng Marketing','Đây là phòng 03')";
        db.execSQL(sql);
        sql = " INSERT INTO PHONG VALUES ('P05','Phòng Kỹ Thuật','Đây là phòng 03')";
        db.execSQL(sql);



        sql = " CREATE TABLE DUAN(maDuAn TEXT PRIMARY KEY NOT NULL, tenDuAn TEXT NOT NULL,moTa TEXT NOT NULL)";
        db.execSQL(sql);
        sql = " INSERT INTO DUAN VALUES ('DA01','Dự án 01','Dự án là một tập hợp các hoạt động có liên quan đến nhau được thực hiện trong một khoảng thời gian có hạn, với những nguồn lực đã được giới hạn; nhất là nguồn tài chính có giới hạn để đạt được những mục tiêu cụ thể, rõ ràng, làm thỏa mãn nhu cầu của đối tượng mà dự án hướng đến. Thực chất, Dự án là tổng thể những chính sách, hoạt động và chi phí liên quan với nhau được thiết kế nhằm đạt được những mục tiêu nhất định trong một thời gian nhất định.')";
        db.execSQL(sql);
        sql = " INSERT INTO DUAN VALUES ('DA02','Dự án 02','Dự án là một tập hợp các hoạt động có liên quan đến nhau được thực hiện trong một khoảng thời gian có hạn, với những nguồn lực đã được giới hạn; nhất là nguồn tài chính có giới hạn để đạt được những mục tiêu cụ thể, rõ ràng, làm thỏa mãn nhu cầu của đối tượng mà dự án hướng đến. Thực chất, Dự án là tổng thể những chính sách, hoạt động và chi phí liên quan với nhau được thiết kế nhằm đạt được những mục tiêu nhất định trong một thời gian nhất định.')";
        db.execSQL(sql);
        sql = " INSERT INTO DUAN VALUES ('DA03','Dự án 03','Dự án là một tập hợp các hoạt động có liên quan đến nhau được thực hiện trong một khoảng thời gian có hạn, với những nguồn lực đã được giới hạn; nhất là nguồn tài chính có giới hạn để đạt được những mục tiêu cụ thể, rõ ràng, làm thỏa mãn nhu cầu của đối tượng mà dự án hướng đến. Thực chất, Dự án là tổng thể những chính sách, hoạt động và chi phí liên quan với nhau được thiết kế nhằm đạt được những mục tiêu nhất định trong một thời gian nhất định.')";
        db.execSQL(sql);
        sql = " INSERT INTO DUAN VALUES ('DA04','Dự án 04','Dự án là một tập hợp các hoạt động có liên quan đến nhau được thực hiện trong một khoảng thời gian có hạn, với những nguồn lực đã được giới hạn; nhất là nguồn tài chính có giới hạn để đạt được những mục tiêu cụ thể, rõ ràng, làm thỏa mãn nhu cầu của đối tượng mà dự án hướng đến. Thực chất, Dự án là tổng thể những chính sách, hoạt động và chi phí liên quan với nhau được thiết kế nhằm đạt được những mục tiêu nhất định trong một thời gian nhất định.')";
        db.execSQL(sql);
        sql = " INSERT INTO DUAN VALUES ('DA05','Dự án 05','Dự án là một tập hợp các hoạt động có liên quan đến nhau được thực hiện trong một khoảng thời gian có hạn, với những nguồn lực đã được giới hạn; nhất là nguồn tài chính có giới hạn để đạt được những mục tiêu cụ thể, rõ ràng, làm thỏa mãn nhu cầu của đối tượng mà dự án hướng đến. Thực chất, Dự án là tổng thể những chính sách, hoạt động và chi phí liên quan với nhau được thiết kế nhằm đạt được những mục tiêu nhất định trong một thời gian nhất định.')";
        db.execSQL(sql);


        sql = " CREATE TABLE IF NOT EXISTS NHANVIEN(maNhanVien TEXT PRIMARY KEY NOT NULL, tenNhanVien TEXT NOT NULL,gioiTinh TEXT NOT NULL, ngaySinh TEXT NOT NULL, SDT TEXT NOT NULL,diaChi TEXT NOT NULL, emailNhanVien TEXT NOT NULL, maPhong TEXT REFERENCES PHONG(maPhong) NOT NULL,maDuAn TEXT REFERENCES DUAN(maDuAn) NOT NULL, HinhAnh BLOB)";
        db.execSQL(sql);
        sql = " INSERT INTO NHANVIEN VALUES ('NV01','Nguyễn Văn Anh','Nam','01/01/2001','0962345678','Hà Nội','nguyenvananh@gmail.com','P01','DA01',null)";
        db.execSQL(sql);
        sql = " INSERT INTO NHANVIEN VALUES ('NV02','Nguyễn Quyết Thắng','Nam','02/02/2002','0962313999','Hà Nội','nguyenquyetthang@gmail.com','P02','DA02',null)";
        db.execSQL(sql);
        sql = " INSERT INTO NHANVIEN VALUES ('NV03','Tuyết Mai','Nữ','09/03/1996','0936666666','Bắc Ninh','tuyetmai@gmail.com','P03','DA03',null)";
        db.execSQL(sql);
        sql = " INSERT INTO NHANVIEN VALUES ('NV04','Mai Anh','Nữ','09/09/2000','0936599999','Nam Định','maianh@gmail.com','P03','DA03',null)";
        db.execSQL(sql);
        sql = " INSERT INTO NHANVIEN VALUES ('NV05','Bạch Tuyết','Nữ','03/03/2001','0936598888','Thái Bình','tuyetmai@gmail.com','P05','DA05',null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PHONG");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS DUAN");
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS NHANVIEN");
        onCreate(db);
    }

}
