package com.example.reefood.controller;

import static android.widget.Toast.*;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.reefood.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

// Actividad para gestionar configuraciones de usuario, como notificaciones, modo oscuro y eliminación de cuenta
    public class Configuraciones extends AppCompatActivity {


        private Switch switchNotificaciones;
        private SwitchMaterial swDarkMode;


        ImageButton botonhome, btneditperfil;


        View editarperfil;
        LinearLayout btnelminarperfil;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_configuraciones);

            // Inicializa los switches desde el layout
            switchNotificaciones = findViewById(R.id.switch_notificaciones);
            swDarkMode = findViewById(R.id.swmodooscuro);

            // Listener para el switch de modo oscuro (funcionalidad no implementada)
            swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(Configuraciones.this, "CONFIGIGURACION PROXIMAMENTE", LENGTH_SHORT ).show();
                }
            });

            // Listener para el switch de notificaciones
            switchNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                       Toast.makeText(Configuraciones.this, "Notificaciones encendidas", LENGTH_SHORT).show();
                    } else {

                       Toast.makeText(Configuraciones.this, "Notificaciones apagadas", LENGTH_SHORT).show();
                    }
                }
            });

            // Botón para editar perfil, redirige a MisDonaciones (probable error lógico)
            btneditperfil = findViewById(R.id.btnperfil);
            btneditperfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Configuraciones.this, MisDonaciones.class);
                    startActivity(intent);
                }
            });

            // Botón para volver al menú principal
            botonhome = findViewById(R.id.homeButton);
            botonhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Configuraciones.this, Menu.class);
                    startActivity(intent);
                }
            });

            // Vista de texto "Editar perfil", redirige a MisDonaciones (probable error lógico)
            editarperfil = findViewById(R.id.Texteditarperfil);
            editarperfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Configuraciones.this, MisDonaciones.class);
                    startActivity(intent);
                }
            });

            // Botón para eliminar perfil, muestra un diálogo de confirmación
            btnelminarperfil=findViewById(R.id.btnelminarperfil);
            btnelminarperfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialogoconfirmacion();
                }
            });
        }

        // recuadro de elminacion de cuenta(falta terminar)
        private void Dialogoconfirmacion() {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar cuenta")
                    .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción es irreversible y perderás todos tus datos asociados.") // Mensaje
                    .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Log.d("Configuraciones", "Usuario confirmó eliminación.");

                           //funcion para eliminar cuenta(no disponibe)

                            makeText(Configuraciones.this, "Procediendo a eliminar cuenta...", LENGTH_SHORT).show();

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
    }