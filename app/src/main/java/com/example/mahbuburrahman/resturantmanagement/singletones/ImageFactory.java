package com.example.mahbuburrahman.resturantmanagement.singletones;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by Mahbuburrahman on 12/23/17.
 */

public class ImageFactory {
    private static final ImageFactory ourInstance = new ImageFactory();

    public static ImageFactory getInstance() {
        return ourInstance;
    }

    private ImageFactory() {
        allImages = new HashMap<>();
    }
    private HashMap<Integer, Bitmap> allImages;

    public HashMap<Integer, Bitmap> getAllImages() {
        return allImages;
    }

    public void setAllImages(HashMap<Integer, Bitmap> allImages) {
        this.allImages = allImages;
    }

    public Bitmap getImageBitmap(int id) {
        return allImages.get(id);
    }
    public void setImageBitmap(int id, Bitmap bitmap) {
        allImages.put(id, bitmap);
    }
}
