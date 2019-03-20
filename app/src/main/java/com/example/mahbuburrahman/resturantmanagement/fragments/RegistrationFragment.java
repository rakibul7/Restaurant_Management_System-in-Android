package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.LoginActivity;
import com.example.mahbuburrahman.resturantmanagement.database.DataSource;
import com.example.mahbuburrahman.resturantmanagement.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {


    EditText nameET;
    EditText mailET;
    EditText passET;
    EditText confPassET;
    EditText phoneET;
    Button registerBtn;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        nameET = view.findViewById(R.id.edit_name);
        mailET = view.findViewById(R.id.edit_mail);
        passET = view.findViewById(R.id.edit_pass);
        confPassET = view.findViewById(R.id.edit_pass_conf);
        phoneET = view.findViewById(R.id.edit_mobile);
        registerBtn = view.findViewById(R.id.register_conf_btn);

        registerBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        String name = nameET.getText().toString();
        String mail = mailET.getText().toString();
        String pass = passET.getText().toString();
        String confPass = confPassET.getText().toString();
        String phone = phoneET.getText().toString();

        if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(pass)
                && !TextUtils.isEmpty(confPass)
                && !TextUtils.isEmpty(mail)
                && !TextUtils.isEmpty(phone)) {
            if (pass.equals(confPass) ) {
                DataSource dataSource = new DataSource(getContext());
                dataSource.insertUser(new User(name, mail, pass, phone, null, null,0,0));
                Log.d("login", "onClick: "+mail+" "+pass);
                LoginActivity
                        .loginManager
                        .beginTransaction()
                        .replace(R.id.log_container, new LoginFragment())
                        .commit();

            } else {
                Toast.makeText(getContext(), "Password do not match!", Toast.LENGTH_SHORT).show();
            }

        } else {
            //TODO: error message
        }

    }
}
