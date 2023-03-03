package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.OrderActivity;
import com.example.e_commerce.Activity.ProductActivity;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Cart;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    Button btn_order;
    ListView list_cart;
    ArrayList<Cart> cart_products = new ArrayList<>();
    LinearLayout empty, cart;
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        list_cart = v.findViewById(R.id.cart_list_user_cart);
        empty = v.findViewById(R.id.embty);
        cart = v.findViewById(R.id.cart);

        get_data();

        if(cart_products.size() == 0){
            empty.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);
        }
        else{
            empty.setVisibility(View.GONE);
            cart.setVisibility(View.VISIBLE);
            set_list();
        }

        btn_order = v.findViewById(R.id.cart_btn_confirm_order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getContext(), OrderActivity.class));
            }
        });



        return v;
    }

    public void get_data(){
        Database db = new Database(getContext());
        User user = User.getInstance();
        cart_products = db.get_cart(user.getId());
    }

    public void set_list(){
        get_data();
        CartFragment.UserCartAdapter userCartAdapter = new CartFragment.UserCartAdapter(cart_products);
        list_cart.setAdapter(userCartAdapter);
    }

    class UserCartAdapter extends BaseAdapter {

        ArrayList<Cart> cart_products = new ArrayList<>();

        public UserCartAdapter(ArrayList<Cart> cart_products) {
            this.cart_products = cart_products;
        }

        @Override
        public int getCount() {
            return cart_products.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return cart_products.get(i).getName();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.cart_product_item, null);

            ImageView product_image = (ImageView) item.findViewById(R.id.user_cart_iv_product_image);
            TextView product_name = (TextView) item.findViewById(R.id.user_cart_tv_product_name);
            TextView product_price = (TextView) item.findViewById(R.id.user_cart_tv_product_price);
            TextView product_quantity= (TextView) item.findViewById(R.id.user_cart_tv_product_amount);
            ImageButton btn_del = (ImageButton) item.findViewById(R.id.user_cart_btn_delete);

            ImageButton btn_increment = (ImageButton) item.findViewById(R.id.user_cart_btn_increment_amount);
            ImageButton btn_decrement = (ImageButton) item.findViewById(R.id.user_cart_btn_decrement_amount);


            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = User.getInstance();
                    int product_id = cart_products.get(i).getProduct_id();
                    Database db = new Database(getContext());
                    db.delete_from_cart(product_id, user.getId());

                    get_data();
                    if(cart_products.size() == 0){
                        empty.setVisibility(View.VISIBLE);
                        cart.setVisibility(View.GONE);
                    }
                    else{
                        empty.setVisibility(View.GONE);
                        cart.setVisibility(View.VISIBLE);
                        set_list();
                    }
                }
            });

            btn_increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = User.getInstance();
                    int quantity = Integer.parseInt(product_quantity.getText().toString());
                    quantity++;
                    product_quantity.setText(quantity+"");
                    Database db = new Database(getContext());
                    db.change_cart_quantity(cart_products.get(i).getProduct_id(), user.getId(), quantity);
                }
            });

            btn_decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = User.getInstance();
                    int quantity = Integer.parseInt(product_quantity.getText().toString());

                    if(quantity != 1){
                        quantity--;
                        product_quantity.setText(quantity+"");
                        Database db = new Database(getContext());
                        db.change_cart_quantity(cart_products.get(i).getProduct_id(), user.getId(), quantity);
                    }

                }
            });

            product_name.setText(cart_products.get(i).getName());
            product_price.setText(cart_products.get(i).getPrice()+"");
            product_quantity.setText(cart_products.get(i).getQuantity()+"");
            String url = cart_products.get(i).getImage();
            Glide.with(getContext()).load(url).into(product_image);

            
            return item;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        get_data();
        if(cart_products.size() == 0){
            empty.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);
        }
        else{
            empty.setVisibility(View.GONE);
            cart.setVisibility(View.VISIBLE);
            set_list();
        }
    }
}