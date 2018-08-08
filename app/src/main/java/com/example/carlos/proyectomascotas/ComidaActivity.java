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

import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Configuration;
import com.example.carlos.proyectomascotas.modelo.Mensaje;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);
        raspberry = getIntent().getExtras().getString("raspberry");

        cantidadActualComida = (TextView)findViewById(R.id.cantidadActualComida);
        cantidadPonerseComida = (TextView) findViewById(R.id.cantidadComidaPonerse);
        ultimaPuestaFechaComida = (TextView) findViewById(R.id.ultimaPuestaComidaFecha);
        ultimaPuestaHoraComida = (TextView)findViewById(R.id.ultimaPuestaComidaHora);

        monitorear();

        ultimaPuestaFechaComida.setText("07-06-2018");
        ultimaPuestaHoraComida.setText("15h35");
        cantidadActualComida.setText(monitorear()); //raspberry pi
        cantidadPonerseComida.setText(verConfiguracion()); //pre-configurado

        botonComida = (Button) findViewById(R.id.buttonPonerAlimento);

        botonComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liberarComida(view);
            }
        });
    }

    private String verConfiguracion() {
        Configuration configuration = leerEscribirArchivos.leerArchivo("configuration.bin");
        return configuration.getComida().toString();
    }

    private String monitorear() {
        //sensor raspberry pi
        monitorearServicio();
        Toast.makeText(getApplicationContext(),"Comida Monitoreada",Toast.LENGTH_SHORT).show();
        return "50.5";

    }

    public void liberarComida(View view){
        //configuracion raspberry liberar cant comida gr.
        Dialogo dialogo = new Dialogo(ComidaActivity.this);
        if(dialogo.confirmarPonerComida()){
            ordenarComidaServicio();
        }
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

    public void monitorearServicio(){

    }


}
