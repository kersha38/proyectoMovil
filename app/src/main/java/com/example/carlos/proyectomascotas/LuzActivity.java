package com.example.carlos.proyectomascotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LuzActivity extends AppCompatActivity {
    TextView txtEstadoIluminacion;
    ImageView imgEncenderApagar;
    Switch switchLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luz);

        txtEstadoIluminacion=(TextView)findViewById(R.id.textEstadoIluminacion);
        imgEncenderApagar=(ImageView)findViewById(R.id.imageEncenderApagar);
        switchLuz = (Switch)findViewById(R.id.switchLuz);

        switchLuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // envio peticion al servior
                //Toast.makeText(getApplicationContext(),"Orden Enviada",Toast.LENGTH_SHORT).show();

                //datos quemados cambiar
                if(switchLuz.isChecked()){
                    txtEstadoIluminacion.setText("encendido");
                    imgEncenderApagar.setBackgroundResource(R.drawable.focoencendido);
                }else{
                    txtEstadoIluminacion.setText("apagado");
                    imgEncenderApagar.setBackgroundResource(R.drawable.focoapagado);
                }

            }
        });
    }
}
