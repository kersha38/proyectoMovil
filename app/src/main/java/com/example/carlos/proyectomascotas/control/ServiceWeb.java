package com.example.carlos.proyectomascotas.control;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWeb {
    private static ServiceWeb myServiceWeb;
    Retrofit retrofit ;
    RequestInterface request;

    public ServiceWeb(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:300")//.baseUrl("http://tranquil-mountain-87492.herokuapp.com")
                .baseUrl("http://172.29.64.110:300")//.baseUrl("http://tranquil-mountain-87492.herokuapp.com")
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
