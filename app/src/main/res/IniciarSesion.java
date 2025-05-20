package com.example.refood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class IniciarSesion extends AppCompatActivity {

    // Declaración de componentes UI
    private TextInputLayout contenedorEmail;
    private TextInputLayout contenedorPassword;
    private TextInputEditText campoEmail;
    private TextInputEditText campoPassword;
    private MaterialButton botonIniciarSesion;
    private MaterialButton botonRegistrarse;
    private MaterialButton botonOlvidoPassword;
    private LinearLayout pantallacarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        // Inicialización de elementos visuales
        contenedorEmail = findViewById(R.id.email_layout);
        contenedorPassword = findViewById(R.id.password_layout);
        campoEmail = findViewById(R.id.email_edit_text);
        campoPassword = findViewById(R.id.password_edit_text);
        botonIniciarSesion = findViewById(R.id.login_button);
        botonRegistrarse = findViewById(R.id.register_button);
        botonOlvidoPassword = findViewById(R.id.forgot_password_button);
        pantallacarga = findViewById(R.id.loading_container);



        // Configuración del botón de inicio de sesión
        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentarIniciarSesion();
            }
        });

        // Configuración del botón de registro - Navega a la pantalla de registro
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(IniciarSesion.this, Registro.class);
                startActivity(registro);
            }
        });

        // Configuración del botón de recuperación de contraseña
        botonOlvidoPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(campoEmail.getText());
                if (email.isEmpty()) {
                    Toast.makeText(IniciarSesion.this, "Ingresa tu correo para recuperar tu contraseña", Toast.LENGTH_LONG).show();
                    contenedorEmail.setError("Necesario para recuperar contraseña");
                    return;
                }

                if (!esEmailValido(email)) {
                    Toast.makeText(IniciarSesion.this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
                    contenedorEmail.setError("Formato de correo inválido");
                    return;
                }

                // Enviar correo de recuperación
                enviarCorreoRecuperacion(email);
            }
        });
    }

    /**
     * Envía un correo de recuperación de contraseña a través de Firebase
     */
    private void enviarCorreoRecuperacion(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(IniciarSesion.this, "Se ha enviado un correo para restablecer tu contraseña", Toast.LENGTH_LONG).show();
                        } else {
                            manejarErrorRecuperacion(task.getException());
                        }
                    }
                });
    }

    //Gestiona los diferentes tipos de errores durante la recuperación de contraseña

    private void manejarErrorRecuperacion(Exception exception) {
        String mensajeError = "No se pudo enviar el correo de recuperación";

        if (exception instanceof FirebaseAuthInvalidUserException) {
            mensajeError = "No existe una cuenta con este correo electrónico";
        } else if (exception instanceof FirebaseNetworkException) {
            mensajeError = "Error de conexión a internet. Verifica tu conexión";
        }

        Toast.makeText(IniciarSesion.this, mensajeError, Toast.LENGTH_LONG).show();
    }

    /**
     * Valida los campos y realiza el intento de inicio de sesión
     */
    private void intentarIniciarSesion() {
        // Limpiar errores anteriores
        contenedorEmail.setError(null);
        contenedorPassword.setError(null);

        // Obtener valores de entrada
        String email = String.valueOf(campoEmail.getText());
        String contraseña = String.valueOf(campoPassword.getText());

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

        // Intentar autenticación con Firebase
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso
                            cargarsiguientepantalla();
                        } else {
                            // Manejo detallado de errores
                            manejarErrorAutenticacion(task.getException());
                        }
                    }
                });
    }

    /**
     * Gestiona los diferentes tipos de errores durante la autenticación
     */
    private void manejarErrorAutenticacion(Exception exception) {
        if (exception == null) {
            Toast.makeText(this, "Error desconocido al iniciar sesión", Toast.LENGTH_SHORT).show();
            return;
        }

        // Identificar el tipo de excepción para mostrar mensajes específicos
        if (exception instanceof FirebaseAuthInvalidUserException) {
            // El usuario no existe o ha sido deshabilitado
            contenedorEmail.setError("Cuenta no encontrada");
            Toast.makeText(this, "No existe una cuenta con este correo electrónico", Toast.LENGTH_SHORT).show();
        }
        else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            String errorCode = ((FirebaseAuthInvalidCredentialsException) exception).getErrorCode();
            if (errorCode != null && errorCode.equals("ERROR_INVALID_EMAIL")) {
                contenedorEmail.setError("Formato de correo inválido");
                Toast.makeText(this, "El formato del correo electrónico es inválido", Toast.LENGTH_SHORT).show();
            } else {
                // Contraseña incorrecta u otros errores de credenciales
                contenedorPassword.setError("Contraseña incorrecta");
                Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
            }
        }
        else if (exception instanceof FirebaseNetworkException) {
            // Problemas de conexión
            Toast.makeText(this, "Error de conexión a internet. Verifica tu conexión", Toast.LENGTH_SHORT).show();
        }
        else {
            // Cualquier otro error
            Toast.makeText(this, "Error al iniciar sesión: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Valida si un email tiene formato correcto
     */
    private boolean esEmailValido(String email) {
        // Validación usando expresión regular
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(pattern);
    }

    /**
     * Muestra pantalla de carga y navega a la pantalla principal
     */
    private void cargarsiguientepantalla(){
        pantallacarga.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pantallacarga.setVisibility(View.GONE);
                Intent menu = new Intent(IniciarSesion.this, Menu.class);
                startActivity(menu);
                finish();
            }
        }, 5000); // Espera 5 segundos antes de navegar
    }
}