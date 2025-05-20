package com.example.refood;

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

import com.google.android.material.switchmaterial.SwitchMaterial;

// Clase para gestionar la pantalla de configuraciones de la app
public class Configuraciones extends AppCompatActivity {

    // Declaración de controles de la interfaz
    private Switch switchNotificaciones;   // Control para activar o desactivar notificaciones
    private SwitchMaterial swDarkMode;     // Control para activar/desactivar modo oscuro

    // Botones de navegación
    ImageButton botonhome, btneditperfil;

    // Elementos adicionales de la interfaz
    View editarperfil;                     // Vista para acceder a edición de perfil
    LinearLayout btnelminarperfil;         // Botón para eliminar el perfil

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        // Inicialización de los componentes de la interfaz
        switchNotificaciones = findViewById(R.id.switch_notificaciones);
        swDarkMode = findViewById(R.id.swmodooscuro);

        // Configuración del switch de modo oscuro
        swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Configuraciones.this, "CONFIGIGURACION PROXIMAMENTE", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración del switch de notificaciones
        switchNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Notificaciones encendidas
                    Toast.makeText(Configuraciones.this, "Notificaciones encendidas", Toast.LENGTH_SHORT).show();
                } else {
                    // Notificaciones apagadas
                    Toast.makeText(Configuraciones.this, "Notificaciones apagadas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configuración del botón para editar perfil
        btneditperfil = findViewById(R.id.btnperfil);
        btneditperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configuraciones.this, MisDonaciones.class);
                startActivity(intent);
            }
        });

        // Configuración del botón para volver al menú principal
        botonhome = findViewById(R.id.homeButton);
        botonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configuraciones.this, Menu.class);
                startActivity(intent);
            }
        });

        // Configuración de la vista para editar perfil
        editarperfil = findViewById(R.id.Texteditarperfil);
        editarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configuraciones.this, MisDonaciones.class);
                startActivity(intent);
            }
        });

        // Configuración del botón para eliminar perfil
        btnelminarperfil=findViewById(R.id.btnelminarperfil);
        btnelminarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogoconfirmacion();
            }
        });
    }

    // Método para mostrar diálogo de confirmación al eliminar cuenta
    private void Dialogoconfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta") // Título del diálogo
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción es irreversible y perderás todos tus datos asociados.") // Mensaje
                .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // El usuario confirma la eliminación de cuenta
                        Log.d("Configuraciones", "Usuario confirmó eliminación.");

                        // PUNTO DE INTEGRACIÓN PARA BACKEND
                        // Aquí se debe agregar el código para llamar al backend
                        // usando Volley para eliminar la cuenta en la base de datos SQL.
                        // Una vez que la eliminación en el backend sea exitosa, se debe:
                        // 1. Limpiar los datos de sesión locales del usuario en la app.
                        // 2. Redirigir al usuario a la pantalla de inicio de sesión/principal.

                        Toast.makeText(Configuraciones.this, "Procediendo a eliminar cuenta...", Toast.LENGTH_SHORT).show();
                        // Llamada a la función de eliminación (pendiente de implementar)
                        Log.i("Configuraciones", "!!! Llama a la función de eliminación del backend AQUÍ !!!");
                        // Ejemplo: deleteAccountInBackendUsingVolley();
                    }
                })
                .setNegativeButton("No, cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // El usuario cancela la eliminación
                        Log.d("Configuraciones", "Usuario canceló eliminación.");
                        dialog.dismiss(); // Cierra el diálogo sin hacer nada más
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert) // Icono de advertencia
                .show(); // Muestra el diálogo
    }
}