package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class EditProductActivity extends AppCompatActivity {

    EditText txt_image, txt_name, txt_price, txt_quantity;
    Button btn_show, btn_edit;
    Spinner spinner_categoty;
    ImageView iv_product_image;
    ArrayList<Category> categories= new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        getSupportActionBar().hide();

        txt_image = findViewById(R.id.edit_product_txt_image);
        txt_name = findViewById(R.id.edit_product_txt_name);
        txt_price = findViewById(R.id.edit_product_txt_price);
        txt_quantity = findViewById(R.id.edit_product_txt_quantity);
        iv_product_image = findViewById(R.id.edit_product_iv_image);
        btn_show = findViewById(R.id.edit_product_btn_show_image);
        btn_edit = findViewById(R.id.edit_product_btn_edit);
        spinner_categoty = findViewById(R.id.edit_product_spinner_category);

        Database db = new Database(this);

        Intent n = getIntent();
        int id =  n.getExtras().getInt("id") ;
        double price =  n.getExtras().getDouble("price") ;
        String name =  n.getExtras().getString("name") ;
        String image =  n.getExtras().getString("image") ;
        int cat_id = n.getExtras().getInt("cat_id") ;
        int quantity = n.getExtras().getInt("quantity") ;
        int sold = n.getExtras().getInt("sold") ;


        categories = db.get_categories();
        ArrayList<String> category_name = new ArrayList<String>();
        for(int i = 0 ; i < categories.size() ; i++){
            category_name.add(categories.get(i).getName());
        }

        String cat_name;
        int i = 0;
        for(i = 0 ; i < categories.size() ; i++){
            if(categories.get(i).getId() == cat_id){
                cat_name = categories.get(i).getName();
                break;
            }
        }

        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,category_name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categoty.setAdapter(aa);

        spinner_categoty.setSelection(i);
        Glide.with(getApplicationContext()).load(image).into(iv_product_image);
        txt_image.setText(image);
        txt_name.setText(name);
        txt_price.setText(price+"");
        txt_quantity.setText(quantity+"");


        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = txt_image.getText().toString();
                Glide.with(getApplicationContext()).load(url).into(iv_product_image);
            }
        });

        // TODO: show product data in txt fildes

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: edit product
                String name = txt_name.getText().toString();
                Double price = Double.parseDouble(txt_price.getText().toString());
                int quantity = Integer.parseInt(txt_quantity.getText().toString());
                String cat_name = spinner_categoty.getSelectedItem().toString();
                String image = txt_image.getText().toString();
                int sold = n.getExtras().getInt("sold");

                int cat_id = 0;
                for(int i = 0 ; i < categories.size() ; i++){
                   if(categories.get(i).getName() == cat_name){
                       cat_id = categories.get(i).getId();
                       break;
                   }
                }
                Product product = new Product(id, name, image, price, sold, quantity, cat_id);

                Database db = new Database(getApplicationContext());
                db.edit_product(product);
//                finish();
            }
        });

    }
}