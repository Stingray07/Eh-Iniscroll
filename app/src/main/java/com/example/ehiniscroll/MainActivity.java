package com.example.ehiniscroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;


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
        EditText ipAddressBox = findViewById(R.id.editText);
        TextView connectedTextView = findViewById(R.id.connectedTextView);

        startButton.setOnClickListener(v -> {
            String ipAddress = ipAddressBox.getText().toString();
            showToast("Initializing Link");
            setViewToConnecting(startButton, linkingBar, stopButton);
            // wifi logic here

            try {
                URI serverURI = new URI("ws://"+ ipAddress);

                Thread test_thread = new Thread( new WebSocketClient(serverURI) );
                test_thread.start();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            setViewToConnected(
                    ipAddressBox,
                    connectedTextView,
                    linkingBar,
                    invisibleTextView,
                    ipAddress);
        });

        stopButton.setOnClickListener(v -> {
            // wifi logic here

            setViewsToMain(startButton, stopButton, connectedTextView, ipAddressBox);

            showToast("Link Stopped");
            System.out.println("Link Stopped");
            verticalScrollView.fullScroll(ScrollView.FOCUS_UP);
        });

        verticalScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // Check if there is a vertical scroll
            if (scrollY != oldScrollY) {
                System.out.println("ScrollDetection Vertical scroll detected. ScrollY: " + scrollY);

            }

            if (scrollY >= 22000) {
                verticalScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setViewsToMain(Button startButton,
            Button stopButton,
            TextView connectedTextView,
            EditText ipAddressBox) {
        startButton.setVisibility(View.VISIBLE);
        ipAddressBox.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
        connectedTextView.setVisibility(View.INVISIBLE);
        connectedTextView.setText("");
    }

    private void setViewToConnecting(Button startButton,
                                     ProgressBar linkingBar,
                                     Button stopButton) {
        startButton.setVisibility(View.INVISIBLE);
        linkingBar.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.VISIBLE);
    }

    private void setViewToConnected(EditText ipAddressBox,
                                    TextView connectedTextView,
                                    ProgressBar linkingBar,
                                    TextView invisibleTextView,
                                    String ipAddress) {
        ipAddressBox.setVisibility(View.INVISIBLE);
        connectedTextView.setText(
                "Connected to " + ipAddress + "\n " +
                "You can now scroll");
        connectedTextView.setVisibility(View.VISIBLE);
        linkingBar.setVisibility(View.INVISIBLE);
        System.out.println("Link Started");
        invisibleTextView.setVisibility(View.VISIBLE);
    }
}