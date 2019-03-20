package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.AdminActivity;
import com.example.mahbuburrahman.resturantmanagement.adapters.RecylerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductsFragment extends Fragment {


    private Context mContext;
    private RecylerAdapter recyerAdapter;

    public AllProductsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_all_products, container, false);
        RecyclerView rcv = view.findViewById(R.id.all_product_rcv);

        ArrayList<Object> items = AdminActivity.mDataSource.getAllProducts();
        rcv.setLayoutManager(new LinearLayoutManager(mContext));
        recyerAdapter = new RecylerAdapter(inflater.getContext(), items);
        rcv.setAdapter(recyerAdapter);

        recyerAdapter.setOnItemLongClickListener(new RecylerAdapter.OnItemLongClickedListener() {
            @Override
            public void onItemLongClicked(final int id) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete Alert!")
                        .setMessage("Are you sure want, to delete this item ?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if( AdminActivity.mDataSource.deleteProductById(id)){
                                    Toast.makeText(mContext,"Product has been removed!",Toast.LENGTH_SHORT ).show();
                                    recyerAdapter.refreshDataset(AdminActivity.mDataSource.getAllProducts());
                                    recyerAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        Log.d("admin", "onCreateView: All product Fragment");

        return view;
    }

}
