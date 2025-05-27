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
import com.example.reefood.model.Registro_Usuario;
import com.example.reefood.utils.HelperNavegacion;

import java.util.ArrayList;

public class MisDonaciones extends BaseActivity {

    // Declaración de variables para los componentes de la interfaz de usuario (UI).
    ImageButton btnconfiguracion, btnmisdonaciones, btncasa;
    ListView listaproductos;
    TextView nombreusuario;
    // Instancia de ManagerDB para interactuar con la base de datos.
    ManagerDB managerDB;

    private HelperNavegacion nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad.
        setContentView(R.layout.activity_mis_donaciones);


        // Configuración de la navegación en la interfaz de usuario.
        nav = new HelperNavegacion(this);
        nav.configurarNavegacion(
                findViewById(R.id.botonesdenavegacion),
                findViewById(R.id.fab),
                findViewById(R.id.main)
        );

        // Inicializa la instancia de ManagerDB, pasando el contexto de esta actividad.
        managerDB =new ManagerDB(MisDonaciones.this);
        // Obtiene el nombre del usuario actual desde la base de datos.
        String nombre = managerDB.NombreUsuario();
        // Enlaza la variable nombreusuario con el TextView correspondiente en el layout.
        nombreusuario = findViewById(R.id.nombreusuario);
        // Establece el texto del TextView con el nombre del usuario.
        nombreusuario.setText(nombre);

    }
    @Override
    public void onBackPressed() {
        if (!nav.manejarBotonAtras()) {
            super.onBackPressed();
        }
    }

}