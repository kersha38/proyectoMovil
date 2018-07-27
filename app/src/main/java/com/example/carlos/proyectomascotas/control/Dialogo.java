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

    public Dialogo(Context context){
        this.context = context;
        dialogoAlerta= new AlertDialog.Builder(context);
    }

    public void alertarConexionInternet(){


        dialogoAlerta.setTitle("Sin Conexión a Internet");
        dialogoAlerta.setMessage("Verifique su conexión con WiFi o active los datos");

        // interface para el boton Positive
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

    public boolean confirmarPonerComida(){
        //AlertDialog.Builder dialogoAlerta= new AlertDialog.Builder(context);

        dialogoAlerta.setTitle("ALERTA DE COMIDA");
        dialogoAlerta.setMessage("¿Esta seguro de poner comida?");

        // interface para el boton Positive
        dialogoAlerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                confirmo = true;
            }
        });

        // interface para el boton Negative
        dialogoAlerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // interface para el boton Neutral
        dialogoAlerta.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // cancelar sin aplastar un boton
        dialogoAlerta.setCancelable(true);

        dialogoAlerta.create();
        dialogoAlerta.show();

        return confirmo;
    }

    public void mostrarlistaDialogo(final Context context){
        final CharSequence[] items = {"Articulo1","Articulo2","Articulo3","Articulo4"};

        AlertDialog.Builder dialogoAlerta= new AlertDialog.Builder(context);
        dialogoAlerta.setTitle("Mensaje - Titulo");

        // interface para el boton Positive
        dialogoAlerta.setPositiveButton("si, creo?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(context,"Selecciono Si en dialog",Toast.LENGTH_LONG).show();
            }
        });

        // interface para el boton Negative
        dialogoAlerta.setNegativeButton("nop?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(context,"Selecciono No en dialog",Toast.LENGTH_LONG).show();
            }
        });

        // interface para el boton Neutral
        dialogoAlerta.setNeutralButton("neutro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(context,"Selecciono Neutral en dialog",Toast.LENGTH_LONG).show();
            }
        });

        // cancelar sin aplastar un boton
        dialogoAlerta.setCancelable(true);

        dialogoAlerta.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,"Se aplasto un item"+items[which],Toast.LENGTH_LONG).show();

            }
        });


        dialogoAlerta.create();
        dialogoAlerta.show();
    }

    public void mostrarSingleChooseDialogo(final Context context){

        final CharSequence[] items = {"Articulo1","Articulo2","Articulo3","Articulo4"};

        AlertDialog.Builder dialogoAlerta= new AlertDialog.Builder(context);
        dialogoAlerta.setTitle("Mensaje - Titulo");

        // interface para el boton Positive
        dialogoAlerta.setPositiveButton("si, creo?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(context,"Selecciono Si en dialog",Toast.LENGTH_LONG).show();
            }
        });

        // interface para el boton Negative
        dialogoAlerta.setNegativeButton("nop?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(context,"Selecciono No en dialog",Toast.LENGTH_LONG).show();
            }
        });

        // interface para el boton Neutral
        dialogoAlerta.setNeutralButton("neutro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(context,"Selecciono Neutral en dialog",Toast.LENGTH_LONG).show();
            }
        });

        // cancelar sin aplastar un boton
        dialogoAlerta.setCancelable(true);

        dialogoAlerta.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,"single check",Toast.LENGTH_LONG).show();
            }
        });

        dialogoAlerta.create();
        dialogoAlerta.show();
    }

    public void mostrarMultipleChooseDialog(final Context context){
        final CharSequence[] items = {"Articulo1","Articulo2","Articulo3","Articulo4"};
        final ArrayList seleccionados= new ArrayList();

        AlertDialog.Builder dialogoAlerta= new AlertDialog.Builder(context);
        dialogoAlerta.setTitle("Mensaje - Titulo");

// interface para el boton Positive
        dialogoAlerta.setPositiveButton("si, creo?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                String todo="";
                //Log.e("boton si","se presiono si");
                for(int i=0;i<seleccionados.size();i++){
                    todo+=items[Integer.parseInt((String) seleccionados.get(i))].toString();
                    //Log.e("item",items[Integer.parseInt((String) seleccionados.get(i))].toString());
                }

                Toast.makeText(context,todo,Toast.LENGTH_LONG).show();

            }
        });

        // interface para el boton Negative
        dialogoAlerta.setNegativeButton("nop?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(context,"Selecciono No en dialog",Toast.LENGTH_LONG).show();
            }
        });

        dialogoAlerta.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Log.e("ischeked:",Boolean.toString(isChecked));
                Log.e("which:",Integer.toString(which));

                if (isChecked){
                    // seleccione algo
                    seleccionados.add(Integer.toString(which));
                }else{
                    //no seleccione nada
                    // ojo
                    seleccionados.remove(Integer.toString(which));
                }
            }
        });
        dialogoAlerta.setCancelable(true);
        dialogoAlerta.create();
        dialogoAlerta.show();
    }
}
