package com.example.restapiserver.entity;


public class TimeCard {
    private int id;
    private String lastPunchIn;
    private String lastPunchOut; //?? need to fix these

    public TimeCard(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
