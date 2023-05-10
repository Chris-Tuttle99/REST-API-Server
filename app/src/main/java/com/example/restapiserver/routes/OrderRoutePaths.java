package com.example.restapiserver.routes;

import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;

import com.example.restapiserver.data.OrderDao;
import com.example.restapiserver.entity.Order;
import com.example.restapiserver.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class OrderRoutePaths {

    public static void registerRoutes(OrderDao db){
        OrderService orderService = new OrderService(db);

        path("/api", () ->{
            //TODO add logging code: -----
            path("/order",()-> { //ALL order endpoints
                get("/", (request, response) -> {
                    response.type("application/json");
                    return convertToJson(orderService.getAll());
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

                    return prettyAnswer;

                });
                get("/:orderId", (request, response) -> {
                    response.type("application/json");
                    int id=Integer.parseInt(request.params(":orderId"));
                    return convertToJson(orderService.getOrderById(id));
                });
                get("/table/:tableId", (request, response) -> {
                    response.type("application/json");
                    int id = Integer.parseInt(request.params(":tableId"));
                    return convertToJson(orderService.getOrderByTable(id));
                });
            });
        } );
    }


    private static String convertToJson(List<Order> orders){
        String prettyJson="";
        ObjectMapper mapper = new ObjectMapper();

        try{
            prettyJson= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orders);

        }catch (IOException e){
            e.printStackTrace();
        }
        return prettyJson;
    }

    private static String convertToJson(Order order){
        String prettyJson="";
        ObjectMapper mapper = new ObjectMapper();

        try{
            prettyJson= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);

        }catch (IOException e){
            e.printStackTrace();
        }
        return prettyJson;
    }
}
