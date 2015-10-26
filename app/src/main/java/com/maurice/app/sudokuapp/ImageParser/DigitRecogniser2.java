package com.maurice.app.sudokuapp.ImageParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.maurice.app.sudokuapp.utils.Logg;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maurice on 23/08/15.
 */
public class DigitRecogniser2 {
    String TAG = "DIGITRECOGNISER2" ;
    static DigitRecogniser2 instance;

    Context mContext;
    ArrayList<Bitmap> bitmapArray = new ArrayList<>();
    ArrayList<Mat> mapArrayNormal = new ArrayList<>();
    ArrayList<Mat> mapArrayInvert = new ArrayList<>();

    public HashMap<Integer,Mat> finalMap = new HashMap<>();
    public TrainSet trainSet;

    private DigitRecogniser2(Context context){
        mContext = context;
        trainSet = new TrainSet(mContext);

        //setupTrainData
        setupTrainData();



//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.train_1));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.one));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.two));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.three));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.four));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.five));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.six));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.seven));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.eight));
//        bitmapArray.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.nine));

//        for(Bitmap bitmap : bitmapArray){
//
//            Mat src = GenUtils.convertBitmapToMat(bitmap);
//            Mat grey = src.clone();
//            Imgproc.cvtColor(src, grey, Imgproc.COLOR_RGB2GRAY);
//
//            Mat inverted = wrapPerspectiveCustom(grey,new Rectangle(grey.width(),grey.height()));
//            Mat normal= new Mat(inverted.rows(),inverted.cols(), inverted.type(), new Scalar(1,1,1));
//            Core.subtract(normal, inverted, normal);
//            mapArrayNormal.add(normal);
//            mapArrayInvert.add(inverted);
//        }
    }

    public static DigitRecogniser2 getInstance(Context context){
        if(instance==null) instance = new DigitRecogniser2(context);
        return instance;
    }

    private void setupTrainData() {
        Logg.d(TAG, "Setting Up training data");
        ImageParser imageParser = ImageParser.getInstance(mContext);
        for(TrainSet.TrainDataAnswer trainer : trainSet.trainDataArr){
            Mat mat = GenUtils.convertBitmapToMat(trainer.bitmap);
            Mat[][] croppedMats = imageParser.getCroppedMats(mat);
            HashMap<Integer, Integer> countMap = new HashMap<>();
            for(int i=0;i<croppedMats.length;i++){
                for(int j=0;j<croppedMats[0].length;j++){
                    int number = trainer.data[i][j];
                    if(finalMap.containsKey(number)){
                        float newPercent =  (float)1/(countMap.get(number)+1);
                        Mat before = finalMap.get(number);
                        Core.addWeighted(before, 1-newPercent, croppedMats[i][j],newPercent,0,before);
                        countMap.put(number, countMap.get(number) + 1);
                        Logg.d("DEBUGpp","i="+i+" j="+j+" num="+number);
                    }else{
                        countMap.put(number,1);
                        finalMap.put(number,croppedMats[i][j].clone());
                        Logg.d("DEBUGpp", "i="+i + " j=" + j + " num=" + number);
                    }
                }
            }
        }

    }


    public int recogniseDigit(Mat mat){
        if(mat==null) return 0;
        int probableDigit = 0;
        int highestMatch = 0;


        long startTime;
        for(int i=1;i<10;i++) {
            Log.d(TAG, "SIZE " + mat.width());
            Log.d(TAG, "SIZE " + mat.width());
            startTime = System.currentTimeMillis();
            Mat positive = new Mat(mat.size(), CvType.CV_8UC1);
            Mat negative = positive.clone();
            Mat matLearned = finalMap.get(i);
            Log.d(TAG, "REACH0 " + (System.currentTimeMillis() - startTime) + " ms");
            Core.multiply(matLearned, mat, positive);
            Core.subtract(mat,matLearned, negative);
            Log.d(TAG, "REACH1 " + (System.currentTimeMillis() - startTime) + " ms");
            int sumPos = 0;
            for (int x = 0; x <= positive.rows(); x++) {
                for (int y = 0; y <= positive.cols(); y++) {
                    double pos[] = positive.get(y, x);
                    if (pos != null) {
                        sumPos += pos[0];
                    }

                }
            }
            Log.d(TAG, "REACH2 " + (System.currentTimeMillis() - startTime) + " ms");
            int sumNeg = 0;
            for (int x = 0; x <= negative.rows(); x++) {
                for (int y = 0; y <= negative.cols(); y++) {
                    double pos[] = negative.get(y, x);
                    if (pos != null) {
                        sumNeg += pos[0];
                    }
                }
            }
            Log.d(TAG, "REACH3 " + (System.currentTimeMillis() - startTime) + " ms");
            Logg.d("MATCH POS", "" + i + " : " + sumPos + " = " + sumNeg);
            Logg.d("MATCH NEG", "" + i + " : " + (sumPos - sumNeg));
            if (highestMatch < (sumPos - 2*sumNeg)) {
                highestMatch = (sumPos - 2*sumNeg);
                probableDigit = i;
            }
            Log.d(TAG, "REACH4 " + (System.currentTimeMillis() - startTime) + " ms");
        }
        Log.d(TAG, "RECOGNISED " +probableDigit+",");
        return probableDigit;

    }


    public int[][] recogniseDigits(Mat[][] numbersCrop) {
        int[][] digits = new int[9][9];
        for(int i=0;i<numbersCrop.length;i++){
            for(int j=0;j<numbersCrop[0].length;j++){
                digits[i][j] = recogniseDigit(numbersCrop[i][j]);
            }
        }

        GenUtils.printBoard(digits);
        return digits;
    }



}
