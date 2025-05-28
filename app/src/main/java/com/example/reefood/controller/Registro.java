package com.example.reefood.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;
import com.example.reefood.model.ManagerDB;
import com.example.reefood.model.Registro_Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Registro extends BaseActivity {
    // Variables para las vistas
    ImageButton btnBack;
    MaterialButton btnRegistrar;
    TextInputEditText etNombre;
    TextInputEditText etApellido;
    TextInputEditText etCorreo;
    TextInputEditText etContrasena;
    TextInputEditText etTelefono;
    TextInputEditText etFecha;
    AutoCompleteTextView etGenero;
    TextInputLayout tilFecha;
    FloatingActionButton fabAddPhoto;


    // Variables para el calendario y formato de fecha
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;

    ManagerDB managerDB;
    // Elementos visuales para la carga
    private LinearLayout loadingContainer;
    private static final int TIEMPO_DE_CARGA = 5000; // 5 segundos para mostrar pantalla de carga



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        managerDB = new ManagerDB(this);

        // Inicializar calendario para selector de fecha
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


        // Inicializar vistas
        initViews();

        // Configurar botones
        setupButtons();

        // Configurar selector de fecha
        setupDatePicker();

        // Configurar dropdown de género
        setupGenderDropdown();
    }

    private void initViews() {
        // Campos del formulario
        etNombre = findViewById(R.id.etnombre);
        etApellido = findViewById(R.id.etapellido);
        etCorreo = findViewById(R.id.etcorreo);
        etContrasena = findViewById(R.id.etcontrasena);
        etTelefono = findViewById(R.id.ettelefono);
        etFecha = findViewById(R.id.etfecha);
        etGenero = findViewById(R.id.etgenero);
        tilFecha = findViewById(R.id.tilFecha);

        // Botones
        btnBack = findViewById(R.id.btnatras);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        fabAddPhoto = findViewById(R.id.fabAddPhoto);

        // Contenedor para pantalla de carga
        loadingContainer = findViewById(R.id.loading_container);
    }

    private void setupButtons() {
        // Botón de regresar
        btnBack.setOnClickListener(v -> onBackPressed());

        // Botón de registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validar campos antes de registrar
                if (validateFields()){
                    registrarusuario();
                }
            }
        });

        // Botón para agregar foto de perfil
        fabAddPhoto.setOnClickListener(v -> {
            showToast("Seleccionar foto de perfil");

        });
    }

    private void registrarusuario() {
        // Obtener valores de los campos
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String genero = etGenero.getText().toString().trim();

        if (managerDB.correoexiste(correo)){
            Toast.makeText(this, "Este Correo Ya Esta En Uso", Toast.LENGTH_SHORT).show();;
        }

        Registro_Usuario nuevousuario = new Registro_Usuario();
        nuevousuario.setNombre(nombre);
        nuevousuario.setApellido(apellido);
        nuevousuario.setCorreo(correo);
        nuevousuario.setContrasena(contrasena);
        nuevousuario.setTelefono(telefono);
        nuevousuario.setFechaNacimiento(fecha);
        nuevousuario.setGenero(genero);

        long resultado = managerDB.insert(nuevousuario);

        if (resultado > 0) {
            // Registro exitoso
            showToast("¡Usuario registrado correctamente!");

            // Mostrar pantalla de carga
            loadingContainer.setVisibility(View.VISIBLE);

            // Esperar y luego navegar a inicio de sesión
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingContainer.setVisibility(View.GONE);

                    Intent intent = new Intent(Registro.this, IniciarSesion.class);
                    startActivity(intent);
                    finish();
                }
            }, TIEMPO_DE_CARGA);

        } else {
            // Error en el registro
            showToast("Error al registrar usuario, Intenta nuevamente.");
        }


    }


    private void setupDatePicker() {
        // Configurar listener para el campo de fecha
        etFecha.setOnClickListener(v -> showDatePickerDialog());

        // Configurar listener para el icono de calendario
        tilFecha.setEndIconOnClickListener(v -> showDatePickerDialog());
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    etFecha.setText(dateFormatter.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        // Limitar a fecha actual como máximo seleccionable
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void setupGenderDropdown() {
        // Opciones de género para el desplegable
        String[] genderOptions = new String[]{"Masculino", "Femenino", "No binario", "Prefiero no decir"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                genderOptions
        );
        etGenero.setAdapter(adapter);
    }

    private boolean validateFields() {
        // Obtener valores de los campos
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String genero = etGenero.getText().toString().trim();

        // Verificar que no haya campos vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() ||
                contrasena.isEmpty() || telefono.isEmpty() || fecha.isEmpty() || genero.isEmpty()) {
            showToast("Todos los campos son obligatorios");
            return false;
        }

        // Validar formato de correo
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            showToast("Ingrese un correo electrónico válido");
            return false;
        }

        // Validar formato de teléfono (10 dígitos)
        if (!telefono.matches("\\d{10}")) {
            showToast("Teléfono debe contener exactamente 10 dígitos");
            return false;
        }
        // Validar longitud mínima de contraseña
        if (contrasena.length() < 8) {
            showToast("La contraseña debe tener al menos 8 caracteres");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}