package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.ConsultasServiceWeb;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Usuario;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    // variables para logueo de gmail
    private GoogleApiClient googleApiClient;
    private final int CODEgoogle =9001;
    private int CODEfacebook;
    Button botonIngresar;
    EditText nombre;
    EditText password;
    //service
    ConsultasServiceWeb consultasServiceWeb;
    ServiceWeb serviceWeb = new ServiceWeb() ;
    Boolean loginGmailFb = false;
    //varaibles fb
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombre = (EditText)findViewById(R.id.nombreLoggin);
        password = (EditText)findViewById(R.id.passwordLoggin);
        botonIngresar = (Button)findViewById(R.id.buttonMenu);
        SignInButton botonGoogle = (SignInButton) findViewById(R.id.googlebutton);
        //envio contexto para abrir activity desde afuera
        consultasServiceWeb = new ConsultasServiceWeb();
        consultasServiceWeb.obtenerContexto(LoginActivity.this);
        //Gmail
        botonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logeoGmail();
            }
        });

        //Login -> irAMenu
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Authentificacion de Usuario comun
                //if(!loginGmailFb){
                if (!exitenCamposVacios()){
                    Usuario user = consultasServiceWeb.autentificarUsuarioComun(
                            nombre.getText().toString(),
                            password.getText().toString()
                    );
                    limpiarCampos();
                    Log.e("Sync calls","Acabe auth");
//                    if(user != null){
//                        irALoggearseConMac();
//                    }else{
//                        Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });

        //Facebook
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        // If using in a fragment
        //loginButton.setFragment(this);
        //facebook
        callbackManager = CallbackManager.Factory.create();
        /***************************FACEBOOK*******************/
        CODEfacebook=FacebookSdk.getCallbackRequestCodeOffset();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    ProfileTracker mProfileTracker;
                    Profile perfil;

                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        if(Profile.getCurrentProfile() == null) {
                            mProfileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                    perfil=currentProfile;
                                    Log.e("fb Nombre",perfil.getName());
                                    Log.e("fb id",perfil.getId());
                                    //Log.e("fb uri",perfil.getProfilePictureUri(100,200).toString());

                                    //serviceLoginGmailFB(acc.getEmail(), acc.getDisplayName());
                                    consultarServicioParaGmailFb(perfil.getName(),perfil.getId());

                                    //detener profile tracker
                                    mProfileTracker.stopTracking();
                                }
                            };
                            // no need to call startTracking() on mProfileTracker
                            // because it is called by its constructor, internally.
                        }
                        else {
                            perfil = Profile.getCurrentProfile();

                            //serviceLoginGmailFB
                            consultarServicioParaGmailFb(perfil.getName(),perfil.getId());

                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        consultasServiceWeb.obtenerContexto(LoginActivity.this);
    }

    public void logeoGmail(){
        if(googleApiClient!=null){
            //desconecto
            googleApiClient.disconnect();
        }
        // login en google por default solicita email y construye el inicio de sesion
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        // autentifica al cliente con las opciones tomadas arriba
        googleApiClient= new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();

        // abrir ventana google
        Intent signIntend= Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        // programo actividad para q retorno el resultado del logueo
        startActivityForResult(signIntend, CODEgoogle);
    }

    //capturo resultado del logueo gmail
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== CODEgoogle){

            // obtengo resultado del logueo
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){
                // logueo exitoso obtengo cuenta
                GoogleSignInAccount acc = result.getSignInAccount();
                // obtengo datos
                Log.e("email",acc.getEmail());
                Log.e("email",acc.getDisplayName());
                Log.e("email",acc.getId());

                //servicio web
                consultarServicioParaGmailFb(acc.getDisplayName(),acc.getId());

            }
        }else if(requestCode==CODEfacebook){
            // metodo de  result en fb
            if(callbackManager.onActivityResult(requestCode, resultCode, data)){
                return;
            }

        }
    }

    public void consultarServicioParaGmailFb(String name, String idCuenta){
        limpiarCampos();
        consultasServiceWeb.verificarExisteGmailFb(name, idCuenta);

//        if(!existeCuenta){
//            consultasServiceWeb.crearCuentaGmailFb(name, idCuenta);
//                Toast.makeText(getApplicationContext(),"Usuario Gmail/Fb Creado",Toast.LENGTH_SHORT).show();
//                Usuario user = consultasServiceWeb.autentificarUsuarioGmailFb(idCuenta);
//                if(user != null){
//                    irALoggearseConMac();
//                }

//        }else{
//            Usuario user = consultasServiceWeb.autentificarUsuarioGmailFb(idCuenta);
//            if(user != null){
//                irALoggearseConMac();
//            }
//        }
    }

    public void registrarUsuario(View view) {
        Intent intent = new Intent(getApplicationContext(), CamaraActivity.class);
        startActivity(intent);
    }

    public void irALoggearseConMac(){
        Intent intentPrincipal = new Intent(getApplicationContext(),RegistrarMacActivity.class);
        startActivity(intentPrincipal);
    }

    public boolean exitenCamposVacios(){
        boolean error = false;
        if(nombre.getText().length() == 0){
            nombre.setError("Nombre es obligatorio");
            error = true;
        }
        if(password.getText().length() == 0){
            password.setError("Password es obligatorio");
        }
        return error;
    }

    public void limpiarCampos(){
        nombre.getText().clear();
        password.getText().clear();
    }

    public void empezarNuevaActivity(final String activity_to_start){
        if (activity_to_start.equals("ACTIVITY_REG_MAC")){
            Intent intent = new Intent(LoginActivity.this, RegistrarMacActivity.class);
            startActivity(intent);
        }
    }
}
