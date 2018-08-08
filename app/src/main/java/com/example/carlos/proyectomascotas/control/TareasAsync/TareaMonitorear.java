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

import com.example.carlos.proyectomascotas.AguaActivity;
import com.example.carlos.proyectomascotas.ComidaActivity;
import com.example.carlos.proyectomascotas.LuzActivity;
import com.example.carlos.proyectomascotas.MenuActivity;
import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Configuration;
import com.example.carlos.proyectomascotas.modelo.SensoresRaspberry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaMonitorear extends AsyncTask<String, Integer, SensoresRaspberry>{

    private ProgressDialog progressDialog;
    ServiceWeb serviceWeb = new ServiceWeb();
    Context contextoActivity;
    String tipo = "";
    String raspberry="";

    public TareaMonitorear(Context context, String tipo){
        this.tipo = tipo;
        contextoActivity = context;
    }


    @Override
    protected SensoresRaspberry doInBackground(String... strings) {
        String raspberry = strings[0];
        this.raspberry = raspberry;
        //return monitorearRaspberry(raspberry);

        final SensoresRaspberry[] configuration = {null};
        try {
            serviceWeb.getJSONObjeto().monitorear(raspberry)
                    .enqueue(new Callback<SensoresRaspberry>() {
                        @Override
                        public void onResponse(Call<SensoresRaspberry> call, Response<SensoresRaspberry> response) {
                            SensoresRaspberry jsonResponse = response.body();
                            if(jsonResponse != null){
                                configuration[0] = jsonResponse;
                                Log.e("Sensado",jsonResponse.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<SensoresRaspberry> call, Throwable t) {
                            Log.e("Error!!",t.getMessage());
                        }
                    });
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return configuration[0];
    }


    @Override
    protected void onPostExecute(SensoresRaspberry sensores) {
        if(sensores != null){
            switch (tipo){
                case "agua":
                    irActivityAgua(sensores);
                    break;
                case "comida":
                    irActivityComida(sensores);
                    break;
                case "luz":
                    irActivityLuz(sensores);
                    break;
            }
            Toast.makeText(contextoActivity, "Mascota: servicios monitoreados", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(contextoActivity, "No monitoreo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPreExecute() {
        if(verificarConexion(contextoActivity)){
            progressDialog = new ProgressDialog(contextoActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Monitoreando raspberry remota...");
            progressDialog.setCancelable(true);
            progressDialog.show();
            //progressDialog.setMax(100);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    TareaMonitorear.this.cancel(true);
                }
            });
        }else{
            Dialogo dialogo = new Dialogo(contextoActivity);
            dialogo.alertarConexionInternet();
            TareaMonitorear.this.cancel(true);
        }

    }

    public void irActivityAgua(SensoresRaspberry sensores){

        MenuActivity activity = (MenuActivity) contextoActivity;
        Intent intent = new Intent(contextoActivity, AguaActivity.class);
        intent.putExtra("monitoreo", sensores);
        intent.putExtra("raspberry", raspberry);
        activity.startActivity(intent);
        //finalizarActivity();
    }

    public void irActivityComida(SensoresRaspberry sensores){

        MenuActivity activity = (MenuActivity) contextoActivity;
        Intent intent = new Intent(contextoActivity, ComidaActivity.class);
        intent.putExtra("monitoreo", sensores);
        intent.putExtra("raspberry", raspberry);
        activity.startActivity(intent);
        //finalizarActivity();
    }

    public void irActivityLuz(SensoresRaspberry sensores){
        MenuActivity activity = (MenuActivity) contextoActivity;
        Intent intent = new Intent(contextoActivity, LuzActivity.class);
        intent.putExtra("monitoreo", sensores);
        activity.startActivity(intent);
    }

    public boolean verificarConexion(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public void finalizarActivity(){
        MenuActivity activity = (MenuActivity) contextoActivity;
        activity.finish();

    }

    private SensoresRaspberry monitorearRaspberry(String raspberry){
        final SensoresRaspberry[] configuration = {null};
        try {
            serviceWeb.getJSONObjeto().monitorear(raspberry)
                    .enqueue(new Callback<SensoresRaspberry>() {
                        @Override
                        public void onResponse(Call<SensoresRaspberry> call, Response<SensoresRaspberry> response) {
                            SensoresRaspberry jsonResponse = response.body();
                            if(jsonResponse != null){
                                configuration[0] = jsonResponse;
                                Log.e("Sensado",jsonResponse.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<SensoresRaspberry> call, Throwable t) {
                            Log.e("Error",t.getMessage());
                        }
                    });
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return configuration[0];
    }
}
