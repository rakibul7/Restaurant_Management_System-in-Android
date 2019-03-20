package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.AdminActivity;
import com.example.mahbuburrahman.resturantmanagement.adapters.RecylerAdapter;
import com.example.mahbuburrahman.resturantmanagement.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    Context mContext;
    ArrayList<Object> allUsers = null;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        final RecyclerView rcv = view.findViewById(R.id.all_user_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));

        allUsers = AdminActivity.mDataSource.getAllUsers();
        final RecylerAdapter adapter = new RecylerAdapter(mContext, allUsers);

        rcv.setAdapter(adapter);
        adapter.setOnUserLongClickListener(new RecylerAdapter.OnUserLongClickListener() {
            @Override
            public void onUserLongClicked(final User user) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete Alert!")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if( AdminActivity.mDataSource.deleteUserById(user.getId())){
                                    Toast.makeText(mContext,"User profile has been removed!",Toast.LENGTH_SHORT ).show();
                                    adapter.refreshDataset(AdminActivity.mDataSource.getAllUsers());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });

        return view;
    }

}
