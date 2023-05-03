package com.example.restapiserver;

import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;

public class OrderRoutePaths {

    public static void registerRoutes(OrderDao db){
        OrderService orderService = new OrderService(db);

        path("/api", () ->{
            //logging code: -----
            path("/order",()-> { //ALL order endpoints
                get("/", (request, response) -> {
                    //response.type("application/json");
                    orderService.getAll();
                    return "GET Order/ endpoint";
                });
                post("/", (request, response) ->{
                    //response.type
                    System.out.println(request.body());
                    return "POST Order/ endpoint";
                });
                get("/:orderId", (request, response) -> {
                    //response.type
                    System.out.println(request.params(":orderId"));
                    //Order = orderService.getOrderById(request.params(orderId));
                    //Convert Order to JSON
                    //return Order JSON and Status Code
                    //return "GET order/{orderId} endpoint";
                    return orderService.getOrderById(Integer.parseInt(request.params(":orderId")));
                });

            });
        } );
    }
}
