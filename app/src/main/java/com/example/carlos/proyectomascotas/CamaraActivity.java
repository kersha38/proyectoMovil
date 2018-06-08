package com.example.carlos.proyectomascotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.VideoView;

public class CamaraActivity extends AppCompatActivity {
    Switch switchMicrofono;
    VideoView videoView;
    ImageView imagenMicro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        switchMicrofono=(Switch)findViewById(R.id.switch1);
        videoView=(VideoView)findViewById(R.id.videoView);
        imagenMicro=(ImageView)findViewById(R.id.imageView);

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
