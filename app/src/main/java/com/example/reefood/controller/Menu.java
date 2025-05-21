package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;


public class Menu extends AppCompatActivity {

    // Declaración de elementos UI
    Button btndonar, btnrecibir;
    ImageButton btnconfiguracion, btneditperfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicialización de componentes visuales
        btndonar = findViewById(R.id.botonDonar);
        btnrecibir = findViewById(R.id.botonrecibir);
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btneditperfil = findViewById(R.id.btnperfil);

        // Configuración del botón Donar - Navega a la pantalla de donador
        btndonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donar = new Intent(Menu.this, Donador.class);
                startActivity(donar);
            }
        });

        // Configuración del botón Recibir - Navega a la pantalla de publicaciones
        btnrecibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recibir = new Intent(Menu.this, Publicaciones.class);
                startActivity(recibir);
            }
        });

        // Configuración del botón de Configuraciones
        btnconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confi = new Intent(Menu.this, Configuraciones.class);
                startActivity(confi);
            }
        });

        // Configuración del botón de perfil - Navega a Mis Donaciones
        btneditperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(Menu.this, MisDonaciones.class);
                startActivity(profile);
            }
        });
    }
}