package com.example.android_nhom10.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.R;

import java.util.ArrayList;
import java.util.List;

public class DatveActivity extends AppCompatActivity {
    TextView movieTitle, moviePrice, quantityText;
    ImageView moviePoster, increaseQuantity, decreaseQuantity;
    Spinner theaterSpinner, dateSpinner, timeSpinner;
    Button bookButton;
    private DatabaseHelper dbHelper;
    int ticketQuantity=1;
    int ticketPricePerUnit=0;
    int totalPrice=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datve);

        // Khởi tạo các view
        movieTitle=findViewById(R.id.movie_title);
        moviePrice=findViewById(R.id.price);
        moviePoster=findViewById(R.id.movie_poster);
        theaterSpinner=findViewById(R.id.theater_spinner);
        dateSpinner=findViewById(R.id.date_spinner);
        timeSpinner=findViewById(R.id.time_spinner);
        bookButton=findViewById(R.id.bt);
        increaseQuantity=findViewById(R.id.item_giohang_cong);
        decreaseQuantity=findViewById(R.id.item_giohang_tru);
        quantityText=findViewById(R.id.item_giohang_soluong);

        dbHelper=new DatabaseHelper(this);

        // Lấy dữ liệu từ Intent và hiển thị lên UI
        Bundle extras=getIntent().getExtras();
        if (extras != null) {
            String title=extras.getString("TEN_PHIM");
            String posterUrl=extras.getString("POSTER");
            ticketPricePerUnit=dbHelper.getTicketPrice(title);

            movieTitle.setText(title);
            Glide.with(this).load(posterUrl).into(moviePoster);
            updateTotalPrice();
        }

        // Load danh sách rạp vào Spinner
        loadTheaterSpinner();

        // Thiết lập sự kiện chọn rạp để cập nhật danh sách ngày chiếu
        theaterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDateSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần xử lý khi không có lựa chọn
            }
        });

        // Thiết lập sự kiện chọn ngày để cập nhật danh sách giờ chiếu
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadTimeSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần xử lý khi không có lựa chọn
            }
        });

        // Sự kiện nút tăng số lượng vé
        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketQuantity++;
                updateQuantityAndTotalPrice();
            }
        });

        // Sự kiện nút giảm số lượng vé
        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticketQuantity > 1) {
                    ticketQuantity--;
                    updateQuantityAndTotalPrice();
                } else {
                    Toast.makeText(DatveActivity.this, "Số lượng vé không thể nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Sự kiện nút Đặt vé
        bookButton.setOnClickListener(view -> {
            // Lấy dữ liệu từ các Spinner và các view khác
            String selectedMovieTitle=movieTitle.getText().toString().trim();
            String posterUrl=extras.getString("POSTER");
            String selectedTheater=theaterSpinner.getSelectedItem().toString().trim();
            String selectedDate=dateSpinner.getSelectedItem().toString().trim();
            String selectedTime=timeSpinner.getSelectedItem().toString().trim();
            int totalPrice=ticketQuantity * ticketPricePerUnit;

            // Thêm đơn hàng vào cơ sở dữ liệu
            boolean isInserted=dbHelper.insertDonHang(selectedMovieTitle, posterUrl, selectedTheater, selectedDate, selectedTime, totalPrice);

            if (isInserted) {
                Toast.makeText(DatveActivity.this, "Đơn hàng đã được thêm thành công", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DatveActivity.this, Trangchu.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(DatveActivity.this, "Lỗi khi thêm đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTheaterSpinner() {
        List<String> theaters=new ArrayList<>();
        Cursor cursor=dbHelper.getAllTheaters(); // Phương thức này cần được triển khai trong DatabaseHelper của bạn

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tenrap=cursor.getString(cursor.getColumnIndex("tenrap"));
                theaters.add(tenrap);
            } while (cursor.moveToNext());
        }

        // Đưa danh sách rạp vào Spinner
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theaters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theaterSpinner.setAdapter(adapter);

        // Mặc định load danh sách ngày chiếu cho rạp đầu tiên
        if (theaters.size() > 0) {
            loadDateSpinner();
        }
    }

    private void loadDateSpinner() {
        List<String> dates=new ArrayList<>();

        // Lấy id của rạp được chọn từ Spinner
        String selectedTheater=(String) theaterSpinner.getSelectedItem();
        int theaterId=dbHelper.getTheaterIdByName(selectedTheater);

        // Lấy danh sách ngày chiếu từ bảng showtime_table cho rạp có id là theaterId
        Cursor cursor=dbHelper.getShowtimesByTheaterId(theaterId); // Phương thức này cần được triển khai trong DatabaseHelper của bạn

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String ngaychieu=cursor.getString(cursor.getColumnIndex("ngaychieu"));
                if (!dates.contains(ngaychieu)) {
                    dates.add(ngaychieu);
                }
            } while (cursor.moveToNext());
        }

        // Đưa danh sách ngày chiếu vào Spinner
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);

        // Mặc định load danh sách giờ chiếu cho ngày đầu tiên
        if (dates.size() > 0) {
            loadTimeSpinner();
        }
    }


    private void loadTimeSpinner() {
        List<String> times=new ArrayList<>();

        // Lấy ngày chiếu được chọn từ Spinner
        String selectedDate=(String) dateSpinner.getSelectedItem();

        // Lấy id của rạp được chọn từ Spinner
        String selectedTheater=(String) theaterSpinner.getSelectedItem();
        int theaterId=dbHelper.getTheaterIdByName(selectedTheater);

        // Lấy danh sách giờ chiếu từ bảng showtime_table cho ngày chiếu là selectedDate và idrap là theaterId
        Cursor cursor=dbHelper.getShowtimesByDate(theaterId, selectedDate);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String giochieu=cursor.getString(cursor.getColumnIndex("giochieu"));
                times.add(giochieu);
            } while (cursor.moveToNext());
        }

        // Đưa danh sách giờ chiếu vào Spinner
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);
    }

    private void updateQuantityAndTotalPrice() {
        quantityText.setText(String.valueOf(ticketQuantity));
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice=ticketQuantity * ticketPricePerUnit;
        moviePrice.setText(getString(R.string.price_format, totalPrice));
    }
}