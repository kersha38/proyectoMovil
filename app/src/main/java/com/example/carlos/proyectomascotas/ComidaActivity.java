package com.example.carlos.proyectomascotas;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.modelo.Configuration;

public class ComidaActivity extends AppCompatActivity {

    TextView cantidadActualComida;
    TextView cantidadPonerseComida;
    TextView ultimaPuestaFechaComida; //S Web
    TextView ultimaPuestaHoraComida; //S Web
    LeerEscribirArchivos leerEscribirArchivos = new LeerEscribirArchivos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);

        cantidadActualComida = (TextView)findViewById(R.id.cantidadActualComida);
        cantidadPonerseComida = (TextView) findViewById(R.id.cantidadComidaPonerse);
        ultimaPuestaFechaComida = (TextView) findViewById(R.id.ultimaPuestaComidaFecha);
        ultimaPuestaHoraComida = (TextView)findViewById(R.id.ultimaPuestaComidaHora);

        ultimaPuestaFechaComida.setText("07-06-2018");
        ultimaPuestaHoraComida.setText("15h35");

        cantidadActualComida.setText(monitorear()); //raspberry pi
        cantidadPonerseComida.setText(verConfiguracion()); //pre-configurado
    }

    private String verConfiguracion() {
        Configuration configuration = leerEscribirArchivos.leerArchivo("configuration.bin");
        return Double.toString(configuration.getCantidadComida());
    }

    private String monitorear() {
        //sensor raspberry pi
        Toast.makeText(getApplicationContext(),"Comida Monitoreada",Toast.LENGTH_SHORT).show();
        return "50.5";

    }

    public void liberarComida(View view){
        //configuracion raspberry liberar cant comida gr.
        //Toast.makeText(getApplicationContext(),"Dispensador con comida", Toast.LENGTH_SHORT).show();
        crearDialogoAlert();
    }

    public void crearDialogoAlert(){
        // Builder para crear la alerta
        // this = getaplicationcontext
        AlertDialog.Builder dialogoAlerta= new AlertDialog.Builder(this);
        dialogoAlerta.setTitle("ALERTA DE ALIMENTACION");
        dialogoAlerta.setMessage("¿Esta seguro de poner comida?");

        // interface para el boton Positive
        dialogoAlerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(getApplicationContext(),"Comida Liberada",Toast.LENGTH_SHORT).show();
            }
        });

        // interface para el boton Negative
        dialogoAlerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                //Toast.makeText(getApplicationContext(),"N",Toast.LENGTH_LONG).show();
            }
        });

        // interface para el boton Neutral
        dialogoAlerta.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                //Toast.makeText(getApplicationContext(),"Selecciono Neutral",Toast.LENGTH_LONG).show();
            }
        });

        // cancelar sin aplastar un boton
        dialogoAlerta.setCancelable(true);

        dialogoAlerta.create();
        dialogoAlerta.show();
    }

}
