package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Database db = new Database(this);
//        db.some_category();
//        db.some_product();
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); //hide navigation bar

        new Handler().postDelayed(new Runnable() {
            public void run() {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                boolean remember_me = sh.getBoolean("remember_me", false);
                User user = User.getInstance();
                if (remember_me) {
                    user.setId(sh.getInt("id", -1));
                    user.setName(sh.getString("name", ""));
                    user.setEmail(sh.getString("email", ""));
                    user.setPassword(sh.getString("password", ""));
                    user.setGender(sh.getString("gender", ""));
                    user.setBirthdate(sh.getString("birthdate", ""));
                    user.setJob(sh.getString("job", ""));
                    startActivity(new Intent(SplashScreenActivity.this, UserActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2 * 500);

    }
}