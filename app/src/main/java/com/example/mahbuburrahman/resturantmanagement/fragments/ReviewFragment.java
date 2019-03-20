package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    public static final String TAG = "detail";
    TextView itemNameTV;
    EditText reviewEdit;
    Button sendBtn;
    Context mContext;

    String itemName;
    boolean order;

    onReviewCompleteListener mListener;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
        mListener = (onReviewCompleteListener) context;
    }

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        reviewEdit = view.findViewById(R.id.review_edit);
        sendBtn = view.findViewById(R.id.order_send_btn);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(reviewEdit.getText().toString())  ){
                    if (mListener != null) {
                        mListener.afterReviewComplete(reviewEdit.getText().toString());
                    }
                    Toast.makeText(mContext, "Review has been submitted", Toast.LENGTH_SHORT).show();

                }
            }
        });


        Log.d(TAG, "onCreateView: called "+(itemNameTV == null));
        return view;
    }
    public void setArgs(String itemName) {
       this.itemName = itemName;
    }

    public interface onReviewCompleteListener {
        void afterReviewComplete(String name);
    }



}
