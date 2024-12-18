package com.example.android_nhom10.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.Classes.Khachhang;
import com.example.android_nhom10.Classes.Phim;
import com.example.android_nhom10.Classes.PhimAdapter;
import com.example.android_nhom10.Classes.SliderAdapters;
import com.example.android_nhom10.R;

import java.util.List;

public class Trangchu extends AppCompatActivity {
    TextView back;
    ImageView toi, btngiohang;
    ViewPager2 viewPager2;
    private DatabaseHelper dbHelper;
    private Handler slideHandler=new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        back=findViewById(R.id.back);
        toi=findViewById(R.id.imageView17);
        btngiohang=findViewById(R.id.imageView16);
        viewPager2=findViewById(R.id.viewpagerSlide);

        dbHelper=new DatabaseHelper(this);
        Khachhang khachhang=(Khachhang) getIntent().getSerializableExtra("khachhang");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<String> sliderItems=dbHelper.getAllPosterUrls(); //Lấy tất cả poster URL từ database
        //Hiển thị poster lên layout viewPager2
        SliderAdapters sliderAdapters=new SliderAdapters(this, sliderItems, viewPager2);

        viewPager2.setAdapter(sliderAdapters);
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3); //Số lượng trang tối đa được lưu trữ ngoài màn hình là 3
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS); //Chế độ cuộn

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40)); //Biên độ 40px giữa các trang
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });

        List<Phim> phimList=dbHelper.getAllPhim(); //Lấy tất cả các phim trên database
        RecyclerView recyclerView=findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PhimAdapter phimAdapter=new PhimAdapter(this, phimList);
        recyclerView.setAdapter(phimAdapter);

        slideHandler.postDelayed(sliderRunnable, 3000); //Thời gian hiển thị 1 poster là 3 giây

        btngiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Trangchu.this, GioHangActivity.class);
                startActivity(intent);
                finish();
            }
        });

        toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Trangchu.this, Taikhoan.class);
                intent.putExtra("khachhang", khachhang); //Lấy dữ liệu intent sang activity Taikhoan
                startActivity(intent);
            }
        });
    }

    private Runnable sliderRunnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1); //Tăng thêm 1 poster trong viewPager2
            slideHandler.postDelayed(sliderRunnable, 3000); //Thời gian hiển thị 1 poster là 3 giây
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 3000); //Thời gian hiển thị 1 poster là 3 giây
    }
}