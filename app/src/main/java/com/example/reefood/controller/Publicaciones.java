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

// Clase Publicaciones: Esta actividad muestra una lista de donaciones publicadas.
// Hereda de AppCompatActivity para compatibilidad con versiones anteriores de Android.
public class Publicaciones extends AppCompatActivity {

    // Declaración de variables para los componentes de la interfaz de usuario.
    private ImageButton btnconfiguracion, btneditperfil, btncasa;
    private RecyclerView recyclerView;
    private DonacionAdapter adapter;
    private ManagerDB managerDB;

    // Método onCreate: Se ejecuta cuando se crea la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad.
        setContentView(R.layout.activity_publicaciones);

        // Inicializa el ManagerDB, que gestiona las operaciones de la base de datos.
        managerDB = new ManagerDB(this);

        // Llama a los métodos para inicializar vistas, configurar el RecyclerView,
        // cargar las donaciones y configurar los botones.
        inicializarVistas();
        configurarRecyclerView();
        cargarDonaciones();
        configurarBotones();
    }

    // Método inicializarVistas: Vincula las variables con los componentes del layout.
    private void inicializarVistas() {
        // Asigna el RecyclerView del layout a la variable recyclerView.
        recyclerView = findViewById(R.id.listaalimentos);
        // Asigna los ImageButton del layout a sus respectivas variables.
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btneditperfil = findViewById(R.id.btnperfil);
        btncasa = findViewById(R.id.btnhome);
    }

    // Método configurarRecyclerView: Configura el RecyclerView para mostrar las donaciones.
    private void configurarRecyclerView() {
        // Establece un GridLayoutManager con 2 columnas para el RecyclerView.
        // Esto significa que los elementos se mostrarán en una cuadrícula de 2 columnas.
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // Inicializa el DonacionAdapter con una lista vacía de donaciones.
        // El adapter es responsable de vincular los datos de las donaciones con las vistas
        // individuales dentro del RecyclerView.
        // Se pasa 'null' inicialmente porque los datos se cargarán después.
        // 'this' se refiere al contexto actual (la actividad Publicaciones).
        adapter = new DonacionAdapter(null, this);
        // Establece el adapter para el RecyclerView.
        recyclerView.setAdapter(adapter);
    }

    // Método cargarDonaciones: Obtiene las donaciones de la base de datos y las muestra.
    private void cargarDonaciones() {
        // Llama al método obtenerdonaciones() de ManagerDB para obtener la lista de donaciones.
        List<Registro_Donaciones> donaciones = managerDB.obtenerdonaciones();
        // Llama al método actualizarDonaciones() del adapter para actualizar la lista
        // de donaciones que se muestra en el RecyclerView.
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
            // Es importante notar que el nombre del botón (btneditperfil) podría ser más descriptivo
            // si la funcionalidad es ver "Mis Donaciones" en lugar de "Editar Perfil".
        });

        btncasa.setOnClickListener(v -> {
            startActivity(new Intent(Publicaciones.this, Menu.class));
            // Inicia la actividad Menu (pantalla principal o de inicio) cuando se hace clic
            // en el botón de casa/home.
        });
    }
}
