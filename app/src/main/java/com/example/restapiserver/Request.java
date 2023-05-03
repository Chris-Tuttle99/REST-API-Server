package com.example.restapiserver;

import static java.lang.Thread.sleep;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

//handles GET and POST requests
public class Request extends AsyncTask<String, Void, Void> {





    public Request(){
        super();
    }

    @Override
    protected Void doInBackground(String... requestType) {

        try {
            //connect to host
            URL endpoint = new URL(requestType[1]); //"http://localhost:8080"
            HttpURLConnection myConnection = (HttpURLConnection) endpoint.openConnection();

            //setting writes to be JSON
            myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            myConnection.setRequestProperty("Accept", "application/json");


            //determine REST method
            if (requestType[0].equals("POST")) {
                String message="";
                myConnection.setRequestMethod("POST");


                if(requestType.length>2){

                    String orderData="{\"orderId\":\"" +requestType[2]+ "\",\"orderStatus\":\""+requestType[3]+"\",\"tableId\":\""+requestType[4]+"\"}";

                    myConnection.setDoOutput(true);
                    byte[] input=orderData.getBytes("utf-8");
                    myConnection.getOutputStream().write(input,0,input.length);
                }
                else{
                    message = "POST test message";
                    myConnection.setDoOutput(true);
                    myConnection.getOutputStream().write(message.getBytes());
                }


            }
            else{
                myConnection.setRequestMethod("GET");
            }

            //check status and print response
            if (myConnection.getResponseCode() == 200) {
                InputStream response = myConnection.getInputStream();
                Scanner s = new Scanner(response).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";


                //TEST
                System.out.println("endpoint: "+requestType[1]);
                //System.out.println("BEFORE RESULT");
                //TEST
                System.out.println(result);
            }
            else {
                System.out.println("fail");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
