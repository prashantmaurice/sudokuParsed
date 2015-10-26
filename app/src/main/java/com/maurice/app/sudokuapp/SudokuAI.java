package com.maurice.app.sudokuapp;

import android.graphics.Point;

import com.maurice.app.sudokuapp.utils.Logg;

import java.util.ArrayList;

/**
 *  This function of this module is to generate solution to sudoku puzzle in the most efficient wat
 *  The input is generally a nxn matrix and output is the solved version of it
 */

public class SudokuAI {
    static String TAG = "SUDOKU-AI" ;

    public static int[][] getSolved(int[][] question){
        int[][] result = new int[9][9];

        isSolvable(question);

        return question;
    }

    public static boolean isSolvable(int[][] result) {
        String text = "";
        for(int i=1;i<result.length;i++){
            for(int j=1;j<result[0].length;j++){
                if(result[i][j]!=0) text +=result[i][j];
            }
        }
        Logg.d(TAG,"STATUS : "+text);

        //Find row, col of an unassigned cell
        Point cell = getUnassignedCell(result);

        //If there is none, return true
        if(cell==null){
            if(!hasConflict(result)){
                Logg.d(TAG,"COMPLETELY SOLVED");
                return true;
            }
            return false;
        }
        //For digits from 1 to 9
        for(int i=1;i<10;i++){

            //If there is no conflict for digit at row,col assign digit to row,col and recursively try fill in rest of grid
            result[cell.x][cell.y] = i;
            if(!hasConflict(result)){
                if(isSolvable(result)){
                    //If recursion successful, return true
                    //SOLVED
                    Logg.d(TAG,"SOLVED");
                    return true;
                }
            }else{
            //Else, remove digit and try another
                result[cell.x][cell.y] = 0;
            }
        }

        //If all digits have been tried and nothing worked, return false
        return false;




    }

    private static boolean hasConflict(int[][] result) {
        //check rows
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            temp.clear();
            for (int j = 0; j < result[0].length; j++) {
                if(result[i][j]!=0){
                    if(temp.contains(result[i][j])) return true;
                    temp.add(result[i][j]);
                }

            }
        }

        //check columns
        for (int i = 0; i < result.length; i++) {
            temp.clear();
            for (int j = 0; j < result[0].length; j++) {
                if(result[j][i]!=0){
                    if(temp.contains(result[j][i])) return true;
                    temp.add(result[j][i]);
                }

            }
        }

//        //check grid
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                ArrayList<Integer> temp3 = new ArrayList<>();
//                for (int m = 3*i; m < 3*i+3; m++) {
//                    for (int n = 3*j; n < 3*j+3; n++) {
//                        if(result[m][n]!=0){
//                            if(temp3.contains(result[m][n])) return true;
//                            temp3.add(result[m][n]);
//                        }
//
//                    }
//
//                }
//            }
//
//        }

        return false;
    }


    public static Point getUnassignedCell(int[][] result){
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if(result[i][j]==0) return new Point(i,j);
            }
        }
        return null;
    }

}
