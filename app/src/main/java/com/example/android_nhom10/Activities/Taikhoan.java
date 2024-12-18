package com.example.android_nhom10.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.Classes.Khachhang;
import com.example.android_nhom10.R;

public class Taikhoan extends AppCompatActivity {
    TextView back;
    Button thaydoi, dangxuat;
    private Khachhang khachhang;
    private DatabaseHelper dbHelper;
    private TextView hotenTextView, ngaysinhTextView, gioitinhTextView, diachiTextView, sdtTextView, emailTextView, usernameTextView;
    private ActivityResultLauncher<Intent> updateLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data=result.getData();
                    if (data != null) {
                        Khachhang updatedKhachhang=(Khachhang) data.getSerializableExtra("khachhang");
                        if (updatedKhachhang != null) {
                            updateUI(khachhang);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taikhoan);

        back=findViewById(R.id.back);
        thaydoi=findViewById(R.id.thaydoi);
        dangxuat=findViewById(R.id.dangxuat);
        dbHelper=new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        khachhang=(Khachhang) getIntent().getSerializableExtra("khachhang");
        Log.d("IntentDebug", "khachhang received: " + khachhang);

        if (khachhang != null) {
            updateUI(khachhang);
        }

        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Taikhoan.this, Thaydoitaikhoan.class);
                intent.putExtra("HO_TEN", hotenTextView.getText().toString());
                intent.putExtra("GIOI_TINH", gioitinhTextView.getText().toString());
                intent.putExtra("NGAY_SINH", ngaysinhTextView.getText().toString());
                intent.putExtra("DIA_CHI", diachiTextView.getText().toString());
                intent.putExtra("SDT", sdtTextView.getText().toString());
                intent.putExtra("EMAIL", emailTextView.getText().toString());
                intent.putExtra("USERNAME", usernameTextView.getText().toString());
                updateLauncher.launch(intent);
            }
        });

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Taikhoan.this, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Taikhoan.this, Dangnhap.class);
                startActivity(intent);
                finish(); // Dừng activity này
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (khachhang != null) {
            khachhang=dbHelper.getUserByUsernameAndPassword(khachhang.getUsername(), khachhang.getPassword());
            if (khachhang != null) {
                updateUI(khachhang);
            }
        }
    }

    public void updateUI(Khachhang khachhang) {
        hotenTextView=findViewById(R.id.hoten);
        ngaysinhTextView=findViewById(R.id.ngaysinh);
        gioitinhTextView=findViewById(R.id.gioitinh);
        diachiTextView=findViewById(R.id.diachi);
        sdtTextView=findViewById(R.id.sdt);
        emailTextView=findViewById(R.id.email);
        usernameTextView=findViewById(R.id.username);

        hotenTextView.setText(khachhang.getTen());
        gioitinhTextView.setText(khachhang.getGioitinh());
        ngaysinhTextView.setText(khachhang.getNgaysinh());
        diachiTextView.setText(khachhang.getDiachi());
        sdtTextView.setText(khachhang.getSdt());
        emailTextView.setText(khachhang.getEmail());
        usernameTextView.setText(khachhang.getUsername());
    }
}