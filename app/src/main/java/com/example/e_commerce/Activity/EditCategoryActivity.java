package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.R;

public class EditCategoryActivity extends AppCompatActivity {

    EditText txt_name, txt_image;
    Button btn_show, btn_edit;
    ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Database db = new Database(this);

        Intent n = getIntent();
        int id =  n.getExtras().getInt("id") ;
        String name =  n.getExtras().getString("name") ;
        String image =  n.getExtras().getString("image") ;

        txt_name = findViewById(R.id.edit_category_txt_name);
        txt_image = findViewById(R.id.edit_category_txt_image);

        btn_show = findViewById(R.id.edit_category_btn_show_image);
        btn_edit = findViewById(R.id.edit_category_btn_edit_category);
        iv_image = findViewById(R.id.edit_category_iv_image);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = txt_image.getText().toString();
                Glide.with(getApplicationContext()).load(url).into(iv_image);
            }
        });

        // TODO: show category data in txt fildes
        Glide.with(getApplicationContext()).load(image).into(iv_image);
        txt_image.setText(image);
        txt_name.setText(name);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: edit category
                String name = txt_name.getText().toString(),
                        image = txt_image.getText().toString();
                Category category = new Category(id, name, image);
                db.edit_category(category);
            }
        });

    }
}