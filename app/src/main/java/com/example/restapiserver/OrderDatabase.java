package com.example.restapiserver;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Order.class}, version=1)
public abstract class OrderDatabase extends RoomDatabase {
    public abstract OrderDao orderDao();

    private static volatile OrderDatabase INSTANCE;

    static OrderDatabase getInstance(Context context){
        if(INSTANCE==null){
            synchronized (OrderDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), OrderDatabase.class,"Order_Database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
