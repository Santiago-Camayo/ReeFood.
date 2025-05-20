package com.example.refood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class VerDonacion extends AppCompatActivity {

    // Firebase
    private FirebaseFirestore db;
    private String donacionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_donacion);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Obtener los datos enviados desde la actividad anterior
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Extraer los datos de la donación
            String nombre = extras.getString("nombre", "");
            String contacto = extras.getString("contacto", "");
            String titulo = extras.getString("descripcion", "");
            String descripcion = extras.getString("nota", "");
            String metodo = extras.getString("metodo", "");
            donacionId = extras.getString("id", "");

            // Mostrar los datos en las vistas correspondientes
            ((TextView) findViewById(R.id.nombreDonante)).setText(nombre);
            ((TextView) findViewById(R.id.tituloDonacion)).setText(titulo);
            ((TextView) findViewById(R.id.descripcionDonacion)).setText(descripcion);
            ((TextView) findViewById(R.id.metodoentrega)).setText(metodo);
            ((TextView) findViewById(R.id.contactoDonante)).setText(contacto);

            // Si tenemos ID, cargar datos actualizados desde Firestore
            if (!donacionId.isEmpty()) {
                cargarDatosDesdeForestore(donacionId);
            }

            // Configurar el botón de chat para abrir WhatsApp
            findViewById(R.id.botonChat).setOnClickListener(v -> {
                try {
                    // Abrir WhatsApp con el número de contacto
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://wa.me/" + contacto));
                    startActivity(intent);
                } catch (Exception e) {
                    // Mostrar mensaje de error si falla
                    Toast.makeText(this, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show();
                }
            });

            // Configurar el botón de llamar para abrir el marcador telefónico
            findViewById(R.id.botonLlamar).setOnClickListener(v -> {
                // Abrir el marcador con el número de contacto
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contacto));
                startActivity(intent);
            });
        }
    }

    // Método para obtener datos actualizados de la donación desde Firestore
    // Útil en caso de que otros usuarios hayan modificado la donación
    private void cargarDatosDesdeForestore(String id) {
        db.collection("donaciones").document(id)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        Donacion donacion = document.toObject(Donacion.class);
                        if (donacion != null) {
                            // Actualizar la UI con los datos más recientes
                            ((TextView) findViewById(R.id.nombreDonante)).setText(donacion.getNombreDonante());
                            ((TextView) findViewById(R.id.tituloDonacion)).setText(donacion.getDescripcion());
                            ((TextView) findViewById(R.id.descripcionDonacion)).setText(donacion.getNota());
                            ((TextView) findViewById(R.id.metodoentrega)).setText(donacion.getMetodoEntrega());
                            ((TextView) findViewById(R.id.contactoDonante)).setText(donacion.getContacto());
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // No mostrar error, se usan los datos que ya tenemos
                });
    }
}