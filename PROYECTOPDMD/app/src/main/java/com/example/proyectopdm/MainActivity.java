package com.example.proyectopdm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.proyectopdm.General.CapturarArchivos;
import com.example.proyectopdm.General.ReproducirArchivo;
import com.example.proyectopdm.Imagenes.PantalladeEditar;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_conbotones);
        final Button reproducirArchivo = findViewById(R.id.reproducirArchivo);
        final Button editarArchivo = findViewById(R.id.editarArchivo);

        final Button abrircaptura = findViewById(R.id.caputrarArchivo);


        abrircaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcapturar = new Intent(MainActivity.this, CapturarArchivos.class);
                startActivity(intentcapturar);
            }
        });
        reproducirArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcapturar = new Intent(MainActivity.this, ReproducirArchivo.class);
                startActivity(intentcapturar);
            }
        });

        editarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcapturar = new Intent(MainActivity.this, PantalladeEditar.class);
                startActivity(intentcapturar);
            }
        });
    }
}