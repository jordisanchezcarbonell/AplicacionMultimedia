package com.example.proyectopdm.General;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.proyectopdm.Audio.Audio;
import com.example.proyectopdm.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;


/* ============================================================================*
 Archivo:CapturarArchivos.c*

Descripción: Contiene toda las funciones donde grabaremos audio,haremos fotos e videos.
* ========================================================================= */
public class CapturarArchivos  extends AppCompatActivity {

    private final int PERMISO_CONCEDIDO = 1;
    private final int Permisoaudio0=2;
    Uri fileUri;
    MediaController mediaController;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    String[] permiso = {Manifest.permission.CAMERA};
    VideoView videoView;
    ImageView imageView;
    PhotoView photoView;
    Button guardar;
    Bitmap image;
    private static final int VIDEO_CAPTURE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capturarimagenes_video);
        Button btnCamara = findViewById(R.id.btnCamera);
        Button GrabarAudio = findViewById(R.id.GrabarAudio);
        //videoView = findViewById(R.id.videoview);
        mediaController = new MediaController(this);
        //imageView = findViewById(R.id.IV1);
        photoView = (PhotoView) findViewById(R.id.IV1);

//Comprobamos si Tiene camara


        if (!hasCamera()) {
            btnCamara.setEnabled(false);
        }
//Buton para capturar imagen,video y Pe//
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                        } else {

                            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISO_CONCEDIDO);

                        }
                    } else {
                        abrirCamara();
                    }
                } else {
                    abrirCamara();
                }

            }
        });

        GrabarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
                    if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        //
                        if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {

                        } else {

                            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, PERMISO_CONCEDIDO);

                        }
                    } else {
                        abrirgrabadoraudio();
                    }
                } else {
                    abrirgrabadoraudio();
                }

            }


        });

    }



    /* ----------------------------------------------------------------------------*
Función:HashCamaera*
Paràmetros:int *Ninguno*
Descripción:Miramos si el movil tiene camara o no
* -------------------------------------------------------------------------*/
    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        } else {
            return false;
        }
    }


  /* ----------------------------------------------------------------------------*
Función:abrirCamara*
Paràmetros:int *Ninguno*
Descripción:Se hace el intent  que abrira la camara del movil
* -------------------------------------------------------------------------*/

    public void abrirCamara() {
        /*Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);*/


        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);

        startActivityForResult(intent, VIDEO_CAPTURE);

        //startActivityForResult(intent, VIDEO_CAPTURE);
    }
    /* ----------------------------------------------------------------------------*
  Función:abrirgrabadoraudio*
  Paràmetros:int *Ninguno*
  Descripción:Se hace el intent  que abrira la classe donde grabaremos el Audio
  * -------------------------------------------------------------------------*/
    public void abrirgrabadoraudio(){
        Intent intent = new Intent(this,Audio.class);
        startActivity(intent);

    }

}

