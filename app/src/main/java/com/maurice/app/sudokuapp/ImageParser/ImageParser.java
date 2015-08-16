package com.maurice.app.sudokuapp.ImageParser;

import android.content.Context;
import android.graphics.Bitmap;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.GaussianBlur;


/**
 * Created by maurice on 16/08/15.
 *
 *
 *
 */




public class ImageParser {
    Context mContext;
    static String TAG = "IMAGEPARSER";

    //Image Processing Params
    double lineDetect_threshold1 = 50;
    double lineDetect_threshold2 = 200;




    public ImageParser(Context context){
        mContext = context;
    }


    /** MAIN FUNCTION TO GET A CALCULATED BITMAP*/
    public Bitmap parseBitmap(Bitmap bitmap){

        //Convert image to Mat
        Mat mat = GenUtils.convertBitmapToMat(bitmap);

        //Apply Transformations
        Mat result = processMat(mat);

        //Convert back Mat to bitmap
        return GenUtils.convertMatToBitmap(result);
    }

    private Mat processMat(Mat src){

        //Pre process image
//        src = new Mat(src.size(), CvType.CV_8UC1);

        //Grey image
        Mat grey = src.clone();
        Imgproc.cvtColor(src, grey, Imgproc.COLOR_RGB2GRAY);

        //Blur the image
        Mat src2 = grey.clone();
        GaussianBlur(grey, src2, new Size(11,11), 0);


        //Create an adaptive threshold for parsing image
        Mat src3 = src2.clone();
        Imgproc.adaptiveThreshold(src2, src3, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);



        //Detect edges of image usng Canny detector
//        Mat lines = mat.clone();
//        Canny(mat, lines, lineDetect_threshold1, lineDetect_threshold2);
//
//        //Find lines in the image
//        Mat properLines = mat.clone();
//        HoughLines(lines, properLines, 1, Math.PI / 180, 100, 0, 0);



        return src3;

    }

}
