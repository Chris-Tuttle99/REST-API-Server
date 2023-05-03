package com.example.restapiserver;

import static spark.Spark.get;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.restapiserver.data.OrderDao;
import com.example.restapiserver.data.OrderDatabase;
import com.example.restapiserver.entity.Order;
import com.example.restapiserver.routes.OrderRoutePaths;
import com.example.restapiserver.routes.TimeEntryPaths;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        OrderDao db = OrderDatabase.getInstance(this).orderDao();
        //DB printing for testing purposes
        List<Order> allOrders=db.getAllOrders();
        for(Order order: allOrders){
            System.out.println("Order: "+order.getOrderId()+" "+order.getOrderStatus()+" "+order.getTableId());
        }


        //below configures routes for a spark server that will run at :4567
        //-----------------------------------------------------------------------------------
        OrderRoutePaths.registerRoutes(db);
        TimeEntryPaths.registerRoutes();
        //------------------------------------------------------------------------------------


        //below starts up a nanohttpd server at port 8080
        //------------------------------------------------------------------------------------
//        AndroidWebServer androidWebServer = new AndroidWebServer(port);
//        try {
//            androidWebServer.start();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        //-------------------------------------------------------------------------------------


        //below starts up a general underTowServer held in an asynchTask
        //-------------------------------------------------------------------------------------
//        underTowServer server = new underTowServer();
//        server.execute();
        //-------------------------------------------------------------------------------------


        //below starts up an undertowServer in asynchTask with routing for REST API
        //-------------------------------------------------------------------------------------
//        underTowServerRoutingTest server=new underTowServerRoutingTest(this);
//        server.execute();
        //-------------------------------------------------------------------------------------

    }


    //The following blocks of code are Button onClick methods that execute a Request Asynch Task
    //They ping the following endpoints, for server testing purposes
    public void getRequest(View view){ //getBtn
        new Request().execute("GET","http://localhost:4567/api/order/");
    }
    public void getTimeRequest(View view){ //timeBtn
        new Request().execute("GET","http://localhost:4567/api/time-card/");
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
        new Request().execute("GET", "http://localhost:4567/api/order/table/"+tableId);
    }



}

