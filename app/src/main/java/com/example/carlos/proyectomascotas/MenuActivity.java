package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void irAComida(View view){
        Intent intent = new Intent(getApplicationContext(), ComidaActivity.class);
        startActivity(intent);
    }

    public void irAAgua(View view){
        Intent intent = new Intent(getApplicationContext(), AguaActivity.class);
        startActivity(intent);
    }
}
