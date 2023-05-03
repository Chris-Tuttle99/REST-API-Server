package com.example.restapiserver.routes;

import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;

public class TimeEntryPaths {
    public static void registerRoutes(){
        path("/api",() ->{
            //logging
            path("/time-card",()->{
                get("/", (request, response) -> {
                    //responseType
                    return "GET /api/time-card endpoint";
                });
            });
        });
    }

}
