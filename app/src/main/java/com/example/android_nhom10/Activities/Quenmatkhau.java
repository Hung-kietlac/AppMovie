package com.example.android_nhom10.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.android_nhom10.R;
import com.example.android_nhom10.Classes.DatabaseHelper;

import java.util.Random;

public class Quenmatkhau extends AppCompatActivity {
    private static final int REQUEST_SMS_PERMISSION=1;
    TextView back, countdownTextView, textkhac;
    EditText edit1, edit2, edit3;
    CountDownTimer countDownTimer;
    Button bt;
    private DatabaseHelper dbHelper;
    private String maXacMinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quenmatkhau);
        back=findViewById(R.id.back);
        countdownTextView=findViewById(R.id.countdownTextView);
        edit1=findViewById(R.id.edit1);
        edit2=findViewById(R.id.edit2);
        edit3=findViewById(R.id.edit3);
        bt=findViewById(R.id.bt);
        textkhac=findViewById(R.id.textkhac);
        edit2.setInputType(InputType.TYPE_CLASS_PHONE);
        dbHelper=new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() { //Hàm bấm nút để lùi lại
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        countdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Quenmatkhau.this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) { //Kiểm tra quyền SMS có được cấp hay không
                    ActivityCompat.requestPermissions(Quenmatkhau.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION);
                } else { //Nếu được cấp rồi thì gửi được mã
                    sendMaxacminh();
                    countdownTextView.setPaintFlags(countdownTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //Hiện dấu gạch chân
                    countdownTextView.setPaintFlags(0); //Tắt dấu gạch chân
                    countdownTextView.setOnClickListener(null); //Không cho nhấn nút khi chạy thời gian
                    countDownTimer=new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {   //Hàm đếm ngược 60 giây
                            int seconds=(int) (millisUntilFinished / 1000);
                            countdownTextView.setText("Mã gửi sau " + String.format("%02d", seconds) + " giây");
                        }

                        @Override
                        public void onFinish() {  //Hàm này nếu hết giờ thì hiện dòng "Gửi lại mã"
                            countdownTextView.setText("Gửi lại mã!");
                            countdownTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {  //Nếu bấm tiếp "Gửi lại mã" thì sẽ chạy thời gian lại 60 giây và gửi lại mã xác minh khác
                                    countdownTextView.setPaintFlags(countdownTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                    countdownTextView.setPaintFlags(0);
                                    countDownTimer.start();
                                    countdownTextView.setOnClickListener(null);
                                    sendMaxacminh();
                                }
                            });
                        }
                    };
                    countDownTimer.start();
                }
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edit1.getText().toString().trim();
                String sdt=edit2.getText().toString().trim();
                String maxacminh=edit3.getText().toString().trim();
                if (username.isEmpty() || sdt.isEmpty() || maxacminh.isEmpty()) {
                    Toast.makeText(Quenmatkhau.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    edit1.setText("");
                    edit2.setText("");
                    edit3.setText("");
                } else if (!maxacminh.equals(maXacMinh)) {
                    Toast.makeText(Quenmatkhau.this, "Mã xác minh không chính xác!", Toast.LENGTH_SHORT).show();
                    edit1.setText("");
                    edit2.setText("");
                    edit3.setText("");
                } else if (!dbHelper.checkTendangnhap(username) || !dbHelper.checkSdt(sdt)) {
                    Toast.makeText(Quenmatkhau.this, "Tên đăng nhập hoặc số điện thoại không đúng!", Toast.LENGTH_SHORT).show();
                    edit1.setText("");
                    edit2.setText("");
                    edit3.setText("");
                } else {
                    Toast.makeText(Quenmatkhau.this, "Mã xác minh chính xác!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Quenmatkhau.this, Doimatkhau.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private String maXacMinh() { //Hàm ramdom mã xác minh có 4 số ngẫu nhiên
        Random random=new Random();
        int ma=random.nextInt(9000) + 1000; //Random 4 số từ 1000 đến 9999
        return String.valueOf(ma);
    }

    private void sendMaxacminh() {  //Hàm sẽ gửi mã xác minh qua số điện thoại bằng SMS
        String sodienthoai=edit2.getText().toString().trim();
        if (!sodienthoai.isEmpty()) {
            if (dbHelper.checkSdt(sodienthoai)) { //Check nếu có số điện thoại trên database thì gửi mã xác minh qua số điện thoại đó
                maXacMinh=maXacMinh();  //Lưu mã xác minh
                sendSMS(sodienthoai, "Mã xác minh của bạn là: " + maXacMinh); //Nội dung tin nhắn qua số điện thoại đã nhập
            } else if (!dbHelper.checkSdt(sodienthoai)) { //Còn nếu không thì số không tồn tại
                Toast.makeText(Quenmatkhau.this, "Số điện thoại không tồn tại!", Toast.LENGTH_SHORT).show();
                edit1.setText("");
                edit2.setText("");
            }
        } else {
            Toast.makeText(Quenmatkhau.this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            edit1.setText("");
            edit2.setText("");
        }
    }

    private void sendSMS(String sodienthoai, String message) {  //Hàm check coi mã gửi được không
        try {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(sodienthoai, null, message, null, null);
            Toast.makeText(this, "Đã gửi mã xác minh đến " + sodienthoai, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Gửi mã xác minh không thành công. Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {  //Hàm kiểm tra guyền gửi SMS
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Quyền gửi SMS bị từ chối. Không thể gửi mã xác minh!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}