package com.example.carlos.proyectomascotas.control.TareasAsync;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.LoginActivity;
import com.example.carlos.proyectomascotas.MenuActivity;
import com.example.carlos.proyectomascotas.RegistrarMacActivity;
import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaUsuarioGmailFb extends AsyncTask<String,Integer,Usuario> {
    private ProgressDialog progressDialog;
    private Context contextoActivity;
    ServiceWeb serviceWeb = new ServiceWeb();


    public TareaUsuarioGmailFb(Context context){
        contextoActivity = context;
    }

    @Override
    protected Usuario doInBackground(String... strings) {
        String name = strings[0];
        String idCuenta = strings[1];
        Usuario usuario = null;
        if(verificarExisteCuenta(idCuenta)){ //idCuenta corresponde a Email, si existe
            try {
                usuario = autentificarUsuarioGmailFb(idCuenta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                if(crearCuentaGmailFb(name,idCuenta)){
                    usuario = autentificarUsuarioGmailFb(idCuenta);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

    @Override
    protected void onPreExecute() {
        if(verificarConexion(contextoActivity)){
            progressDialog = new ProgressDialog(contextoActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Conectando al service web...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            //progressDialog.setMax(100);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    TareaUsuarioGmailFb.this.cancel(true);
                }
            });
        }else{
            Dialogo dialogo = new Dialogo(contextoActivity);
            dialogo.alertarConexionInternet();
            TareaUsuarioGmailFb.this.cancel(true);
        }

        //progressDialog.setProgress(0);


    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        progressDialog.dismiss();
        if(usuario != null){
            Log.e("Nombre:",usuario.getNickname());//ojo
            Log.e("Cuenta: ",usuario.getMail());
            Toast.makeText(contextoActivity,"Autenticación exitosa",Toast.LENGTH_SHORT).show();
            if(usuario.getIpRasp().equals("")){
                irActivityRegMAC(usuario.getMail());
            }else {
                irActivityMenu(usuario.getIpRasp(), usuario.getMail());
            }
        }else{
            Log.e("Post Exe", "No usuario gmail/fb");
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(contextoActivity, "Conexión cancelada", Toast.LENGTH_SHORT).show();
        super.onCancelled();
    }

    public boolean verificarConexion(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public Boolean verificarExisteCuenta(final String email){
        final boolean[] existe = {false};
        try {
            serviceWeb
                    .getJSONObjeto()
                    .verificarExisteCuenta(email)
                    .enqueue(new Callback<Mensaje>() {
                        @Override
                        public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                            Mensaje jsonResponse= response.body();
                            Log.e("Contenido: ",jsonResponse.getMensaje());
                            if(jsonResponse.getMensaje().equals("true")){ //service response
                                existe[0] = true;
                            }else{
                                existe[0] = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<Mensaje> call, Throwable t) {
                            Log.e("Erro Verificacion..!!", t.getMessage());
                        }
                    });
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return existe[0];
    }

    public Usuario autentificarUsuarioGmailFb(String idCuenta) throws InterruptedException {
        final Usuario[] usuarioGmailFb = {null};
        serviceWeb
                .getJSONObjeto()
                .getUsuarioGmailFbAuth(idCuenta)
                .enqueue(new Callback<Usuario>() {
                             @Override
                             public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                 Usuario jsonResponse = response.body();
                                 Log.e("Nombre: ",jsonResponse.getNickname());//ojo
                                 if(jsonResponse != null){
                                     usuarioGmailFb[0] = jsonResponse;
                                     Log.e("Cuenta: ",usuarioGmailFb[0].getMail());
                                 }
                             }

                             @Override
                             public void onFailure(Call<Usuario> call, Throwable t) {
                                 Log.e("Erro Auth..!!", t.getMessage());
                                 //
                             }
                         }
                );
        Thread.sleep(1500);
        return usuarioGmailFb[0];
    }

    public Boolean crearCuentaGmailFb(String name, final String email) throws InterruptedException {
        final boolean[] creoUsuario = {false};
        Usuario usuarioNuevo = new Usuario(name, "", email, "", "");
        serviceWeb
                .getJSONObjeto()
                .crearUsuarioGmailFb(usuarioNuevo)
                .enqueue(new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        Mensaje jsonResponse= response.body();
                        if(jsonResponse.getMensaje().equals("usuarioCreado")){
                            Toast.makeText(contextoActivity,"Usuario Gmail/Fb Creado",Toast.LENGTH_SHORT).show();
                            //autentificarUsuarioGmailFb(email);
                            creoUsuario[0] = true;
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Log.e("Erro Creacion..!!", t.getMessage());
                    }
                });
        Thread.sleep(1500);
        return creoUsuario[0];
    }

    public void irActivityRegMAC(String email){
        LoginActivity activity = (LoginActivity) contextoActivity;
        Intent intent = new Intent(contextoActivity, RegistrarMacActivity.class);
        intent.putExtra("email", email);
        activity.startActivity(intent);
    }

    public void irActivityMenu(String ipRasp, String email){
        LoginActivity activity = (LoginActivity) contextoActivity;
        Intent intent = new Intent(contextoActivity, MenuActivity.class);
        intent.putExtra("ipRasp", ipRasp);
        intent.putExtra("email", email);
        activity.startActivity(intent);
    }
}
