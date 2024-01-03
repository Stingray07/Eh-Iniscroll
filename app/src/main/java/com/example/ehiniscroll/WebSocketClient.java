package com.example.ehiniscroll;

import java.io.IOException;
import java.net.URI;
import javax.websocket.*;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class WebSocketClient {
    Session userSession = null;
    public boolean isConnected = false;
    public URI uri;
    private final CountDownLatch latch = new CountDownLatch(1);
    public WebSocketClient(URI uri) {
        this.uri = uri;
    }

    @OnOpen
    public void onOpen(Session usersession) {
        this.userSession = usersession;
        isConnected = true;
        latch.countDown();
        System.out.println("Connected to WebSocket Server");
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("Closed WebSocket");
        this.userSession = null;
        this.isConnected = false;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message came from server! " + message);
    }

    public void connectToServer(URI endpointURI, ConnectionCallback callback) {

        Thread connectToServerThread = new Thread(() -> {
            try {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, endpointURI);
                callback.onConnectSuccess();
            } catch (DeploymentException | IOException e) {
                e.printStackTrace();
                callback.onConnectError(e.getMessage());
            }
        });
        connectToServerThread.start();
    }

    public void setUri(URI newURI){
        this.uri = newURI;
    }

    public void sendMessage() {
        try {
            latch.await();

            if (userSession != null && userSession.isOpen()) {
                userSession.getAsyncRemote().sendText("TEST");
            } else {
                System.out.println("WebSocket session is not open");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public void closeWebSocket() {
        if (userSession != null && userSession.isOpen()) {
            try {
                userSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
