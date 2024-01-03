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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MainActivity extends AppCompatActivity {
    private final WebSocketClient webSocketClient;
    private final BlockingQueue<Integer> messageQueue;
    private final Thread messageSendingThread;
    private final URI dummyURI;

    public MainActivity() {
        try {
            this.dummyURI = new URI("192.168.100");
            webSocketClient = new WebSocketClient(dummyURI);
            messageQueue = new LinkedBlockingQueue<>();
            messageSendingThread = createMessageSendingThread(messageQueue, webSocketClient);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);
        ProgressBar linkingBar = findViewById(R.id.loadingProgressBar);
        ScrollView verticalScrollView = findViewById(R.id.verticalScrollView);
        TextView invisibleTextView = findViewById(R.id.invisibleTextView);
        EditText ipAddressBox = findViewById(R.id.editText);
        TextView connectedTextView = findViewById(R.id.connectedTextView);

        startButton.setOnClickListener(v -> handleStartButtonClick(
                ipAddressBox, startButton, stopButton, linkingBar,
                invisibleTextView, connectedTextView));

        stopButton.setOnClickListener(v -> handleStopButtonClick(ipAddressBox, startButton, stopButton,
                connectedTextView, verticalScrollView, linkingBar));

        verticalScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            handleScrollChange(scrollX, scrollY, oldScrollX, oldScrollY, verticalScrollView);
        });

    }

    private void handleStartButtonClick(EditText ipAddressBox, Button startButton,
                                        Button stopButton, ProgressBar linkingBar,
                                        TextView invisibleTextView, TextView connectedTextView
    ){
        String ipAddress = ipAddressBox.getText().toString();
        setViewsToConnecting(startButton, linkingBar, stopButton);

        // Try to connect to WebSocket Server
        try {
            System.out.println("IP ADDRESS: " + ipAddress);
            URI serverURI = new URI("ws://"+ ipAddress);
            webSocketClient.setUri(serverURI);
            webSocketClient.connectToServer(serverURI, new ConnectionCallback() {
                @Override
                public void onConnectSuccess() {
                    setViewsToConnected(
                            ipAddressBox,
                            connectedTextView,
                            linkingBar,
                            invisibleTextView,
                            ipAddress);
                    if (messageSendingThread.getState() == Thread.State.NEW){
                        messageSendingThread.start();
                    }
                }

                @Override
                public void onConnectError(String errorMessage) {
                    showToast("Connection Failed");
                    setViewsToMain(startButton, stopButton, connectedTextView, ipAddressBox, linkingBar);
                }
            });

        } catch (URISyntaxException e) {
            showToast("INVALID IP ADDRESS");
        }
    }

    private void handleScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY,
                                    ScrollView verticalScrollView) {
        // Check if there is a vertical scroll
        if (scrollY != oldScrollY && !webSocketClient.uri.equals(dummyURI)) {
            System.out.println("ScrollDetection Vertical scroll detected. ScrollY: " + scrollY);
            try {
                messageQueue.put(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (scrollY >= 22000) {
            verticalScrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private void handleStopButtonClick(EditText ipAddressBox, Button startButton,
                                       Button stopButton, TextView connectedTextView,
                                       ScrollView verticalScrollView, ProgressBar linkingBar) {
        webSocketClient.closeWebSocket();

        setViewsToMain(startButton, stopButton, connectedTextView, ipAddressBox, linkingBar);

        showToast("Link Stopped");
        verticalScrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    private Thread createMessageSendingThread(BlockingQueue<Integer> messageQueue, WebSocketClient webSocketClient) {
        // Start a background thread for sending messages
        return new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    if (messageQueue.take() != null) {
                        webSocketClient.sendMessage();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
    }

    private void showToast(String message) {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        });
    }


    private void setViewsToMain(Button startButton,
            Button stopButton,
            TextView connectedTextView,
            EditText ipAddressBox,
            ProgressBar linkingBar) {
        runOnUiThread(() -> {
            startButton.setVisibility(View.VISIBLE);
            ipAddressBox.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.INVISIBLE);
            connectedTextView.setVisibility(View.INVISIBLE);
            connectedTextView.setText("");
            linkingBar.setVisibility(View.INVISIBLE);
        });
    }

    private void setViewsToConnecting(Button startButton,
                                     ProgressBar linkingBar,
                                     Button stopButton) {
        runOnUiThread(() -> {
            startButton.setVisibility(View.INVISIBLE);
            linkingBar.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        });
    }

    private void setViewsToConnected(EditText ipAddressBox,
                                    TextView connectedTextView,
                                    ProgressBar linkingBar,
                                    TextView invisibleTextView,
                                    String ipAddress) {
        runOnUiThread(() -> {
            ipAddressBox.setVisibility(View.INVISIBLE);
            connectedTextView.setText(
                    "Connected to " + ipAddress + "\n " +
                            "You can now scroll");
            connectedTextView.setVisibility(View.VISIBLE);
            linkingBar.setVisibility(View.INVISIBLE);
            invisibleTextView.setVisibility(View.VISIBLE);
        });
    }
}