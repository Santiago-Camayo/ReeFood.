package com.example.reefood.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;
import com.example.reefood.model.Registro_Donaciones;
import android.widget.ImageView;

public class VerDonacion extends BaseActivity {

    private Registro_Donaciones donacion;
    private TextView tituloDonacion;
    private TextView nombreDonante;
    private TextView contactoDonante;
    private TextView descripcionDonacion;
    private TextView metodoEntrega;
    private ImageView imagenDonacion; // <-- AÑADIR ImageView
    private Button botonChat;
    private Button botonLlamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_donacion);

        // Obtener la donación del intent
        donacion = (Registro_Donaciones) getIntent().getSerializableExtra("donacion");


        // Verificación alternativa en caso de que se envíen los campos por separado
        if (donacion == null) {
            // Verificar si hay datos individuales
            String nombre = getIntent().getStringExtra("nombre");
            String telefono = getIntent().getStringExtra("telefono");
            String titulo = getIntent().getStringExtra("titulo");
            String descripcion = getIntent().getStringExtra("descripcion");
            String entrega = getIntent().getStringExtra("entrega");

            if (nombre != null && titulo != null) {
                // Crear objeto Donaciones con los datos individuales
                donacion = new Registro_Donaciones(nombre, titulo, descripcion, telefono, entrega, null);
            } else {
                Toast.makeText(this, "Error al cargar la donación", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        // Inicializar vistas
        tituloDonacion = findViewById(R.id.tituloDonacion);
        nombreDonante = findViewById(R.id.nombreDonante);
        contactoDonante = findViewById(R.id.contactoDonante);
        descripcionDonacion = findViewById(R.id.descripcionDonacion);
        metodoEntrega = findViewById(R.id.metodoentrega);
        imagenDonacion = findViewById(R.id.imagenDonacion);
        botonChat = findViewById(R.id.botonChat);
        botonLlamar = findViewById(R.id.botonLlamar);

        // Mostrar datos de la donación
        mostrarDatosDonacion();

        // Configurar listeners de los botones
        configurarBotones();
    }

    private void mostrarDatosDonacion() {
        tituloDonacion.setText(donacion.getTitulo());
        nombreDonante.setText(donacion.getNombre());
        contactoDonante.setText(donacion.getTelefono());
        descripcionDonacion.setText(donacion.getDescripcion());
        metodoEntrega.setText(donacion.getEntrega());
        // --- Cargar la imagen ---
        String uriString = donacion.getImagenUri();
        if (uriString != null && !uriString.isEmpty()) {
            try {
                Uri imgUri = Uri.parse(uriString);
                imagenDonacion.setImageURI(imgUri);
            } catch (Exception e) {
                imagenDonacion.setImageResource(R.drawable.ic_placeholder_image); // Usa tu placeholder
                e.printStackTrace();
            }
        } else {
            imagenDonacion.setImageResource(R.drawable.ic_placeholder_image); // Usa tu placeholder
        }
        // --- Fin Cargar la imagen ---
    }

    private void configurarBotones() {
        // Botón para llamar al contacto
        botonLlamar.setOnClickListener(v -> {
            if (donacion.getTelefono() != null && !donacion.getTelefono().isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + donacion.getTelefono()));
                startActivity(intent);
            } else {
                Toast.makeText(VerDonacion.this, "Número de contacto no disponible", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para iniciar chat (implementación básica)
        botonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VerDonacion.this,
                        "Funcionalidad de chat en desarrollo",
                        Toast.LENGTH_SHORT).show();
                // Aquí puedes implementar la lógica para abrir un chat
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}