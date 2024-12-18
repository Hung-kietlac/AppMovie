package com.example.android_nhom10.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_nhom10.Classes.DatabaseHelper;
import com.example.android_nhom10.Classes.PreferenceHelper;
import com.example.android_nhom10.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9;
    Button button;
    TextView back;
    private Calendar calendar;
    private DatabaseHelper dbHelper;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        editText4=findViewById(R.id.editText4);
        editText5=findViewById(R.id.editText5);
        editText6=findViewById(R.id.editText6);
        editText7=findViewById(R.id.editText7);
        editText8=findViewById(R.id.editText8);
        editText9=findViewById(R.id.editText9);
        button=findViewById(R.id.button);
        back=findViewById(R.id.back);
        TextInputLayout textInputLayout1=findViewById(R.id.textInputLayout1);
        TextInputLayout textInputLayout2=findViewById(R.id.textInputLayout2);
        calendar=Calendar.getInstance();
        editText2.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
        editText5.setInputType(InputType.TYPE_CLASS_PHONE);
        editText6.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText8.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText9.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dbHelper=new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() { //Nút để bấm lùi lại
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textInputLayout1.setEndIconOnClickListener(new View.OnClickListener() { //Khi nhấn vào icon cuốn lịch thì sẽ hiện ra cuốn lịch để chọn ngày và xuất ra Edittext
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        textInputLayout2.setEndIconOnClickListener(new View.OnClickListener() { //Khi nhấn vào icon cuốn lịch thì sẽ hiện ra giới tính để chọn giới tính và xuất ra Edittext
            @Override
            public void onClick(View v) {
                pickGender();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            public boolean isValidGmail(String email) {   //Hàm xét cú pháp gmail
                String gmailPattern="^[a-zA-Z0-9._%+-]+@gmail\\.com$";
                return email.matches(gmailPattern);
            }

            @Override
            public void onClick(View view) {
                String ten=editText1.getText().toString().trim();
                String ngaysinh=editText2.getText().toString().trim();
                String gioitinh=editText3.getText().toString().trim();
                String diachi=editText4.getText().toString().trim();
                String sdt=editText5.getText().toString().trim();
                String email=editText6.getText().toString().trim();
                String username=editText7.getText().toString().trim();
                String password=editText8.getText().toString().trim();
                String confirmPassword=editText9.getText().toString().trim();

                if (ten.isEmpty() || ngaysinh.isEmpty() || gioitinh.isEmpty() || diachi.isEmpty() || sdt.isEmpty()
                        || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUp.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                    editText7.setText("");
                    editText8.setText("");
                    editText9.setText("");

                } else if (!isValidGmail(email)) {
                    Toast.makeText(SignUp.this, "Vui lòng nhập đúng cú pháp của gmail!", Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                    editText7.setText("");
                    editText8.setText("");
                    editText9.setText("");

                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUp.this, "Mật khẩu không trùng khớp! Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                    editText7.setText("");
                    editText8.setText("");
                    editText9.setText("");

                } else if (dbHelper.checkTendangnhap(username)) {
                    Toast.makeText(SignUp.this, "Tên đăng nhập này đã tồn tại! Vui lòng nhập tên khác!", Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                    editText7.setText("");
                    editText8.setText("");
                    editText9.setText("");
                } else if (dbHelper.checkEmail(email)) {
                    Toast.makeText(SignUp.this, "Email này đã tồn tại! Vui lòng nhập email khác!", Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                    editText7.setText("");
                    editText8.setText("");
                    editText9.setText("");

                } else {
                    dbHelper.addKhachhang(ten, ngaysinh, gioitinh, diachi, sdt, email, username, password); //Điền tất cả dữ liệu đã nhập lên database
                    Toast.makeText(SignUp.this, "Bạn đã đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUp.this, Dangnhap.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void showDatePickerDialog() { //Hàm chọn ngày sinh
        final Calendar calendar=Calendar.getInstance();
        int nam=calendar.get(Calendar.YEAR);
        int thang=calendar.get(Calendar.MONTH);
        int ngay=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(SignUp.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        editText2.setText(dateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void pickGender() { //Hàm chọn giới tính
        final String[] genderOptions={"Nam", "Nữ", "Khác"};
        AlertDialog.Builder builder=new AlertDialog.Builder(SignUp.this);
        builder.setTitle("Chọn giới tính");
        builder.setItems(genderOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText3.setText(genderOptions[which]);
            }
        });
        builder.show();
    }
}