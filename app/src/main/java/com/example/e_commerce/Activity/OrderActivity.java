package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Fragment.CartFragment;
import com.example.e_commerce.Model.Cart;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class OrderActivity extends AppCompatActivity {

    Button btn_confirm;
    ListView user_order_list;
    TextView txt_total;
    RatingBar ratingBar;
    ArrayList<Cart> cart_products = new ArrayList<>();
    SimpleDateFormat dateFormat;
    float rate = -1;

    //location variables
    EditText txt_get_location;
    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Double total = 0.0;
        user_order_list = findViewById(R.id.user_order_list);
        Database db = new Database(getApplicationContext());
        User user = User.getInstance();
        cart_products = db.get_cart(user.getId());
        OrderActivity.UserOrdertAdapter userOrdertAdapter = new OrderActivity.UserOrdertAdapter(cart_products);
        user_order_list.setAdapter(userOrdertAdapter);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(OrderActivity.this);
        EditText editText_feedback_text = bottomSheetDialog.findViewById(R.id.editText_feedback_text);
        txt_get_location = findViewById(R.id.txt_get_location);
        txt_total = findViewById(R.id.txt_total);
        btn_confirm = findViewById(R.id.btn_confirm);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                bottomSheetDialog.show();

                Button btn_send_feedback = bottomSheetDialog.findViewById(R.id.btn_send_feedback);
                EditText txt_feedback = bottomSheetDialog.findViewById(R.id.editText_feedback_text);
                ratingBar = bottomSheetDialog.findViewById(R.id.ratingBar);

                btn_send_feedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Send to orders table
                        User user = User.getInstance();
                        Calendar calendar = Calendar.getInstance();
                        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        String date = dateFormat.format(calendar.getTime());
                        String address = txt_get_location.getText().toString();

                        String feedback = txt_feedback.getText().toString();
                        rate = ratingBar.getRating();
                        Order order =  new Order(user.getId(), date, address, feedback, rate);

                        if(!txt_feedback.getText().toString().isEmpty() && rate!=-1 && !address.isEmpty()){
                            cart_products = db.get_cart(user.getId());
                            db.confirm_order(order, cart_products);
                            bottomSheetDialog.dismiss();
                            startActivity(new Intent(OrderActivity.this, UserActivity.class));

                        }else{
                            Toast.makeText(getApplicationContext(), "Please Enter Location, Feedback and Rateing", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        for (int i = 0 ; i<cart_products.size() ; i++){
            total += cart_products.get(i).getPrice()*cart_products.get(i).getQuantity();
        }
        txt_total.setText(total+"");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        txt_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });

    }

    class UserOrdertAdapter extends BaseAdapter {

        ArrayList<Cart> cart_products = new ArrayList<>();

        public UserOrdertAdapter(ArrayList<Cart> cart_products) {
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
            View item = layoutInflater.inflate(R.layout.user_order_item, null);

            TextView product_name = (TextView) item.findViewById(R.id.order_product_name);
            TextView product_price = (TextView) item.findViewById(R.id.order_product_price);
            TextView product_quantity= (TextView) item.findViewById(R.id.order_product_quantity);

            product_name.setText(cart_products.get(i).getName());
            product_price.setText(cart_products.get(i).getPrice()+"");
            product_quantity.setText(cart_products.get(i).getQuantity()+"");

            return item;
        }
    }

    private void getLastLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                try {
                                    Geocoder geocoder = new Geocoder(OrderActivity.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    txt_get_location.setText(""+addresses.get(0).getAddressLine(0));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(OrderActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else {
                Toast.makeText(OrderActivity.this,"Please provide the required permission",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}