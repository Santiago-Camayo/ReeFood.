package com.example.reefood.controller;

// Importaciones necesarias para la funcionalidad de la actividad
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

public class Configuraciones extends BaseActivity {

    // Componentes de UI
    private Switch switchNotificaciones; // Switch para activar/desactivar notificaciones
    private SwitchMaterial switchModoOscuro; // Switch para modo oscuro (usando Material Design)
    ImageButton botonhome; // Botones de navegación
    View textoEditarPerfil; // Texto clickeable para editar perfil
    LinearLayout botonEliminarPerfil; // Botón para eliminar cuenta
    private HelperNavegacion navegacion; // Helper para manejar navegación

    // Manejo de preferencias y Toast
    private Toast toastActual; // Referencia al Toast actual para evitar superposición
    private static final String CLAVE_NOTIFICACIONES = "notificaciones"; // Clave para preferencia de notificaciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Configura animaciones de transición suave al abrir la actividad
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);

        // Establece el layout de la actividad
        setContentView(R.layout.activity_configuraciones);

        // Inicializa el sistema de navegación personalizado
        navegacion = new HelperNavegacion(this);
        navegacion.configurarNavegacion(
                findViewById(R.id.botonesdenavegacion), // Contenedor de botones de navegación
                findViewById(R.id.fab),                // Botón flotante (FAB)
                findViewById(R.id.main)                // Layout principal
        );

        // Secuencia de inicialización
        inicializarVistas();       // Obtiene referencias a los elementos UI
        configurarListeners();    // Configura los eventos de interacción
        cargarEstadosGuardados();  // Carga las preferencias guardadas
    }

    private void inicializarVistas() {
        // Switches de configuración
        switchNotificaciones = findViewById(R.id.switch_notificaciones);
        switchModoOscuro = findViewById(R.id.swmodooscuro);

        // Elementos de perfil
        textoEditarPerfil = findViewById(R.id.Texteditarperfil);
        botonEliminarPerfil = findViewById(R.id.btnelminarperfil);
    }

    private void configurarListeners() {
        // Listener para editar perfil
        textoEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la actividad EditaPerfil
                Intent intent = new Intent(Configuraciones.this, EditaPerfil.class);
                startActivity(intent); // Inicia la nueva actividad
            }
        });

        // Listener para eliminar perfil
        botonEliminarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion(); // Muestra diálogo de confirmación
            }
        });
    }

     //Carga y aplica las preferencias guardadas del usuario
     //Estado del modo oscuro y estado de las notificaciones

    private void cargarEstadosGuardados() {
        // Configuración del modo oscuro usando métodos heredados de BaseActivity

        // Desactiva temporalmente el listener para evitar triggers durante la carga
        switchModoOscuro.setOnCheckedChangeListener(null);

        // Obtiene el estado guardado usando el metodo heredado
        boolean modoOscuroActivado = obtenerPreferenciaTema();

        // Aplica el estado al switch
        switchModoOscuro.setChecked(modoOscuroActivado);

        // Aplica el tema correspondiente
        AppCompatDelegate.setDefaultNightMode(
                modoOscuroActivado ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        // Restablece el listener
        configurarListenerModoOscuro();

        // Configuración de notificaciones

        // Desactiva temporalmente el listener
        switchNotificaciones.setOnCheckedChangeListener(null);

        // Obtiene el estado guardado
        boolean notificacionesActivadas = obtenerPreferencias().getBoolean(CLAVE_NOTIFICACIONES, false);

        // Aplica el estado al switch
        switchNotificaciones.setChecked(notificacionesActivadas);

        // Configura el listener para cambios
        switchNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Guarda el nuevo estado en preferencias
                obtenerPreferencias().edit()
                        .putBoolean(CLAVE_NOTIFICACIONES, isChecked)
                        .apply(); // Guarda asíncronamente

                // Muestra feedback al usuario
                mostrarToast(isChecked ? "Notificaciones activadas" : "Notificaciones desactivadas");
            }
        });
    }

     //Configura el listener para cambios en el switch de modo oscuro
     //Maneja tanto el guardado de preferencias como el cambio de tema

    private void configurarListenerModoOscuro() {
        switchModoOscuro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Verifica que el cambio venga de interacción del usuario
                if (buttonView.isPressed()) {
                    // Usa el metodo heredado de BaseActivity para guardar el tema de preferencia
                    guardarPreferenciaTema(isChecked);

                    // Muestra feedback
                    mostrarToast(isChecked ? "Modo oscuro activado" : "Modo oscuro desactivado");

                    // Aplica el tema
                    AppCompatDelegate.setDefaultNightMode(
                            isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
                    );

                    // Recarga la actividad después de un pequeño delay
                    buttonView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recreate(); // Recrea la actividad para aplicar el tema
                        }
                    }, 100); // 100ms de delay
                }
            }
        });
    }

     //Muestra un diálogo de confirmación para eliminar la cuenta
     //iene opciones para confirmar o cancelar
     //Muestra advertencia sobre la irreversibilidad

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta") // Título del diálogo
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción es irreversible y perderás todos tus datos asociados.") // Mensaje detallado
                .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica al confirmar eliminación
                        mostrarToast("Procediendo a eliminar cuenta...");

                    }
                })
                .setNegativeButton("No, cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cierra el diálogo
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert) // Ícono de advertencia
                .show(); // Muestra el diálogo
    }

     //Muestra un mensaje Toast al usuario
     //Evita superposición de múltiples Toasts

    private void mostrarToast(String mensaje) {
        // Cancela el Toast anterior si existe
        if (toastActual != null) {
            toastActual.cancel();
        }
        // Crea y muestra el nuevo Toast
        toastActual = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toastActual.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpieza: cancela el Toast si la actividad se destruye
        if (toastActual != null) {
            toastActual.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        // Maneja el botón de retroceso usando el helper de navegación
        if (!navegacion.manejarBotonAtras()) {
            // Si el helper no maneja el evento, procede con el comportamiento por defecto
            super.onBackPressed();
            // Aplica animación al cerrar
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}