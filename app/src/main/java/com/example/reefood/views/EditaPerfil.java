package com.example.reefood.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

// Clase para editar la información del perfil de usuario
public class EditaPerfil extends Configuraciones {

    // Declaración de elementos de la interfaz
    ImageButton btncasa, btnconfiguracion, btneditperfil;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextContactNumber;
    private EditText editTextAddress;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);

        // Inicialización de los campos de formulario
        editTextAddress = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonSave = findViewById(R.id.buttonSave);

        // Configuración del botón guardar
        buttonSave.setOnClickListener(v -> {
            // Obtener los valores de los campos de texto
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String contactNumber = editTextContactNumber.getText().toString();
            String address = editTextAddress.getText().toString();

            // Mostrar mensaje de confirmación
            // Nota: Aquí falta implementar el guardado real de datos
            Toast.makeText(EditaPerfil.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
        });

        // Configuración del botón de configuraciones
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btnconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configuraciones = new Intent(EditaPerfil.this, Configuraciones.class);
                startActivity(configuraciones);
            }
        });

        // Configuración del botón para volver al menú principal
        btncasa = findViewById(R.id.btnhome);
        btnconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent casa = new Intent(EditaPerfil.this, Menu.class);
                startActivity(casa);
            }
        });
    }
}