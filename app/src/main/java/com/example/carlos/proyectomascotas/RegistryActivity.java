package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.JSONResponse;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.Usuario;

import java.util.ArrayList;
import java.util.Arrays;

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
    ServiceWeb serviceWeb = new ServiceWeb();

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
                    Usuario usuario = new Usuario(
                            txtName.getText().toString(),
                            txtPassword.getText().toString(),
                            txtMail.getText().toString(), "", "");
                    //creau usuario comun
                    serviceWeb
                            .getJSONObjeto()
                            .crearUsuarioNuevo(usuario)
                            .enqueue(new Callback<Mensaje>() {
                                @Override
                                public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                                    Mensaje jsonResponse = response.body();
                                    Log.e("Resp ServiceWeb::", jsonResponse.getMensaje()+"");
                                    if(jsonResponse.getMensaje().equals("usuarioCreado")){
                                        Toast.makeText(getApplicationContext(),"Usuario creado",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Email ya esta ocupado",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Mensaje> call, Throwable t) {
                                    Log.e("Erro..!!", t.getMessage());
                                }
                            });


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
