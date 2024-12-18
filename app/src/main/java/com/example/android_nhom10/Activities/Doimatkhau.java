package com.example.android_nhom10.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.R;
import com.example.android_nhom10.Classes.DatabaseHelper;

public class Doimatkhau extends AppCompatActivity {
    TextView back;
    EditText edit1, edit2, edit3, edit4;
    Button bt;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        back=findViewById(R.id.back);
        edit1=findViewById(R.id.edit1);
        edit2=findViewById(R.id.edit2);
        edit3=findViewById(R.id.edit3);
        edit4=findViewById(R.id.edit4);
        bt=findViewById(R.id.bt);
        dbHelper=new DatabaseHelper(this);
        edit3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        back.setOnClickListener(new View.OnClickListener() { //Hàm bấm nút để lùi lại
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edit1.getText().toString().trim();
                String sodienthoai=edit2.getText().toString().trim();
                String passmoi=edit3.getText().toString().trim();
                String repassmoi=edit4.getText().toString().trim();
                if (username.isEmpty() || sodienthoai.isEmpty() || passmoi.isEmpty() || repassmoi.isEmpty()) {
                    Toast.makeText(Doimatkhau.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    edit1.setText("");
                    edit2.setText("");
                    edit3.setText("");
                    edit4.setText("");
                } else if (!dbHelper.checkTendangnhap(username) || !dbHelper.checkSdt(sodienthoai)) {
                    Toast.makeText(Doimatkhau.this, "Tên đăng nhập hoặc số điện thoại không đúng!", Toast.LENGTH_SHORT).show();
                    edit1.setText("");
                    edit2.setText("");
                    edit3.setText("");
                    edit4.setText("");
                } else if (!passmoi.equals(repassmoi)) {
                    Toast.makeText(Doimatkhau.this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                    edit1.setText("");
                    edit2.setText("");
                    edit3.setText("");
                    edit4.setText("");
                } else {
                    dbHelper.updatePassword(username, sodienthoai, passmoi); //Cập nhật mật khẩu mới lên database
                    Toast.makeText(Doimatkhau.this, "Bạn đã đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Doimatkhau.this, Dangnhap.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}