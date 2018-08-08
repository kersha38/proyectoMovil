package com.example.carlos.proyectomascotas.control.TareasAsync;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.carlos.proyectomascotas.AguaActivity;
import com.example.carlos.proyectomascotas.MenuActivity;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Configuration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaMonitorear extends AsyncTask<String, Integer, Configuration>{

    private ProgressDialog progressDialog;
    ServiceWeb serviceWeb = new ServiceWeb();
    Context contextoActivity;

    public TareaMonitorear(Context context){
        contextoActivity = context;
    }


    @Override
    protected Configuration doInBackground(String... strings) {
        String raspberry = strings[0];

        return monitorearRaspberry(raspberry);

    }

    @Override
    protected void onPostExecute(Configuration configuration) {
        if(configuration != null){
            //AguaActivity
        }
    }

    public void irActivityRegMAC(String email){
        MenuActivity activity = (MenuActivity) contextoActivity;
        Intent intent = new Intent(contextoActivity, AguaActivity.class);
        intent.putExtra("email", email);
        activity.startActivity(intent);
    }

    private Configuration monitorearRaspberry(String raspberry){
        final Configuration[] configuration = {null};
        try {
            serviceWeb.getJSONObjeto().monitorear(raspberry)
                    .enqueue(new Callback<Configuration>() {
                        @Override
                        public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                            Configuration jsonResponse = response.body();
                            if(jsonResponse != null){
                                configuration[0] = jsonResponse;
                            }
                        }

                        @Override
                        public void onFailure(Call<Configuration> call, Throwable t) {

                        }
                    });
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return configuration[0];
    }
}
