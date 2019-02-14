package com.app.adprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.adprogressbarlib.AdCircleProgress;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    AdCircleProgress adCircleProgress;
    AdCircleProgress adCircleProgress2;
    AdCircleProgress adCircleProgress3;
    AdCircleProgress adCircleProgress4;
    AdCircleProgress adCircleProgress5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adCircleProgress = findViewById(R.id.pgb_progress);
        adCircleProgress2 = findViewById(R.id.pgb_progress2);
        adCircleProgress3 = findViewById(R.id.pgb_progress3);
        adCircleProgress4 = findViewById(R.id.pgb_progress4);
        adCircleProgress5 = findViewById(R.id.pgb_progress5);


        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {


                        adCircleProgress.setAdProgress(i);
                        adCircleProgress2.setAdProgress(i);
                        adCircleProgress3.setAdProgress(i);
                        adCircleProgress4.setAdProgress(i);
                        adCircleProgress5.setAdProgress(i);
                        i++;
                    }
                });
            }
        }, 0, 50);

        adCircleProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
