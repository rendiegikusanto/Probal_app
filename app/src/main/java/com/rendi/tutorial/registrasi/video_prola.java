package com.rendi.tutorial.registrasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class video_prola extends AppCompatActivity {
    Button btn_keluar;
    VideoView videov;
    MediaController mediaC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_prola);
        btn_keluar = (Button)findViewById(R.id.btn_keluar);
        videov = (VideoView) findViewById(R.id.videoView);
        mediaC = new MediaController(this);

        String videopath ="android.resource://com.rendi.tutorial.registrasi/"+R.raw.prola;
        Uri uri = Uri.parse(videopath);
        videov.setVideoURI(uri);
        videov.setMediaController(mediaC);
        mediaC.setAnchorView(videov);




        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
            }

        });
    }
}