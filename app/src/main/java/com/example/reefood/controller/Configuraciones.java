package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.reefood.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class Configuraciones extends BaseActivity {
    private SwitchMaterial swDarkMode;
    private Switch switchNotificaciones;
    private ImageButton botonhome, btneditperfil;
    private TextView txteliminar;
    private Toast currentToast;
    private static final String KEY_NOTIFICATIONS = "notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        initViews();
        setupListeners();
        loadSavedStates();
    }

    private void initViews() {
        swDarkMode = findViewById(R.id.swmodooscuro);
        switchNotificaciones = findViewById(R.id.switch_notificaciones);
        botonhome = findViewById(R.id.homeButton);
        btneditperfil = findViewById(R.id.btnperfil);
        txteliminar = findViewById(R.id.eliminar);
    }

    private void setupListeners() {
        swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean currentPref = getDarkModePreference();
                if (currentPref != isChecked) {
                    saveThemePreference(isChecked);
                    showToast(isChecked ? "Modo oscuro activado" : "Modo oscuro desactivado");

                    AppCompatDelegate.setDefaultNightMode(
                            isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
                    );

                    recreate();
                }
            }
        });

        botonhome.setOnClickListener(v -> finish());
        btneditperfil.setOnClickListener(v -> startActivity(new Intent(this, EditaPerfil.class)));
        txteliminar.setOnClickListener(v -> mostrarDialogoConfirmacion());
    }

    private void loadSavedStates() {
        swDarkMode.setChecked(getDarkModePreference());
        // Desactivar el listener temporalmente
        switchNotificaciones.setOnCheckedChangeListener(null);
        boolean notificationsEnabled = getSharedPreferences().getBoolean(KEY_NOTIFICATIONS, false); // Cambiado a false
        Log.d("Configuraciones", "Loading notifications state: " + notificationsEnabled);
        switchNotificaciones.setChecked(notificationsEnabled);
        // Restaurar el listener
        switchNotificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d("Configuraciones", "Notifications toggled: " + isChecked);
            getSharedPreferences().edit()
                    .putBoolean(KEY_NOTIFICATIONS, isChecked)
                    .apply();
            showToast(isChecked ? "Notificaciones activadas" : "Notificaciones desactivadas");
        });
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    Log.d("Configuraciones", "Cuenta eliminada");
                    showToast("Cuenta eliminada correctamente");
                })
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showToast(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
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
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}