package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carlos.proyectomascotas.control.JSONResponse;
import com.example.carlos.proyectomascotas.control.RequestInterface;
import com.example.carlos.proyectomascotas.control.ServiceWeb;
import com.example.carlos.proyectomascotas.modelo.Mensaje;
import com.example.carlos.proyectomascotas.modelo.Usuario;
import com.facebook.AccessToken;
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

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    // variables para logueo de gmail
    private GoogleApiClient googleApiClient;
    private final int CODEgoogle =9001;
    private int CODEfacebook;
    Button botonIngresar;
    //service
    ServiceWeb serviceWeb = new ServiceWeb() ;
    Boolean loginGmailFb = false;
    //varaibles fb
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        botonIngresar = (Button)findViewById(R.id.buttonMenu);
        SignInButton botonGoogle = (SignInButton) findViewById(R.id.googlebutton);

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
                if(!loginGmailFb){
                    serviceWeb
                            .getJSONObjeto()
                            .getUsuarioAuth("Pao","12345")
                            .enqueue(new Callback<Usuario>() {
                                         @Override
                                         public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                             Usuario jsonResponse = response.body();
                                             Log.e("Contenido: ",jsonResponse.getNickname()+"");
                                             if(jsonResponse != null){
                                                 Intent intent = new Intent(getApplicationContext(), RegistrarMacActivity.class);
                                                 startActivity(intent);
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Auth invalida", Toast.LENGTH_SHORT).show();
                                             }

                                         }

                                         @Override
                                         public void onFailure(Call<Usuario> call, Throwable t) {
                                             Log.e("Erro..!!", t.getMessage());
                                         }
                                     }
                            );
                }else{
                    //authenticacion usuario GAMIL / FB
                    serviceWeb
                            .getJSONObjeto()
                            .getUsuarioGmailFbAuth("pao@gmail.com")
                            .enqueue(new Callback<Usuario>() {
                                         @Override
                                         public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                             Usuario jsonResponse = response.body();
                                             Log.e("Contenido: ",jsonResponse.getNickname()+"");
                                             if(jsonResponse != null){
                                                 Toast.makeText(getApplicationContext(), "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                                                 Intent intent = new Intent(getApplicationContext(), RegistrarMacActivity.class);
                                                 startActivity(intent);
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Auth invalida", Toast.LENGTH_SHORT).show();
                                             }

                                         }

                                         @Override
                                         public void onFailure(Call<Usuario> call, Throwable t) {
                                             Log.e("Erro..!!", t.getMessage());
                                         }
                                     }
                            );
                }


            }
        });

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
                                    Log.e("fb first",perfil.getFirstName());
                                    Log.e("fb last",perfil.getLastName());
                                    Log.e("fb uri",perfil.getProfilePictureUri(100,200).toString());

                                    //serviceLoginGmailFB(acc.getEmail(), acc.getDisplayName());


                                    Intent intentPrincipal = new Intent(getApplicationContext(),RegistrarMacActivity.class);
                                    startActivity(intentPrincipal);
                                    mProfileTracker.stopTracking();
                                }
                            };
                            // no need to call startTracking() on mProfileTracker
                            // because it is called by its constructor, internally.
                        }
                        else {
                            perfil = Profile.getCurrentProfile();
                            Log.e("fb Nombre",perfil.getName());
                            Log.e("fb id",perfil.getId());
                            Log.e("fb first",perfil.getFirstName());
                            Log.e("fb last",perfil.getLastName());
                            Log.e("fb uri",perfil.getProfilePictureUri(100,200).toString());

                            //serviceLoginGmailFB(acc.getEmail(), acc.getDisplayName());


                            Intent intentPrincipal = new Intent(getApplicationContext(),RegistrarMacActivity.class);
                            startActivity(intentPrincipal);
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

                //consulto al servicio
                //serviceLoginGmailFB(acc.getEmail(), acc.getDisplayName());

            }
        }else if(requestCode==CODEfacebook){
            // metodo de  result en fb
            if(callbackManager.onActivityResult(requestCode, resultCode, data)){
                return;
            }

        }
    }

    public void registrarUsuario(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistryActivity.class);
        startActivity(intent);
    }

    public void serviceLoginGmailFB(final String email, final String displayName){
        //verificar si existe correo gmail o facebook
        serviceWeb
                .getJSONObjeto()
                .verificarExisteCuenta(email)
                .enqueue(new Callback<Mensaje>() {
                    @Override
                    public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                        Mensaje jsonResponse= response.body();
                        Log.e("Contenido: ",jsonResponse+"");
                        if(jsonResponse.getMensaje().equals("true")){
                            Toast.makeText(getApplicationContext(), "Ya existe esta cuenta", Toast.LENGTH_SHORT).show();
                        }else{
                            Usuario usuario = new Usuario(displayName, "", email, "", "");
                            serviceWeb
                                    .getJSONObjeto()
                                    .crearUsuarioGmailFb(usuario)
                                    .enqueue(new Callback<Mensaje>() {
                                        @Override
                                        public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {
                                            Mensaje jsonResponse= response.body();
                                            if(jsonResponse.getMensaje().equals("usuarioCreado")){
                                                loginGmailFb = true;
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Mensaje> call, Throwable t) {
                                            Log.e("Erro..!!", t.getMessage());
                                        }
                                    });
                        }

                    }

                    @Override
                    public void onFailure(Call<Mensaje> call, Throwable t) {
                        Log.e("Erro..!!", t.getMessage());
                    }
                });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Retrofit[] retrofit = {new Retrofit.Builder()
//                .baseUrl("http://192.168.1.6:3000")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()};
//        RequestInterface request = retrofit[0].create(RequestInterface.class);
//
//        //Usuario usuario = new Usuario("Pao", "12345", "", "","");
//        Call<JSONResponse> call = request.getUsuarioAuth("Pao", "12345");
//        call.enqueue(new Callback<JSONResponse>() {
//            @Override
//            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
//                JSONResponse jsonResponse = response.body();
//                ArrayList a = new ArrayList<> (Arrays.asList(jsonResponse.getUsuarios()));
//
//                Log.e("Contenido: ", ((Usuario) a.get(0)).getNickname()+"");
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse> call, Throwable t) {
//                Log.e("Erro..!!", t.getMessage());
//            }
//        });
//    }

}
