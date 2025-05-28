package com.example.reefood.controller;

import static android.widget.Toast.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.reefood.R;
import com.example.reefood.utils.HelperNavegacion;
import com.google.android.material.switchmaterial.SwitchMaterial;

// Actividad para gestionar configuraciones de usuario, como notificaciones, modo oscuro y eliminación de cuenta
public class Configuraciones extends BaseActivity {

    private Switch switchNotificaciones;
    private SwitchMaterial swDarkMode;

    ImageButton botonhome, btneditperfil;

    View editarperfil;
    LinearLayout btnelminarperfil;
    private HelperNavegacion nav;

    // Variables para mejorar el manejo de Toast y preferencias
    private Toast currentToast;
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String PREFERENCES_NAME = "app_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Transición suave entre actividades
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        // Configuración de la navegación usando HelperNavegacion
        nav = new HelperNavegacion(this);
        nav.configurarNavegacion(
                findViewById(R.id.botonesdenavegacion),
                findViewById(R.id.fab),
                findViewById(R.id.main)
        );

        initViews();
        setupListeners();
        loadSavedStates();
    }

    private void initViews() {
        // Inicializa los switches desde el layout
        switchNotificaciones = findViewById(R.id.switch_notificaciones);
        swDarkMode = findViewById(R.id.swmodooscuro);

        // Vista de texto "Editar perfil"
        editarperfil = findViewById(R.id.Texteditarperfil);

        // Botón para eliminar perfil
        btnelminarperfil = findViewById(R.id.btnelminarperfil);
    }

    private void setupListeners() {
        // Vista de texto "Editar perfil", redirige a EditaPerfil
        editarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configuraciones.this, EditaPerfil.class);
                startActivity(intent);
            }
        });

        // Botón para eliminar perfil, muestra un diálogo de confirmación
        btnelminarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoConfirmacion();
            }
        });
    }

    private void loadSavedStates() {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        // Cargar estado del modo oscuro
        // Desactivar temporalmente el listener para evitar triggers
        swDarkMode.setOnCheckedChangeListener(null);
        boolean darkModeEnabled = prefs.getBoolean(KEY_DARK_MODE, false);
        swDarkMode.setChecked(darkModeEnabled);

        // Aplicar el tema sin recrear la actividad
        AppCompatDelegate.setDefaultNightMode(
                darkModeEnabled ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        // Restaurar el listener después de establecer el estado
        setupDarkModeListener();

        // Cargar estado de notificaciones
        // Desactivar el listener temporalmente para evitar triggers innecesarios
        switchNotificaciones.setOnCheckedChangeListener(null);
        boolean notificationsEnabled = prefs.getBoolean(KEY_NOTIFICATIONS, false);
        Log.d("Configuraciones", "Loading notifications state: " + notificationsEnabled);
        switchNotificaciones.setChecked(notificationsEnabled);

        // Restaurar el listener para notificaciones con funcionalidad mejorada
        switchNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Configuraciones", "Notifications toggled: " + isChecked);
                SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                prefs.edit()
                        .putBoolean(KEY_NOTIFICATIONS, isChecked)
                        .apply();
                mostrarToast(isChecked ? "Notificaciones activadas" : "Notificaciones desactivadas");
            }
        });
    }

    private void setupDarkModeListener() {
        swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Solo proceder si el cambio viene de interacción del usuario
                if (buttonView.isPressed()) {
                    SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                    prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
                    mostrarToast(isChecked ? "Modo oscuro activado" : "Modo oscuro desactivado");

                    AppCompatDelegate.setDefaultNightMode(
                            isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
                    );

                    // Pequeño delay para evitar conflictos
                    buttonView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recreate();
                        }
                    }, 100);
                }
            }
        });
    }

    // Diálogo de confirmación para eliminación de cuenta (mejorado)
    private void dialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción es irreversible y perderás todos tus datos asociados.")
                .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Configuraciones", "Usuario confirmó eliminación.");

                        // Función para eliminar cuenta (no disponible aún)
                        mostrarToast("Procediendo a eliminar cuenta...");

                        Log.i("Configuraciones", "!!! Llama a la función de eliminación del backend AQUÍ !!!");
                    }
                })
                .setNegativeButton("No, cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Registra en el log que el usuario canceló la eliminación
                        Log.d("Configuraciones", "Usuario canceló eliminación.");
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Método mejorado para mostrar Toast (evita múltiples toast superpuestos)
    private void mostrarToast(String mensaje) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentToast != null) {
            currentToast.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        // Usar el sistema de navegación personalizado
        if (!nav.manejarBotonAtras()) {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}