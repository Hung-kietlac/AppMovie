package com.example.android_nhom10.Classes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="XemPhim.db";
    public static final int DB_VERSION=2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1="CREATE TABLE phimrap (idphim INTEGER PRIMARY KEY AUTOINCREMENT, tenphim TEXT, theloai TEXT, ngaychieu DATE, thoiluong TEXT, poster TEXT, tendaodien TEXT, dienvien TEXT, noidung TEXT, quocgia TEXT, giave INT)";
        String sql2="CREATE TABLE khachhang (idkhach INTEGER PRIMARY KEY AUTOINCREMENT, ten TEXT, ngaysinh DATE, gioitinh TEXT, diachi TEXT, sdt TEXT, email TEXT, username TEXT, password TEXT)";
        String sql3="CREATE TABLE combodoanrap (idcombo INTEGER PRIMARY KEY AUTOINCREMENT, tencombo TEXT, gia INTEGER, chitiet TEXT)";
        String sql4="CREATE TABLE danhgia (iddanhgia INTEGER PRIMARY KEY AUTOINCREMENT, idkhach INTEGER , comments TEXT, sosao INTEGER, FOREIGN KEY(idkhach) REFERENCES khachhang(idkhach))";
        String sql5="CREATE TABLE datdoanrap (iddatdoan INTEGER PRIMARY KEY AUTOINCREMENT, idcombo INTEGER, idkhach INTEGER, soluong INTEGER, tongtien INTEGER, FOREIGN KEY(idkhach) REFERENCES khachhang(idkhach), FOREIGN KEY(idcombo) REFERENCES combo(idcombo))";
        String sql6="CREATE TABLE datverap (idve INTEGER PRIMARY KEY AUTOINCREMENT, idphim INTEGER, idkhach INTEGER, loaighengoi TEXT, giave INTEGER, FOREIGN KEY(idphim) REFERENCES phim(idphim), FOREIGN KEY(idkhach) REFERENCES khachhang(idkhach))";
        String sql7="CREATE TABLE gio_hang (id INTEGER PRIMARY KEY AUTOINCREMENT, movie_title TEXT, showtime TEXT, name TEXT, email TEXT, phone TEXT)";
        String sql8="CREATE TABLE showtime_table (idshowtime INTEGER PRIMARY KEY AUTOINCREMENT, idrap INTEGER, giochieu TIME, ngaychieu DATE,FOREIGN KEY(idrap) REFERENCES rap(idrap))";
        String sql9="CREATE TABLE rap (idrap INTEGER PRIMARY KEY AUTOINCREMENT, tenrap TEXT, diachi TEXT)";
        String sql10="CREATE TABLE donhang (iddon INTEGER PRIMARY KEY AUTOINCREMENT, idkhach INTEGER, tenphim TEXT, poster TEXT, tenrap TEXT, ngaychieu TEXT, giochieu TEXT, tongtien INTEGER ,FOREIGN KEY(idkhach) REFERENCES khachhang(idkhach))";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
        sqLiteDatabase.execSQL(sql6);
        sqLiteDatabase.execSQL(sql7);
        sqLiteDatabase.execSQL(sql8);
        sqLiteDatabase.execSQL(sql9);
        sqLiteDatabase.execSQL(sql10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql1="DROP TABLE IF EXISTS phimrap";
        String sql2="DROP TABLE IF EXISTS khachhang";
        String sql3="DROP TABLE IF EXISTS combodoanrap";
        String sql4="DROP TABLE IF EXISTS danhgia";
        String sql5="DROP TABLE IF EXISTS datdoanrap";
        String sql6="DROP TABLE IF EXISTS datverap";
        String sql7="DROP TABLE IF EXISTS gio_hang";
        String sql8="DROP TABLE IF EXISTS showtime_table";
        String sql9="DROP TABLE IF EXISTS rap";
        String sql10="DROP TABLE IF EXISTS donhang";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
        sqLiteDatabase.execSQL(sql6);
        sqLiteDatabase.execSQL(sql7);
        sqLiteDatabase.execSQL(sql8);
        sqLiteDatabase.execSQL(sql9);
        sqLiteDatabase.execSQL(sql10);
        onCreate(sqLiteDatabase);
    }

    public void addKhachhang(String ten, String ngaysinh, String gioitinh, String diachi, String sdt, String email, String username, String password) {  //Điền dữ liệu khách hàng được nhập từ trang đăng ký lên database
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("ten", ten);
        values.put("ngaysinh", ngaysinh);
        values.put("gioitinh", gioitinh);
        values.put("diachi", diachi);
        values.put("sdt", sdt);
        values.put("email", email);
        values.put("username", username);
        values.put("password", password);
        db.insert("khachhang", null, values);
        db.close();
    }

    public int updateKhachhang(Khachhang khachhang) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("ten", khachhang.getTen());
        values.put("ngaysinh", khachhang.getNgaysinh());
        values.put("gioitinh", khachhang.getGioitinh());
        values.put("diachi", khachhang.getDiachi());
        values.put("sdt", khachhang.getSdt());
        values.put("email", khachhang.getEmail());
        values.put("username", khachhang.getUsername());
        values.put("password", khachhang.getPassword());

        int rowsAffected=0;
        try {
            // Câu lệnh UPDATE trả về số lượng hàng được cập nhật
            rowsAffected=db.update("khachhang", values, "idkhach = ?", new String[]{String.valueOf(khachhang.getIdkhach())});
        } catch (Exception e) {
            // Log lỗi nếu có
            Log.e("DB_UPDATE_ERROR", "Error updating khachhang: " + e.getMessage());
        } finally {
            db.close();
        }
        return rowsAffected;
    }

    @SuppressLint("Range")
    public Khachhang getUserByUsernameAndPassword(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("khachhang", null, "username = ? AND password = ?", new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id=cursor.getInt(cursor.getColumnIndex("idkhach"));
            String ten=cursor.getString(cursor.getColumnIndex("ten"));
            String gioitinh=cursor.getString(cursor.getColumnIndex("gioitinh"));
            String ngaysinh=cursor.getString(cursor.getColumnIndex("ngaysinh"));
            String diachi=cursor.getString(cursor.getColumnIndex("diachi"));
            String sdt=cursor.getString(cursor.getColumnIndex("sdt"));
            String email=cursor.getString(cursor.getColumnIndex("email"));
            String usernamedb=cursor.getString(cursor.getColumnIndex("username"));

            cursor.close();
            return new Khachhang(id, ten, gioitinh, ngaysinh, diachi, sdt, email, usernamedb);
        } else {
            return null;
        }
    }

    public boolean checkAdminLogin(String tendangnhap, String password) {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={"tendangnhap"};
        String selection="tendangnhap = ? AND password = ?";
        String[] selectionArgs={tendangnhap, password};
        Cursor cursor=null;
        try { //Kiểm tra lỗi ngoài lệ
            cursor=db.query("admin", columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    //Lấy dữ liệu database truyền vào admin
    public Admin getAdminByUsernameAndPassword(String tendangnhap, String password) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM admin WHERE tendangnhap = ? AND password = ?";
        Cursor cursor=db.rawQuery(query, new String[]{tendangnhap, password});
        if (cursor != null && cursor.moveToFirst()) {
            Admin admin=new Admin();
            admin.setIdadmin(cursor.getInt(cursor.getColumnIndexOrThrow("idadmin")));
            admin.setHoten(cursor.getString(cursor.getColumnIndexOrThrow("hoten")));
            admin.setNgaysinh(cursor.getString(cursor.getColumnIndexOrThrow("ngaysinh")));
            admin.setGioitinh(cursor.getString(cursor.getColumnIndexOrThrow("gioitinh")));
            admin.setDiachi(cursor.getString(cursor.getColumnIndexOrThrow("diachi")));
            admin.setSdt(cursor.getString(cursor.getColumnIndexOrThrow("sdt")));
            admin.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            admin.setTendangnhap(cursor.getString(cursor.getColumnIndexOrThrow("tendangnhap")));
            admin.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            cursor.close();
            return admin;
        }
        return null;
    }

    public boolean checkUserAndPass(String username, String password) {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={"username"};
        String selection="username = ? AND password = ?";
        String[] selectionArgs={username, password};
        Cursor cursor=null;
        try { //Kiểm tra lỗi ngoài lệ
            cursor=db.query("khachhang", columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public boolean checkSdt(String sdt) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM khachhang WHERE sdt = ?";
        Cursor cursor=db.rawQuery(query, new String[]{sdt});
        boolean exists=cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean checkTendangnhap(String username) {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={"username"};
        String selection="username = ?";
        String[] selectionArgs={username};
        Cursor cursor=null;
        try { //Kiểm tra lỗi ngoài lề
            cursor=db.query("khachhang", columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={"email"};
        String selection="email = ?";
        String[] selectionArgs={email};
        Cursor cursor=null;
        try { //Kiểm tra lỗi ngoài lề
            cursor=db.query("khachhang", columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public void updatePassword(String username, String sdt, String newPassword) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password", newPassword);  // Đặt giá trị mật khẩu mới vào ContentValues
        db.update("khachhang", values, "sdt = ? AND username = ?", new String[]{sdt, username}); // Cập nhật bảng khachhang với mật khẩu mới, nơi username và số điện thoại khớp với giá trị đã cho
        db.close();
    }

    @SuppressLint("Range")
    public String getPasswordByUsername(String username) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("khachhang", new String[]{"password"}, "username=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String password=cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return password;
        }
        return null;
    }

    @SuppressLint("Range")
    public List<String> getAllPosterUrls() { //Hàm lấy tất cả ảnh trên database
        List<String> poster=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM phimrap", null); //Lấy tất cả trong bảng phimrap

        if (cursor.moveToFirst()) {
            do {
                poster.add(cursor.getString(cursor.getColumnIndex("poster"))); //Lấy trong cột poster
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return poster;
    }

    @SuppressLint("Range")
    public List<Phim> getAllPhim() {
        List<Phim> phimrap=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM phimrap", null);

        if (cursor.moveToFirst()) {
            do {
                Phim phim=new Phim();
                phim.setTenphim(cursor.getString(cursor.getColumnIndex("tenphim")));
                phim.setTheloai(cursor.getString(cursor.getColumnIndex("theloai")));
                phim.setNgaychieu(cursor.getString(cursor.getColumnIndex("ngaychieu")));
                phim.setThoiluong(cursor.getString(cursor.getColumnIndex("thoiluong")));
                phim.setPoster(cursor.getString(cursor.getColumnIndex("poster")));
                phim.setTendaodien(cursor.getString(cursor.getColumnIndex("tendaodien")));
                phim.setDienvien(cursor.getString(cursor.getColumnIndex("dienvien")));
                phim.setNoidung(cursor.getString(cursor.getColumnIndex("noidung")));
                phim.setQuocgia(cursor.getString(cursor.getColumnIndex("quocgia")));
                phim.setGiave(cursor.getString(cursor.getColumnIndex("giave")));
                phimrap.add(phim);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return phimrap;
    }

    public Cursor getAllTheaters() {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM rap", null);
    }

    @SuppressLint("Range")
    public int getTheaterIdByName(String tenrap) {
        SQLiteDatabase db=this.getReadableDatabase();
        int theaterId=-1;

        String query="SELECT idrap FROM rap WHERE tenrap = ?";
        Cursor cursor=db.rawQuery(query, new String[]{tenrap});

        if (cursor.moveToFirst()) {
            theaterId=cursor.getInt(cursor.getColumnIndex("idrap"));
        }

        cursor.close();
        return theaterId;
    }

    public Cursor getShowtimesByTheaterId(int theaterId) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT ngaychieu FROM showtime_table WHERE idrap = ?";
        return db.rawQuery(query, new String[]{String.valueOf(theaterId)});
    }

    public Cursor getShowtimesByDate(int theaterId, String selectedDate) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT giochieu FROM showtime_table WHERE idrap = ? AND ngaychieu = ?";
        return db.rawQuery(query, new String[]{String.valueOf(theaterId), selectedDate});
    }

    public boolean insertDonHang(String tenPhim, String poster, String tenRap, String ngayChieu, String gioChieu, int tongTien) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tenphim", tenPhim);
        contentValues.put("poster", poster);
        contentValues.put("tenrap", tenRap);
        contentValues.put("ngaychieu", ngayChieu);
        contentValues.put("giochieu", gioChieu);
        contentValues.put("tongtien", tongTien);
        long result=db.insert("donhang", null, contentValues);
        return result != -1;
    }

    public void addPhim(String tenphim, String theloai, String ngaychieu, String thoiluong, String poster,
                        String tendaodien, String dienvien, String noidung, String quocgia) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("tenphim", tenphim);
        values.put("theloai", theloai);
        values.put("ngaychieu", ngaychieu);
        values.put("thoiluong", thoiluong);
        values.put("poster", poster);
        values.put("tendaodien", tendaodien);
        values.put("dienvien", dienvien);
        values.put("noidung", noidung);
        values.put("quocgia", quocgia);

        db.insert("phimrap", null, values);
        db.close();
    }

    public boolean deletePhim(String tenphim) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("phimrap", "tenphim = ?", new String[]{tenphim}) > 0;
    }

    public int getTicketPrice(String movieTitle) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("phimrap", new String[]{"giave"}, "tenphim = ?", new String[]{movieTitle}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int ticketPrice=cursor.getInt(cursor.getColumnIndex("giave"));
            cursor.close();
            return ticketPrice;
        }
        return 0; // Hoặc giá trị mặc định nếu không tìm thấy
    }
}