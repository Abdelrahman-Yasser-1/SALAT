package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.EditCategoryActivity;
import com.example.e_commerce.Activity.EditProductActivity;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageCategoryFragment extends Fragment {

    ListView manage_category_list;
    ArrayList<Category> categories = new ArrayList<>();

    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManageCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageCategoryFragment.
     */
    // Rename and change types and number of parameters
    public static ManageCategoryFragment newInstance(String param1, String param2) {
        ManageCategoryFragment fragment = new ManageCategoryFragment();
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
        View v = inflater.inflate(R.layout.fragment_manage_category, container, false);

        manage_category_list = v.findViewById(R.id.manage_category_list);
        // TODO: get categories from database and show it in listView
//        Database dp = new Database(getContext());
//        categories = dp.get_categories();
//        ManageCategoryFragment.AdminCategoryAdapter adminCategoryAdapter = new ManageCategoryFragment.AdminCategoryAdapter(categories);
//        manage_category_list.setAdapter(adminCategoryAdapter);
        fill_list();
        return v;
    }

    public void fill_list(){
        Database dp = new Database(getContext());
        categories = dp.get_categories();
        ManageCategoryFragment.AdminCategoryAdapter adminCategoryAdapter = new ManageCategoryFragment.AdminCategoryAdapter(categories);
        manage_category_list.setAdapter(adminCategoryAdapter);
    }

    class AdminCategoryAdapter extends BaseAdapter {

        // This adapter will used in user home and search.

        ArrayList<Category> categories = new ArrayList<>();

        public AdminCategoryAdapter(ArrayList<Category> category) {
            this.categories = category;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return categories.get(i).getName();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.admin_category_item, null);

            ImageView category_image = (ImageView) item.findViewById(R.id.admin_category_iv_image);
            TextView category_name = (TextView) item.findViewById(R.id.admin_category_tv_name);

            Button btn_edit = item.findViewById(R.id.admin_category_btn_edit);
            Button btn_del= item.findViewById(R.id.admin_category_btn_delete);


            category_name.setText(categories.get(i).getName());
            String url = categories.get(i).getImage();
            Glide.with(getContext()).load(url).into(category_image);

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getBaseContext(), EditCategoryActivity.class);
                    intent = new Intent(getActivity(), EditCategoryActivity.class);

                    intent.putExtra("id",categories.get(i).getId());
                    intent.putExtra("name",categories.get(i).getName());
                    intent.putExtra("image",categories.get(i).getImage());

                    getActivity().startActivity(intent);
                }
            });

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Database db = new Database(getContext());
                    db.delete_category(categories.get(i).getId());

                    fill_list();
                }
            });

            return item;
        }
    }

}