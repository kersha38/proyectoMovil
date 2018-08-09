package com.example.carlos.proyectomascotas.control;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.modelo.Mensaje;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dialogo {
    private Context context;
    AlertDialog.Builder dialogoAlerta;
    boolean confirmo = false;
    ConsultasServiceWeb consultasServiceWeb;

    public Dialogo(Context context){
        this.context = context;
        consultasServiceWeb = new ConsultasServiceWeb(this.context);
        dialogoAlerta= new AlertDialog.Builder(context);
    }

    public void alertarConexionInternet(){


        dialogoAlerta.setTitle("Sin Conexión a Internet");
        dialogoAlerta.setMessage("Verifique su conexión con WiFi o active los datos");

        dialogoAlerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
            }
        });

        // cancelar sin aplastar un boton
        dialogoAlerta.setCancelable(true);

        dialogoAlerta.create();
        dialogoAlerta.show();
    }

    public void confirmarPonerComidaAgua(final String raspberry, final String tipo){
        dialogoAlerta.setTitle("ALERTA DE "+tipo.toUpperCase());
        dialogoAlerta.setMessage("¿Esta seguro de poner "+ tipo +" a mascota?");

        dialogoAlerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                boolean orden = consultasServiceWeb.ordenarComidaAgua(raspberry, tipo);

                if(orden){
                    AlertDialog.Builder dialogoExito = new AlertDialog.Builder(context);
                    dialogoExito.setTitle("ORDEN EXITOSA");
                    dialogoExito.setMessage(tipo.toLowerCase()+" ha sido puesta exitosamente");

                    dialogoExito.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                }

            }
        });

        dialogoAlerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogoAlerta.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // cancelar sin aplastar un boton
        dialogoAlerta.setCancelable(true);

        dialogoAlerta.create();
        dialogoAlerta.show();

    }


}
