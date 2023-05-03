package com.example.restapiserver.service;

import com.example.restapiserver.data.OrderDao;

public class TimekeepingService {

    OrderDao db;

    public TimekeepingService(OrderDao db){
        this.db=db;
    }



}
