package com.example.android_nhom10.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_nhom10.R;

public class GioHangActivity extends AppCompatActivity {

    TextView giohangtrong, tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gio_hang);
        initView();
        setContentView(R.layout.activity_gio_hang);
    }

    private void initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleviewgiohang);

    }
}
