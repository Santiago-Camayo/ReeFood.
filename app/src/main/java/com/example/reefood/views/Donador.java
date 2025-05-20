package com.example.reefood.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;
import com.example.reefood.model.Donacion;
import com.google.firebase.firestore.FirebaseFirestore;

public class Donador extends AppCompatActivity {

    private EditText edtnombre,edtcontacto,edttituloproducto,edtnota;
    private RadioGroup deliveryMethodGroup;
    private Button btnSiguiente;
    private ImageButton btnatras;
    private String metodoEntrega = "";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donador);


        db = FirebaseFirestore.getInstance();


        btnatras = findViewById(R.id.btnatras);
        btnatras.setOnClickListener(v -> {
            Intent intent = new Intent(Donador.this, Menu.class);
            startActivity(intent);
        });
        edtnombre = findViewById(R.id.edtNombre);
        edtcontacto = findViewById(R.id.edtContacto);
        edttituloproducto = findViewById(R.id.edtNombreProducto);
        edtnota = findViewById(R.id.edtnota);
        deliveryMethodGroup = findViewById(R.id.deliveryMethodGroup);
        btnSiguiente = findViewById(R.id.btnsiguiente);


        deliveryMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.recojerenubi) {

                metodoEntrega = "Recoger en ubicación";
            } else if (checkedId == R.id.hacerenvio) {

                metodoEntrega = "Envío a domicilio";
            }
        });


        btnSiguiente.setOnClickListener(v -> {

            if (validateFields()) {
                guardarDonacionEnFirestore();
            }
        });
    }
    private void guardarDonacionEnFirestore() {
        Toast.makeText(this, "Guardando donación...", Toast.LENGTH_SHORT).show();

        Donacion nuevaDonacion = new Donacion(
                edtnombre.getText().toString(),
                edtcontacto.getText().toString(),
                edttituloproducto.getText().toString(),
                edtnota.getText().toString(),
                metodoEntrega
        );


        db.collection("donaciones")
                .add(nuevaDonacion)
                .addOnSuccessListener(documentReference -> {

                    Toast.makeText(this, "Donación registrada con éxito", Toast.LENGTH_SHORT).show();


                    startActivity(new Intent(Donador.this, Publicaciones.class));
                    finish();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(this, "Error al registrar la donación: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }


    private boolean validateFields() {

        if (edtnombre.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del donante", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (edtcontacto.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese un número de contacto", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edttituloproducto.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el título de la donación", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (metodoEntrega.isEmpty()) {
            Toast.makeText(this, "Seleccione un método de entrega", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}