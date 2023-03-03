package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.e_commerce.Fragment.AddCategoryFragment;
import com.example.e_commerce.Fragment.AddProductFragment;
import com.example.e_commerce.Fragment.ChartFragment;
import com.example.e_commerce.Fragment.FeedbackFragment;
import com.example.e_commerce.Fragment.ManageCategoryFragment;
import com.example.e_commerce.Fragment.ManageProductFragment;
import com.example.e_commerce.R;
import com.example.e_commerce.Fragment.ReportFragment;

public class AdminActivity extends AppCompatActivity {

    Fragment admin_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new ManageProductFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_manage_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new ManageProductFragment()).commit();
                return true;
            case R.id.nav_add_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new AddProductFragment()).commit();
                return true;
            case R.id.nav_manage_category:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new ManageCategoryFragment()).commit();
                return true;
            case R.id.nav_add_category:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new AddCategoryFragment()).commit();
                return true;
            case R.id.nav_report:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new ReportFragment()).commit();
                return true;
            case R.id.nav_feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new FeedbackFragment()).commit();
                return true;
            case R.id.nav_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, new ChartFragment()).commit();
                return true;
        }
        return true;
    }
}