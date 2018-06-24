package com.example.carlos.proyectomascotas.control;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWeb {
    private static ServiceWeb myServiceWeb=null;
    Retrofit[] retrofit ;
    RequestInterface request;

    public ServiceWeb(){
        retrofit = new Retrofit[]{new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()};
    }

    public RequestInterface getJSONApi(){
        return retrofit[0].create(RequestInterface.class);
    }

//    public static ServiceWeb getInstance(){
//        if(myServiceWeb==null){
//            myServiceWeb = new ServiceWeb();
//        }
//        return myServiceWeb;
//    }

}
