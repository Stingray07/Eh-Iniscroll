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

import javax.websocket.DeploymentException;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize classes
        final URI dummyURI;
        try {
            dummyURI = new URI("192.168.100");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        WebSocketClient webSocketClient = new WebSocketClient(dummyURI);
        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
        Thread messageSendingThread =  createMessageSendingThread(messageQueue, webSocketClient);

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
            setViewsToConnecting(startButton, linkingBar, stopButton);

            try {
                System.out.println("IP ADDRESS: " + ipAddress);
                URI serverURI = new URI("ws://"+ ipAddress);
                webSocketClient.setUri(serverURI);
                connectWebSocketInThread(webSocketClient, serverURI);

            } catch (URISyntaxException | DeploymentException e) {
                showToast("INVALID IP ADDRESS");
                return;
            }

            setViewsToConnected(
                    ipAddressBox,
                    connectedTextView,
                    linkingBar,
                    invisibleTextView,
                    ipAddress);
            if (messageSendingThread.getState() == Thread.State.NEW){
                messageSendingThread.start();
            }

        });

        stopButton.setOnClickListener(v -> {
            // wifi logic here
            webSocketClient.closeWebSocket();

            setViewsToMain(startButton, stopButton, connectedTextView, ipAddressBox);

            showToast("Link Stopped");
            verticalScrollView.fullScroll(ScrollView.FOCUS_UP);
        });

        verticalScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // Check if there is a vertical scroll
            if (scrollY != oldScrollY && webSocketClient.uri != dummyURI) {
                System.out.println("ScrollDetection Vertical scroll detected. ScrollY: " + scrollY);
                try {
                    messageQueue.put("SOMETHING");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (scrollY >= 22000) {
                verticalScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

    }

    private void connectWebSocketInThread(WebSocketClient webSocketClient,
                                          URI serverURI) throws DeploymentException{

        Thread connectToWebSocketThread = new Thread(() -> {
            try {
                webSocketClient.connectToServer(serverURI);
            } catch (DeploymentException e) {
                System.out.println("DeploymentException");
            }
        });
        connectToWebSocketThread.start();
    }

    private Thread createMessageSendingThread(BlockingQueue<String> messageQueue, WebSocketClient webSocketClient) {
        // Start a background thread for sending messages
        return new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    String message = messageQueue.take();
                    if (message != null) {
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
            EditText ipAddressBox) {
        runOnUiThread(() -> {
            startButton.setVisibility(View.VISIBLE);
            ipAddressBox.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.INVISIBLE);
            connectedTextView.setVisibility(View.INVISIBLE);
            connectedTextView.setText("");
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