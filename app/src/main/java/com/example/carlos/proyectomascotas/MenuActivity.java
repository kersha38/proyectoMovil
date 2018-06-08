package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.modelo.Configuration;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        configurar();
    }

    public void irAComida(View view){
        Intent intent = new Intent(getApplicationContext(), ComidaActivity.class);
        startActivity(intent);
    }

    public void irAAgua(View view){
        Intent intent = new Intent(getApplicationContext(), AguaActivity.class);
        startActivity(intent);
    }

    public void configurar(){
        LeerEscribirArchivos leerEscribirArchivos= new LeerEscribirArchivos();
        Configuration configuration=leerEscribirArchivos.leerArchivo("configuration.bin");
        if(configuration==null){
            Configuration configurationPorDefecto=new Configuration(125.0,125.0,"");
            leerEscribirArchivos.escribirArchivo(configurationPorDefecto,"configuration.bin");
        }
    }
}
