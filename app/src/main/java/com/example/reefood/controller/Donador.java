package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;
import com.example.reefood.model.Registro_Donaciones;
import com.example.reefood.model.ManagerDB;

public class Donador extends BaseActivity {
    // Campos de texto para la entrada de datos del usuario
    private EditText edtNombre, edtContacto, edtTituloProducto, edtNota;
    // Grupo de botones de radio para seleccionar el método de entrega
    private RadioGroup deliveryMethodGroup;
    // Botón para proceder al siguiente paso o guardar la donación
    private Button btnSiguiente;
    // Botón para retroceder a la pantalla anterior
    private ImageButton btnAtras;
    // Variable para almacenar el método de entrega seleccionado
    private String metodoEntrega = "";
    // Instancia de ManagerDB para interactuar con la base de datos
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el layout de la actividad
        setContentView(R.layout.activity_donador);

        // Inicializa la instancia de ManagerDB para operaciones de base de datos
        managerDB = new ManagerDB(this);

        // Llama a los métodos para inicializar las vistas y configurar los listeners
        inicializarVistas();
        configurarListeners();
    }

    private void inicializarVistas() {
        btnAtras = findViewById(R.id.btnatras);
        edtNombre = findViewById(R.id.edtNombre);
        edtContacto = findViewById(R.id.edtContacto);
        edtTituloProducto = findViewById(R.id.edtNombreProducto);
        edtNota = findViewById(R.id.edtnota);
        // Asocia el RadioGroup con su ID en el layout
        deliveryMethodGroup = findViewById(R.id.deliveryMethodGroup);
        btnSiguiente = findViewById(R.id.btnsiguiente);
    }


    private void configurarListeners() {
        // Configura el listener para el botón de retroceso
        btnAtras.setOnClickListener(v -> {
            // Inicia la actividad Menu al hacer clic en el botón de atrás
            startActivity(new Intent(Donador.this, Publicaciones.class));
            // Finaliza la actividad actual para que el usuario no pueda volver a ella con el botón de retroceso del dispositivo
            finish();
        });

        // Configura el listener para el RadioGroup del método de entrega
        deliveryMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Verifica qué botón de radio fue seleccionado
            if (checkedId == R.id.recojerenubi) {
                // Si se seleccionó "Recoger en ubicación", actualiza la variable metodoEntrega
                metodoEntrega = "Recoger en ubicación";
            } else if (checkedId == R.id.hacerenvio) {
                // Si se seleccionó "Envío a domicilio", actualiza la variable metodoEntrega
                metodoEntrega = "Envío a domicilio";
            }
        });

        // Configura el listener para el botón "Siguiente"
        btnSiguiente.setOnClickListener(v -> {
            if (validarCampos()) {
                guardarDonacionEnDB();
            }
        });
    }


    private boolean validarCampos() {
        if (edtNombre.getText().toString().trim().isEmpty()) {
            // Muestra un mensaje si el nombre del donante está vacío
            mostrarToast("Ingrese el nombre del donante");
            return false;
        }

        if (edtContacto.getText().toString().trim().isEmpty()) {
            // Muestra un mensaje si el número de contacto está vacío
            mostrarToast("Ingrese un número de contacto");
            return false;
        }

        if (edtTituloProducto.getText().toString().trim().isEmpty()) {
            // Muestra un mensaje si el título de la donación está vacío
            mostrarToast("Ingrese el título de la donación");
            return false;
        }

        if (metodoEntrega.isEmpty()) {
            // Muestra un mensaje si no se ha seleccionado un método de entrega
            mostrarToast("Seleccione un método de entrega");
            return false;
        }
        // Si todas las validaciones pasan, retorna true
        return true;
    }


    private void guardarDonacionEnDB() {
        // Crea un nuevo objeto Registro_Donaciones con los datos ingresados
        Registro_Donaciones nuevaDonacion = new Registro_Donaciones(
                edtNombre.getText().toString(),
                edtTituloProducto.getText().toString(),
                edtNota.getText().toString(),
                edtContacto.getText().toString(),
                metodoEntrega // Utiliza la variable que almacena el método de entrega seleccionado
        );
        // Inserta la donación en la base de datos a través de ManagerDB y obtiene el resultado
        long resultado = managerDB.InsertarDonacion(nuevaDonacion);
        // Verifica si la inserción fue exitosa (resultado > 0 indica el ID de la fila insertada)
        if (resultado > 0) {
            mostrarToast("Donación registrada con éxito");
            startActivity(new Intent(Donador.this, Publicaciones.class));
            finish();
        } else {
            mostrarToast("Error al registrar la donación");
        }
    }


    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}