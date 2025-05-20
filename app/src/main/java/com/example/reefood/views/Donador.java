package com.example.reefood.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.model.Donacion;
import com.google.firebase.firestore.FirebaseFirestore;

// Clase para gestionar el formulario de creación de nuevas donaciones
public class Donador extends AppCompatActivity {

    // Referencias a los elementos de la interfaz
    private EditText establishmentName;    // Campo para el nombre del donante
    private EditText contactNumber;        // Campo para el número de contacto
    private EditText titulodonacion;       // Campo para el título de la donación
    private EditText note;                 // Campo para notas adicionales
    private RadioGroup deliveryMethodGroup; // Grupo de radio buttons para método de entrega
    private Button btnSiguiente;           // Botón para enviar el formulario
    private ImageButton btnatras;          // Botón para volver atrás
    private String metodoEntrega = "";     // Almacena el método de entrega seleccionado

    // Instancia de Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donador);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Configuración del botón atrás para volver al menú
        btnatras = findViewById(R.id.btnatras);
        btnatras.setOnClickListener(v -> {
            Intent intent = new Intent(Donador.this, Menu.class);
            startActivity(intent);
        });

        // Inicializar referencias a las vistas del layout
        establishmentName = findViewById(R.id.establishmentName);
        contactNumber = findViewById(R.id.contactNumber);
        titulodonacion = findViewById(R.id.tituloproducto);
        note = findViewById(R.id.note);
        deliveryMethodGroup = findViewById(R.id.deliveryMethodGroup);
        btnSiguiente = findViewById(R.id.btnsiguiente);

        // Configurar listener para el grupo de radio buttons
        deliveryMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.recojerenubi) {
                // Si selecciona "Recoger en ubicación"
                metodoEntrega = "Recoger en ubicación";
            } else if (checkedId == R.id.hacerenvio) {
                // Si selecciona "Envío a domicilio"
                metodoEntrega = "Envío a domicilio";
            }
        });

        // Configurar listener para el botón "Siguiente"
        btnSiguiente.setOnClickListener(v -> {
            // Verificar que todos los campos necesarios estén completos
            if (validateFields()) {
                guardarDonacionEnFirestore();
            }
        });
    }

    // Guarda los datos de la donación en Firestore
    private void guardarDonacionEnFirestore() {
        // Mostrar mensaje de carga
        Toast.makeText(this, "Guardando donación...", Toast.LENGTH_SHORT).show();

        // Crear un nuevo objeto Donacion con los datos ingresados
        Donacion nuevaDonacion = new Donacion(
                establishmentName.getText().toString(),
                contactNumber.getText().toString(),
                titulodonacion.getText().toString(),
                note.getText().toString(),
                metodoEntrega
        );

        // Guardar en Firestore en la colección "donaciones"
        db.collection("donaciones")
                .add(nuevaDonacion)
                .addOnSuccessListener(documentReference -> {
                    // Éxito al guardar
                    Toast.makeText(this, "Donación registrada con éxito", Toast.LENGTH_SHORT).show();

                    // Ir a la actividad de publicaciones
                    startActivity(new Intent(Donador.this, Publicaciones.class));
                    finish(); // Cerrar esta actividad
                })
                .addOnFailureListener(e -> {
                    // Error al guardar
                    Toast.makeText(this, "Error al registrar la donación: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    // Valida que los campos requeridos estén completos
    private boolean validateFields() {
        // Verificar que el nombre no esté vacío
        if (establishmentName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del donante", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar que el contacto no esté vacío
        if (contactNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese un número de contacto", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar que la descripción no esté vacía
        if (titulodonacion.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el título de la donación", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar que se haya seleccionado un método de entrega
        if (metodoEntrega.isEmpty()) {
            Toast.makeText(this, "Seleccione un método de entrega", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // Todos los campos están completos
    }
}