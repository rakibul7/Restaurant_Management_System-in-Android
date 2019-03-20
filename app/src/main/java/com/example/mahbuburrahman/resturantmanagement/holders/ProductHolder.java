package com.example.mahbuburrahman.resturantmanagement.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mahbuburrahman.resturantmanagement.R;

/**
 * Created by Mahbuburrahman on 12/15/17.
 */

public class ProductHolder extends RecyclerView.ViewHolder {

    public View mView;
    public ImageView mImageView;
    public TextView mItemName;
    public TextView mItemPrice;
    public RatingBar mRatingBar;

    public ProductHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mImageView = mView.findViewById(R.id.pizza_image_iv_gen);
        mItemName = mView.findViewById(R.id.pizza_name_tv_gen);
        mItemPrice = mView.findViewById(R.id.price_tv);
        mRatingBar = mView.findViewById(R.id.rating_bar);
    }

}
