package com.example.carlos.proyectomascotas.control;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {
    //autenticacion
    @GET("/Usuario/autentificarComun")
    Call<JSONResponse> getUsuarioAuth(@Query("nickname") String nickname, @Query("password") String password);

}
