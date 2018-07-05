package com.example.carlos.proyectomascotas.control;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWeb {
    private static ServiceWeb myServiceWeb;
    Retrofit retrofit ;
    RequestInterface request;

    public ServiceWeb(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5:300")//.baseUrl("http://tranquil-mountain-87492.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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
