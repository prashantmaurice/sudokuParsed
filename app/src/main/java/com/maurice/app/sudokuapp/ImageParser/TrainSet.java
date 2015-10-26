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

    ArrayList<TrainDataAnswer> trainDataArr = new ArrayList<>();

    public TrainSet(Context context){

        //train set 1
        TrainDataAnswer train1 = new TrainDataAnswer();
        train1.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.train_1);
        train1.data[0] = new int[]{2,9,5,7,4,3,8,6,1};
        train1.data[1] = new int[]{4,3,1,8,6,5,9,2,7};
        train1.data[2] = new int[]{8,7,6,1,9,2,5,4,3};
        train1.data[3] = new int[]{3,8,7,4,5,9,2,1,6};
        train1.data[4] = new int[]{6,1,2,3,8,7,4,9,5};
        train1.data[5] = new int[]{5,4,9,2,1,6,7,3,8};
        train1.data[6] = new int[]{7,6,3,5,3,4,1,8,9};
        train1.data[7] = new int[]{9,2,8,6,7,1,3,5,4};
        train1.data[8] = new int[]{1,5,4,9,3,8,6,7,2};
        trainDataArr.add(train1);

        //train set 2
        TrainDataAnswer train2 = new TrainDataAnswer();
        train1.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.train_2);
        train1.data[0] = new int[]{2,9,5,7,4,3,8,6,1};
        train1.data[1] = new int[]{4,3,1,8,6,5,9,2,7};
        train1.data[2] = new int[]{8,7,6,1,9,2,5,4,3};
        train1.data[3] = new int[]{3,8,7,4,5,9,2,1,6};
        train1.data[4] = new int[]{6,1,2,3,8,7,4,9,5};
        train1.data[5] = new int[]{5,4,9,2,1,6,7,3,8};
        train1.data[6] = new int[]{7,6,3,5,3,4,1,8,9};
        train1.data[7] = new int[]{9,2,8,6,7,1,3,5,4};
        train1.data[8] = new int[]{1,5,4,9,3,8,6,7,2};
        trainDataArr.add(train2);

    }

    public class TrainDataAnswer{
        int[][] data = new int[9][9];
        Bitmap bitmap;
    }






}
