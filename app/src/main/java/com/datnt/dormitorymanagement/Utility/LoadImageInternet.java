package com.datnt.dormitorymanagement.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class LoadImageInternet extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public LoadImageInternet(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        String pathToFile = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(pathToFile).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ReduceBitmapSize reduceBitmapSize = new ReduceBitmapSize();
        bitmap = reduceBitmapSize.getResizedBitmap(bitmap, 500);
        bmImage.setImageBitmap(bitmap);
    }
}
