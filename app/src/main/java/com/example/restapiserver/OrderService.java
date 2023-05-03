package com.example.restapiserver;

import java.util.List;

public class OrderService {

    OrderDao db;

    public OrderService(OrderDao db){
        this.db=db;
    }


    public void getAll(){
        List<Order> allOrders=db.getAllOrders();
        for(Order order: allOrders){
            System.out.println("Order: "+order.getOrderId()+" "+order.getOrderStatus()+" "+order.getTableId());
        }
    }

    public String getOrderById(int id){
        Order order=db.findById(id);
        return order.toString();
    }
}
