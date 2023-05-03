package com.example.restapiserver;

import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class AndroidWebServer extends NanoHTTPD {

    public AndroidWebServer(int port) {
        super(port);
    }

    public AndroidWebServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        //respond to requests
        if (session.getMethod() == Method.POST) {
            try {
                //get parameters passed in POST message
                session.parseBody(new HashMap<>());
                String requestBody = session.getQueryParameterString();

                //return the message back to the user to confirm receipt
                return newFixedLengthResponse("Request body = " + requestBody);
            } catch (IOException | ResponseException e) {
                throw new RuntimeException(e);
            }
        } else if (session.getMethod() == Method.GET) {
            //send fixed message for GET request
            return newFixedLengthResponse("GET return message.");
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "resource not found");

    }
    //...
}