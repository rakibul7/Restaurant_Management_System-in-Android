package com.example.mahbuburrahman.resturantmanagement.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahbuburrahman.resturantmanagement.R;

/**
 * Created by Mahbuburrahman on 12/26/17.
 */

public class UserHolder extends RecyclerView.ViewHolder {

    public ImageView mImage;
    public TextView mUserNameTV;
    public TextView mMailTv;
    public TextView mPhoneTv;
    public View view;

    public UserHolder(View itemView) {
        super(itemView);
        view = itemView;
        mImage = view.findViewById(R.id.profile_img);
        mUserNameTV = view.findViewById(R.id.profile_name_tv);
        mMailTv = view.findViewById(R.id.profile_mail_tv);
        mPhoneTv = view.findViewById(R.id.profile_phone_tv);
    }

}
