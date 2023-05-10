package com.example.restapiserver.service;

import com.example.restapiserver.data.OrderDao;
import com.example.restapiserver.entity.TimeCard;

public class TimekeepingService {

    OrderDao db;

    public TimekeepingService(OrderDao db){
        this.db=db;
    }

    public TimeCard getTime(){
        return new TimeCard(1); //placeholder
    }



}
