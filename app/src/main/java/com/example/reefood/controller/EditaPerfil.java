package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.reefood.R;

/**
 * Clase EditaPerfil.
 * Extiende de Configuraciones.
 * Permite al usuario editar los detalles de su perfil, como nombre, correo electrónico,
 * número de contacto y dirección.
 * Los cambios se guardan (actualmente solo muestra un mensaje de confirmación).
 * Incluye navegación a la pantalla de Configuraciones y al menú principal.
 */
public class EditaPerfil extends Configuraciones {

    // Declaración de elementos de la interfaz de usuario (UI)
    // ImageButtons para la navegación:
    ImageButton btncasa, btnconfiguracion, btneditperfil;
    // EditTexts para la entrada de datos del perfil:
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextContactNumber;
    private EditText editTextAddress;
    // Button para guardar los cambios:
    private Button buttonSave;

    /**
     * Método onCreate.
     * Se llama cuando la actividad es creada por primera vez.
     * Inicializa la interfaz de usuario, enlaza los elementos del layout XML
     * y configura los listeners para los botones.
     * @param savedInstanceState Si la actividad se reinicia después de haber sido
     *                           previamente cerrada, este Bundle contiene los datos
     *                           más recientes suministrados en onSaveInstanceState().
     *                           Nota: De lo contrario es null.
     */
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
            // Actualmente, no se implementa el guardado real de los datos.
            // En una aplicación real, aquí se llamaría a un método para persistir estos datos
            // (e.g., en una base de datos local, SharedPreferences o un servidor remoto).
            Toast.makeText(EditaPerfil.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
        });

        // Configuración del botón de configuraciones
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btnconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para iniciar la actividad Configuraciones.
                // 'this' (EditaPerfil.this) es el contexto actual.
                // 'Configuraciones.class' es la clase de la actividad de destino.
                Intent configuraciones = new Intent(EditaPerfil.this, Configuraciones.class);
                // Inicia la actividad.
                startActivity(configuraciones);
            }
        });

        // Configuración del OnClickListener para el botón "Casa" (volver al menú principal)
        // Se enlaza el ImageButton 'btncasa' con el elemento correspondiente en el layout.
        btncasa = findViewById(R.id.btnhome);
        // Se asigna un listener al botón de configuraciones (btnconfiguracion) en lugar de btncasa.
        // POSIBLE ERROR: Este listener debería estar asignado a 'btncasa.setOnClickListener'.
        // Actualmente, al pulsar el botón 'btnconfiguracion', se ejecutará este código,
        // además del listener definido anteriormente para 'btnconfiguracion'.
        btnconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para iniciar la actividad Menu.
                Intent casa = new Intent(EditaPerfil.this, Menu.class);
                startActivity(casa);
            }
        });
    }
}