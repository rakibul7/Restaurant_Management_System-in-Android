package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.mahbuburrahman.resturantmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoresFragment extends ListFragment {


    public StoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.stores));
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
