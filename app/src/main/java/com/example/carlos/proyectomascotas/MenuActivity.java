package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.modelo.Configuration;

public class MenuActivity extends AppCompatActivity {
    private String raspberry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        raspberry = getIntent().getExtras().getString("raspberry");
        configurar();
    }

    public void irAComida(View view){
        Intent intent = new Intent(getApplicationContext(), ComidaActivity.class);
        intent.putExtra("raspberry", raspberry);
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

            Configuration configurationPorDefecto=new Configuration(125.0,125.0);
            leerEscribirArchivos.escribirArchivo(configurationPorDefecto,"configuration.bin");

        }
    }

    public void irALuz(View view){
        Intent intent = new Intent(getApplicationContext(), LuzActivity.class);
        intent.putExtra("raspberry", raspberry);
        startActivity(intent);
    }

    public void irACamara(View view){
        Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
        startActivity(intent);
    }

    public void irAQr(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void irAPersonalizacion(View view){
        Intent intent = new Intent(getApplicationContext(), PersonalizacionActivity.class);
        startActivity(intent);
    }
}
