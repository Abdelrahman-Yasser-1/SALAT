package com.example.e_commerce.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Cart;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {

    ListView feedback_list;
    ArrayList<Order> orders = new ArrayList<>();
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
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
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
        Database db = new Database(getContext());
        orders = db.get_orders();
        feedback_list = v.findViewById(R.id.admin_feedback_list);

        // TODO: get all orders with feedback and show itin listView
        FeedbackFragment.AdminOrdersFeedbackAdapter adminOrdersFeedbackAdapter = new FeedbackFragment.AdminOrdersFeedbackAdapter(orders);
        feedback_list.setAdapter(adminOrdersFeedbackAdapter);

        return v;
    }

    class AdminOrdersFeedbackAdapter extends BaseAdapter {

        ArrayList<Order> orders = new ArrayList<>();

        public AdminOrdersFeedbackAdapter(ArrayList<Order> orders) {
            this.orders = orders;
        }

        @Override
        public int getCount() {
            return orders.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return orders.get(i).getDate();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.admin_feedback_item, null);

            TextView order_date = (TextView) item.findViewById(R.id.admin_feedback_order_date);
            TextView order_user_id = (TextView) item.findViewById(R.id.admin_feedback_order_user_id);
            TextView order_id= (TextView) item.findViewById(R.id.admin_feedback_order_id);
            TextView order_feedback= (TextView) item.findViewById(R.id.admin_feedback_order_feedback);
            RatingBar order_rate= (RatingBar) item.findViewById(R.id.admin_frrdback_order_rate);

            order_date.setText(orders.get(i).getDate());
            order_user_id.setText(orders.get(i).getUser_id()+"");
            order_id.setText(orders.get(i).getOrder_id()+"");
            order_feedback.setText(orders.get(i).getFeedback());
            order_rate.setRating((float) orders.get(i).getRate());
            return item;
        }
    }

}