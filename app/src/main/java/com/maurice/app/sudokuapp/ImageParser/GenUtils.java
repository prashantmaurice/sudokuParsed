package com.maurice.app.sudokuapp.ImageParser;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 * Created by maurice on 16/08/15.
 */
public class GenUtils {
    public static Mat convertBitmapToMat(Bitmap image){
        Mat mat = new Mat ( image.getHeight(), image.getWidth(), CvType.CV_8U, new Scalar(4));
        Bitmap myBitmap32 = image.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(myBitmap32, mat);
        return mat;
    }
    public static Bitmap convertMatToBitmap(Mat mat){
        Bitmap resultBitmap = Bitmap.createBitmap(mat.cols(),  mat.rows(),Bitmap.Config.ARGB_8888);;
        Utils.matToBitmap(mat, resultBitmap);
        return resultBitmap;
    }
}
