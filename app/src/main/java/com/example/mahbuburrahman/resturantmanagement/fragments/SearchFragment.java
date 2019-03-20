package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.MainActivity;
import com.example.mahbuburrahman.resturantmanagement.adapters.RecylerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    ArrayList<Object> items;
    Context mContext;
    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView rcv = view.findViewById(R.id.items_rcv);



       /* items.add(new Generic("Spahetti Bologness",R.drawable.pasta_one));
        items.add(new Generic("Lasagne",R.drawable.pasta_two));
        items.add(new Generic("American Pizza", R.drawable.pizza_one));
        items.add(new Generic("Diavolo", R.drawable.pizza_two));
        items.add(new Generic("Lasagne",R.drawable.pasta_two));
        items.add(new Generic("American Pizza with extra cheese", R.drawable.pizza_one));

        //TODO: get the settings
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean grid = settings.getBoolean(getString(R.string.gird_prefs_key), false);

        if (grid) {
            rcv.setLayoutManager(new GridLayoutManager(mContext, 2));
        } else{
            rcv.setLayoutManager(new LinearLayoutManager(mContext));
        }*/

        rcv.setLayoutManager(new LinearLayoutManager(mContext));
        if (items == null) {
            items = MainActivity.allProducts;
        }
        RecylerAdapter recyerAdapter = new RecylerAdapter(inflater.getContext(), items);
        rcv.setAdapter(recyerAdapter);
        Log.d("search", "onCreateView: SearchFrag ");
        return view;
    }
    public void setData(ArrayList<Object> list) {
        items = list;
    }

}
