package com.maurice.app.sudokuapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.maurice.app.sudokuapp.ImageParser.DigitRecogniser2;
import com.maurice.app.sudokuapp.ImageParser.GenUtils;
import com.maurice.app.sudokuapp.ImageParser.ImageParser;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity{

//    static{ System.loadLibrary("opencv_java"); }
    static {
        if (!OpenCVLoader.initDebug()) {
//            // Handle initialization error
        }
    }

    ImageView imageView,imageView2,imageView3,imageView4;
    ImageParser imageparser;
    DigitRecogniser2 digitRecogniser2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(OpenCVLoader.initDebug()){

            imageView = (ImageView) findViewById(R.id.imageView);
            imageView2 = (ImageView) findViewById(R.id.imageView2);
            imageView3 = (ImageView) findViewById(R.id.imageView3);
            imageView4 = (ImageView) findViewById(R.id.imageView4);
            imageparser = ImageParser.getInstance(this);
            digitRecogniser2 = DigitRecogniser2.getInstance(this);


            //Source Image Bitmap
            Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample2);

            //Get rendered bitmap
            Bitmap renderedBitmap = imageparser.parseBitmap(sourceBitmap);

            //Show rendered bitmap
            imageView.setImageBitmap(renderedBitmap);
//            imageView.setImageBitmap(digitRecogniser2.trainSet.trainDataArr.get(0).bitmap);
            imageView2.setImageBitmap(GenUtils.convertMatToBitmap(digitRecogniser2.finalMap.get(5)));
            imageView3.setImageResource(R.drawable.train_3);
        }else{
            Log.e("ERROR","OpenCVLoader not initialized");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
