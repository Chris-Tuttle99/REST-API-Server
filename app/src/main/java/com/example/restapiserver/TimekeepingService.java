package com.example.restapiserver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TimekeepingService {

    OrderDao db;
    //private String path;
    //OrderDao db =OrderDatabase.getInstance();
    public TimekeepingService(OrderDao db){
        this.db=db;
    }

    public String getTime(){

        return "Time: 4pm";

    }


}
