package com.example.carlos.proyectomascotas.control.TareasAsync;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.RegistryActivity;
import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaRegistrarUsuario extends AsyncTask<String, Integer, Boolean> {
    private ProgressDialog progressDialog;
    ServiceWeb serviceWeb = new ServiceWeb();
    Context contextoActivity;

    public TareaRegistrarUsuario(Context context){
        contextoActivity = context;
    }

    @Override
    protected Boolean doInBackground(String ... strings) {
        String name = strings[0];
        String password = strings[1];
        String mail = strings[2];

        Usuario usuario = new Usuario(name, password, mail, "", "");

        return  crearUsuario(usuario);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressDialog.dismiss();
        if(aBoolean){
            Toast.makeText(contextoActivity,"Usuario creado",Toast.LENGTH_SHORT).show();
            finalizarActivity();
        }else{
            Toast.makeText(contextoActivity,"Email ya esta ocupado",Toast.LENGTH_SHORT).show();
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
                    TareaRegistrarUsuario.this.cancel(true);
                }
            });
        }else{
            Dialogo dialogo = new Dialogo(contextoActivity);
            dialogo.alertarConexionInternet();
            TareaRegistrarUsuario.this.cancel(true);
        }

    }

    @Override
    protected void onCancelled() {
        Toast.makeText(contextoActivity, "Conexión cancelada", Toast.LENGTH_SHORT).show();
        super.onCancelled();
    }

    public Boolean crearUsuario(Usuario usuario){
        final boolean[] creoUsuario = {false};
        try {
            serviceWeb
                    .getJSONObjeto()
                    .crearUsuarioNuevo(usuario)
                    .enqueue(new Callback<Mensaje>() {
                        @Override
                        public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                            Mensaje jsonResponse = response.body();
                            Log.e("Resp ServiceWeb::", jsonResponse.getMensaje()+"");
                            if(jsonResponse.getMensaje().equals("usuarioCreado")){
                                //Toast.makeText(contextoActivity,"Usuario creado",Toast.LENGTH_SHORT).show();
                                creoUsuario[0] = true;
                            }else{
                                creoUsuario[0] = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<Mensaje> call, Throwable t) {
                            Log.e("Erro..!!", t.getMessage());
                        }
                    });
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return creoUsuario[0];
    }

    public boolean verificarConexion(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public void finalizarActivity(){
        RegistryActivity activity = (RegistryActivity) contextoActivity;
        activity.finish();

    }

}
