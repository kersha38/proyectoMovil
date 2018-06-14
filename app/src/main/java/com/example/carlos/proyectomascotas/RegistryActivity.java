package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistryActivity extends AppCompatActivity {

    // elementos interfaz
    EditText txtName;
    EditText txtMail;
    EditText txtPassword;
    EditText txtConfirm;
    Button btnRegistrar;

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

                if(txtPassword.getText().toString().equals(txtConfirm.getText().toString())){
//                    LeerEscribirArchivos archivo=new LeerEscribirArchivos();
//                    archivo.escribirArchivo(
//                            new Usuario(
//                                    txtName.getText().toString(),
//                                    txtPassword.getText().toString(),
//                                    txtMail.getText().toString()),
//                            "registrados.bin");
                    Toast.makeText(getApplicationContext(),"Usuario creado",Toast.LENGTH_LONG).show();
//                    Intent intentLogin=new Intent(getApplicationContext(),RegistrarMacActivity.class);
//                    startActivity(intentLogin);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Erro Registrando",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
