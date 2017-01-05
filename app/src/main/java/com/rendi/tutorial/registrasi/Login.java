package com.rendi.tutorial.registrasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
  Button regis;
    Button btn_masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regis = (Button) findViewById(R.id.btn_regis);
        btn_masuk = (Button)findViewById(R.id.btn_wisata);


        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Menu_utama.class);
                startActivity(intent);
            }
        });


        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Registrasi.class);
                startActivity(intent);
            }
        });
    }
}
