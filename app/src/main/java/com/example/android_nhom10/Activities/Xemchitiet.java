package com.example.android_nhom10.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.R;

public class Xemchitiet extends AppCompatActivity {
    TextView back;
    Button datve;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemchitiet);

        back=findViewById(R.id.back);
        TextView tenphimTextView=findViewById(R.id.tenphim);
        TextView theloaiTextView=findViewById(R.id.theloai);
        TextView ngaychieuTextView=findViewById(R.id.ngaychieu);
        TextView thoiluongTextView=findViewById(R.id.thoiluong);
        TextView tendaodienTextView=findViewById(R.id.tendaodien);
        TextView dienvienTextView=findViewById(R.id.dienvien);
        ImageView posterImageView=findViewById(R.id.poster);
        TextView noidungTextView=findViewById(R.id.noidung);
        TextView quocgiaTextView=findViewById(R.id.quocgia);
        TextView giaveTextView=findViewById(R.id.giave);
        datve=findViewById(R.id.datve);
        dbHelper=new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Lấy dữ liệu từ intent
        String tenphim=getIntent().getStringExtra("TEN_PHIM");
        String theloai=getIntent().getStringExtra("THE_LOAI");
        String ngaychieu=getIntent().getStringExtra("NGAY_CHIEU");
        String thoiluong=getIntent().getStringExtra("THOI_LUONG");
        String poster=getIntent().getStringExtra("POSTER");
        String tendaodien=getIntent().getStringExtra("TEN_DAO_DIEN");
        String dienvien=getIntent().getStringExtra("DIEN_VIEN");
        String noidung=getIntent().getStringExtra("NOI_DUNG");
        String quocgia=getIntent().getStringExtra("QUOC_GIA");
        String giave=getIntent().getStringExtra("GIA_VE");

        //Hiển thị dữ liệu
        tenphimTextView.setText(tenphim);
        theloaiTextView.setText(theloai);
        ngaychieuTextView.setText(ngaychieu);
        thoiluongTextView.setText(thoiluong);
        tendaodienTextView.setText(tendaodien);
        dienvienTextView.setText(dienvien);
        noidungTextView.setText(noidung);
        quocgiaTextView.setText(quocgia);
        giaveTextView.setText(giave);
        Glide.with(this).load(poster).into(posterImageView); //Lấy ảnh từ URL

        datve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Xemchitiet.this, DatveActivity.class);
                intent.putExtra("TEN_PHIM", tenphim);
                intent.putExtra("POSTER", poster);
                intent.putExtra("GIA_VE", giave);
                startActivity(intent);
            }
        });
    }
}