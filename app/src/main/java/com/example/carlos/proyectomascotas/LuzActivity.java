package com.example.carlos.proyectomascotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Mensaje;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LuzActivity extends AppCompatActivity {
    TextView txtEstadoIluminacion;
    ImageView imgEncenderApagar;
    Switch switchLuz;
    ServiceWeb serviceWeb = new ServiceWeb();
    String raspberry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luz);
        raspberry = getIntent().getExtras().getString("raspberry");
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
                    //Orden al ServiceWeb - Raspberry
                    serviceWeb.getJSONObjeto().ordenar("luzON",raspberry).enqueue(
                            new Callback<Mensaje>() {
                                @Override
                                public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                                    Mensaje jsonResponse = response.body();
                                    if(jsonResponse.getMensaje().equals("ordenExitosa")){
                                        txtEstadoIluminacion.setText("encendido");
                                        imgEncenderApagar.setBackgroundResource(R.drawable.focoencendido);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Mensaje> call, Throwable t) {

                                }
                            }
                    );

                }else{
                    //
                    serviceWeb.getJSONObjeto().ordenar("luzOFF",raspberry).enqueue(
                            new Callback<Mensaje>() {
                                @Override
                                public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                                    Mensaje jsonResponse = response.body();
                                    if(jsonResponse.getMensaje().equals("ordenExitosa")){
                                        txtEstadoIluminacion.setText("apagado");
                                        imgEncenderApagar.setBackgroundResource(R.drawable.focoapagado);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Mensaje> call, Throwable t) {

                                }
                            }
                    );

                }

            }
        });
    }
}
