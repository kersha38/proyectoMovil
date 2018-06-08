package com.example.carlos.proyectomascotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.modelo.Configuration;

public class PersonalizacionActivity extends AppCompatActivity {

    EditText txtRacionAlimento;
    EditText txtRacionAgua;
    Button btnGuardar;
    Configuration configuration;
    LeerEscribirArchivos leerEscribirArchivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizacion);
        leerEscribirArchivos=new LeerEscribirArchivos();

        txtRacionAgua=(EditText) findViewById(R.id.editTextRacionAgua);
        txtRacionAlimento=(EditText) findViewById(R.id.editTextRacionAlimento);
        btnGuardar=(Button)findViewById(R.id.btnGuardarConfi);

        configuration=cargarConfiguracion();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration.setCantidadAgua(Double.parseDouble(txtRacionAgua.getText().toString()));
                configuration.setCantidadComida(Double.parseDouble(txtRacionAlimento.getText().toString()));
                leerEscribirArchivos.escribirArchivo(configuration,"configuration.bin");
                finish();
            }
        });
    }

    public Configuration cargarConfiguracion(){
        Configuration configuration=leerEscribirArchivos.leerArchivo("configuration.bin");

        txtRacionAgua.setText(Double.toString(configuration.getCantidadAgua()));
        txtRacionAlimento.setText(Double.toString(configuration.getCantidadComida()));

        return configuration;
    }
}
