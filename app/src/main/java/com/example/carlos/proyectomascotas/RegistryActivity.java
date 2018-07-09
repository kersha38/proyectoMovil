package com.example.carlos.proyectomascotas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.Dialogo;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.control.TareasAsync.TareaRegistrarUsuario;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistryActivity extends AppCompatActivity {

    // elementos interfaz
    EditText txtName;
    EditText txtMail;
    EditText txtPassword;
    EditText txtConfirm;
    Button btnRegistrar;
    //ServiceWeb serviceWeb = new ServiceWeb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        // inicializo
        txtName=(EditText)findViewById(R.id.editTextName);
        txtMail=(EditText)findViewById(R.id.editTextMail);
        txtPassword=(EditText)findViewById(R.id.editTextPassword);
        txtConfirm=(EditText)findViewById(R.id.editTextPassword2);
        btnRegistrar=(Button) findViewById(R.id.buttonRegistrarse);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //validaciones
            if(!camposVacios()) {
                if(txtPassword.getText().toString().equals(txtConfirm.getText().toString())){
                    TareaRegistrarUsuario tarea = new TareaRegistrarUsuario(RegistryActivity.this);
                    tarea.execute(
                            txtName.getText().toString(),
                            txtPassword.getText().toString(),
                            txtMail.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"Password no iguales",Toast.LENGTH_SHORT).show();
                }
            }else{

            }


            }
        });
    }

    public boolean camposVacios(){
        boolean errores=false;
        if(txtName.getText().length() == 0){
            txtName.setError("Nombre es obligatorio");
            errores = true;
        }
        if(txtMail.getText().length() == 0){
            txtMail.setError("Email es obligatorio");
            errores = true;
        }
        if(txtPassword.getText().length() == 0){
            txtPassword.setError("Password es obligatorio");
            errores = true;
        }
        if(txtConfirm.getText().length() == 0){
            txtConfirm.setError("Confirme el password");
            errores = true;
        }
        return errores;
    }

}
