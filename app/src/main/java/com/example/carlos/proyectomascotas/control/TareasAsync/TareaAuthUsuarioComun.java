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
import com.example.carlos.proyectomascotas.modelo.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaAuthUsuarioComun extends AsyncTask<String, Integer, Usuario> {
    private ProgressDialog progressDialog;
    private Context contextoActivity;
    ServiceWeb serviceWeb = new ServiceWeb();


    public TareaAuthUsuarioComun(Context context){
        contextoActivity = context;
    }

    @Override
    protected Usuario doInBackground(String ... strings) {
        String name = strings[0];
        String password = strings[1];
        //final Usuario[] usuario = {null};
        Usuario usuario = authUsuarioComun(name, password);

        return usuario;
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        progressDialog.dismiss();
        if(usuario != null){
            Log.e("Nombre: ",usuario.getNickname()+"");
            Log.e("Raspb: ",usuario.getIpRasp()+"");
            if(usuario.getIpRasp().equals("")){
                Log.e("mail: ",usuario.getMail()+"");
                irActivityRegMAC(usuario.getMail());
            }else {
                irActivityMenu(usuario.getIpRasp(), usuario.getMail());
            }
        }else{
            Toast.makeText(contextoActivity, "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }
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
                    TareaAuthUsuarioComun.this.cancel(true);
                }
            });
        }else{
            Dialogo dialogo = new Dialogo(contextoActivity);
            dialogo.alertarConexionInternet();
            TareaAuthUsuarioComun.this.cancel(true);
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progreso = values[0].intValue();
        //progressDialog.setProgress(progreso);
    }


    @Override
    protected void onCancelled() {
        Toast.makeText(contextoActivity, "Conexión cancelada", Toast.LENGTH_SHORT).show();
        super.onCancelled();
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
        intent.putExtra("raspberry", ipRasp);
        intent.putExtra("email", email);
        activity.startActivity(intent);
    }

    public Usuario authUsuarioComun(String name, String password){
        final Usuario[] usuarioComun = {null};
        try {
            serviceWeb
                    .getJSONObjeto()
                    .getUsuarioAuth(name,password)
                    .enqueue(new Callback<Usuario>() {
                                 @Override
                                 public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                     Usuario jsonResponse = response.body();
                                     if(jsonResponse != null){
                                         usuarioComun[0] = jsonResponse;
                                         Log.e("Usu",usuarioComun[0].getNickname());
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Usuario> call, Throwable t) {
                                     Log.e("ErroR NO response..!!", t.getMessage());

                                 }

                             }
                    );
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return usuarioComun[0];
    }

    public boolean verificarConexion(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
