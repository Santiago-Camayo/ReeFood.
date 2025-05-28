package com.example.reefood.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;
import com.example.reefood.model.ManagerDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class IniciarSesion extends BaseActivity {

    // Declaración de componentes UI
    private TextInputLayout contenedorEmail;
    private TextInputLayout contenedorPassword;
    private TextInputEditText campoEmail;
    private TextInputEditText campoPassword;
    private MaterialButton botonIniciarSesion;
    private MaterialButton botonRegistrarse;

    private LinearLayout pantallacarga;

    // Base de datos SQLite
    private ManagerDB managerDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        // Inicializar base de datos SQLite
        managerDB = new ManagerDB(this);

        // Inicialización de elementos visuales
        contenedorEmail = findViewById(R.id.email_layout);
        contenedorPassword = findViewById(R.id.password_layout);
        campoEmail = findViewById(R.id.email_edit_text);
        campoPassword = findViewById(R.id.password_edit_text);
        botonIniciarSesion = findViewById(R.id.login_button);
        botonRegistrarse = findViewById(R.id.register_button);
        pantallacarga = findViewById(R.id.loading_container);

        // Configuración del botón de inicio de sesión - VERSIÓN SQLITE
        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentarIniciarSesionSQLite();
            }
        });

        // Configuración del botón de registro
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(IniciarSesion.this, Registro.class);
                startActivity(registro);
            }
        });
    }

    private void intentarIniciarSesionSQLite() {
        // Limpiar errores anteriores
        contenedorEmail.setError(null);
        contenedorPassword.setError(null);

        // Obtener valores de entrada
        String email = String.valueOf(campoEmail.getText()).trim();
        String contraseña = String.valueOf(campoPassword.getText()).trim();

        // Validar entradas primero
        boolean hayError = false;

        if (email.isEmpty()) {
            contenedorEmail.setError("El correo es obligatorio");
            hayError = true;
        } else if (!esEmailValido(email)) {
            contenedorEmail.setError("Correo electrónico inválido");
            hayError = true;
        }

        if (contraseña.isEmpty()) {
            contenedorPassword.setError("La contraseña es obligatoria");
            hayError = true;
        }

        if (hayError) {
            return;
        }

        // Validar usuario en SQLite
        boolean usuarioValido = managerDB.verificarlogin(email, contraseña);

        if (usuarioValido) {
            // Login exitoso
            Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show();
            cargarsiguientepantalla();
        } else {
            // Credenciales incorrectas
            if (managerDB.correoexiste(email)) {
                // El correo existe pero la contraseña es incorrecta
                contenedorPassword.setError("Contraseña incorrecta");
                Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
            } else {
                // El correo no existe
                contenedorEmail.setError("Cuenta no encontrada");
                Toast.makeText(this, "No existe una cuenta con este correo electrónico", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //validar el formato del email
    private boolean esEmailValido(String email) {
        // Validación usando expresión regular
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(pattern);
    }

    //mostrar pantalla de carga
    private void cargarsiguientepantalla() {
        pantallacarga.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pantallacarga.setVisibility(View.GONE);
                Intent menu = new Intent(IniciarSesion.this, Publicaciones.class);
                startActivity(menu);
                finish();
            }
        }, 3000); // Espera 3 segundos antes de navegar
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la base de datos al destruir la actividad
        if (managerDB != null) {
            managerDB.cerrarDB();
        }
    }
}