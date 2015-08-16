package com.maurice.app.sudokuapp.ImageParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
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
        Mat src2 = new Mat(grey.size(), CvType.CV_8UC1);
        GaussianBlur(grey, src2, new Size(11,11), 0);


        //Create an adaptive threshold for parsing image
        Mat src3 = new Mat(src2.size(), CvType.CV_8UC1);
        Imgproc.adaptiveThreshold(src2, src3, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);

        //TODO : may be do a floodfill here

        //Find lines in the image
        int threshold = 100;
        int minLineSize = 600;
        int lineGap = 60;
        Mat lines = new Mat();
        Imgproc.HoughLinesP(src3, lines, 1, Math.PI/180, threshold, minLineSize, lineGap);

        //draw color lines on the image
        Mat color = new Mat();
        Imgproc.cvtColor(src3, color, Imgproc.COLOR_GRAY2BGR);
        for (int x = 0; x < lines.rows(); x++)
        {
            double[] vec = lines.get(x,0);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            Imgproc.line(color, start, end, new Scalar(255, 0, 0), 3);
            Log.d(TAG, "Lines detected : " + start.toString() + " : " + end.toString());
        }
        Log.d(TAG, lines.rows()+" lines detected in image");


        Imgproc.line(color, new Point(0,100), new Point(900,100), new Scalar(255, 250,0), 30);
        return color;
    }




}
