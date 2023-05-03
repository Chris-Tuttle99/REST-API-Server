package com.example.restapiserver;


import android.os.AsyncTask;

import androidx.loader.content.AsyncTaskLoader;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.attribute.ExchangeAttribute;
import io.undertow.attribute.ExchangeAttributes;
import io.undertow.io.Receiver;
import io.undertow.predicate.Predicates;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import spark.Spark;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

import static spark.Spark.*;

public class underTowServer extends AsyncTask {
    //create server in the main activity file with:
    //underTowServer server = new underTowServer();
    //server.execute();

    public ArrayList<Order> setOrders(){
        ArrayList<Order> orders=new ArrayList<>();
        orders.add(new Order(1,"Processing",1));
        orders.add(new Order(2,"Complete",2));
        orders.add(new Order(3,"Out for Delivery",3));
        return orders;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        ArrayList<Order> orders=setOrders();

        //creates server on local host port 8080
        //TODO HERE IS WHERE WE WOULD DO THE LOGIC FOR EACH ENDPOINT
        Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
                .setHandler( Handlers.path().addExactPath("/order/2", new HttpHandler()  {
                    @Override //THIS IS THE /order/orderId
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        int orderId2=Integer.parseInt(exchange.getPathParameters().toString());

                        int orderId=2;
                        System.out.println("HANDLER 1");
                        System.out.println("Handler 1 order id: "+orderId+"");
                        //req is the REST method passed to the server
                        String req = exchange.getRequestMethod().toString();
                        System.out.println("IN SERVER - request URL: "+exchange.getRequestURL());
                        if(req.equals("GET")){
                            //send generic response message with GET call
                            exchange.getResponseSender().send("Order id: "+orders.get(orderId-1).getOrderId()+
                                    "Order status: "+orders.get(orderId-1).getOrderStatus());

                        }
                        else if(req.equals("POST")){
                            //send the POST message back to the caller to confirm receipt
                            exchange.getRequestReceiver().receiveFullBytes(new Receiver.FullBytesCallback() {
                                @Override
                                public void handle(HttpServerExchange exchange, byte[] message) {
                                    exchange.getResponseSender().send(new String(message) + " Received.");
                                }
                            });
                        }
                        else {
                            exchange.getResponseSender().send("unmatched request");
                        }
                    }
                }).addPrefixPath("/",new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        //req is the REST method passed to the server
                        System.out.println("HANDLER 2");
                        String req = exchange.getRequestMethod().toString();
                        System.out.println("IN SERVER - request URL: "+exchange.getRequestURL());
                        if(req.equals("GET")){
                            //send generic response message with GET call
                            exchange.getResponseSender().send("Response message from Get Test");
                        }
                        else if(req.equals("POST")){
                            //send the POST message back to the caller to confirm receipt
                            exchange.getRequestReceiver().receiveFullBytes(new Receiver.FullBytesCallback() {
                                @Override
                                public void handle(HttpServerExchange exchange, byte[] message) {
                                    exchange.getResponseSender().send(new String(message) + " Received.");
                                }
                            });
                        }
                        else {
                            exchange.getResponseSender().send("unmatched request");
                        }
                    }
                })).build();



        server.start();
        return null;
    }
}
