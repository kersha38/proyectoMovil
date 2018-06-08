package com.example.carlos.proyectomascotas;

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
        Toast.makeText(getApplicationContext(),"Dispensador con comida", Toast.LENGTH_SHORT).show();
    }

}
