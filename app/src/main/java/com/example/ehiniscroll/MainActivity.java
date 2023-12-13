package com.example.ehiniscroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create views
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);
        ProgressBar linkingBar = findViewById(R.id.loadingProgressBar);
        ScrollView verticalScrollView = findViewById(R.id.verticalScrollView);
        TextView invisibleTextView = findViewById(R.id.invisibleTextView);

        startButton.setOnClickListener(v -> {
            showToast("Initializing Link");
            startButton.setVisibility(View.INVISIBLE);
            linkingBar.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
            // wifi logic here

            linkingBar.setVisibility(View.INVISIBLE);
            System.out.println("Link Started");
            invisibleTextView.setVisibility(View.VISIBLE);
        });

        stopButton.setOnClickListener(v -> {
            // wifi logic here

            startButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.INVISIBLE);

            showToast("Link Stopped");
            System.out.println("Link Stopped");
        });

        verticalScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // Check if there is a vertical scroll
            if (scrollY != oldScrollY) {
                System.out.println("ScrollDetection Vertical scroll detected. ScrollY: " + scrollY);
            }

            if (scrollY >= 22000) {
                showToast("NEAR END");
                verticalScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}