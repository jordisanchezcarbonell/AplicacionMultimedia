package com.example.proyectopdm.General;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.proyectopdm.Audio.ReproducirAudio;
import com.example.proyectopdm.Imagenes.ImagenfullScreen;
import com.example.proyectopdm.R;
import com.example.proyectopdm.Video.Video;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ReproducirArchivo extends AppCompatActivity {
    private final int PERMISO_CONCEDIDO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    VideoView videoView;
    ImageView imageView;
    PhotoView photoView;
    MediaController mediaController;
    Button guardar;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagen_toda_pantalla);
        Button btrepdroducir = findViewById(R.id.Reproducir);

      //  videoView = findViewById(R.id.videoview);
        mediaController = new MediaController(this);
        //imageView = findViewById(R.id.IV1);
        photoView = (PhotoView) findViewById(R.id.IV1);
        btrepdroducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No se necesita dar una explicación al usuario, sólo pedimos el permiso.
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISO_CONCEDIDO);
                            // MY_PERMISSIONS_REQUEST_CAMARA es una constante definida en la app. El método callback obtiene el resultado de la petición.
                        }


                    } else { //have permissions
                        abrirfichero();
                    }
                } else { // Pre-Marshmallow
                    abrirfichero();
                }

            }
        });
    }

    private void abrirfichero() {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");

        startActivityForResult(fileintent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {

                String path = String.valueOf(data.getData());


                Uri mVideoURI = data.getData();


                if (path.contains("video"))
                {

                    Intent intent = new Intent(this, Video.class);
                    intent.putExtra("VIDEO_URI", mVideoURI.toString());
                    startActivity(intent);


                }

                else if (path.contains("image"))
                {

                    Uri selectedImage = data.getData();
                    InputStream inputStream;

                    try {
                        inputStream = getContentResolver().openInputStream(selectedImage);

                        image = BitmapFactory.decodeStream(inputStream);

                        Intent intentcapturar = new Intent(this, ImagenfullScreen.class);
                        ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG, 20, _bs);
                        byte[] byteArray = _bs.toByteArray();
                        intentcapturar.putExtra("objectEsport", byteArray);

                        startActivity(intentcapturar);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }



                }

                else if(path.contains("mp3")){

                    Intent intentRaudio = new Intent(this, ReproducirAudio.class);
                    intentRaudio.putExtra("Aduio", mVideoURI.toString());
                    startActivity(intentRaudio);

                }
            }
        }
    }





}
