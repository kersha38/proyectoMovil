package com.example.carlos.proyectomascotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LuzActivity extends AppCompatActivity {
    TextView txtEstadoIluminacion;
    Button btnEncenderApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luz);

        txtEstadoIluminacion=(TextView)findViewById(R.id.textEstadoIluminacion);
        btnEncenderApagar=(Button)findViewById(R.id.buttonEncenderApagar);

        btnEncenderApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // envio peticion al servior
                Toast.makeText(getApplicationContext(),"Orden Enviada",Toast.LENGTH_LONG).show();

                //datos quemados cambiar
                if(txtEstadoIluminacion.getText()=="apagado")
                    txtEstadoIluminacion.setText("encendido");
                else
                    txtEstadoIluminacion.setText("apagado");
            }
        });
    }
}
