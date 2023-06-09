package com.example.clienttest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ClientTestActivity extends AppCompatActivity {

    ExampleClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_test);

        //Test websocket client connection to spark server
        URI uri = URI.create("ws://localhost:4567/echo/");

        try {
            client = new ExampleClient(new URI("ws://localhost:4567/echo"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("Before connect");
            client.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }

    public class ExampleClient extends org.java_websocket.client.WebSocketClient {

        public ExampleClient(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }

        public ExampleClient(URI serverURI) {
            super(serverURI);
        }

        public ExampleClient(URI serverUri, Map<String, String> httpHeaders) {
            super(serverUri, httpHeaders);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            send("Hello, it is me. Mario :)");
            System.out.println("opened connection");
            // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
        }

        @Override
        public void onMessage(String message) {
            System.out.println("received: " + message);

        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            // The close codes are documented in class org.java_websocket.framing.CloseFrame
            System.out.println(
                    "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                            + reason);
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
            // if the error is fatal then onClose will be called additionally
        }

    }
}



