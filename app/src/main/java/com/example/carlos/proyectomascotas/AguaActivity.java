package com.example.carlos.proyectomascotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.modelo.Configuration;

public class AguaActivity extends AppCompatActivity {

    TextView cantidadActualAgua;
    TextView cantidadAguaPonerse;
    TextView ultimaPuestaAguaFecha;
    TextView ultimaPuestaAguaHora;
    LeerEscribirArchivos leerEscribirArchivos = new LeerEscribirArchivos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agua);

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
        return Double.toString(configuration.getCantidadComida());
    }

    private String monitorear() {
        //sensor raspberry pi
        Toast.makeText(getApplicationContext(),"Agua Monitoreada",Toast.LENGTH_SHORT).show();
        return "50.5";
    }

    public void llenarAgua(View view){
        //configuracion raspberry liberar agua ml
        Toast.makeText(getApplicationContext(),"Recipiente con agua", Toast.LENGTH_SHORT).show();
    }
}
