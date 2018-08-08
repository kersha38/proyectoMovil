package com.example.carlos.proyectomascotas.control;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWeb {
    private static ServiceWeb myServiceWeb;
    Retrofit retrofit ;
    RequestInterface request;

    public ServiceWeb(){
        try {
            retrofit = new Retrofit.Builder()
                    //baseUrl("http://172.29.64.184:300")//.baseUrl("http://tranquil-mountain-87492.herokuapp.com")
                    .baseUrl("http://tranquil-mountain-87492.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }catch (Error e){
            Log.e("Error conecting service",e.getMessage());
        }


    }

//    public RequestInterface getJSONArray(){
//        return retrofit[0].create(RequestInterface.class);
//    }

    public RequestInterface getJSONObjeto() {
        return retrofit.create(RequestInterface.class);
    }

    //    public static ServiceWeb getInstance(){
//        if(myServiceWeb==null){
//            myServiceWeb = new ServiceWeb();
//        }
//        return myServiceWeb;
//    }



}
