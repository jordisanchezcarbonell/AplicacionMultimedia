package com.example.proyectopdm.General;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectopdm.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class EditarArchivos extends AppCompatActivity {

    /* ----------------------------------------------------------------------------*
    Función:SaveImage*
    Paràmetros: Bitmap
    Descripción:Guarda el Bitmap pasado por paramentro en una carpeta indicada y con las modificaciones
    * -------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editararchivos);

        /*guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap zoomedBitmap= Bitmap.createScaledBitmap(image, photoView.getWidth(), photoView.getHeight(), true);


                SaveImage(zoomedBitmap);
            }
        })*/


    }
    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Nuevacarpeta");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);

            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // finalBitmap.compress(Bitmap.CompressFormat.JPEG, ,90, out);
            out.flush();
            out.close();
            AbleToSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void AbleToSave() {
        Toast.makeText(this, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}