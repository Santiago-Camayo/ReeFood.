package com.example.reefood.controller;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

// Clase base para actividades que usan modo oscuro
// Configuraciones hereda de esta clase para manejar el tema
public abstract class BaseActivity extends AppCompatActivity {
    // Preferencias compartidas con Configuraciones
    protected static final String NOMBRE_PREFERENCIAS = "preferencias_app"; // Nombre del archivo
    protected static final String CLAVE_MODO_OSCURO = "modo_oscuro"; // Clave para el tema

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Aplicar el tema ANTES de crear la vista (evita parpadeo)
        aplicarTemaGuardado();
        super.onCreate(savedInstanceState);
    }

    // Aplica el tema guardado (claro/oscuro)
    protected void aplicarTemaGuardado() {
        boolean modoOscuro = obtenerPreferenciaTema();

        // Configura el tema de la app
        AppCompatDelegate.setDefaultNightMode(
                modoOscuro ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    // Obtiene si el modo oscuro está activado
    protected boolean obtenerPreferenciaTema() {
        // false = valor por defecto si no existe
        return obtenerPreferencias().getBoolean(CLAVE_MODO_OSCURO, false);
    }

    // Guarda la preferencia del tema
    protected void guardarPreferenciaTema(boolean modoOscuro) {
        // Guarda el valor (apply = guardado en segundo plano)
        obtenerPreferencias().edit()
                .putBoolean(CLAVE_MODO_OSCURO, modoOscuro)
                .apply();
    }

    // Metodo helper para acceder a la preferencia
    protected SharedPreferences obtenerPreferencias() {
        return getSharedPreferences(NOMBRE_PREFERENCIAS, MODE_PRIVATE);
    }
}