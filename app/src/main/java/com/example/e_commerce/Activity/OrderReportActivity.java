package com.example.e_commerce.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class OrderReportActivity extends AppCompatActivity {
    Intent n;
    int order_id;
    TextView textView;
    ListView order_details;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_report);
        n = getIntent();
        order_id =  n.getExtras().getInt("id") ;
        textView = findViewById(R.id.order_id);
        textView.setText("Order Id: " + order_id);
        order_details = findViewById(R.id.order_details);
        Database db = new Database(this);
        products = db.get_order_details(order_id);
        OrderReportActivity.ReportAdapter reportAdapter = new OrderReportActivity.ReportAdapter(products);
        order_details.setAdapter(reportAdapter);
    }

    class ReportAdapter extends BaseAdapter {

        ArrayList<Product> orderDetails = new ArrayList<>();

        public ReportAdapter(ArrayList<Product> orderDetails) {
            this.orderDetails = orderDetails;
        }

        @Override
        public int getCount() {
            return orderDetails.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return orderDetails.get(i).getId();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.listrow_details_item, null);

            TextView product_name = (TextView) item.findViewById(R.id.report_product_name);
            TextView product_quantity = (TextView) item.findViewById(R.id.report_product_quantity);

            product_name.setText(orderDetails.get(i).getName() + "");
            product_quantity.setText(orderDetails.get(i).getQuantity() + "");

            return item;
        }
    }
}