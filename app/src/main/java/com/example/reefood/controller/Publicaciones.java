package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reefood.R;
import com.example.reefood.databinding.AccionesbtnaddBinding;
import com.example.reefood.databinding.ActivityMenuBinding;
import com.example.reefood.model.Registro_Donaciones;
import com.example.reefood.model.ManagerDB;
import com.example.reefood.utils.HelperNavegacion;
import com.example.reefood.utils.HelperNavegacion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Publicaciones extends BaseActivity {

    // Declaración de variables para los componentes de la interfaz de usuario.

    private RecyclerView recyclerView;
    private DonacionAdapter adapter;
    private ManagerDB managerDB;

    private HelperNavegacion nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad.
        setContentView(R.layout.activity_publicaciones);



        nav = new HelperNavegacion(this);
        nav.configurarNavegacion(
                findViewById(R.id.botonesdenavegacion),
                findViewById(R.id.fab),
                findViewById(R.id.main)
        );



        // Inicializa el ManagerDB, que gestiona las operaciones de la base de datos.
        managerDB = new ManagerDB(this);

        inicializarVistas();
        configurarRecyclerView();
        cargarDonaciones();
        configurarBotones();
    }

    @Override
    public void onBackPressed() {
        if (!nav.manejarBotonAtras()) {
            super.onBackPressed();
        }
    }

    private void inicializarVistas() {
        // Asigna el RecyclerView del layout a la variable recyclerView.
        recyclerView = findViewById(R.id.listaalimentos);


    }


    private void configurarRecyclerView() {


        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
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
        nav = new HelperNavegacion(this);
        nav.configurarNavegacion(
                findViewById(R.id.botonesdenavegacion),
                findViewById(R.id.fab),
                findViewById(R.id.main)
        );
    }



}
