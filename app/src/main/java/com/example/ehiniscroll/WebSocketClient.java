package com.example.ehiniscroll;

import java.net.URI;
import javax.websocket.*;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class WebSocketClient implements Runnable {
    public void test()  {
        System.out.println("TEST");

    }
    Session userSession = null;

    public URI uri;
    private final CountDownLatch latch = new CountDownLatch(1);
    public WebSocketClient(URI uri) {
        this.uri = uri;
    }


    @OnOpen
    public void onOpen(Session usersession) {
        this.userSession = usersession;
        latch.countDown();
        System.out.println("Connected to WebSocket Server");
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("Closed WebSocket");
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message came from server! " + message);
    }

    public void connectToServer(URI endpointURI){

        try {
           WebSocketContainer container = ContainerProvider.getWebSocketContainer();
           container.connectToServer(this, endpointURI);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUri(URI newURI){
        this.uri = newURI;
    }

    public void sendMessage() {
        try {
            // Wait until the WebSocket connection is open
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

    @Override
    public void run() {
        test();
        connectToServer(uri);
        sendMessage();
    }
}
