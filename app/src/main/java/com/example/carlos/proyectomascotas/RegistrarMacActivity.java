package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrarMacActivity extends AppCompatActivity {
    Button btnRegQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mac);

        btnRegQR=(Button) findViewById(R.id.buttonRegQR);

        btnRegQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenQR= new Intent(getApplicationContext(),QRActivity.class);
                startActivity(intenQR);
            }
        });
    }
}
