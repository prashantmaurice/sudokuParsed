package com.maurice.app.sudokuapp;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.maurice.app.sudokuapp.ImageParser.ImageParser;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity{

//    static{ System.loadLibrary("opencv_java"); }
    static {
        if (!OpenCVLoader.initDebug()) {
//            // Handle initialization error
        }
    }

    ImageView imageView;
    ImageParser imageparser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(OpenCVLoader.initDebug()){

            imageView = (ImageView) findViewById(R.id.imageView);
            imageparser = new ImageParser(this);
            imageView.setBackground(new BitmapDrawable(imageparser.serveImage()));
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
