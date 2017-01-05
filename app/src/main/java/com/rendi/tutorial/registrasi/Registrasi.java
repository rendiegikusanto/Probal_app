package com.rendi.tutorial.registrasi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.rendi.tutorial.registrasi.config;

import java.util.HashMap;

public class Registrasi extends AppCompatActivity {
    Button btn_daftar;

    EditText namalengkap;
    EditText username;
    EditText password;
    EditText confrimpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi2);
        namalengkap= (EditText)findViewById(R.id.txt_namalengkap);
        username = (EditText)findViewById(R.id.txt_username);
        password = (EditText)findViewById(R.id.txt_pass);
        confrimpass = (EditText)findViewById(R.id.txt_confrimpass);
        btn_daftar = (Button)findViewById(R.id.btn_daftar);


        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String nama_lengkap = namalengkap.getText().toString().trim();
                final String username1 = username.getText().toString().trim();
                final String password1 = password.getText().toString().trim();
                final String confrim_password = confrimpass.getText().toString().trim();



                namalengkap.setText("");
                password.setText("");
                confrimpass.setText("");
                username.setText("");



                //pembuatan class asyntask yang berfungsi untuk koneksi ke database server

                class TambahData extends AsyncTask<Void,Void,String> {

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(Registrasi.this,"Proses Kirim Data...","Wait...",false,false);

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(Registrasi.this, s, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registrasi.this,Login.class);
                        startActivity(intent);
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String,String> params = new HashMap<>();
                        //Sesuaikan bagian ini dengan field di tabel mahasiswa
                        params.put(config.KEY_EMP_Nama_Lengkap,nama_lengkap);
                        params.put(config.KEY_EMP_User_Name,username1);
                        params.put(config.KEY_EMP_Password,password1);
                        params.put(config.KEY_EMP_Confrim_Password,confrim_password);

                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(config.URL_ADD1,params);
                        return res;

                    }
                }
                //jadikan class tambahdata sebagai object baru
                TambahData ae = new TambahData();
                ae.execute();
            }
        });

    }
}
