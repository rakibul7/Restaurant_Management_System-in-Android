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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.database.DataSource;
import com.example.mahbuburrahman.resturantmanagement.model.Category;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.utils.Imageutils;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProductFragment extends Fragment implements Imageutils.ImageAttachmentListener, View.OnClickListener {


    private static final int GALLERY_REQUEST = 111;
    private Uri uri = null;


    private ImageView itemImage;
    private EditText itemNameET;
    private EditText itemPriceET;
    private Button uploadBTN;
    private Spinner categorySpinner;
    private EditText newCategoryET;
    private DataSource mDataSource;

    //For Image Attachment

    private Bitmap bitmapImage;
    private String file_name;
    private String path;
    Imageutils imageutils;
    private Context mContext;

    private OnUploadClickListener mListener;

    public NewProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mListener = (OnUploadClickListener) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_product, container, false);
        mDataSource = new DataSource(inflater.getContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        imageutils =new Imageutils(getActivity(),this,true);
        itemImage = view.findViewById(R.id.item_img);
        itemNameET = view.findViewById(R.id.prod_name_et);
        itemPriceET = view.findViewById(R.id.prod_price_et);
        newCategoryET = view.findViewById(R.id.prod_cat_new_et);
        uploadBTN = view.findViewById(R.id.upload_btn);
        categorySpinner = view.findViewById(R.id.category_spinner);


        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);

            }
        });
        uploadBTN.setOnClickListener(this);

        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Fragment", "onRequestPermissionsResult: "+requestCode);
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        Bitmap bitmap = file;
        file_name = filename;
        path =  Environment.getExternalStorageDirectory() + File.separator + "ImageProduct" + File.separator;
        itemImage.setImageBitmap(file);

        imageutils.createImage(file,filename,path,false);

        Log.d("upload", "image_attachment: "+file.toString()+" filepath: "+path+ " fileName: "+file_name);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("Fragment", "onActivityResult: ");
        imageutils.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View view) {
        //TODO: upload item
        String name = itemNameET.getText().toString();
        String priceST = itemPriceET.getText().toString();
        String catNew = newCategoryET.getText().toString();
        String cat = categorySpinner.getSelectedItem().toString();

        if (cat.equals("Category")){
            cat = null;
        }
        if (!TextUtils.isEmpty(catNew)) {
            cat = catNew;
            if (!mDataSource.isExistsCategory(cat)){
                mDataSource.insertCategory(new Category(cat));
                Log.d("upload", "new Category: "+cat);
            }

        }

        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(priceST)&&
                !TextUtils.isEmpty(file_name)&&
                !TextUtils.isEmpty(path)&&
                !TextUtils.isEmpty(cat) ){
            double price = Double.parseDouble(priceST);

            Product product = new Product(name, price, file_name, path, 5, cat);
            mListener.onUploadClick(product);
            Toast.makeText(mContext, "New product uploaded", Toast.LENGTH_SHORT).show();
            itemNameET.setText("");
            itemPriceET.setText("");
            newCategoryET.setText("");
            categorySpinner.setId(0);
            itemImage.setImageResource(R.drawable.add_image);

            Log.d("upload", "onClick: "+product.toString()+" Category: "+cat);


        } else {
            //TODO: show error message

        }

    }
    public interface OnUploadClickListener{
        void onUploadClick(Product product);
    }
}
