package com.example.carlos.proyectomascotas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class CamaraActivity extends YouTubeBaseActivity{
    Switch switchMicrofono;
    YouTubePlayerView viewYoutube;
    ImageView imagenMicro;

    // para youtube player
    YouTubePlayer.OnInitializedListener onInitializedListener;
    String claveYoutube="AIzaSyCMHEwtHCjNMCrqWunXe8_Exm0BFxCzib0";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        switchMicrofono=(Switch)findViewById(R.id.switch1);
        viewYoutube=(YouTubePlayerView)findViewById(R.id.viewYoutube);
        imagenMicro=(ImageView)findViewById(R.id.imageView);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(b){
                    Log.e("logro","LO LOGRO");
                    youTubePlayer.cueVideo("Nc7sTqdRpHc");
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("fallo:","ESTAMOS EN EL FALLO");
            }
        };

        viewYoutube.initialize(claveYoutube,onInitializedListener);


        switchMicrofono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchMicrofono.isChecked()){
                    imagenMicro.setBackgroundResource(R.drawable.microfonoon);
                    //transmitir audio
                }else{
                    imagenMicro.setBackgroundResource(R.drawable.microfonooff);
                    //dejar de transmitir audio
                }
            }
        });
    }

}
