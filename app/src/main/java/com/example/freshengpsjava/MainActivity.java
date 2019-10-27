package com.example.freshengpsjava;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import cdflynn.android.library.checkview.CheckView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    private int chinSize;

    public void activateCheck(View view) {
        // Do something in response to button click
        CheckView mCheckView = findViewById(R.id.check);
        mCheckView.check();
    }
    public void activateCheck_delete(View view) {
        // Do something in response to button click
        CheckView mCheckView = findViewById(R.id.check_delete);
        mCheckView.check();
    }

    public void activateCheck_acquire(View view) {
        // Do something in response to button click
        CheckView mCheckView = findViewById(R.id.check_acquire);
        mCheckView.check();
    }

    public void activateCheck_time(View view) {
        // Do something in response to button click
        CheckView mCheckView = findViewById(R.id.check_time);
        mCheckView.check();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
        Log.v("checklog", "just set ambient");


        //chris's code here JAVA
        //ui code
        // find the outermost element
        final View container = findViewById(R.id.outer_container);

        // attach a View.OnApplyWindowInsetsListener
        container.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                chinSize = insets.getSystemWindowInsetBottom();
                // The following line is important for inner elements which react to insets
                v.onApplyWindowInsets(insets);
                return insets;
            }
        });

        //this used to be onreadyforcontent
        Log.v("checklog", "Starting onreadyforcontent");
        setContentView(R.layout.activity_main);
        Log.v("checklog", "Set view.");

        //activateCheck();

        //SystemClock.sleep(10000);

        //gps code
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 1);

        Bundle extras = new Bundle();
        extras.putBoolean("all", true);

        Log.v("checklog", "Beginning refresh ofg AGPS");

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean delete_return = locationManager.sendExtraCommand(LocationManager.GPS_PROVIDER,"delete_aiding_data", extras);
        if (delete_return == true)
        {
            activateCheck_delete(findViewById(R.id.outer_container));
        }
        boolean acquire_return = locationManager.sendExtraCommand("gps", "force_xtra_injection", extras);
        if (acquire_return == true)
        {
            activateCheck_acquire(findViewById(R.id.outer_container));
        }
        boolean time_return = locationManager.sendExtraCommand("gps", "force_time_injection", extras);
        if (time_return == true)
        {
            activateCheck_time(findViewById(R.id.outer_container));
        }

        Log.v("checklog", "Finished refreshing AGPS");
        Log.v("checklog", "delete_return = " + delete_return);
        Log.v("checklog", "acquire_return = " + acquire_return);
        Log.v("checklog", "time_return = " + time_return);

        //SystemClock.sleep(1000);

        activateCheck(findViewById(R.id.outer_container));
        //SystemClock.sleep(1000);
        //System.exit(0);

        if (delete_return == true && acquire_return == true && time_return == true)
        {
            //signal status bar to stop
            //signal check mark to appear

            //change text box to say something like acquired
        }

    }



 //   protected void onReadyForContent(Bundle savedInstanceState) {

  //  }




    }


