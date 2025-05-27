package com.example.reefood.utils;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.reefood.R;
import com.example.reefood.controller.*;
import com.example.reefood.databinding.AccionesbtnaddBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// HelperNavegacion - Clase reutilizable para manejar la navegación en toda la app
// Esta clase centraliza toda la lógica de navegación para evitar repetir código
// en cada Activity que tenga barra de navegación y FAB.
//
// COMO USAR:
// 1. HelperNavegacion navegador = new HelperNavegacion(this);
// 2. navegador.configurarNavegacion(barraNavegacion, botonFlotante, layoutPrincipal);
// 3. En onBackPressed(): navegador.manejarBotonAtras()
public class HelperNavegacion {

    // Variables principales de la clase
    private AppCompatActivity actividad;                    // La actividad que está usando este helper
    private AccionesbtnaddBinding menuAcciones;             // Vista del menú que aparece desde el FAB
    private boolean menuEsVisible = false;                  // Controla si el menú está mostrado u oculto
    private CoordinatorLayout layoutPrincipal;              // Layout donde se agregan los menús
    private FloatingActionButton botonFlotante;             // El FAB (botón flotante)

    // Constructor - Solo necesita la actividad que lo va a usar
    public HelperNavegacion(AppCompatActivity actividad) {
        this.actividad = actividad;
    }

    // MÉTODO PRINCIPAL - Configura toda la navegación de una sola vez
    // Este método es el que llamas desde tu Activity para configurar todo
    public void configurarNavegacion(BottomNavigationView barraNavegacion,
                                     FloatingActionButton botonFlotante,
                                     CoordinatorLayout layoutPrincipal) {
        // Guardamos las referencias para usarlas después
        this.botonFlotante = botonFlotante;
        this.layoutPrincipal = layoutPrincipal;

        // Configuramos los dos elementos principales
        configurarBarraNavegacion(barraNavegacion);
        configurarBotonFlotante();
    }

    // Configura la barra de navegación inferior
    // Define qué pasa cuando tocas cada botón de la barra
    private void configurarBarraNavegacion(BottomNavigationView barraNavegacion) {
        // Quitar el fondo/sombra de la barra
        barraNavegacion.setBackground(null);

        // Configurar qué hacer cuando se toca cada botón
        barraNavegacion.setOnItemSelectedListener(item -> {
            int idBoton = item.getItemId();

            // Dependiendo del botón tocado, navegar a la actividad correspondiente
            if (idBoton == R.id.btnhome) {
                irAActividad(Publicaciones.class);
            } else if (idBoton == R.id.btnperfil) {
                irAActividad(MisDonaciones.class);
            } else if (idBoton == R.id.btnConfiguraciones) {
                irAActividad(Configuraciones.class);
            } else if (idBoton == R.id.btnchat) {
                Toast.makeText(actividad, "Chat Proximamente...", Toast.LENGTH_SHORT).show();
            }
            return true; // Indica que el evento fue manejado
        });
    }

    // Configura el botón flotante (FAB)
    // Define qué pasa cuando lo tocas
    private void configurarBotonFlotante() {
        botonFlotante.setOnClickListener(v -> alternarMenu());
    }

    // Alterna entre mostrar y ocultar el menú de acciones
    // Si está visible lo oculta, si está oculto lo muestra
    private void alternarMenu() {
        if (menuEsVisible) {
            ocultarMenu();
        } else {
            mostrarMenu();
        }
    }

    // Muestra el menú de acciones desde abajo
    // Este menú contiene opciones como "Nueva Donación", "Nuevo Chat", etc.
    private void mostrarMenu() {
        // 1. Ocultar el FAB para que no estorbe
        botonFlotante.hide();

        // 2. Crear la vista del menú usando el binding
        menuAcciones = AccionesbtnaddBinding.inflate(actividad.getLayoutInflater());

        // 3. Configurar dónde se va a mostrar (abajo de la pantalla)
        CoordinatorLayout.LayoutParams parametros = new CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,    // Ancho: toda la pantalla
                CoordinatorLayout.LayoutParams.WRAP_CONTENT);   // Alto: el necesario
        parametros.gravity = android.view.Gravity.BOTTOM;       // Posición: abajo
        menuAcciones.getRoot().setLayoutParams(parametros);

        // 4. Agregar el menú al layout principal
        layoutPrincipal.addView(menuAcciones.getRoot());

        // 5. Configurar qué hace cada botón del menú
        configurarBotonesMenu();

        // 6. Mostrar con animación de entrada
        menuAcciones.getRoot().startAnimation(
                AnimationUtils.loadAnimation(actividad, R.anim.entrada));

        // 7. Marcar que el menú ya está visible
        menuEsVisible = true;
    }

    // Configura los botones del menú de acciones
    // Define qué hace cada botón cuando lo tocas
    private void configurarBotonesMenu() {
        // Botón Cancelar - cierra el menú
        menuAcciones.cancelButton.setOnClickListener(v -> ocultarMenu());

        // Botón Nueva Donación - va a la pantalla de donación
        menuAcciones.layoutnewdonacion.setOnClickListener(v -> {
            ocultarMenu();
            irAActividad(Donador.class);
        });

        // Botón Nuevo Chat - por ahora solo cierra el menú
        menuAcciones.layoutnewchat.setOnClickListener(v -> {
            ocultarMenu();
            Toast.makeText(actividad, "Chat Proximamente...", Toast.LENGTH_SHORT).show();
        });
    }

    // Oculta el menú de acciones con animación
    // Quita el menú de la pantalla y vuelve a mostrar el FAB
    private void ocultarMenu() {
        // Solo ocultar si el menú está visible y existe
        if (menuEsVisible && menuAcciones != null) {
            // 1. Cargar la animación de salida
            android.view.animation.Animation animacionSalida =
                    AnimationUtils.loadAnimation(actividad, R.anim.salida);

            // 2. Configurar qué hacer cuando termine la animación
            animacionSalida.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                public void onAnimationStart(android.view.animation.Animation animation) {}
                public void onAnimationRepeat(android.view.animation.Animation animation) {}

                // Cuando termina la animación:
                public void onAnimationEnd(android.view.animation.Animation animation) {
                    // Quitar el menú del layout
                    layoutPrincipal.removeView(menuAcciones.getRoot());
                    // Limpiar la referencia
                    menuAcciones = null;
                    // Marcar como no visible
                    menuEsVisible = false;
                    // Mostrar el FAB de nuevo
                    botonFlotante.show();
                }
            });

            // 3. Iniciar la animación
            menuAcciones.getRoot().startAnimation(animacionSalida);
        }
    }

    // Navegar a otra actividad
    // Método helper para cambiar de pantalla
    private void irAActividad(Class<?> claseActividad) {
        Intent intent = new Intent(this.actividad, claseActividad);
        this.actividad.startActivity(intent);
    }

    // Manejar el botón "Atrás" del dispositivo
    // Si el menú está visible, lo cierra; si no, permite el comportamiento normal
    public boolean manejarBotonAtras() {
        if (menuEsVisible) {
            ocultarMenu();
            return true; // Ya manejamos el evento
        }
        return false; // No lo manejamos, que siga normal
    }
}