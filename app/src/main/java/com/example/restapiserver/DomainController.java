package com.example.restapiserver;

import java.net.MalformedURLException;
import java.net.URL;



public class DomainController {

    OrderDao db;
    String url;
    String requestMethod;

    public DomainController(OrderDao db, String url, String requestMethod){
        this.db=db;
        this.url=url;
        this.requestMethod=requestMethod;
    }

    public String getResponse() throws MalformedURLException {

        System.out.println("from getResponse: "+url+" "+requestMethod+"!!");
        URL u=new URL(url);
        String path=u.getPath(); //we have the path, /api is already guaranteed
        System.out.println(path);
        String [] domains=path.split("/");
        System.out.println(domains[2]);
        //domains[1] will always == api
        //domains[2] will be our domain, order, timekeeping, etc.

        //TODO Do all of the routing, by RequestType and by Path
        //TODO write the business logic, I.e. addOrder, getOrderById, etc. in Service classes
        switch (domains[1]){
            case "timekeeping":
                //initialize timekeeping service class 
                System.out.println("timekeeping");
                switch (path){
                    case "/api/timekeeping/input/test":
                        break;
                    default:
                        System.out.println("404");
                }

                break;
            case "order":
                System.out.println("order");
                break;
            default:
                System.out.println("404");

        }





        return "";
    }



}
