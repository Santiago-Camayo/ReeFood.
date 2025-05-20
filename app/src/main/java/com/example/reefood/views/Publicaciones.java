package com.example.reefood.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reefood.model.Donacion;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Publicaciones extends AppCompatActivity {

    // Componentes de la interfaz
    ImageButton btnconfiguracion, btneditperfil, btncasa;
    private RecyclerView recyclerView;     // RecyclerView para mostrar las donaciones
    private DonacionAdapter adapter;       // Adaptador para el RecyclerView
    private List<Donacion> listaDonaciones;  // Lista local de donaciones

    // Firebase
    private FirebaseFirestore db;          // Instancia de Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

       db = FirebaseFirestore.getInstance();

        // Inicializar la lista de donaciones
        listaDonaciones = new ArrayList<>();

        // Inicializar el RecyclerView
        recyclerView = findViewById(R.id.listaalimentos);

        // Configurar el LayoutManager (2 columnas para mostrar los elementos en grid)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Crear y configurar el adaptador con la lista vacía inicialmente
        adapter = new DonacionAdapter(listaDonaciones, this);
        recyclerView.setAdapter(adapter);

        // Cargar donaciones desde Firestore
        cargarDonaciones();

        // Configurar botones de navegación
        configurarBotones();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar donaciones cada vez que se vuelve a la actividad
        cargarDonaciones();
    }

    // Método para obtener las donaciones desde Firestore y mostrarlas en el RecyclerView
    private void cargarDonaciones() {
        // Limpiar la lista actual
        listaDonaciones.clear();

        // Consultar Firestore para obtener las donaciones ordenadas por timestamp (más recientes primero)
        db.collection("donaciones")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Procesar los documentos obtenidos
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<Donacion> donacionesFirestore = queryDocumentSnapshots.toObjects(Donacion.class);
                        listaDonaciones.addAll(donacionesFirestore);

                        // Notificar al adaptador que los datos han cambiado
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Publicaciones.this, "No hay donaciones disponibles", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Publicaciones.this, "Error al cargar donaciones: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    // Método para inicializar y configurar los botones de navegación
    private void configurarBotones() {
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btnconfiguracion.setOnClickListener(v -> {
            Intent confi = new Intent(Publicaciones.this, Configuraciones.class);
            startActivity(confi);
        });

        btneditperfil = findViewById(R.id.btnperfil);
        btneditperfil.setOnClickListener(v -> {
            Intent profile = new Intent(Publicaciones.this, MisDonaciones.class);
            startActivity(profile);
        });

        btncasa = findViewById(R.id.btnhome);
        btncasa.setOnClickListener(v -> {
            Intent casa = new Intent(Publicaciones.this, Menu.class);
            startActivity(casa);
        });
    }
}