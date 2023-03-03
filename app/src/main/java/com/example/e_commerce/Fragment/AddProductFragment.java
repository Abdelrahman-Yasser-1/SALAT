package com.example.e_commerce.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {

    EditText txt_image, txt_name, txt_price, txt_quantity;
    Button btn_show, btn_add;
    Spinner spinner_categoty;
    ImageView iv_product_image;
    ArrayList<Category> categories= new ArrayList<Category>();

    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_product, container, false);

        spinner_categoty = v.findViewById(R.id.add_product_spinner_category);
        txt_name = v.findViewById(R.id.add_product_txt_name);
        txt_image = v.findViewById(R.id.add_product_txt_image);
        txt_price = v.findViewById(R.id.add_product_txt_price);
        txt_quantity = v.findViewById(R.id.add_product_txt_quantity);
        iv_product_image = v.findViewById(R.id.add_product_iv_image);
        btn_show = v.findViewById(R.id.add_product_btn_show_image);
        btn_add = v.findViewById(R.id.add_product_btn_add);

        Database db = new Database(getContext());

        categories = db.get_categories();
        ArrayList<String> category_name = new ArrayList<String>();
        for(int i = 0 ; i < categories.size() ; i++){
            category_name.add(categories.get(i).getName());
        }

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,category_name);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categoty.setAdapter(aa);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt_image.getText().toString().isEmpty()) {
                    String url = txt_image.getText().toString();
                    Glide.with(getContext()).load(url).into(iv_product_image);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add product
                String image = txt_image.getText().toString();
                String name = txt_name.getText().toString();
                double price = -1;
                price = Double.parseDouble(txt_price.getText().toString());
                int quantiry = -1;
                quantiry = Integer.parseInt(txt_quantity.getText().toString());
                String category = spinner_categoty.getSelectedItem().toString();
                int cat_id = 0;
                if(!image.isEmpty() && !name.isEmpty() && price!=-1 && quantiry!=-1){
                    for(int i = 0 ; i < categories.size() ; i++){
                        if(categories.get(i).getName() == category){
                            cat_id = categories.get(i).getId();
                            break;
                        }
                    }
                    Product product = new Product();
                    product.setName(name);
                    product.setPrice(price);
                    product.setQuantity(quantiry);
                    product.setImage(image);
                    product.setCat_id(cat_id);

                    db.add_product(product);

                    txt_image.setText("");
                    txt_name.setText("");
                    txt_price.setText("");
                    txt_quantity.setText("");
                    iv_product_image.setImageResource(R.drawable.image_placeholder);

                }else{
                    Toast.makeText(getContext(), "Fill all data first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}