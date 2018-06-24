package com.example.carlos.proyectomascotas.control;

import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {
    //autenticacion
    @GET("/Usuario/autentificarComun")
    Call<Usuario> getUsuarioAuth(
            @Query("nickname") String nickname,
            @Query("password") String password);

    @POST("Usuario/crearComun")
    Call<Mensaje> crearUsuarioNuevo(
            @Body Usuario usuario);

    @GET("Usuario/existeGmailFb")
    Call<Mensaje> verificarExisteCuenta(@Query("mail") String mail);

    @POST("Usuario/crearConGmailFb")
    Call<Mensaje> crearUsuarioGmailFb(
            @Body Usuario usuario
    );

    @GET("/Usuario/autentificarGmailFb")
    Call<Usuario> getUsuarioGmailFbAuth(
            @Query("mail") String mail);

}
