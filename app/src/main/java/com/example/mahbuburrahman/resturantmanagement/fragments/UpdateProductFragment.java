package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.AdminActivity;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.utils.Imageutils;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProductFragment extends Fragment implements Imageutils.ImageAttachmentListener{

    private Context mContext;
    private Product mProduct;
    private EditText nameET;
    private EditText priceET;
    private ImageView itemImg;
    private Button upDateBtn;
    private Imageutils mImageutils;
    private String file_name = null;
    private String path = null;

    public UpdateProductFragment() {
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
        View view = inflater.inflate(R.layout.fragment_update_product, container, false);
        mImageutils =new Imageutils(getActivity(),this,true);
        nameET = view.findViewById(R.id.item_name_et);
        priceET = view.findViewById(R.id.item_price_et);
        itemImg = view.findViewById(R.id.item_image);
        upDateBtn = view.findViewById(R.id.update_btn);

        if (mProduct != null) {
            nameET.setText(mProduct.getItemName());
            priceET.setText(mProduct.getItemPrice()+"");
        }

        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageutils.imagepicker(1);
            }
        });

        upDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(nameET.getText().toString()) &&
                        !TextUtils.isEmpty(priceET.getText().toString())){
                    mProduct.setItemName(nameET.getText().toString());
                    mProduct.setItemPrice(Double.parseDouble(priceET.getText().toString()));
                    AdminActivity.mDataSource.updateProduct(mProduct);
                }
            }
        });


        return view;
    }
    public void setData(Product product) {
        mProduct = product;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Fragment", "onRequestPermissionsResult: "+requestCode);
        mImageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        Bitmap bitmap = file;
        file_name = filename;
        path =  Environment.getExternalStorageDirectory() + File.separator + "ImageProduct" + File.separator;
        itemImg.setImageBitmap(file);

        mProduct.setItemImage(filename);
        mProduct.setItemImagePath(path);

        mImageutils.createImage(file,filename,path,false);

        Log.d("upload", "image_attachment: "+file.toString()+" filepath: "+path+ " fileName: "+file_name);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("Fragment", "onActivityResult: ");
        mImageutils.onActivityResult(requestCode, resultCode, data);

    }


}
