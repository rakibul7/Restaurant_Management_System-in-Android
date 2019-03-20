package com.example.mahbuburrahman.resturantmanagement.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.database.DataSource;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.singletones.ImageFactory;
import com.example.mahbuburrahman.resturantmanagement.utils.Imageutils;

import java.io.File;

public class UpdateActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener {

    private Product mProduct;
    private EditText nameET;
    private EditText priceET;
    private ImageView itemImg;
    private Button upDateBtn;
    private Spinner mCatSpinner;
    private Imageutils mImageutils;
    private String file_name = null;
    private String path = null;
    private DataSource mDataSource;
    public static final String PROD_ID = "prod_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Attach the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update product");

        mImageutils = new Imageutils(this);
        mDataSource = new DataSource(this);

        nameET = findViewById(R.id.item_name_et);
        priceET = findViewById(R.id.item_price_et);
        itemImg = findViewById(R.id.item_image);
        upDateBtn = findViewById(R.id.update_btn);
        mCatSpinner = findViewById(R.id.cat_spinner);


        int id = getIntent().getExtras().getInt(PROD_ID, -1);
        mProduct = mDataSource.getProductById(id, null);


        if (mProduct != null) {
            nameET.setText(mProduct.getItemName());
            priceET.setText(mProduct.getItemPrice()+"");
            ///mCatSpinner.setSelection(arrayAdapter.getPosition("Category 2"));

            String[] categoris = this.getResources().getStringArray(R.array.categories);
            for(int i = 0; i < categoris.length ; i++) {
                if (mProduct.getCategory().equals(categoris[i])){
                    mCatSpinner.setSelection(i);
                    Log.d("update", "onCreate: category match "+categoris[i]);
                }

            }

            if (mProduct.getItemImage() != null && mProduct.getItemImagePath() != null) {
                Bitmap bitmap = ImageFactory.getInstance().getImageBitmap(mProduct.getId());
                if (bitmap == null) {
                    mImageutils.getImage(mProduct.getItemImage(), mProduct.getItemImagePath());
                }
                itemImg.setImageBitmap(bitmap);
            }

            itemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mImageutils.imagepicker(1);
                }
            });
        }



        upDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(nameET.getText().toString()) &&
                        !TextUtils.isEmpty(priceET.getText().toString())){

                    if (mCatSpinner.getSelectedItemId() > 0) {
                        mProduct.setCategory(mCatSpinner.getSelectedItem().toString());
                    }

                    mProduct.setItemName(nameET.getText().toString());
                    mProduct.setItemPrice(Double.parseDouble(priceET.getText().toString()));

                   if ( mDataSource.updateProduct(mProduct)){
                       Toast.makeText(UpdateActivity.this,"Product has been updated!" ,Toast.LENGTH_SHORT).show();
                       Intent adminIntent = new Intent(UpdateActivity.this, AdminActivity.class);
                       startActivity(adminIntent);
                       finish();
                   }else{
                       Toast.makeText(UpdateActivity.this,"Product can not update!" ,Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });
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
}
