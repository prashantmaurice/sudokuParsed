package com.maurice.app.sudokuapp.ImageParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.maurice.app.sudokuapp.R;

import java.util.ArrayList;

/**
 * Created by maurice on 26/10/15.
 */
public class TrainSet {
    static TrainSet instance;
    public ArrayList<TrainDataAnswer> trainDataArr = new ArrayList<>();
    public ArrayList<TrainDataAnswer> sampleProblemArr = new ArrayList<>();

    private TrainSet(Context context){

        //train set 1
//        TrainDataAnswer train1 = new TrainDataAnswer();
//        train1.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.train_1);
//        train1.data[0] = new int[]{2,9,5,7,4,3,8,6,1};
//        train1.data[1] = new int[]{4,3,1,8,6,5,9,2,7};
//        train1.data[2] = new int[]{8,7,6,1,9,2,5,4,3};
//        train1.data[3] = new int[]{3,8,7,4,5,9,2,1,6};
//        train1.data[4] = new int[]{6,1,2,3,8,7,4,9,5};
//        train1.data[5] = new int[]{5,4,9,2,1,6,7,3,8};
//        train1.data[6] = new int[]{7,6,3,5,3,4,1,8,9};
        //
//        train1.data[7] = new int[]{9,2,8,6,7,1,3,5,4};
//        train1.data[8] = new int[]{1,5,4,9,3,8,6,7,2};
//        trainDataArr.add(train1);

        //train set 3
        TrainDataAnswer train3 = new TrainDataAnswer();
        train3.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.train_3);
        train3.data[0] = new int[]{2,9,6,3,1,8,5,7,4};
        train3.data[1] = new int[]{5,8,4,9,7,2,6,1,3};
        train3.data[2] = new int[]{7,1,3,6,4,5,2,8,9};
        train3.data[3] = new int[]{6,2,5,8,9,7,3,4,1};
        train3.data[4] = new int[]{9,3,1,4,2,6,8,5,7};
        train3.data[5] = new int[]{4,7,8,5,3,1,9,2,6};
        train3.data[6] = new int[]{1,6,7,2,5,3,4,9,8};
        train3.data[7] = new int[]{8,5,9,7,6,4,1,3,2};
        train3.data[8] = new int[]{3,4,2,1,8,9,7,6,5};
        trainDataArr.add(train3);

        //train set 4
//        TrainDataAnswer train4 = new TrainDataAnswer();
//        train4.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.train_4);
//        train4.data[0] = new int[]{2,9,6,3,1,8,5,7,4};
//        train4.data[1] = new int[]{5,8,4,9,7,2,6,1,3};
//        train4.data[2] = new int[]{7,1,3,6,4,5,2,8,9};
//        train4.data[3] = new int[]{6,2,5,8,9,7,3,4,1};
//        train4.data[4] = new int[]{9,3,1,4,2,6,8,5,7};
//        train4.data[5] = new int[]{4,7,8,5,3,1,9,2,6};
//        train4.data[6] = new int[]{1,6,7,2,5,3,4,9,8};
//        train4.data[7] = new int[]{8,5,9,7,6,4,1,3,2};
//        train4.data[8] = new int[]{3,4,2,1,8,9,7,6,5};
//        trainDataArr.add(train4);


        //Sample Problem
        TrainDataAnswer prob1 = new TrainDataAnswer();
        prob1.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.train_3);
        prob1.data[0] = new int[]{0, 0, 6, 0, 0, 8, 5, 0, 0};
        prob1.data[1] = new int[]{0, 0, 0, 0, 7, 0, 6, 1, 3};
        prob1.data[2] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 9};
        prob1.data[3] = new int[]{0, 0, 0, 0, 9, 0, 0, 0, 1};
        prob1.data[4] = new int[]{0, 0, 1, 0, 0, 0, 8, 0, 0};
        prob1.data[5] = new int[]{4, 0, 0, 5, 3, 0, 0, 0, 0};
        prob1.data[6] = new int[]{1, 0, 7, 0, 5, 3, 0, 0, 0};
        prob1.data[7] = new int[]{0, 5, 0, 0, 6, 4, 0, 0, 0};
        prob1.data[8] = new int[]{3, 0, 0, 1, 0, 0, 0, 6, 0};
        sampleProblemArr.add(prob1);

        //Sample Problem
        TrainDataAnswer prob2 = new TrainDataAnswer();
        prob2.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.train_3);
        prob2.data[0] = new int[]{2,9,6,3,1,8,5,7,4};
        prob2.data[1] = new int[]{5,8,0,9,7,2,6,1,3};
        prob2.data[2] = new int[]{7,1,3,6,4,5,2,8,9};
        prob2.data[3] = new int[]{6,2,5,8,9,7,3,4,1};
        prob2.data[4] = new int[]{9,3,1,4,2,6,8,5,7};
        prob2.data[5] = new int[]{4,7,8,5,3,1,9,2,6};
        prob2.data[6] = new int[]{1,6,7,2,5,3,4,9,8};
        prob2.data[7] = new int[]{8,5,9,7,6,4,1,3,2};
        prob2.data[8] = new int[]{3,4,2,1,8,9,7,6,5};
        sampleProblemArr.add(prob2);


    }

    public class TrainDataAnswer{
        public int[][] data = new int[9][9];
        public Bitmap bitmap;
    }


    public static TrainSet getInstance(Context context){
        if(instance==null) instance = new TrainSet(context);
        return instance;
    }




}
