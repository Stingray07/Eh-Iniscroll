package com.example.ehiniscroll;

import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class WebSocketClient implements Runnable {
    public void test()  {
        System.out.println("TEST");

    }

    private URI uri;

    public WebSocketClient(URI uri) {
        this.uri = uri;
    }


    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to WebSocket Server");
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

    @Override
    public void run() {
        test();
        connectToServer(uri);
    }
}
