package com.example.android_nhom10.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.R;

public class Xoaphim extends AppCompatActivity {
    EditText tenphim;
    Button xacnhan;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xoaphim);

        tenphim=findViewById(R.id.editText1);
        xacnhan=findViewById(R.id.button);
        dbHelper=new DatabaseHelper(this);

        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName=tenphim.getText().toString().trim();
                if (!movieName.isEmpty()) {
                    boolean result=dbHelper.deletePhim(movieName);
                    if (result) {
                        Toast.makeText(Xoaphim.this, "Phim đã được xóa!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Xoaphim.this, AdminTaikhoan.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Xoaphim.this, "Không tìm thấy phim này!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Xoaphim.this, "Hãy nhập tên phim!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}