package com.example.proyectopdm.Imagenes;

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
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.proyectopdm.Imagenes.EditarImagen;
import com.example.proyectopdm.Imagenes.ImagenfullScreen;
import com.example.proyectopdm.R;
import com.example.proyectopdm.Video.EditarVideo;
import com.example.proyectopdm.Video.Video;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

public class PantalladeEditar extends AppCompatActivity {
    private final int PERMISO_CONCEDIDO = 1;
    private final int Permisoaudio0 = 2;
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
        setContentView(R.layout.editarvideoeimagen);

        Button video = findViewById(R.id.ButtonVideo);

        video.setOnClickListener(new View.OnClickListener() {
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
    /* ----------------------------------------------------------------------------*
Función:abrirfichero*
Paràmetros:
Descripción:Se abre el explorador para selecionar la imagen o video ....
* -------------------------------------------------------------------------*/
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

//Dependiendo del contenido del Path ara las funciones indicadas del video o de imagen
                if (path.contains("video"))
                {

                    Intent intent = new Intent(this, EditarVideo.class);
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

                        Intent intentcapturar = new Intent(this, EditarImagen.class);
                        ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 20, _bs);
                        byte[] byteArray = _bs.toByteArray();
                        intentcapturar.putExtra("objectEsport", byteArray);

                        startActivity(intentcapturar);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }



                }



                }
            }
        }
    }



