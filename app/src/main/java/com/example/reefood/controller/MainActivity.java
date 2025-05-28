package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;


public class MainActivity extends BaseActivity {

    // Declaración de elementos UI
    private ImageView backgroundImage;
    private ImageView logo;
    private static final int SPLASH_DURATION = 3000; // 3 segundos para la pantalla splash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración para modo inmersivo - oculta barras del sistema
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Inicialización de vistas
        backgroundImage = findViewById(R.id.background_image);
        logo = findViewById(R.id.logo);

        // Hacer las vistas visibles antes de animar
        backgroundImage.setVisibility(View.VISIBLE);
        logo.setVisibility(View.VISIBLE);

        // Animación para la imagen de fondo
        Animation backgroundAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        backgroundImage.startAnimation(backgroundAnim);

        // Delay corto antes de iniciar la animación del logo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Animación del logo con un pequeño retraso para efecto visual
                Animation logoAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.logo_animation);
                logo.startAnimation(logoAnim);
            }
        }, 300); // Pequeño retraso para secuenciar animaciones

        // Configuración de transición automática a pantalla de login
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
                startActivity(intent);
                finish(); // Cierra esta actividad para que no se pueda volver atrás
            }
        }, SPLASH_DURATION);
    }

}