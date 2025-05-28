package com.example.reefood.controller;

// Importaciones necesarias para la funcionalidad de la actividad.
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

// Importaciones de recursos y clases del proyecto.
import com.example.reefood.R;
import com.example.reefood.model.ManagerDB;
import com.example.reefood.model.Registro_Donaciones;
import com.example.reefood.model.Registro_Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase MisDonaciones: Representa la pantalla donde el usuario puede ver sus donaciones.
 * Esta actividad permite navegar a otras secciones de la aplicación como Configuración,
 * el Menú principal y recargar la misma pantalla de Mis Donaciones.
 * También muestra el nombre del usuario actual.
 */
public class MisDonaciones extends AppCompatActivity {

    // Declaración de variables para los componentes de la interfaz de usuario (UI).
    ImageButton btnconfiguracion, btnmisdonaciones, btncasa;
    ListView listaproductos;
    TextView nombreusuario;
    // Instancia de ManagerDB para interactuar con la base de datos.
    ManagerDB managerDB;

    /**
     * Método onCreate: Se ejecuta cuando se crea la actividad.
     * Inicializa la interfaz de usuario, configura los listeners de los botones
     * y obtiene el nombre del usuario de la base de datos para mostrarlo.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad.
        setContentView(R.layout.activity_mis_donaciones);
        managerDB = new ManagerDB(this);

        listaproductos = findViewById(R.id.listproductos);
        // 1) Obtener la lista de objetos Registro_Donaciones
        List<Registro_Donaciones> donaciones = managerDB.obtenerdonaciones();
        // 2) Convertir a una lista de strings para mostrar
        List<String> items = new ArrayList<>();
        for (Registro_Donaciones d : donaciones) {
            items.add(d.getNombre() + " — " + d.getTitulo());
        }

        // 3) Crear y asignar el adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );
        listaproductos.setAdapter(adapter);

        // 4) Cuando hagan click, abrimos VerDonacion pasando el objeto
        listaproductos.setOnItemClickListener((parent, view, position, id) -> {
            Registro_Donaciones seleccion = donaciones.get(position);
            Intent i = new Intent(MisDonaciones.this, VerDonacion.class);
            i.putExtra("donacion", seleccion);
            startActivity(i);
        });

        // Inicializa la instancia de ManagerDB, pasando el contexto de esta actividad.
        managerDB =new ManagerDB(MisDonaciones.this);
        // Obtiene el nombre del usuario actual desde la base de datos.
        String nombre = managerDB.NombreUsuario();
        // Enlaza la variable nombreusuario con el TextView correspondiente en el layout.
        nombreusuario = findViewById(R.id.nombreusuario);
        // Establece el texto del TextView con el nombre del usuario.
        nombreusuario.setText(nombre);

        // Enlaza las variables de los ImageButton con sus respectivos componentes en el layout.
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btnmisdonaciones = findViewById(R.id.btnperfil);
        btncasa = findViewById(R.id.btnhome);

        // Configura el listener para el botón de configuración.
        btnconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para iniciar la actividad Configuraciones.
                Intent confi = new Intent(MisDonaciones.this, Configuraciones.class);
                startActivity(confi);
            }
        });

        btnmisdonaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para reiniciar la actividad MisDonaciones (actualizar la vista).
                Intent profile = new Intent(MisDonaciones.this, MisDonaciones.class);
                startActivity(profile);
            }
        });

        // Configura el listener para el botón de inicio (casa).
        btncasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para iniciar la actividad Menu (pantalla principal).
                Intent casa = new Intent(MisDonaciones.this, Menu.class);
                startActivity(casa);
            }
        });
    }

}