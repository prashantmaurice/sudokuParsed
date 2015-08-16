package com.maurice.app.sudokuapp.ImageParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.maurice.app.sudokuapp.R;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.IOException;


/**
 * Created by maurice on 16/08/15.
 */
public class ImageParser {
    Context mContext;
    static String TAG = "IMAGEPARSER";
    public ImageParser(Context context){
        mContext = context;
    }





    public Mat fetchImage(){
        Mat image = null;
        try {
            image = Utils.loadResource(mContext,R.drawable.sample);
            Log.d(TAG,"Loaded image sample.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    public Bitmap serveImage(){
        Mat map = fetchImage();

        Bitmap resultBitmap = Bitmap.createBitmap(map.cols(),  map.rows(),Bitmap.Config.ARGB_8888);;
        Utils.matToBitmap(map, resultBitmap);

        return resultBitmap;

    }


}
