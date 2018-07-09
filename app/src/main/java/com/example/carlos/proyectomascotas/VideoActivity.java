package com.example.carlos.proyectomascotas;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView=(VideoView)findViewById(R.id.videoView3);
        webView=(WebView) findViewById(R.id.webView);

        //video view
        Uri videoUri = Uri.parse("http://192.168.5.10:300/Usuario/obtenerVideo");
        MediaController controller= new MediaController(this);
        //controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.start();

        webView.loadUrl("http://192.168.5.10:300/Usuario/obtenerVideo");


    }
}
