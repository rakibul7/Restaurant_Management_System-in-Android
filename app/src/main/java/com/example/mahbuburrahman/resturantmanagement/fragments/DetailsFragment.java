package com.example.mahbuburrahman.resturantmanagement.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahbuburrahman.resturantmanagement.R;
import com.example.mahbuburrahman.resturantmanagement.activities.DetailActivity;
import com.example.mahbuburrahman.resturantmanagement.activities.LoginActivity;
import com.example.mahbuburrahman.resturantmanagement.activities.MainActivity;
import com.example.mahbuburrahman.resturantmanagement.model.Favorite;
import com.example.mahbuburrahman.resturantmanagement.model.Product;
import com.example.mahbuburrahman.resturantmanagement.model.Review;
import com.example.mahbuburrahman.resturantmanagement.model.User;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    private static final String TAG = "detail";
    private float rating = -1.0f;
    private Product mProduct;
    private Bitmap mBitmap;

    TextView itemNameTv;
    TextView itemPrice;
    TextView itemRating;
    TextView itemRatedCont;
    TextView itemReviewCont;
    ListView listView;
    ImageButton favBtn;
    boolean favClciked = false;
    ArrayList<Review> mReviews = null;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        itemNameTv = view.findViewById(R.id.item_name_tv);
        itemPrice = view.findViewById(R.id.item_price);
        itemRating = view.findViewById(R.id.rating_tv);
        itemRatedCont = view.findViewById(R.id.rated_people_tv);
        itemReviewCont = view.findViewById(R.id.review_rate_tv);
        favBtn = view.findViewById(R.id.favBtn);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.user != null && !favClciked && mProduct != null) {
                    favBtn.setImageResource(R.drawable.heart_green_big);
                    Favorite favorite = new Favorite(MainActivity.user.getId(),mProduct.getId());
                    if (DetailActivity.mDataSource.insertFavorite(favorite)){
                        Toast.makeText(getContext(), "Added to favorite!", Toast.LENGTH_SHORT).show();
                        favClciked = true;
                    }
                }
                else if (MainActivity.user != null && favClciked && mProduct != null) {
                    DetailActivity.mDataSource.removFavorite(MainActivity.user.getId(), mProduct.getId());
                    favBtn.setImageResource(R.drawable.heart_big);
                }else {
                    Toast.makeText(getContext(), "Sign in first to add favorite.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        listView = view.findViewById(R.id.review_listView);


        final RatingBar minimumRating = view.findViewById(R.id.ratingbar);
        minimumRating.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View view, MotionEvent event)
            {
                if (!MainActivity.isLoggedIn) {
                    Intent logIntenet = new Intent(getContext(), LoginActivity.class);
                    startActivity(logIntenet);
                }else {

                    float touchPositionX = event.getX();
                    float width = minimumRating.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int) starsf + 1;
                    minimumRating.setRating(stars);
                    Log.d(TAG, "onTouch: " + stars);
                    rating = stars;
                    if (mProduct != null) {
                        if (mProduct.getNumOfPeopleRated() == 0){
                            mProduct.setRating(0);
                        }
                        mProduct.setNumOfPeopleRated((mProduct.getNumOfPeopleRated() + 1));
                        double rat = (float) (((mProduct.getNumOfPeopleRated() * mProduct.getRating())  + stars) /mProduct.getNumOfPeopleRated());
                        if (rat > 5) {
                            rat = (rat * 5.0f) % 5;
                        }


                        Log.d(TAG, "onTouch: "+rat);
                        mProduct.setRating(rat);
                        DetailActivity.mDataSource.updateProduct(mProduct);
                    }

                }
                return true;
            }
        });


        if (mProduct != null) {
            itemNameTv.setText(mProduct.getItemName());
            itemPrice.setText(mProduct.getItemPrice()+"Tk.");
            itemRatedCont.setText(mProduct.getNumOfPeopleRated()+" people rated.");
            itemReviewCont.setText(mProduct.getGetNumOfPeopleReview()+" people reviewed.");
            DecimalFormat df = new DecimalFormat("#.#");
            float onDigitsF = Float.valueOf(df.format(mProduct.getRating()));
            itemRating.setText(onDigitsF+"");



            if (MainActivity.user !=null && DetailActivity.mDataSource.isFavorite(MainActivity.user.getId(), mProduct.getId())){
                favBtn.setImageResource(R.drawable.heart_green_big);
                MainActivity.user.setFevCount(MainActivity.user.getFevCount()+1);
                DetailActivity.mDataSource.updateUser(MainActivity.user);
            }

            //TODO: review list
            mReviews = DetailActivity.mDataSource.getReviewById(mProduct.getId());

        }

        if (mReviews != null ) {
            ArrayList<String> revs = new ArrayList<>();

            for (Review review : mReviews) {
                User user = DetailActivity.mDataSource.getUserById(review.getUser_id(), null);
                if (user != null) {
                    String commnt = review.getComment();
                    String uname = user.getUserName();
                    revs.add(uname + "\n" + commnt);
                }

            }
            Log.d("review", "onCreateView: "+revs.toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,revs);

            listView.setAdapter(adapter);
            Log.d("review", "onCreateView: "+revs.size());
        }else {
            Log.d("review", "onCreate: null");
        }




        return view;
    }
    public void setItemId(int id) {
        mProduct = DetailActivity.mDataSource.getProductById(id, null) ;
    }


}
