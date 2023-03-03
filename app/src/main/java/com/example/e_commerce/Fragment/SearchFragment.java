package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.ProductActivity;
import com.example.e_commerce.Barcode.CaptuerAct;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    Spinner spinner_search_type;
    ImageButton imageButton_voice, imageButton_barcode;
    EditText txt_search;
    ListView list_search;

    int voiceCode = 1;

    ArrayList<Product> products = new ArrayList<>();
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        txt_search = v.findViewById(R.id.user_search_txt_search);
        imageButton_voice = v.findViewById(R.id.user_search_btn_voice);
        imageButton_barcode = v.findViewById(R.id.user_search_btn_barcode);
        spinner_search_type =v.findViewById(R.id.user_search_spinner_search_type);
        list_search = v.findViewById(R.id.user_search_list_products);

        spinner_search_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txt_search.setText("");
                String item = adapterView.getItemAtPosition(i).toString();
                if(i == 1) {
                    imageButton_voice.setVisibility(view.VISIBLE);
                    imageButton_barcode.setVisibility(view.GONE);
                }
                else if(i == 2) {
                    imageButton_barcode.setVisibility(view.VISIBLE);
                    imageButton_voice.setVisibility(view.GONE);
                }
                else{
                    imageButton_barcode.setVisibility(view.GONE);
                    imageButton_voice.setVisibility(view.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageButton_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(i, voiceCode);
                }
        });

        imageButton_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });

        // this function to handel change in txt_sexch text
        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO: functions for search by text, voicr, or barcode
                String text = txt_search.getText().toString();
                Database dp = new Database(getContext());
                products = dp.search_product(text);
                SearchFragment.UserSearchProductAdapter userSearchProductAdapter = new SearchFragment.UserSearchProductAdapter(products);
                list_search.setAdapter(userSearchProductAdapter);

                list_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(getActivity().getBaseContext(), ProductActivity.class);
                        intent = new Intent(getActivity(), ProductActivity.class);

                        intent.putExtra("id",products.get(i).getId());
                        intent.putExtra("name",products.get(i).getName());
                        intent.putExtra("price",products.get(i).getPrice());
                        intent.putExtra("image",products.get(i).getImage());
                        getActivity().startActivity(intent);
                        Toast.makeText(getContext(), "Product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == voiceCode && resultCode == getActivity().RESULT_OK){
            ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            txt_search.setText(text.get(0));
        }
    }

    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan Barcode, Volume up to flash on.\n\n\n\n\n\n\n\n\n");
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptuerAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
       if(result.getContents() != null)
           txt_search.setText(result.getContents());
    });


    class UserSearchProductAdapter extends BaseAdapter {

        ArrayList<Product> products = new ArrayList<>();

        public UserSearchProductAdapter(ArrayList<Product> products) {
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
            Glide.with(getContext()).load(url).into(product_image);

            return item;
        }
    }

}