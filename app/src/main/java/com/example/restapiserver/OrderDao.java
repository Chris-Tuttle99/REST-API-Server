package com.example.restapiserver;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insertAll(Order... orders);

    @Query("SELECT * FROM `order`")
    List<Order> getAllOrders();

    @Delete
    void delete(Order order);

    @Query("DELETE FROM `order`")
    void deleteAll();

    @Query("SELECT * FROM `order`WHERE orderId=:id")
    Order findById(int id);

    @Query("SELECT * FROM `order` WHERE table_id=:tableId")
    List<Order> findByTableId(int tableId);
}
