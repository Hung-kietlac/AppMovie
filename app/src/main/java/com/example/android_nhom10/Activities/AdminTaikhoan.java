package com.example.android_nhom10.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.Classes.Admin;
import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.R;

public class AdminTaikhoan extends AppCompatActivity {
    TextView back;
    Button themphim, xoaphim, dangxuat;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_taikhoan);

        back=findViewById(R.id.back);
        themphim=findViewById(R.id.themphim);
        xoaphim=findViewById(R.id.xoaphim);
        dangxuat=findViewById(R.id.dangxuat);
        dbHelper=new DatabaseHelper(this);

        // Nhận đối tượng admin từ Intent
        Admin admin=(Admin) getIntent().getSerializableExtra("admin");
        if (admin != null) {
            // Xử lý dữ liệu admin tại đây
            Log.d("AdminTaikhoan", "Received Admin: " + admin.getHoten());
        } else {
            Log.d("AdminTaikhoan", "Admin object is null");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (admin != null) {
            updateUI(admin);
        }

        themphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminTaikhoan.this, Themphim.class);
                startActivity(intent);
            }
        });

        xoaphim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminTaikhoan.this, Xoaphim.class);
                startActivity(intent);
            }
        });

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminTaikhoan.this, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminTaikhoan.this, Dangnhap.class);
                startActivity(intent);
                finish(); //Dừng activity này
            }
        });
    }

    //Cập nhật lại giao diện activity
    @Override
    protected void onResume() { //Dữ lại dữ liệu
        super.onResume();
        // Lấy lại dữ liệu admin từ cơ sở dữ liệu
        Admin admin=(Admin) getIntent().getSerializableExtra("admin");
        admin=dbHelper.getAdminByUsernameAndPassword(admin.getTendangnhap(), admin.getPassword());
        if (admin != null) {
            // Cập nhật giao diện với dữ liệu mới
            updateUI(admin);
        }
    }

    private void updateUI(Admin admin) {
        TextView hotenTextView=findViewById(R.id.hoten);
        TextView ngaysinhTextView=findViewById(R.id.ngaysinh);
        TextView gioitinhTextView=findViewById(R.id.gioitinh);
        TextView diachiTextView=findViewById(R.id.diachi);
        TextView sdtTextView=findViewById(R.id.sdt);
        TextView emailTextView=findViewById(R.id.email);
        TextView tendangnhapTextView=findViewById(R.id.username);
        hotenTextView.setText(admin.getHoten());
        gioitinhTextView.setText(admin.getGioitinh());
        ngaysinhTextView.setText(admin.getNgaysinh());
        diachiTextView.setText(admin.getDiachi());
        sdtTextView.setText(admin.getSdt());
        emailTextView.setText(admin.getEmail());
        tendangnhapTextView.setText(admin.getTendangnhap());
    }
}