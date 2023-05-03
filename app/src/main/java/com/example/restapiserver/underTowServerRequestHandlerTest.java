package com.example.restapiserver;


import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.PathTemplateMatch;
import spark.Spark;


public class underTowServerRequestHandlerTest extends AsyncTask {
    //create server in the main activity file with:
    //underTowRoutingServer server = new underTowRoutingServer();
    //server.execute();
    private Context mContext; //TODO FIX THIS TO AVOID MEMORY LEAKS

    /* tweak this, this will allow the passing of the context to access room DB instance, but without memory leaks
    private WeakReference<Activity> weakActivity = null;
    public underTowServerRoutingTest(Activity myActivity){
        this.weakActivity=new WeakReference<Activity>(myActivity);
    }*/


    public underTowServerRequestHandlerTest(Context context){
        mContext=context;
    }

    OrderDao db=OrderDatabase.getInstance(mContext).orderDao();




    //OrderDao db=OrderDatabase.getInstance(weakActivity.get().getApplicationContext()).orderDao();

    @Override
    protected Object doInBackground(Object[] objects) {

        //creates server on local host port 8080
        Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
                .setHandler( Handlers.path().addPrefixPath("/api", Handlers.routing()
                        .get("/*", new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {
                                System.out.println("GET");


                                //Spark.get("/api/*",(req, res)->"Hello, world");


                                System.out.println(exchange.getRequestMethod());
                                new DomainController(db, exchange.getRequestURL(), exchange.getRequestMethod().toString()).getResponse();
                                List<Order> allOrders=db.getAllOrders();
                                for(Order order: allOrders){
                                    System.out.println("Order: "+order.getOrderId()+" "+order.getOrderStatus()+" "+order.getTableId());
                                }
                                //DB TEST
                                exchange.getResponseSender().send("Response Message from Get Test");
                            }
                        }).post("/*", new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {
                                System.out.println("GET order by ID");

                                int orderId=Integer.parseInt(exchange.getQueryParameters().get("orderId").getFirst());
                                Order order= db.findById(orderId);
                                //Jackson for Java Object to JSON conversion
                                String prettyJson="";
                                ObjectMapper mapper = new ObjectMapper();
                                try{
                                    prettyJson= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);

                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                exchange.getResponseSender().send(prettyJson); //send JSON response
                            }
                        }).delete("/*", new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {
                                int tableId=Integer.parseInt(exchange.getQueryParameters().get("tableId").getFirst());
                                List<Order> orders=db.findByTableId(tableId);
                                //convert to JSON with Jackson
                                ObjectMapper mapper=new ObjectMapper();
                                String answer="";
                                for(Order order: orders){
                                    try{
                                        answer+=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order)+",\n";
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }

                                }
                                exchange.getResponseSender().send(answer);

                            }
                        }).put("/*", new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {

                                exchange.getRequestReceiver().receiveFullBytes(new Receiver.FullBytesCallback() {
                                    @Override
                                    public void handle(HttpServerExchange exchange, byte[] message) {
                                        //message is a JSON string of the new order
                                        //Jackson for conversion to Java Object
                                        Order order=null;
                                        String prettyAnswer="";
                                        ObjectMapper mapper=new ObjectMapper();
                                        try{
                                            //System.out.println(new String(message));
                                            order=mapper.readValue(new String(message), Order.class);
                                            //System.out.println(order);
                                            prettyAnswer=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
                                        }catch(IOException e){
                                            e.printStackTrace();
                                        }
                                        //adding new java object to orders database
                                        db.insertAll(order);

                                        //sending response as JSON
                                        exchange.getResponseSender().send(prettyAnswer);
                                    }
                                });

                            }
                        }).setFallbackHandler(new HttpHandler() {
                            @Override
                            public void handleRequest(HttpServerExchange exchange) throws Exception {
                                System.out.println("Fallback Handler");
                                exchange.getResponseSender().send("Default Fallback");
                            }
                        }))).build();
        server.start();
        return null;
    }

/*
    @Override
    protected void onPostExecute(Object o) {
        Activity activity=weakActivity.get();
        if (activity == null
                || activity.isFinishing()
                || activity.isDestroyed()) {
            // activity is no longer valid, don't do anything!
            return;
        }
    }
    */

}
