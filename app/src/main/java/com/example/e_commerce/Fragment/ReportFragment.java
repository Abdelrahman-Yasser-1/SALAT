package com.example.e_commerce.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.OrderDetails;
import com.example.e_commerce.Activity.OrderReportActivity;
import com.example.e_commerce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {
    EditText txt_report_date;
    Calendar myCalendar = Calendar.getInstance();
    String selected_date;
    Spinner spinner_user_id;
    ListView listView;

    ArrayList<OrderDetails> orderDetails = new ArrayList<>();
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        spinner_user_id = v.findViewById(R.id.spinner_user_id);

        Database db = new Database(getContext());
        ArrayList<String> users_ids = new ArrayList<>();
        users_ids = db.getUsersIds();
        users_ids.add(0, "All");
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,users_ids);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_user_id.setAdapter(aa);

        txt_report_date = v.findViewById(R.id.txt_report_date);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                selected_date = dateFormat.format(myCalendar.getTime()).toString();
                txt_report_date.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        txt_report_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        listView = v.findViewById(R.id.report_list);

        txt_report_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String date = txt_report_date.getText().toString();
                String selected_user = spinner_user_id.getSelectedItem().toString();
                Database db = new Database(getContext());

                if(selected_user.equalsIgnoreCase("all")){
                    orderDetails = db.get_report(date);
                    listView = v.findViewById(R.id.report_list);

                    ReportFragment.ReportAdapter reportAdapter = new ReportFragment.ReportAdapter(orderDetails);
                    listView.setAdapter(reportAdapter);

                }else{
                    int user_id = Integer.parseInt(selected_user);
                    orderDetails = db.get_report(date, user_id);
                    listView = v.findViewById(R.id.report_list);

                    ReportFragment.ReportAdapter reportAdapter = new ReportFragment.ReportAdapter(orderDetails);
                    listView.setAdapter(reportAdapter);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getBaseContext(), OrderReportActivity.class);
                intent = new Intent(getActivity(), OrderReportActivity.class);

                intent.putExtra("id",orderDetails.get(i).getOrder_id());
                getActivity().startActivity(intent);
            }
        });

        return v;
    }

    class ReportAdapter extends BaseAdapter {

        ArrayList<OrderDetails> orderDetails = new ArrayList<>();

        public ReportAdapter(ArrayList<OrderDetails> orderDetails) {
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
            return orderDetails.get(i).getOrder_id();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.listrow_group_item, null);
            TextView order_id = (TextView) item.findViewById(R.id.textView1);
            order_id.setText(orderDetails.get(i).getOrder_id() + "");
            return item;
        }
    }

}