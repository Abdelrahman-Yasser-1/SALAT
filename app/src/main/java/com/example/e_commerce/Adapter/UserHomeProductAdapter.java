//package com.example.e_commerce.Adapter;
//
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.e_commerce.Model.Product;
//import com.example.e_commerce.R;
//
//import java.util.ArrayList;
//
//public abstract class UserHomeProductAdapter extends BaseAdapter {
//
//    // This adapter will used in user home and search.
//
//    ArrayList<Product> products = new ArrayList<>();
//
//    public UserHomeProductAdapter(Cursor cursor) {
//        while (cursor.isAfterLast() == false) {
//            this.products.add(new Product( // int id, int quantity, int cat_id, int sold, String name, String image, double price.
//                    cursor.getInt(0),      // order of this will change passed on database attributes order.
//                    cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getDouble(6)));
//            cursor.moveToNext();
//        }
//    }
//
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        LayoutInflater layoutInflater = getLayoutInflater();
//        View item = layoutInflater.inflate(R.layout.user_home_product_item, null);
//
//        ImageView product_image = (ImageView) item.findViewById(R.id.user_home_iv_product_image);
//        TextView product_name = (TextView) item.findViewById(R.id.user_home_tv_product_name);
//        TextView product_price = (TextView) item.findViewById(R.id.user_home_tv_product_price);
//
//        product_name.setText(products.get(i).getName());
//        product_price.setText(products.get(i).getPrice());
//        String url = products.get(i).getImage();
//        Glide.with(getContext()).load(url).into(product_image);
//
//        return item;
//    }
//}
