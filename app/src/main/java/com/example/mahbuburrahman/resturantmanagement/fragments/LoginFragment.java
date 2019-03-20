package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.LoginActivity;
import com.example.mahbuburrahman.resturantmanagement.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText emailText,passText;
    private Button logBtn,regBtn;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailText = view.findViewById(R.id.log_edit_email);
        passText =  view.findViewById(R.id.log_edit_pass);
        logBtn = view.findViewById(R.id.log_btn);
        regBtn = view.findViewById(R.id.register_btn);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(emailText.getText().toString())
                        && !TextUtils.isEmpty(passText.getText().toString())){

                    //TODO: admin check
                    if (emailText.getText().toString().equals("admin@gmail.com") && passText.getText().toString().equals("password")){
                        MainActivity.Admin = true;
                        LoginActivity
                                .loginManager
                                .popBackStack();
                        getActivity().finish();
                    }

                    MainActivity.user = MainActivity.mDataSource.getUserByMainAndPass(emailText.getText().toString(),passText.getText().toString() );
                    if (MainActivity.user != null) {
                        Log.d("login", "onClick: "+(MainActivity.user == null));
                        MainActivity.isLoggedIn = true;
                        LoginActivity
                                .loginManager
                                .popBackStack();
                        getActivity().finish();
                    }else {
                        Snackbar.make(view, "Email or password do not match.Try again!",Snackbar.LENGTH_INDEFINITE).setDuration(5000).show();
                    }
                }
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity
                        .loginManager
                        .beginTransaction()
                        .replace(R.id.log_container, new RegistrationFragment())
                        .commit();
            }
        });



        return view;
    }

}
