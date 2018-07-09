package com.example.carlos.proyectomascotas.control;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.LoginActivity;
import com.example.carlos.proyectomascotas.RegistrarMacActivity;
import com.example.carlos.proyectomascotas.RegistryActivity;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultasServiceWeb {
    //Usuario usuarioComun = null;
    Usuario usuarioGmailFb = null;
    private boolean bandera;
    ServiceWeb serviceWeb = new ServiceWeb();
    Context realContext;

    public ConsultasServiceWeb(Context context){
        realContext = context;
    }

    public void verificarExisteGmailFb(final String name, final String email){
        serviceWeb
                .getJSONObjeto()
                .verificarExisteCuenta(email)
                .enqueue(new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        Mensaje jsonResponse= response.body();
                        Log.e("Contenido: ",jsonResponse.getMensaje());
                        if(jsonResponse.getMensaje().equals("true")){ //service responde TRUE || false
                            //si existe cuenta google fb, directo autentica
                            autentificarUsuarioGmailFb(email);
                        }else{
                            //no existe, primero registra y luego autentica
                            crearCuentaGmailFb(name, email);
                        }

                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Log.e("Erro..!!", t.getMessage());
                    }
                });

    }

    public void crearCuentaGmailFb(final String name, final String email){
        Usuario usuarioNuevo = new Usuario(name, "", email, "", "");
        serviceWeb
                .getJSONObjeto()
                .crearUsuarioGmailFb(usuarioNuevo)
                .enqueue(new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        Mensaje jsonResponse= response.body();
                        if(jsonResponse.getMensaje().equals("usuarioCreado")){
                            Toast.makeText(realContext,"Usuario Gmail/Fb Creado",Toast.LENGTH_SHORT).show();
                            autentificarUsuarioGmailFb(email);
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Log.e("Erro..!!", t.getMessage());
                    }
                });
    }

    public Usuario autentificarUsuarioComun(String name, String password){
        final Usuario[] usuarioComun = {null};
        serviceWeb
                .getJSONObjeto()
                .getUsuarioAuth(name,password)
                .enqueue(new Callback<Usuario>() {
                             @Override
                             public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                 Usuario jsonResponse = response.body();
                                 if(jsonResponse != null){
                                     usuarioComun[0] = jsonResponse;
                                     Log.e("Nombre: ",jsonResponse.getNickname()+"");
                                     Log.e("Password: ",jsonResponse.getPassword()+"");

                                     LoginActivity loginActivity = (LoginActivity) realContext;
                                     loginActivity.empezarNuevaActivity("ACTIVITY_REG_MAC");
                                 }
                             }

                             @Override
                             public void onFailure(Call<Usuario> call, Throwable t) {
                                 Log.e("ErroR NO response..!!", t.getMessage());
                                 Toast.makeText(realContext, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                             }
                         }
                );
        return usuarioComun[0];
    }

    public Usuario autentificarUsuarioGmailFb(String email){
        serviceWeb
                .getJSONObjeto()
                .getUsuarioGmailFbAuth(email)
                .enqueue(new Callback<Usuario>() {
                             @Override
                             public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                 Usuario jsonResponse = response.body();
                                 Log.e("Nombre: ",jsonResponse.getNickname()+"");
                                 Log.e("Password: ",jsonResponse.getPassword()+"");
                                 if(jsonResponse != null){
                                     usuarioGmailFb = jsonResponse;
                                     LoginActivity loginActivity = (LoginActivity) realContext;
                                     loginActivity.empezarNuevaActivity("ACTIVITY_REG_MAC");
                                 }
                             }

                             @Override
                             public void onFailure(Call<Usuario> call, Throwable t) {
                                 Log.e("Erro..!!", t.getMessage());
                                 Toast.makeText(realContext,"No autenticado",Toast.LENGTH_SHORT).show();
                             }
                         }
                );
        return usuarioGmailFb;
    }



}
