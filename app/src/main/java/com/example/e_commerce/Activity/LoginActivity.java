package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText txt_usrename, txt_password;
    TextView textView_forgot_password, textView_signup;
    CheckBox checkBox_remember_me;
    Boolean remember_me = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        startActivity(new Intent(LoginActivity.this, AdminActivity.class));//comment me after finish task
        //Hide ActionBar
        getSupportActionBar().hide();
        Database db = new Database(this);

        checkBox_remember_me = (CheckBox) findViewById(R.id.login_checkBox_remember_me);
        txt_usrename = findViewById(R.id.login_txt_username);
        txt_password = findViewById(R.id.login_txt_password);
        btn_login = (Button) findViewById(R.id.login_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_remember_me.isChecked())
                    remember_me = true;
                else
                    remember_me = false;

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putBoolean("remember_me", remember_me);
                myEdit.commit();

                // TODO: login
                String username = txt_usrename.getText().toString(),
                        password = txt_password.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()){

                    if(username.equals("admin") && password.equals("admin")){
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    }else{
                        Cursor cursor = db.user_login(username, password);
                        if(cursor.getCount() > 0){
                            User user = User.getInstance();
                            user.setId(cursor.getInt(0));
                            user.setName(cursor.getString(1));
                            user.setEmail(cursor.getString(2));
                            user.setPassword(cursor.getString(3));
                            user.setGender(cursor.getString(4));
                            user.setBirthdate(cursor.getString(5));
                            user.setJob(cursor.getString(6));

                            myEdit.putInt("id", cursor.getInt(0));
                            myEdit.putString("name", cursor.getString(1));
                            myEdit.putString("email", cursor.getString(2));
                            myEdit.putString("password", cursor.getString(3));
                            myEdit.putString("gender", cursor.getString(4));
                            myEdit.putString("birthdate", cursor.getString(5));
                            myEdit.putString("job", cursor.getString(6));
                            myEdit.commit();
                            finish();
                            startActivity(new Intent(LoginActivity.this, UserActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Make suer from your data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Fill your data first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView_signup = (TextView) findViewById(R.id.login_tv_signup);
        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to signup screen
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        textView_forgot_password = (TextView) findViewById(R.id.login_tv_forgot_password);
        textView_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to forgot password screen
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }
}