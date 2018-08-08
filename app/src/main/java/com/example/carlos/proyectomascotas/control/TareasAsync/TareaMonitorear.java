package com.example.carlos.proyectomascotas.control.TareasAsync;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.AguaActivity;
import com.example.carlos.proyectomascotas.MenuActivity;
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

    public TareaMonitorear(Context context){
        contextoActivity = context;
    }


    @Override
    protected SensoresRaspberry doInBackground(String... strings) {
        String raspberry = strings[0];

        return monitorearRaspberry(raspberry);

    }

    @Override
    protected void onPostExecute(SensoresRaspberry sensores) {
        if(sensores != null){
            irActivityAgua(sensores);
        }else{
            Toast.makeText(contextoActivity, "No monitoreo", Toast.LENGTH_SHORT).show();
        }
    }

    public void irActivityAgua(SensoresRaspberry sensores){
        MenuActivity activity = (MenuActivity) contextoActivity;
        Intent intent = new Intent(contextoActivity, AguaActivity.class);
        intent.putExtra("monitoreo", sensores);
        activity.startActivity(intent);
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
