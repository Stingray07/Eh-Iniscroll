package com.example.ehiniscroll;
//import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//@ClientEndpoint
public class WebSocketClient {
    public static void main(String[] args)  {

        System.out.println("TEST");

    }

//    private static Session session;
//
//    @OnOpen
//    public void onOpen(Session session) {
//        WebSocketClient.session = session;
//
//        // Send an initial message to the server
//        try {
//            session.getBasicRemote().sendText("Hello, WebSocket Server!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        // Handle incoming messages from the server
//        System.out.println("Received message from server: " + message);
//    }
//
//    @OnClose
//    public void onClose() {
//        // Handle the connection closing
//        System.out.println("Server disconnected.");
//    }
//
//    public static void main(String[] args) throws Exception {
//        URI uri = new URI("ws://localhost:3000");
//
//        // Create and configure the WebSocket container
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        container.connectToServer(WebSocketClient.class, uri);
//    }
}
