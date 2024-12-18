package com.example.android_nhom10.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.Classes.Phim;
import com.example.android_nhom10.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Themphim extends AppCompatActivity {
    Button xacnhan;
    TextView back;
    EditText tenphim, theloai, ngaychieu, thoiluong, poster, tendaodien, dienvien, noidung, quocgia;
    private DatabaseHelper dbHelper;
    private Phim phim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themphim);

        back=findViewById(R.id.back);
        tenphim=findViewById(R.id.editText1);
        theloai=findViewById(R.id.editText2);
        ngaychieu=findViewById(R.id.editText3);
        thoiluong=findViewById(R.id.editText4);
        poster=findViewById(R.id.editText5);
        tendaodien=findViewById(R.id.editText6);
        dienvien=findViewById(R.id.editText7);
        noidung=findViewById(R.id.editText8);
        quocgia=findViewById(R.id.editText9);
        xacnhan=findViewById(R.id.button);
        TextInputLayout textInputLayout1=findViewById(R.id.textInputLayout1);

        dbHelper=new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textInputLayout1.setEndIconOnClickListener(new View.OnClickListener() { //Khi nhấn vào icon cuốn lịch thì sẽ hiện ra cuốn lịch để chọn ngày và xuất ra Edittext
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=tenphim.getText().toString().trim();
                String loai=theloai.getText().toString().trim();
                String ngay=ngaychieu.getText().toString().trim();
                String thoi=thoiluong.getText().toString().trim();
                String post=poster.getText().toString().trim();
                String daodien=tendaodien.getText().toString().trim();
                String dien=dienvien.getText().toString().trim();
                String mota=noidung.getText().toString().trim();
                String nuoc=quocgia.getText().toString().trim();

                if (ten.isEmpty() || loai.isEmpty() || ngay.isEmpty() || thoi.isEmpty() ||
                        post.isEmpty() || daodien.isEmpty() || dien.isEmpty() || mota.isEmpty() || nuoc.isEmpty()) {
                    Toast.makeText(Themphim.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addPhim(ten, loai, ngay, thoi, post, daodien, dien, mota, nuoc);
                    Toast.makeText(Themphim.this, "Thêm phim thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Themphim.this, AdminTaikhoan.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void showDatePickerDialog() { //Hàm chọn ngày chiếu
        final Calendar calendar=Calendar.getInstance();
        int nam=calendar.get(Calendar.YEAR);
        int thang=calendar.get(Calendar.MONTH);
        int ngay=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(Themphim.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        ngaychieu.setText(dateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
        datePickerDialog.show();
    }
}