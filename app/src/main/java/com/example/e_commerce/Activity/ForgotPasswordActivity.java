package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText txt_email;
    Button btn_get_password;
    TextView textView_password;
    TextView forgot_password_tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        
        Database db = new Database(this);
        setTitle("Forgot Password");

        txt_email = (EditText) findViewById(R.id.forgot_password_txt_email);
        textView_password = (TextView) findViewById(R.id.login_tv_forgot_password);
        forgot_password_tv_password = findViewById(R.id.forgot_password_tv_password);

        btn_get_password = (Button) findViewById(R.id.forgot_password_btn_get_password);
        btn_get_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Forgot password code
                String email = txt_email.getText().toString();
                if(!email.isEmpty()){
                    String password = db.frgot_password(email);
                    if(password != null)
                        forgot_password_tv_password.setText(password);
                    else
                        Toast.makeText(getApplicationContext(), "Make sure from your email", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Enter your mail first", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}