package com.example.restapiserver.routes;

import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;

import com.example.restapiserver.data.OrderDao;
import com.example.restapiserver.entity.TimeCard;
import com.example.restapiserver.service.TimekeepingService;

public class TimeEntryPaths {
    public static void registerRoutes(OrderDao db){
        TimekeepingService tks=new TimekeepingService(db);
        path("/api",() ->{
            //logging
            path("/time-card",()->{
                get("/", (request, response) -> {
                    //responseType
                    TimeCard tc=tks.getTime();
                    int id=tc.getId();
                    return "GET /api/time-card endpoint-- id= "+id;
                });
            });
        });
    }

}
