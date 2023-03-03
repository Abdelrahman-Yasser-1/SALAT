package com.example.e_commerce.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class CategoryProductsActivity extends AppCompatActivity {
    ListView user_list_category_products;
    ArrayList<Product> products = new ArrayList<>();
    Intent n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        n = getIntent();
        int id =  n.getExtras().getInt("id") ;

        user_list_category_products = findViewById(R.id.user_category_list_products);
        Database dp = new Database(getApplicationContext());
        products = dp.get_category_products(id);
        CategoryProductsActivity.UserHomeCategoryProductsAdapter userHomeCategoryProductsAdapter = new CategoryProductsActivity.UserHomeCategoryProductsAdapter(products);
        user_list_category_products.setAdapter(userHomeCategoryProductsAdapter);

        user_list_category_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent = new Intent(getApplicationContext(), ProductActivity.class);

                intent.putExtra("id",products.get(i).getId());
                intent.putExtra("name",products.get(i).getName());
                intent.putExtra("price",products.get(i).getPrice());
                intent.putExtra("image",products.get(i).getImage());
                startActivity(intent);
            }
        });

    }

    class UserHomeCategoryProductsAdapter extends BaseAdapter {

        ArrayList<Product> products = new ArrayList<>();

        public UserHomeCategoryProductsAdapter(ArrayList<Product> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return products.get(i).getName();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.user_home_product_item, null);

            ImageView product_image = (ImageView) item.findViewById(R.id.user_home_iv_product_image);
            TextView product_name = (TextView) item.findViewById(R.id.user_home_tv_product_name);
            TextView product_price = (TextView) item.findViewById(R.id.user_home_tv_product_price);

            product_name.setText(products.get(i).getName());
            product_price.setText(products.get(i).getPrice()+"");
            String url = products.get(i).getImage();
            Glide.with(getApplicationContext()).load(url).into(product_image);

            return item;
        }
    }
}