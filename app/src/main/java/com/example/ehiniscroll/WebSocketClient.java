package com.example.ehiniscroll;

import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class WebSocketClient implements Runnable {
    public void test()  {
        System.out.println("TEST");

    }
    Session userSession = null;

    private final URI uri;

    public WebSocketClient(URI uri) {
        this.uri = uri;
    }


    @OnOpen
    public void onOpen(Session usersession) {
        System.out.println("Connected to WebSocket Server");
        this.userSession = usersession;
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

    public void sendMessage() {
        this.userSession.getAsyncRemote().sendText("TEST MESSAGE");
    }

    @Override
    public void run() {
        test();
        connectToServer(uri);
        sendMessage();
    }
}
