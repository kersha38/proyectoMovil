package com.example.carlos.proyectomascotas;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.ConsultasServiceWeb;
import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Configuration;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.SensoresRaspberry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComidaActivity extends AppCompatActivity {

    Button botonComida;
    TextView cantidadActualComida;
    TextView cantidadPonerseComida;
    TextView ultimaPuestaFechaComida; //S Web
    TextView ultimaPuestaHoraComida; //S Web
    LeerEscribirArchivos leerEscribirArchivos = new LeerEscribirArchivos();
    String raspberry;
    ServiceWeb serviceWeb = new ServiceWeb();
    SensoresRaspberry sensoresRaspberry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);

        raspberry = getIntent().getExtras().getString("raspberry");
        //sensoresRaspberry = (SensoresRaspberry) getIntent().getExtras().getSerializable("monitoreo");
        //Thread.sleep(1000);
        monitorear();
        //ConsultasServiceWeb consultasServiceWeb = new ConsultasServiceWeb(ComidaActivity.this);
        //sensoresRaspberry = consultasServiceWeb.monitorear(this.raspberry);

        cantidadActualComida = (TextView)findViewById(R.id.cantidadActualComida);
        cantidadPonerseComida = (TextView) findViewById(R.id.cantidadComidaPonerse);
        ultimaPuestaFechaComida = (TextView) findViewById(R.id.ultimaPuestaComidaFecha);
        ultimaPuestaHoraComida = (TextView)findViewById(R.id.ultimaPuestaComidaHora);


    }

    private void monitorear() {

        serviceWeb.getJSONObjeto().monitorear(raspberry)
                .enqueue(new Callback<SensoresRaspberry>() {
                    @Override
                    public void onResponse(Call<SensoresRaspberry> call, Response<SensoresRaspberry> response) {
                        SensoresRaspberry jsonResponse = response.body();
                        Log.e("Sensado",jsonResponse.toString());
                        if(jsonResponse != null){
                            sensoresRaspberry = jsonResponse;
                            Log.e("Sensado despues",jsonResponse.toString());
                            // Log.e("Sensor ",sensoresRaspberry.toString());
                            ultimaPuestaFechaComida.setText(sensoresRaspberry.getFecha());
                            ultimaPuestaHoraComida.setText(sensoresRaspberry.getHora());
                            cantidadActualComida.setText(sensoresRaspberry.getComida()); //raspberry pi
                            cantidadPonerseComida.setText(verConfiguracion()); //pre-configurado

                            botonComida = (Button) findViewById(R.id.buttonPonerAlimento);

                            botonComida.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    liberarComida(view);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<SensoresRaspberry> call, Throwable t) {
                        Log.e("Error!!",t.getMessage());
                    }
                });
    }

    private String verConfiguracion() {
        Configuration configuration = leerEscribirArchivos.leerArchivo("configuration.bin");
        return configuration.getComida().toString();
    }


    public void liberarComida(View view){
        //configuracion raspberry liberar cant comida gr.
        Dialogo dialogo = new Dialogo(ComidaActivity.this);
        dialogo.confirmarPonerComida(raspberry);

    }

    public void ordenarComidaServicio(){
        Log.e("rasp a enviar", raspberry+"");
        serviceWeb.getJSONObjeto().ordenar("comida", raspberry).enqueue(
                new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        Mensaje jsonResponse = response.body();
                        if(jsonResponse.getMensaje().equals("ordenExitosa")){
                            Toast.makeText(getApplicationContext(),"Comida Liberada",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Log.e("No logro comida",":(");
                    }
                }
        );
    }



}
