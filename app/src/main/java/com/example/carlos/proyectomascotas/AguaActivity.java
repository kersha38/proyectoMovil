package com.example.carlos.proyectomascotas;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.modelo.Configuration;
import com.example.carlos.proyectomascotas.modelo.SensoresRaspberry;

public class AguaActivity extends AppCompatActivity {

    TextView cantidadActualAgua;
    TextView cantidadAguaPonerse;
    TextView ultimaPuestaAguaFecha;
    TextView ultimaPuestaAguaHora;
    LeerEscribirArchivos leerEscribirArchivos = new LeerEscribirArchivos();
    SensoresRaspberry sensoresRaspberry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agua);

        sensoresRaspberry = (SensoresRaspberry) getIntent().getExtras().getSerializable("monitoreo");
        cantidadActualAgua = (TextView)findViewById(R.id.cantidadActualAgua);
        ultimaPuestaAguaFecha = findViewById(R.id.ultimaPuestaAguaFecha);
        ultimaPuestaAguaHora = findViewById(R.id.ultimaPuestaAguaHora);
        cantidadAguaPonerse = findViewById(R.id.cantidadAguaPonerse);
        ultimaPuestaAguaFecha.setText("07-06-2018");
        ultimaPuestaAguaHora.setText("15h35");

        cantidadActualAgua.setText(monitorear());
        cantidadAguaPonerse.setText(verConfiguracion());
    }

    private String verConfiguracion() {
        Configuration configuration = leerEscribirArchivos.leerArchivo("configuration.bin");
        //return Double.toString(configuration.getCantidadComida());
        return "";
    }

    private String monitorear() {
        //sensor raspberry pi
        Toast.makeText(getApplicationContext(),"Agua Monitoreada",Toast.LENGTH_SHORT).show();
        return "50.5";
    }

    public void llenarAgua(View view){
        //configuracion raspberry liberar agua ml
        //Toast.makeText(getApplicationContext(),"Recipiente con agua", Toast.LENGTH_SHORT).show();
        crearDialogoAlert();
    }

    public void crearDialogoAlert(){
        // Builder para crear la alerta
        // this = getaplicationcontext
        AlertDialog.Builder dialogoAlerta= new AlertDialog.Builder(this);
        dialogoAlerta.setTitle("ALERTA PARA LLENAR AGUA");
        dialogoAlerta.setMessage("¿Esta seguro de llenar recipiente con agua?");

        // interface para el boton Positive
        dialogoAlerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(getApplicationContext(),"Recipiente con agua",Toast.LENGTH_SHORT).show();
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
