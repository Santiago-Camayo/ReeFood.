package com.example.reefood.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MisDonaciones extends AppCompatActivity {

    // Componentes de la interfaz
    ImageButton btnconfiguracion, btnmisdonaciones, btncasa;
    ListView listaproductos;
    TextView nombreusuario;

    // Firebase
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_donaciones);

        // Inicializar Firebase
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        iduser = mAuth.getCurrentUser().getUid();

        // Inicializar Vistas
        btnconfiguracion = findViewById(R.id.btnConfiguraciones);
        btnmisdonaciones = findViewById(R.id.btnperfil);
        btncasa = findViewById(R.id.btnhome);
        listaproductos = findViewById(R.id.listproductos);
        nombreusuario = findViewById(R.id.nombreusuario);

        // Configurar Botones
        btnconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confi = new Intent(MisDonaciones.this, Configuraciones.class);
                startActivity(confi);
            }
        });

        btnmisdonaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(MisDonaciones.this, MisDonaciones.class);
                startActivity(profile);
            }
        });

        btncasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent casa = new Intent(MisDonaciones.this, Menu.class);
                startActivity(casa);
            }
        });

        // Extraer datos del usuario
        extraerdatosusuario();
    }

    // Método para obtener los datos del usuario actual desde Firestore
    public void extraerdatosusuario() {
        mFirestore.collection("usuarios").document(iduser).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) { // Si el usuario existe, asignar datos al textview
                    String nombre = documentSnapshot.getString("nombre");
                    String apellido = documentSnapshot.getString("apellido");
                    nombreusuario.setText(nombre + " " + apellido);
                } else {
                    Toast.makeText(MisDonaciones.this, "Error: Datos de usuario no encontrados en la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}