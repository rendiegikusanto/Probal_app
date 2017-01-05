package com.rendi.tutorial.registrasi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class Tambah_lokasi extends AppCompatActivity {
    Button btn_simpan;
    EditText namalokasi;
    EditText latitude1;
    EditText longitude1;
    EditText alamat1;
    EditText dataparam;

    ImageView imageView;



    Button tambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lokasi);
        namalokasi= (EditText)findViewById(R.id.txt_namalokasi);
        latitude1 = (EditText)findViewById(R.id.txt_lati);
        longitude1 = (EditText)findViewById(R.id.txt_long);
        alamat1 = (EditText)findViewById(R.id.txt_alamat);
        dataparam = (EditText)findViewById(R.id.txt_parameter);
        btn_simpan = (Button)findViewById(R.id.btn_simpan);

        imageView = (ImageView)findViewById(R.id.imageView_tambah);






        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String nama_lokasi = namalokasi.getText().toString().trim();
                final String latitude = latitude1.getText().toString().trim();
                final String longitude = longitude1.getText().toString().trim();
                final String alamat = alamat1.getText().toString().trim();
                final String data_param = dataparam.getText().toString().trim();

                namalokasi.setText("");
                latitude1.setText("");
                longitude1.setText("");
                alamat1.setText("");
                dataparam.setText("");


                //pembuatan class asyntask yang berfungsi untuk koneksi ke database server

                class TambahData extends AsyncTask<Void,Void,String> {

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(Tambah_lokasi.this,"Proses Kirim Data...","Wait...",false,false);

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(Tambah_lokasi.this, s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String,String> params = new HashMap<>();
                        //Sesuaikan bagian ini dengan field di tabel mahasiswa
                        params.put(config.KEY_EMP_nama_lokasi,nama_lokasi);
                        params.put(config.KEY_EMP_latitude,latitude);
                        params.put(config.KEY_EMP_longitude,longitude);
                        params.put(config.KEY_EMP_alamat,alamat);
                        params.put(config.KEY_EMP_data_parameter,data_param);
                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(config.URL_ADD,params);
                        return res;

                    }
                }
                //jadikan class tambahdata sebagai object baru
                TambahData ae = new TambahData();
                ae.execute();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });



    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Tambah_lokasi.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    imageView.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path from gallery..**..", picturePath+"");
                //viewImage.setImageBitmap(thumbnail);
                imageView.setImageBitmap(thumbnail);


            }
        }
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

      



    }
}
