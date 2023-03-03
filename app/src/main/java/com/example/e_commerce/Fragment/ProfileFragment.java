package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_commerce.Activity.LoginActivity;
import com.example.e_commerce.Activity.SplashScreenActivity;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView tv_username, tv_email, tv_password, tv_gender, tv_birthdate, tv_job;
    Button btn_logout;

    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = User.getInstance();

        tv_username = v.findViewById(R.id.profile_tv_username);
        tv_email = v.findViewById(R.id.profile_tv_email);
        tv_password = v.findViewById(R.id.profile_tv_paswword);
        tv_job = v.findViewById(R.id.profile_tv_job);
        tv_birthdate = v.findViewById(R.id.profile_tv_birthdate);
        tv_gender = v.findViewById(R.id.profile_tv_gender);

        // TODO: get user info from database and show it
        tv_username.setText(user.getName());
        tv_password.setText(user.getPassword());
        tv_job.setText(user.getJob());
        tv_birthdate.setText(user.getBirthdate());
        tv_gender.setText(user.getGender());
        tv_email.setText(user.getEmail());

        btn_logout = v.findViewById(R.id.profile_btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: logout user
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", 0);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.clear();
//                myEdit.putBoolean("remember_me", false);
                myEdit.commit();

                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return v;
    }
}