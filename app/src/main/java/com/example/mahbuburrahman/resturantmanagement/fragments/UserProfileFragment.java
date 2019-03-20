package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.MainActivity;
import com.example.mahbuburrahman.resturantmanagement.model.User;
import com.example.mahbuburrahman.resturantmanagement.utils.Imageutils;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment implements Imageutils.ImageAttachmentListener{

    ImageView mImage;
    TextView mUserNameTV;
    TextView mMailTv;
    TextView mPhoneTv;
    TextView mFavContTv;
    TextView mRatedCountTv;
    Imageutils mImageutils;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mImageutils =new Imageutils(getActivity(),this,true);

       mImage = view.findViewById(R.id.profile_img);
       mUserNameTV = view.findViewById(R.id.profile_name_tv);
       mMailTv = view.findViewById(R.id.profile_mail_tv);
       mPhoneTv = view.findViewById(R.id.profile_phone_tv);
       mFavContTv = view.findViewById(R.id.profile_fav_tv);
       mRatedCountTv = view.findViewById(R.id.profile_rat_tv);

       User user = MainActivity.user;
       if (user != null) {
           mUserNameTV.setText(user.getUserName());
           mMailTv.setText(user.getUserMail());
           mPhoneTv.setText("Phone: "+user.getUserPhone());
           mFavContTv.setText("Favorite: "+user.getFevCount());
           mRatedCountTv.setText("Reviewed: "+user.getRevCount());

           if (user.getUserImage() != null && user.getUserImagePath() != null) {
               Bitmap bitmap = getImage(user.getUserImage(), user.getUserImagePath());
               mImage.setImageBitmap(bitmap);
               Log.d("upload", "onCreateView: "+user.getUserImage());
           } else {
               mImage.setImageResource(R.drawable.avatar);
               Log.d("upload", "onCreateView: image null "+user.getUserImage()+" "+user.getUserImagePath());
           }
       }

       mImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mImageutils.imagepicker(1);
           }
       });

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Fragment", "onRequestPermissionsResult: "+requestCode);
        mImageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        Bitmap bitmap = file;
        String file_name = filename;
        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageUsers" + File.separator;
        mImage.setImageBitmap(file);

        mImageutils.createImage(file,filename,path,false);

        MainActivity.user.setUserImage(file_name);
        MainActivity.user.setUserImagePath(path);

        if (MainActivity.mDataSource.updateUser(MainActivity.user)){
            Log.d("upload", "image uploaded: "+MainActivity.user.toString());
            int id = MainActivity.user.getId();
            MainActivity.user = MainActivity
                    .mDataSource.getUserById(id, null);
            Log.d("upload", "user reload: "+MainActivity.user.toString());
        }


        Log.d("upload", "image_attachment: "+file.toString()+" filepath: "+path+ " fileName: "+file_name);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("Fragment", "onActivityResult: ");
        mImageutils.onActivityResult(requestCode, resultCode, data);

    }

    public Bitmap getImage(String file_name, String file_path)    {

        File path = new File(file_path);
        File file = new File(path,file_name);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 2;
        options.inTempStorage = new byte[16 * 1024];

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);

        if(bitmap!=null)
            return bitmap;
        else
            return null;
    }


}
