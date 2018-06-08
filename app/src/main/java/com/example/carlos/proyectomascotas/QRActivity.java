package com.example.carlos.proyectomascotas;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.StringTokenizer;

import com.example.carlos.proyectomascotas.control.LeerEscribirArchivos;
import com.example.carlos.proyectomascotas.modelo.Configuration;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUESTCAMERA = 1;
    private ZXingScannerView scannerView;
    private  static int camID= Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        // permisos a camera
        scannerView = new ZXingScannerView(this);
        // cuando se abre el activitu va directo a la camera
        setContentView(scannerView);

        // verifico version sdk superior a sdk 23
        if(Build.VERSION.SDK_INT>=22){
            if (tienePermisos()){
                Toast.makeText(getApplicationContext(),"No tiene permiso",Toast.LENGTH_LONG);
            }else{
                solicitarPermiso();
            }

        }
    }


    public boolean tienePermisos(){
        return ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;

    }

    public void solicitarPermiso(){
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA},REQUESTCAMERA);

    }

    // metodo para bloquear camera cuando dejas de usar
    @TargetApi(22)
    @Override
    protected void onResume() {
        super.onResume();
        int apiVersion = Build.VERSION.SDK_INT;
        if(apiVersion>=22){
            if(scannerView==null){
                scannerView= new ZXingScannerView(this);
                setContentView(scannerView);
            }

            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }else{
            solicitarPermiso();
        }
    }

    // para la camara al finalizar la app
    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    // administro resultado de permisos
    @TargetApi(22)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUESTCAMERA:
                if(grantResults.length>0){
                    boolean aceptaPermiso = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(aceptaPermiso){
                        Toast.makeText(getApplicationContext(),"SE acepto :v",Toast.LENGTH_LONG);
                    }else{
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                                // dialogo
                                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUESTCAMERA);

                                solicitarPermiso();
                            }
                        }
                    }
                }
        }
    }

    @Override
    public void handleResult(Result result) {

        // AlertDialog.Builder alerDialog= new AlertDialog.Builder(this);
        //resultado
        //alerDialog.setMessage(result.getText());
        // formato codigo de barras
        //alerDialog.setMessage(result.getBarcodeFormat());

        //alerDialog.show();

        Log.e("result",result.getText());
        Log.e("resultQR",result.getBarcodeFormat().toString());

        /***********lo que se debe hacer capturado el QR************/
        LeerEscribirArchivos leerEscribirArchivos=new LeerEscribirArchivos();
        Configuration configurationActual=leerEscribirArchivos.leerArchivo("configuration.bin");

        if(configurationActual.getUrl()==result.getText()){
            crearDialogoAlert();
        }else{
            configurationActual.setUrl(result.getText());
            leerEscribirArchivos.escribirArchivo(configurationActual,"configuration.bin");
        }
    }

    public void crearDialogoAlert(){
        // Builder para crear la alerta
        // this = getaplicationcontext
        AlertDialog.Builder dialogoAlerta= new AlertDialog.Builder(this);
        dialogoAlerta.setTitle("Mensaje");
        dialogoAlerta.setMessage("El dispositivo ya fue refistrado anteriormente");

        // interface para el boton Positive
        dialogoAlerta.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lo q sucede al dar clic
                Toast.makeText(getApplicationContext(),"Registre otro dispositivo",Toast.LENGTH_LONG).show();
            }
        });


        // cancelar sin aplastar un boton
        dialogoAlerta.setCancelable(true);

        dialogoAlerta.create();
        dialogoAlerta.show();
    }
}
