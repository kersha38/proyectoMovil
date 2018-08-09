package com.example.carlos.proyectomascotas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Configuration;
import com.example.carlos.proyectomascotas.modelo.SensoresRaspberry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AguaActivity extends AppCompatActivity {

    TextView cantidadActualAgua;
    TextView cantidadAguaPonerse;
    TextView ultimaPuestaAguaFecha;
    TextView ultimaPuestaAguaHora;
    LeerEscribirArchivos leerEscribirArchivos = new LeerEscribirArchivos();
    SensoresRaspberry sensoresRaspberry= null;
    String raspberry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agua);

        raspberry = getIntent().getExtras().getString("raspberry");

        cantidadActualAgua = (TextView)findViewById(R.id.cantidadActualAgua);
        ultimaPuestaAguaFecha = findViewById(R.id.ultimaPuestaAguaFecha);
        ultimaPuestaAguaHora = findViewById(R.id.ultimaPuestaAguaHora);
        cantidadAguaPonerse = findViewById(R.id.cantidadAguaPonerse);

        TareaMonitorear tareaMonitorear = new TareaMonitorear();
        tareaMonitorear.execute(raspberry);

    }

    private String verConfiguracion() {
        Configuration configuration = leerEscribirArchivos.leerArchivo("configuration.bin");

        return configuration.getComida().toString();

    }

    private String monitorear() {
        //sensor raspberry pi
        Toast.makeText(getApplicationContext(),"Agua Monitoreada",Toast.LENGTH_SHORT).show();
        return "50.5";
    }

    public void llenarAgua(View view){
        Dialogo dialogo = new Dialogo(AguaActivity.this);
        dialogo.confirmarPonerComidaAgua(raspberry, "agua");
    }



    class TareaMonitorear extends AsyncTask<String, Integer, SensoresRaspberry> {

        private ProgressDialog progressDialog;
        ServiceWeb serviceWeb = new ServiceWeb();

        @Override
        protected SensoresRaspberry doInBackground(String... strings) {
            String raspberry = strings[0];

            try {
                serviceWeb.getJSONObjeto().monitorear(raspberry)
                        .enqueue(new Callback<SensoresRaspberry>() {
                            @Override
                            public void onResponse(Call<SensoresRaspberry> call, Response<SensoresRaspberry> response) {
                                SensoresRaspberry jsonResponse = response.body();
                                if(jsonResponse != null){
                                    sensoresRaspberry = jsonResponse;
                                    Log.e("Sensado despues",jsonResponse.toString());

                                    ultimaPuestaAguaFecha.setText(sensoresRaspberry.getFecha());
                                    ultimaPuestaAguaHora.setText(sensoresRaspberry.getHora());
                                    cantidadActualAgua.setText(sensoresRaspberry.getAgua());
                                    cantidadAguaPonerse.setText(verConfiguracion());
                                }
                            }

                            @Override
                            public void onFailure(Call<SensoresRaspberry> call, Throwable t) {
                                Log.e("Error!!",t.getMessage());
                            }
                        });
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return sensoresRaspberry;
        }


        @Override
        protected void onPostExecute(SensoresRaspberry sensores) {
            if(sensores != null){

                Toast toast = Toast.makeText(getApplicationContext(), "Servicios monitoreados", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else{
                Toast.makeText(getApplicationContext(), "No monitoreo", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            if(verificarConexion()){
                progressDialog = new ProgressDialog(getApplicationContext());
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
                Dialogo dialogo = new Dialogo(AguaActivity.this);
                dialogo.alertarConexionInternet();
                TareaMonitorear.this.cancel(true);
            }

        }

        public boolean verificarConexion() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
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

}
