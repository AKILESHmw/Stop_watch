package com.max.stop_watch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView output;
    MaterialButton reset,start,stop;
    int sec,millisecs,min;
    long millisec,startTime,timebuff,updatetime=0L;
    Handler handler;
    private final Runnable runnable=new Runnable() {
        @Override
        public void run() {
        millisec= SystemClock.uptimeMillis()-startTime;
        updatetime=timebuff+millisec;
        sec=(int) (updatetime/1000);
        min=sec/60;
        sec=sec%60;
        millisecs=(int)(updatetime%1000);
        output.setText(MessageFormat.format("{0}:{1}:{2}",min, String.format(Locale.getDefault(), "%02d", sec),String.format(Locale.getDefault(), "%02d",millisecs)));
        handler.postDelayed(this,0);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output=findViewById(R.id.clock);
        reset=findViewById(R.id.reset);
        start=findViewById(R.id.start);
        stop=findViewById(R.id.stop);

        handler=new Handler(Looper.getMainLooper());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime=SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timebuff+=millisec;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                millisec=0L;
                timebuff=0L;
                startTime=0L;
                updatetime=0L;
                sec=0;
                min=0;
                millisecs=0;
                output.setText("00:00:00");
            }
        });

        output.setText("00:00:00");

    }
}