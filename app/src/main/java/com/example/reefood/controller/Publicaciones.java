package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reefood.R;
import com.example.reefood.model.Registro_Donaciones;
import com.example.reefood.model.ManagerDB;

import java.util.List;

public class Publicaciones extends AppCompatActivity {

    // Declaración de variables para los componentes de la interfaz de usuario.
    private ImageButton btnconfiguracion, btneditperfil, btncasa;
    private RecyclerView recyclerView;
    private DonacionAdapter adapter;
    private ManagerDB managerDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad.
        setContentView(R.layout.activity_publicaciones);

        // Inicializa el ManagerDB, que gestiona las operaciones de la base de datos.
        managerDB = new ManagerDB(this);

        inicializarVistas();
        configurarRecyclerView();
        cargarDonaciones();
        configurarBotones();
    }

    private void inicializarVistas() {
        // Asigna el RecyclerView del layout a la variable recyclerView.
        recyclerView = findViewById(R.id.listaalimentos);
        // Asigna los ImageButton del layout a sus respectivas variables.
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btneditperfil = findViewById(R.id.btnperfil);
        btncasa = findViewById(R.id.btnhome);
    }


    private void configurarRecyclerView() {


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // Inicializa el DonacionAdapter con una lista vacía de donaciones.

        adapter = new DonacionAdapter(null, this);
        // Establece el adapter para el RecyclerView.
        recyclerView.setAdapter(adapter);
    }


    private void cargarDonaciones() {
        // Llama al método obtenerdonaciones() de ManagerDB para obtener la lista de donaciones.
        List<Registro_Donaciones> donaciones = managerDB.obtenerdonaciones();
        // Llama al método actualizarDonaciones() del adapter para actualizar la lista
        adapter.actualizarDonaciones(donaciones);
    }

    private void configurarBotones() {
        btnconfiguracion.setOnClickListener(v -> {
            startActivity(new Intent(Publicaciones.this, Configuraciones.class));
            // Inicia la actividad Configuraciones cuando se hace clic en el botón de configuración.
        });

        btneditperfil.setOnClickListener(v -> {
            startActivity(new Intent(Publicaciones.this, MisDonaciones.class));
            // Inicia la actividad MisDonaciones cuando se hace clic en el botón de perfil/mis donaciones.

        });

        btncasa.setOnClickListener(v -> {
            startActivity(new Intent(Publicaciones.this, Menu.class));
            // Inicia la actividad Menu (pantalla principal o de inicio) cuando se hace clic

        });
    }
}
