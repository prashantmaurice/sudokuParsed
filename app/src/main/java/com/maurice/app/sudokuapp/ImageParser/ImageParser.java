package com.maurice.app.sudokuapp.ImageParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.maurice.app.sudokuapp.ImageParser.models.LineSegment;
import com.maurice.app.sudokuapp.ImageParser.models.Rectangle;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    //board Params
    static final int BOARD_SIZE = 9;
    static int IMAGE_WIDTH = 100;
    static int IMAGE_HEIGHT = 100;



    public ImageParser(Context context){
        mContext = context;
    }


    /** MAIN FUNCTION TO GET A CALCULATED BITMAP*/
    public Bitmap parseBitmap(Bitmap bitmap){

        //Convert image to Mat
        Mat mat = GenUtils.convertBitmapToMat(bitmap);

        //Apply Transformations
        Mat result = processMat(mat);

        //TODO :add sudokuAI module
//        SudokuAI ai = new SudokuAI();

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
        GaussianBlur(grey, src2, new Size(11, 11), 0);


        //Create an adaptive threshold for parsing image
        Mat src3 = new Mat(src2.size(), CvType.CV_8UC1);
        Imgproc.adaptiveThreshold(src2, src3, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 15, 4);
        //TODO : may be do a floodfill here

        //find lines in the image
        ArrayList<LineSegment> segments = findLines(src3);

        //Filtered Line segments : filter from around 130 segements to final 20
        ArrayList<LineSegment> filteredSegments = filterValidLineSegments(segments);



        //get points array
        Point[][] points = getKeyPoints(filteredSegments);

        Mat colorPic = new Mat();
        Imgproc.cvtColor(src3, colorPic, Imgproc.COLOR_GRAY2BGR);

        //Draw Lines
        for(LineSegment lineSegment : filteredSegments){
//            Imgproc.line(color, lineSegment.point1, lineSegment.point2, new Scalar(Math.random()*255, Math.random()*255,Math.random()*255), 3);
            Imgproc.line(colorPic, lineSegment.point1, lineSegment.point2, new Scalar(255, 0,0), 3);
        }

//        Imgproc.line(color, new Point(0,100), new Point(900,100), new Scalar(255, 250,0), 30);
        return wrapPerspective(colorPic,new Rectangle(new Point(0,0),new Point(0,600),new Point(300,0),new Point(300,600)));
    }

    private ArrayList<LineSegment> findLines(Mat src){
        //Find lines in the image
        int threshold = 100;
        int minLinelength = 900;
        int maxlineGap = 120;
        Mat lines = new Mat();
        Imgproc.HoughLinesP(src, lines, 1, Math.PI/180, threshold, minLinelength, maxlineGap);

        //draw color lines on the image
        ArrayList<LineSegment> segments = new ArrayList<>();
        for (int x = 0; x < lines.rows(); x++)
        {
            double[] vec = lines.get(x,0);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);
            segments.add(new LineSegment(start, end));
        }
        Log.d(TAG, segments.size()+" lines detected in image");
        return segments;
    }

    private ArrayList<LineSegment> filterValidLineSegments(ArrayList<LineSegment> segments){
        ArrayList<LineSegment> filtered = segments;
        filtered = filterSimilarLines(filtered);
        return filtered;
    }

//    private ArrayList<Point> getBoundaryPoints(ArrayList<LineSegment> segments){
//
//    }

    private ArrayList<LineSegment> filterSimilarLines(ArrayList<LineSegment> segments ){
        //params
        double minPointDistance = 60;
        double minangleDistance = 20;

        ArrayList<LineSegment> filtered = new ArrayList<>();
        ArrayList<Integer> rejectedIndices = new ArrayList<>();

        for(int i=0;i<segments.size();i++){
            for(int j=0;j<segments.size();j++){
                if(i==j) continue;
                if(rejectedIndices.contains(i)||rejectedIndices.contains(j)) continue;
                if(Math.abs(segments.get(i).getAngle()-segments.get(j).getAngle())>minangleDistance) continue;//perpendicular lines
                double p1Dist = (new LineSegment(segments.get(i).point1,segments.get(j).point1)).getLength();
                double p2Dist = (new LineSegment(segments.get(i).point2,segments.get(j).point2)).getLength();
                if(p1Dist+p2Dist<minPointDistance) rejectedIndices.add(i);
            }
        }

        //Finally add in filtered array
        for(int i=0;i<segments.size();i++){//find highest
            if(!rejectedIndices.contains(i)) filtered.add(segments.get(i));
        }

        Log.d(TAG, "Filtered similar lines : "+filtered.size()+" segs from "+segments.size()+" segs");
        return filtered;

    }

    private Mat wrapPerspective(Mat src, Rectangle rect){
        //points are in order  top-left, top-right, bottom-right, bottom-left

        Mat src_mat=new Mat(4,1,CvType.CV_32FC2);
        Mat dst_mat=new Mat(4,1,CvType.CV_32FC2);

        Rectangle dest = new Rectangle(200);

        src_mat.put(0,0,rect.lt.x,rect.lt.y,rect.rt.x,rect.rt.y,rect.lb.x,rect.lb.y,rect.rb.x,rect.rb.y);
        dst_mat.put(0,0,dest.lt.x,dest.lt.y,dest.rt.x,dest.rt.y,dest.lb.x,dest.lb.y,dest.rb.x,dest.rb.y);
        Mat perspectiveTransform=Imgproc.getPerspectiveTransform(src_mat, dst_mat);

        Mat dst=src.clone();

        Imgproc.warpPerspective(src, dst, perspectiveTransform, new Size(Math.abs(dest.lt.x-dest.rt.x),Math.abs(dest.lt.y-dest.lb.y)));

        return dst;

    }

    private Point[][] getKeyPoints(ArrayList<LineSegment> filteredSegments){
        //Segregate vertical and horizontal segements
        ArrayList<LineSegment> vertical = new ArrayList<>();
        ArrayList<LineSegment> horizontal = new ArrayList<>();
        for(LineSegment segment : filteredSegments){
            if(Math.abs(Math.abs(segment.getAngle())-Math.PI/2)<Math.PI/4){
                vertical.add(segment);
            }else{
                horizontal.add(segment);
            }
        }

        //sort horizontal
        Collections.sort(horizontal, new Comparator<LineSegment>() {
            @Override
            public int compare(final LineSegment object1, final LineSegment object2) {
                return (int) (object1.point1.y-(object2.point1.y));
            }
        });

        //sort vertical
        Collections.sort(vertical, new Comparator<LineSegment>() {
            @Override
            public int compare(final LineSegment object1, final LineSegment object2) {
                return (int) (object1.point1.x-(object2.point1.x));
            }
        });

        //Find key points as intersecting points
        Point [][] twoDim = new Point [horizontal.size()][vertical.size()];
        for(int i = 0; i < horizontal.size(); i++){
            for(int j = 0; j <vertical.size(); j++) {
                Point intersect = GenUtils.intersectingPoint(horizontal.get(i),vertical.get(j));
                twoDim[i][j] = intersect;
            }
        }
        Log.d(TAG,"2D ARRAY : "+GenUtils.print2dArray(twoDim));
        return twoDim;
    }






}
