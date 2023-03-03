package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText txt_birth_date, txt_username, txt_email, txt_password, txt_job;
    TextView tv_login;
    Button btn_signup;
    Spinner spinner_gender;

    String birth_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();
        Database db = new Database(this);

        txt_birth_date = findViewById(R.id.signup_txt_birth_date);

        txt_username = findViewById(R.id.signup_txt_username);
        txt_email = findViewById(R.id.signup_txt_email);
        txt_password = findViewById(R.id.signup_txt_password);
        txt_job = findViewById(R.id.signup_txt_job);
        spinner_gender = findViewById(R.id.signup_spinner_gender);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                birth_date = dateFormat.format(myCalendar.getTime()).toString();
                txt_birth_date.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        txt_birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        tv_login = (TextView) findViewById(R.id.signup_tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        btn_signup = (Button) findViewById(R.id.signup_btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: SignUp

                String username = txt_username.getText().toString(),
                        email = txt_email.getText().toString(),
                        job = txt_job.getText().toString(),
                        password = txt_password.getText().toString();
                if(!username.isEmpty() && !email.isEmpty() && !job.isEmpty() && !job.isEmpty() && !password.isEmpty() && !birth_date.isEmpty()){
                    User user = User.getInstance();
                    user.setName(username);
                    user.setEmail(email);
                    user.setJob(job);
                    user.setPassword(password);
                    user.setBirthdate(birth_date);
                    user.setGender(spinner_gender.getSelectedItem().toString());
                    db.insert_user(user);
                    Toast.makeText(getApplicationContext(), "Signup done", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Fill your data first", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });


    }
}