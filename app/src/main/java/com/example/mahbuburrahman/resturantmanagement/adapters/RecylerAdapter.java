package com.example.mahbuburrahman.resturantmanagement.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.holders.ProductHolder;
import com.example.mahbuburrahman.resturantmanagement.holders.UserHolder;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.model.User;
import com.example.mahbuburrahman.resturantmanagement.singletones.ImageFactory;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "adapter";
    Context mContext;
    ArrayList<Object> mItemList;
    OnFoodItemClickedListener mItemClickListener;
    OnUserLongClickListener mOnUserLongClickListener;
    OnItemLongClickedListener mOnItemLongClickedListener;

    private static final int USER_TYPE = 1;
    private static final int PROD_TYPE = 2;

    //TODO: setOnclick listener
    public void setOnUserLongClickListener(OnUserLongClickListener listener) {
        this.mOnUserLongClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickedListener longClickListener) {
        this.mOnItemLongClickedListener = longClickListener;
    }

    public RecylerAdapter(Context context, ArrayList<Object> itemList) {
        mContext = context;
        mItemList = itemList;
        mItemClickListener = (OnFoodItemClickedListener) context;
     // Log.d(TAG, "RecylerAdapter: Constructor "+itemList.size()+" "+(mItemList.size()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case USER_TYPE:
                View v1 = inflater.inflate(R.layout.all_users_list_layout, parent, false);
                holder  = new UserHolder(v1);
                break;

            case PROD_TYPE:
                //TODO: get the settings
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
                boolean grid = settings.getBoolean(mContext.getString(R.string.gird_prefs_key), false);
                int layoutID = grid ? R.layout.items_grid_layout : R.layout.items_layout;
                View v3 = inflater.inflate(layoutID, parent, false);
                holder = new ProductHolder(v3);
                break;

        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: "+holder.getItemViewType());
        Bitmap bitmap = null;
        switch (holder.getItemViewType()) {
            case USER_TYPE:
                final User user = (User) mItemList.get(position);
                UserHolder userHolder = (UserHolder) holder;
                userHolder.mUserNameTV.setText(user.getUserName());
                userHolder.mMailTv.setText(user.getUserMail());
                userHolder.mPhoneTv.setText(user.getUserPhone());
                bitmap = getImage(user.getId(), user.getUserImage(), user.getUserImagePath(), USER_TYPE);
                if (bitmap != null) {
                    userHolder.mImage.setImageBitmap(bitmap);
                }else {
                    userHolder.mImage.setImageResource(R.drawable.avatar);
                }
                userHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (mOnUserLongClickListener != null) {
                            mOnUserLongClickListener.onUserLongClicked(user);
                        }
                        return true;
                    }
                });


                break;

            case PROD_TYPE:
                final Product item = (Product) mItemList.get(position);
                ProductHolder productHolder = (ProductHolder) holder;
                productHolder.mItemName.setText(item.getItemName());
                productHolder.mItemPrice.setText(item.getItemPrice()+"Tk.");

                DecimalFormat df = new DecimalFormat("#.#");
                float onDigitsF = Float.valueOf(df.format(item.getRating()));
                Log.d("test", "onBindViewHolder: "+onDigitsF);

                productHolder.mRatingBar.setRating(onDigitsF);
                bitmap = getImage(item.getId(), item.getItemImage(), item.getItemImagePath(), PROD_TYPE);
                if (bitmap != null) {
                    productHolder.mImageView.setImageBitmap(bitmap);
                }else {
                    productHolder.mImageView.setImageResource(R.drawable.pasta_one);
                }

                productHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onItemClicked(item);
                            Log.d(TAG, "onClick: " + item.getId() + " " + position);
                        }
                    }
                });
                productHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (mOnItemLongClickedListener != null) {
                            mOnItemLongClickedListener.onItemLongClicked(item.getId());
                        }
                        return true;
                    }
                });

                break;

        }


    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    //TODO: override getItemViewType method
    @Override
    public int getItemViewType(int position) {
        if (mItemList.get(position) instanceof  User) {
            return USER_TYPE;
        } else if (mItemList.get(position) instanceof  Product) {
            return PROD_TYPE;
        }
        return 0;
    }

    public Bitmap getImage(int id, String file_name, String file_path, int type)    {
        Bitmap bitmap = null;
        if (type == PROD_TYPE) {
            bitmap = ImageFactory.getInstance().getImageBitmap(id);
        }
        if (bitmap != null) {
            return bitmap;
        }
        if (file_name == null || file_path == null) {
            return null;
        }
        File path = new File(file_path);
        File file = new File(path,file_name);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 2;
        options.inTempStorage = new byte[16 * 1024];


        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);


        return bitmap;
    }

    public void refreshDataset(ArrayList<Object> dataset) {
       this.mItemList = dataset;
    }


//TODO: onCLick interfaces
    public interface OnFoodItemClickedListener {
        //void onItemClicked(int id);
        void onItemClicked(Product item);
    }
    public interface OnItemLongClickedListener {
        //void onItemClicked(int id);
        void onItemLongClicked(int id);
    }

    public interface OnUserLongClickListener{
        void onUserLongClicked(User user);
    }


}
