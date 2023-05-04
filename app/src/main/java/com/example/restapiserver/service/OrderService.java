package com.example.restapiserver.service;

import com.example.restapiserver.entity.Order;
import com.example.restapiserver.data.OrderDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class OrderService {

    OrderDao db;

    public OrderService(OrderDao db){
        this.db=db;
    }


    public List<Order> getAll(){
        List<Order> orders=db.getAllOrders();
        return orders;
    }

    public Order getOrderById(int id){
        Order order=db.findById(id);
        return order;
    }

    public List<Order> getOrderByTable(int id){
        List<Order> orders = db.findByTableId(id);
        return orders;
    }

    public void addOrder(Order o){
        db.insertAll(o);
    }


}
