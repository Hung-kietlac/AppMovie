package com.example.android_nhom10.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.Classes.Khachhang;
import com.example.android_nhom10.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Thaydoitaikhoan extends AppCompatActivity {
    TextView back;
    EditText hoten, gioitinh, ngaysinh, diachi, sdt, email, username, password;
    Button button;
    private Khachhang khachhang;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thaydoitaikhoan);

        back=findViewById(R.id.back);
        hoten=findViewById(R.id.hoten);
        gioitinh=findViewById(R.id.gioitinh);
        ngaysinh=findViewById(R.id.ngaysinh);
        diachi=findViewById(R.id.diachi);
        sdt=findViewById(R.id.sdt);
        email=findViewById(R.id.email);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        button=findViewById(R.id.button);
        TextInputLayout textInputLayout1=findViewById(R.id.textInputLayout1);
        TextInputLayout textInputLayout2=findViewById(R.id.textInputLayout2);
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        sdt.setInputType(InputType.TYPE_CLASS_PHONE);
        dbHelper=new DatabaseHelper(this);

        Log.d("IntentDebug", "Attempting to get khachhang from intent");
        khachhang=(Khachhang) getIntent().getSerializableExtra("khachhang");
        Log.d("IntentDebug", "khachhang received: " + khachhang);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textInputLayout1.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        textInputLayout2.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickGender();
            }
        });

        String hoTen=getIntent().getStringExtra("HO_TEN");
        String gioiTinh=getIntent().getStringExtra("GIOI_TINH");
        String ngaySinh=getIntent().getStringExtra("NGAY_SINH");
        String diaChi=getIntent().getStringExtra("DIA_CHI");
        String sdtValue=getIntent().getStringExtra("SDT");
        String emailValue=getIntent().getStringExtra("EMAIL");
        String usernameValue=getIntent().getStringExtra("USERNAME");

        hoten.setText(hoTen);
        gioitinh.setText(gioiTinh);
        ngaysinh.setText(ngaySinh);
        diachi.setText(diaChi);
        sdt.setText(sdtValue);
        email.setText(emailValue);
        username.setText(usernameValue);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update khachhang with new data from EditText fields
                khachhang.setTen(hoten.getText().toString().trim());
                khachhang.setGioitinh(gioitinh.getText().toString().trim());
                khachhang.setNgaysinh(ngaysinh.getText().toString().trim());
                khachhang.setDiachi(diachi.getText().toString().trim());
                khachhang.setSdt(sdt.getText().toString().trim());
                khachhang.setEmail(email.getText().toString().trim());
                khachhang.setUsername(username.getText().toString().trim());
                khachhang.setPassword(password.getText().toString().trim());

                // Update database
                int rowsAffected=dbHelper.updateKhachhang(khachhang);

                // Check if update was successful
                if (rowsAffected > 0) {
                    // If successful, send updated khachhang back to calling activity
                    Toast.makeText(Thaydoitaikhoan.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                    Intent resultIntent=new Intent();
                    resultIntent.putExtra("HO_TEN", hoten.getText().toString());
                    resultIntent.putExtra("GIOI_TINH", gioitinh.getText().toString());
                    resultIntent.putExtra("NGAY_SINH", ngaysinh.getText().toString());
                    resultIntent.putExtra("DIA_CHI", diachi.getText().toString());
                    resultIntent.putExtra("SDT", sdt.getText().toString());
                    resultIntent.putExtra("EMAIL", email.getText().toString());
                    resultIntent.putExtra("USERNAME", username.getText().toString());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(Thaydoitaikhoan.this, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar=Calendar.getInstance();
        int nam=calendar.get(Calendar.YEAR);
        int thang=calendar.get(Calendar.MONTH);
        int ngay=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(Thaydoitaikhoan.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        ngaysinh.setText(dateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void pickGender() {
        final String[] genderOptions={"Nam", "Nữ", "Khác"};
        AlertDialog.Builder builder=new AlertDialog.Builder(Thaydoitaikhoan.this);
        builder.setTitle("Chọn giới tính");
        builder.setItems(genderOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gioitinh.setText(genderOptions[which]);
            }
        });
        builder.show();
    }
}