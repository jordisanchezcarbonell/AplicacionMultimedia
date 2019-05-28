package com.example.proyectopdm.Imagenes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.proyectopdm.R;
import com.github.chrisbanes.photoview.PhotoView;




/* ----------------------------------------------------------------------------*
Classe donde obtendremos la imagen con un intent y se escalara a toda la pantalla
* -------------------------------------------------------------------------*/

public class ImagenfullScreen  extends Activity {

    PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagenfuulscreen);

        photoView = (PhotoView) findViewById(R.id.IV1);

        Bitmap bmp;

        byte[] byteArray = getIntent().getByteArrayExtra("objectEsport");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);

        photoView.setImageBitmap(bmp);





    }
}
