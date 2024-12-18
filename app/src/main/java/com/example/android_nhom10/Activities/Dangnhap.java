package com.example.android_nhom10.Activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.Classes.Admin;
import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.Classes.Khachhang;
import com.example.android_nhom10.Classes.PreferenceHelper;
import com.example.android_nhom10.R;

public class Dangnhap extends AppCompatActivity {
    TextView text1, text2, back;
    EditText edt1, edt2;
    Button btn;
    private DatabaseHelper dbHelper;
    private PreferenceHelper preferenceHelper;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        btn=findViewById(R.id.btn);
        edt1=findViewById(R.id.edt1);
        edt2=findViewById(R.id.edt2);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        check=findViewById(R.id.checkBox);
        back=findViewById(R.id.back);
        edt2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dbHelper=new DatabaseHelper(this);
        preferenceHelper=new PreferenceHelper(this);

        back.setOnClickListener(new View.OnClickListener() { //Nút bấm lùi lại
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Tự động điền thông tin nếu có
        if (preferenceHelper.isRemembered()) {
            edt1.setText(preferenceHelper.getUsername());
            edt2.setText(preferenceHelper.getPassword());
            check.setChecked(true);
        }

        // Tự động điền mật khẩu khi nhập tên đăng nhập
        edt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String username=edt1.getText().toString().trim();
                    if (!username.isEmpty()) {
                        String storedPassword=dbHelper.getPasswordByUsername(username);
                        if (storedPassword != null && !storedPassword.isEmpty()) {
                            edt2.setText(storedPassword);
                        }
                    }
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edt1.getText().toString().trim();
                String pass=edt2.getText().toString().trim();
                boolean nhomatkhau=check.isChecked();
                if (name.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Dangnhap.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    edt1.setText("");
                    edt2.setText("");
                } else if (dbHelper.checkAdminLogin(name, pass)) {
                    Admin admin=dbHelper.getAdminByUsernameAndPassword(name, pass);
                    if (nhomatkhau) {
                        preferenceHelper.saveLoginDetails(name, pass, true);
                    } else {
                        preferenceHelper.clearLoginDetails();
                    }
                    Toast.makeText(Dangnhap.this, "Bạn đã đăng nhập với tư cách quản trị viên!", Toast.LENGTH_SHORT).show();
                    Log.d("Dangnhap", "Admin object to send: " + admin);
                    Intent intent=new Intent(Dangnhap.this, AdminTaikhoan.class);
                    intent.putExtra("admin", admin); // Lấy dữ liệu từ Intent sang activity Admin
                    startActivity(intent);
                } else if (dbHelper.checkUserAndPass(name, pass)) {
                    Khachhang khachhang=dbHelper.getUserByUsernameAndPassword(name, pass);
                    if (nhomatkhau) {
                        preferenceHelper.saveLoginDetails(name, pass, true);
                    } else {
                        preferenceHelper.clearLoginDetails();
                    }
                    Toast.makeText(Dangnhap.this, "Bạn đã đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Dangnhap.this, Trangchu.class);
                    intent.putExtra("khachhang", khachhang); // Lấy dữ liệu từ Intent sang activity Trangchu
                    startActivity(intent);
                } else {
                    Toast.makeText(Dangnhap.this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    edt1.setText("");
                    edt2.setText("");
                }
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setPaintFlags(text1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent intent=new Intent(Dangnhap.this, Quenmatkhau.class);
                startActivity(intent);
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2.setPaintFlags(text2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent intent=new Intent(Dangnhap.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}