package com.example.ehiniscroll;

import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class WebSocketClient {
    public void test()  {
        System.out.println("TEST");

    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to WebSocket Server");
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message came from server! " + message);
    }

    public void connectToServer(String uri_param){
        try {
            URI uri = new URI(uri_param);

            javax.websocket.WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            container.connectToServer(WebSocketClient.class, uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
