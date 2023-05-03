package com.example.restapiserver.service;

import com.example.restapiserver.entity.Order;
import com.example.restapiserver.data.OrderDao;

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

    public void addOrder(Order o){
        db.insertAll(o);
    }
}
