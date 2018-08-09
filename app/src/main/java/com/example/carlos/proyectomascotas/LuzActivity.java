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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.SensoresRaspberry;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LuzActivity extends AppCompatActivity {
    TextView txtEstadoIluminacion;
    ImageView imgEncenderApagar;
    Switch switchLuz;
    ServiceWeb serviceWeb = new ServiceWeb();
    SensoresRaspberry sensoresRaspberry= null;
    String raspberry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luz);
        raspberry = getIntent().getExtras().getString("raspberry");
        txtEstadoIluminacion=(TextView)findViewById(R.id.textEstadoIluminacion);
        imgEncenderApagar=(ImageView)findViewById(R.id.imageEncenderApagar);
        switchLuz = (Switch)findViewById(R.id.switchLuz);

        switchLuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchLuz.isChecked()){
                    //Orden al ServiceWeb - Raspberry - ON
                    encenderApagarLucesServicio("luzON","encendido");
                }else{
                    //Orden al ServiceWeb - Raspberry - OFF
                    encenderApagarLucesServicio("luzOFF", "apagado");
                }
            }
        });
    }

    public void encenderApagarLucesServicio(String orden, final String mensaje){
        serviceWeb.getJSONObjeto().ordenar(orden,raspberry).enqueue(
                new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        Mensaje jsonResponse = response.body();
                        if(jsonResponse.getMensaje().equals("ordenExitosa")){

                            txtEstadoIluminacion.setText(mensaje);
                            imgEncenderApagar.setBackgroundResource(R.drawable.focoapagado);
                            Toast.makeText(getApplicationContext(),"Luz remota "+mensaje,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Log.e("No logro ON/OFF",t.getMessage());
                    }
                }
        );
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

                if(sensores.getLuz().equals("luzON")){
                    txtEstadoIluminacion.setText("encendida");
                }else{
                    txtEstadoIluminacion.setText("apagada");
                }

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
                Dialogo dialogo = new Dialogo(LuzActivity.this);
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
