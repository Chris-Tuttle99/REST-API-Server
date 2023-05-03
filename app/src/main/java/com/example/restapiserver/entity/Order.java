package com.example.restapiserver.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int orderId;
    @ColumnInfo(name="order_status")
    private String orderStatus;
    @ColumnInfo(name="table_id")
    private int tableId;

    @Ignore
    public Order(int orderId, String orderStatus, int tableId) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.tableId = tableId;
    }

    public Order(){

    }

    public Order(String orderStatus, int tableId){
        this.orderStatus=orderStatus;
        this.tableId=tableId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderStatus='" + orderStatus + '\'' +
                ", tableId=" + tableId +
                '}';
    }
}
