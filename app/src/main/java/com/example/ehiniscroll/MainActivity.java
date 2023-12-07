package com.example.ehiniscroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);
        ProgressBar linkingBar = findViewById(R.id.loadingProgressBar);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);


        startButton.setOnClickListener(v -> {
            showToast("Initializing Link");
            startButton.setVisibility(View.INVISIBLE);
            linkingBar.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);

            // wifi logic here




            linkingBar.setVisibility(View.INVISIBLE);
        });

        stopButton.setOnClickListener(v -> {
            // wifi logic here

            startButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.INVISIBLE);

            showToast("Link Stopped");
        });

        horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollX != oldScrollX) {
                    System.out.println("Im scrolling");
                }
            }
        });
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}