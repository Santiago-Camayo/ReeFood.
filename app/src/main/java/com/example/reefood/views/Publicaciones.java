package com.example.reefood.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reefood.R;
import com.example.reefood.model.Donaciones;
import com.example.reefood.model.ManagerDB;

import java.util.List;

public class Publicaciones extends AppCompatActivity {

    private ImageButton btnconfiguracion, btneditperfil, btncasa;
    private RecyclerView recyclerView;
    private DonacionAdapter adapter;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        managerDB = new ManagerDB(this);

        inicializarVistas();
        configurarRecyclerView();
        cargarDonaciones();
        configurarBotones();
    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.listaalimentos);
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btneditperfil = findViewById(R.id.btnperfil);
        btncasa = findViewById(R.id.btnhome);
    }

    private void configurarRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // Inicializar el adapter con lista vacía primero
        adapter = new DonacionAdapter(null, this);
        recyclerView.setAdapter(adapter);
    }

    private void cargarDonaciones() {
        List<Donaciones> donaciones = managerDB.obtenerdonaciones();
        adapter.actualizarDonaciones(donaciones);
    }

    private void configurarBotones() {
        btnconfiguracion.setOnClickListener(v -> {
            startActivity(new Intent(Publicaciones.this, Configuraciones.class));
        });

        btneditperfil.setOnClickListener(v -> {
            startActivity(new Intent(Publicaciones.this, MisDonaciones.class));
        });

        btncasa.setOnClickListener(v -> {
            startActivity(new Intent(Publicaciones.this, Menu.class));
        });
    }
}