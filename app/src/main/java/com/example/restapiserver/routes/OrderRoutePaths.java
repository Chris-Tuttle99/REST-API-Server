package com.example.restapiserver.routes;

import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;

import com.example.restapiserver.data.OrderDao;
import com.example.restapiserver.entity.Order;
import com.example.restapiserver.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OrderRoutePaths {

    public static void registerRoutes(OrderDao db){
        OrderService orderService = new OrderService(db);

        path("/api", () ->{
            //TODO add logging code: -----
            path("/order",()-> { //ALL order endpoints
                get("/", (request, response) -> {
                    //response.type("application/json");
                    //TODO this endpoint
                    //Process:
                    //in get all from Order Service
                    //turn into one big JSON object
                    //return
                    //use this class and UndertowServerRoutingTest as examples
                    return "GET Order/ endpoint";
                });
                post("/", (request, response) ->{
                    response.type("application/json");
                    //request.body is a json string of an Order that needs to be added to DB

                    //convert JSON to Order object -- using Jackson
                    Order order;
                    String prettyAnswer="";
                    ObjectMapper mapper=new ObjectMapper();
                    try{
                        order=mapper.readValue(request.body(), Order.class);
                        prettyAnswer=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
                        //insert order into db
                        orderService.addOrder(order);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    //TODO return Json AND status code
                    return prettyAnswer;
                });
                get("/:orderId", (request, response) -> {
                    response.type("application/json");
                    int id=Integer.parseInt(request.params(":orderId"));
                    Order order = orderService.getOrderById(id);
                    System.out.println(id);
                    //Convert Order to JSON -- using Jackson
                    String prettyJson="";
                    ObjectMapper mapper = new ObjectMapper();
                    try{
                        prettyJson= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    //TODO return Order Json AND Status Code
                    return prettyJson;
                });
                get("/table/:tableId", (request, response) -> {
                    //response.type
                    //use this class and this endpoint from UndertowServerRoutingTest as examples
                    return "GET /table/tableId";
                });
            });
        } );
    }
}
