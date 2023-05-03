package com.example.restapiserver;

import static spark.Spark.get;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import fi.iki.elonen.NanoHTTPD;
import spark.Spark;

public class MainActivity extends AppCompatActivity {
    int port = 8080;

    EditText getOrderId, postOrderId, postOrderStatus, postTableId, getTableId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOrderId=findViewById(R.id.getOrderId);
        postOrderId=findViewById(R.id.postOrderId);
        postOrderStatus=findViewById(R.id.postOrderStatus);
        postTableId=findViewById(R.id.postTableId);
        getTableId=findViewById(R.id.getTableId);

        //Creating in-memory Room Database
        //OrderDatabase db = Room.databaseBuilder(getApplicationContext(), OrderDatabase.class, "order-database").allowMainThreadQueries().build();
        OrderDao db =OrderDatabase.getInstance(this).orderDao();
        Order order1 = new Order("Processing", 1);
        Order order2 = new Order("Complete", 2);
        Order order3 = new Order("Out for Delivery", 3);
        //Autofill in-memory DB on startup for testing purposes
        db.insertAll(order1, order2, order3);
        //db.insertAll(order1,order2,order3);
        //db.orderDao().deleteAll();
        //DB printing for testing purposes
        List<Order> allOrders=db.getAllOrders();
        for(Order order: allOrders){
            System.out.println("Order: "+order.getOrderId()+" "+order.getOrderStatus()+" "+order.getTableId());
        }


        //THESE ARE FOR SPARK!!!!
        OrderRoutePaths.registerRoutes(db);
        TimeEntryPaths.registerRoutes();

        //get("/hello", (req, res) -> "Hello World");


        //lines 24-29 are used for the nanohttpd server.
        /*AndroidWebServer androidWebServer = new AndroidWebServer(port);
        try {
            androidWebServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        //These Lines are used for an underTowServer
//        underTowServer server = new underTowServer();
//        server.execute();

        //These Lines are used for undertowServer with routing for REST API
//        underTowServerRoutingTest server=new underTowServerRoutingTest(this);
//        server.execute();

        //These Lines are used for underTowServer with generic routes that lead to domain controllers /order, timekeeping
//        underTowServerRequestHandlerTest server=new underTowServerRequestHandlerTest(this);
//        server.execute();



    }

    public void getRequest(View view){ //getBtn
        //new Request().execute("GET","http://localhost:8080/api/timekeeping/input/test");
        //spark test
        new Request().execute("GET","http://localhost:4567/api/order/");
    }
    public void postRequest(View view){ //postBtn
        new Request().execute("POST","http://localhost:4567/api/order/");
    }
    public void getOrderById(View view){ //btnGetOrder
        //TODO error handling for empty input
        String orderId=getOrderId.getText().toString();
        new Request().execute("GET", "http://localhost:4567/api/order/"+orderId);
    }
    public void postOrder(View view){ //btnPostOrder
        //TODO error handling for empty input
        String orderId=postOrderId.getText().toString();
        String orderStatus=postOrderStatus.getText().toString();
        String tableId=postTableId.getText().toString();
        //pass them as strings here, they will be converted to JSON before they are actually sent to REST API
        new Request().execute("POST","http://localhost:4567/api/order/",orderId,orderStatus,tableId);
    }
    public void getOrderByTable(View view){ //btnGetTableId
        String tableId=getTableId.getText().toString();
        new Request().execute("GET", "http://localhost:8080/api/table/"+tableId+"/order");
    }



}

