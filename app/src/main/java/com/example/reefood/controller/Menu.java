package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.reefood.R;
import com.example.reefood.controller.Configuraciones;
import com.example.reefood.controller.Donador;
import com.example.reefood.controller.MisDonaciones;
import com.example.reefood.controller.Publicaciones;
import com.example.reefood.databinding.AccionesbtnaddBinding;
import com.example.reefood.databinding.ActivityMenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Menu extends AppCompatActivity implements View.OnClickListener {

    private ActivityMenuBinding binding;
    private AccionesbtnaddBinding accionesBinding;
    private boolean menuVisible = false;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.botonesdenavegacion);

        // Botones del menú inferior
        binding.botonesdenavegacion.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.btnhome) {
                startActivity(new Intent(this, Publicaciones.class));
            } else if (id == R.id.btnperfil) {
                startActivity(new Intent(this, MisDonaciones.class));
            } else if (id == R.id.btnConfiguraciones) {
                startActivity(new Intent(this, Configuraciones.class));
            }
            return true;
        });

        bottomNavigationView.setBackground(null);//quitar la sombra mlp esa

        // FAB para mostrar acciones
        binding.fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.fab) {
            mostrarMenuAcciones();
        } else if (id == R.id.cancelButton) {
            cerrarMenuAcciones();
        } else if (id == R.id.layoutnewdonacion) {
            cerrarMenuAcciones();
            startActivity(new Intent(this, Donador.class));
        } else if (id == R.id.layoutnewchat) {
            cerrarMenuAcciones();

        }
    }

    private void mostrarMenuAcciones() {
        if (!menuVisible) {

            // Ocultar el FAB
            binding.fab.hide();

            // Crear el menú de acciones
            accionesBinding = AccionesbtnaddBinding.inflate(getLayoutInflater());

            // Ponerlo abajo de la pantalla
            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
                    CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = android.view.Gravity.BOTTOM;
            accionesBinding.getRoot().setLayoutParams(params);

            // Agregar al layout principal
            binding.main.addView(accionesBinding.getRoot());

            // Configurar los clicks de los botones
            accionesBinding.cancelButton.setOnClickListener(this);
            accionesBinding.layoutnewdonacion.setOnClickListener(this);
            accionesBinding.layoutnewchat.setOnClickListener(this);

            // Animación de entrada
            Animation animacion = AnimationUtils.loadAnimation(this, R.anim.entrada);
            accionesBinding.getRoot().startAnimation(animacion);

            menuVisible = true;
        }
    }

    private void cerrarMenuAcciones() {
        if (menuVisible && accionesBinding != null) {
            Animation animacion = AnimationUtils.loadAnimation(this, R.anim.salida);
            animacion.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {}
                public void onAnimationRepeat(Animation animation) {}
                public void onAnimationEnd(Animation animation) {
                    binding.main.removeView(accionesBinding.getRoot());
                    accionesBinding = null;
                    menuVisible = false;
                    binding.fab.show();  // Muestra el FAB de nuevo
                }
            });
            accionesBinding.getRoot().startAnimation(animacion);
        }
    }

    @Override
    public void onBackPressed() {
        if (menuVisible) {
            cerrarMenuAcciones();
        } else {
            super.onBackPressed();
        }
    }
}