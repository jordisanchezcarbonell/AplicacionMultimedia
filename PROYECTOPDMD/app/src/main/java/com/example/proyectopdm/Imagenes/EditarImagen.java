package com.example.proyectopdm.Imagenes;
import android.Manifest;
import android.app.Activity;
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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.proyectopdm.Audio.Audio;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;
import com.example.proyectopdm.R;
import com.github.chrisbanes.photoview.PhotoView;

public class EditarImagen extends Activity {
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
    Bitmap bmp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editarimagenbotton);

        photoView = (PhotoView) findViewById(R.id.photoview1);
        Button Guardarimage= findViewById(R.id.buttonguardarimagen);



        byte[] byteArray = getIntent().getByteArrayExtra("objectEsport");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);

        photoView.setImageBitmap(bmp);


        Guardarimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se modifica el bitmap obteniendo su altura,y alzada.
                Bitmap zoomedBitmap= Bitmap.createScaledBitmap(bmp, photoView.getWidth(), photoView.getHeight(), true);
                SaveImage(zoomedBitmap);
            }
        });



    }


    //Funcion para Guardar una Foto a partir de Bitmap,indicando la carpeta donde guardaremos las fotografias y genera el numero de foto aleatorio.
    //Tambien se comprueba si esa misma imaagen existe y si existe se elimina y se crea otra,


    /* ----------------------------------------------------------------------------*
Función:SaveImage*
Paràmetros: Bitmap
Descripción:Guarda el Bitmap pasado por paramentro en una carpeta indicada y con las modificaciones
* -------------------------------------------------------------------------*/
        private void SaveImage (Bitmap finalBitmap){

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Nuevacarpeta");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);

            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // finalBitmap.compress(Bitmap.CompressFormat.JPEG, ,90, out);
            out.flush();
            out.close();
            mostrarToast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Metodo para mostrar el toast


    private void mostrarToast () {
        Toast.makeText(this, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }


}

