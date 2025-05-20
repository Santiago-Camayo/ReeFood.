package com.example.refood;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Registro extends AppCompatActivity {
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

    // Firebase
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    // Elementos visuales para la carga
    private LinearLayout loadingContainer;
    private static final int TIEMPO_DE_CARGA = 5000; // 5 segundos para mostrar pantalla de carga

    // Variables de datos a registrar
    private String nombre = "";
    private String apellido = "";
    private String correo = "";
    private String contrasena = "";
    private String telefono = "";
    private String fecha = "";
    private String genero = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

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

    // Método para inicializar las referencias a las vistas del layout
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

    // Método para configurar los listeners de los botones
    private void setupButtons() {
        // Botón de regresar
        btnBack.setOnClickListener(v -> onBackPressed());

        // Botón de registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener valores de los campos
                nombre = etNombre.getText().toString();
                apellido = etApellido.getText().toString();
                correo = etCorreo.getText().toString();
                contrasena = etContrasena.getText().toString();
                telefono = etTelefono.getText().toString();
                fecha = etFecha.getText().toString();
                genero = etGenero.getText().toString();

                // Validar campos antes de registrar
                if (validateFields()){
                    registrarusuario();
                }
            }
        });

        // Botón para agregar foto de perfil
        fabAddPhoto.setOnClickListener(v -> {
            showToast("Seleccionar foto de perfil");
            // Aquí iría la lógica de selección de foto
        });
    }

    // Método para crear usuario en Firebase Authentication y guardar datos en Firestore
    private void registrarusuario() {
        // Primero crear el usuario en Authentication
        mAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Autenticación exitosa, proceder a almacenar datos del usuario
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("nombre", nombre);
                            userData.put("apellido", apellido);
                            userData.put("correo", correo);
                            userData.put("telefono", telefono);
                            userData.put("fechaNacimiento", fecha);
                            userData.put("genero", genero);

                            String id = mAuth.getCurrentUser().getUid();

                            // Guardar información del usuario en Firestore
                            mFirestore.collection("usuarios").document(id).set(userData)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {
                                            if (task2.isSuccessful()) {
                                                // Mostrar pantalla de carga
                                                loadingContainer.setVisibility(View.VISIBLE);

                                                // Esperar y luego navegar a la pantalla de inicio de sesión
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        // Ocultar pantalla de carga
                                                        loadingContainer.setVisibility(View.GONE);

                                                        // Navegar a inicio de sesión
                                                        Intent intent = new Intent(Registro.this, IniciarSesion.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }, TIEMPO_DE_CARGA);

                                            } else {
                                                // Error al escribir en la base de datos
                                                showToast("Error al guardar datos: " +
                                                        (task2.getException() != null ?
                                                                task2.getException().getMessage() : "Error desconocido"));

                                                // Eliminar cuenta creada ya que no se pudieron guardar los datos
                                                if (mAuth.getCurrentUser() != null) {
                                                    mAuth.getCurrentUser().delete();
                                                }
                                            }
                                        }
                                    });
                        } else {
                            // Autenticación fallida, mostrar mensaje específico según el error
                            String errorMsg = "Error en el registro";
                            if (task.getException() != null) {
                                // Obtener mensaje específico del error de Firebase
                                String firebaseError = task.getException().getMessage();
                                if (firebaseError != null) {
                                    if (firebaseError.contains("email address is already in use")) {
                                        errorMsg = "Este correo ya está registrado";
                                    } else if (firebaseError.contains("password is invalid")) {
                                        errorMsg = "La contraseña no cumple con los requisitos de seguridad";
                                    } else if (firebaseError.contains("network error")) {
                                        errorMsg = "Error de conexión a internet";
                                    }
                                }
                            }
                            showToast(errorMsg);
                        }
                    }
                });
    }

    // Método para configurar el selector de fecha
    private void setupDatePicker() {
        // Configurar listener para el campo de fecha
        etFecha.setOnClickListener(v -> showDatePickerDialog());

        // Configurar listener para el icono de calendario
        tilFecha.setEndIconOnClickListener(v -> showDatePickerDialog());
    }

    // Método para mostrar el diálogo de selección de fecha
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

    // Método para configurar las opciones del dropdown de género
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

    // Método para validar campos del formulario
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

    // Método para mostrar mensajes Toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}