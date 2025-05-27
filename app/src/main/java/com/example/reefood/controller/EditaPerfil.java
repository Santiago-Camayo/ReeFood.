package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.reefood.R;
import com.example.reefood.utils.HelperNavegacion;

public class EditaPerfil extends Configuraciones {

    // Declaración de elementos de la interfaz de usuario (UI)
    // ImageButtons para la navegación:

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextContactNumber;
    private EditText editTextAddress;
    // Button para guardar los cambios:
    private Button buttonSave;
    private HelperNavegacion nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llama al método onCreate de la superclase (Configuraciones)
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad desde activity_editarperfil.xml
        setContentView(R.layout.activity_editarperfil);

        // Inicialización de los campos de formulario (EditTexts) y el botón de guardar
        // Se enlazan las variables de la clase con los elementos definidos en el archivo XML del layout.
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonSave = findViewById(R.id.buttonSave);

        // Configuración del OnClickListener para el botón "Guardar"
        // Se define el comportamiento que tendrá el botón al ser presionado.
        buttonSave.setOnClickListener(v -> {
            // Obtiene los valores introducidos por el usuario en los campos de texto.
            // .getText() devuelve un Editable, .toString() lo convierte a String.
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String contactNumber = editTextContactNumber.getText().toString();
            String address = editTextAddress.getText().toString();

            // Muestra un mensaje Toast para confirmar la acción.


            Toast.makeText(EditaPerfil.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
        });

        // Configuración de la navegación
        nav = new HelperNavegacion(this);
        nav.configurarNavegacion(
                findViewById(R.id.botonesdenavegacion),
                findViewById(R.id.fab),
                findViewById(R.id.main)
        );


    }
}