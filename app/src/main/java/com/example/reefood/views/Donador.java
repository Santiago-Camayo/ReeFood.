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
import com.example.reefood.model.Donaciones;
import com.example.reefood.model.ManagerDB;

public class Donador extends AppCompatActivity {

    private EditText edtNombre, edtContacto, edtTituloProducto, edtNota;
    private RadioGroup deliveryMethodGroup;
    private Button btnSiguiente;
    private ImageButton btnAtras;
    private String metodoEntrega = "";
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donador);

        managerDB = new ManagerDB(this);

        inicializarVistas();
        configurarListeners();
    }

    private void inicializarVistas() {
        btnAtras = findViewById(R.id.btnatras);
        edtNombre = findViewById(R.id.edtNombre);
        edtContacto = findViewById(R.id.edtContacto);
        edtTituloProducto = findViewById(R.id.edtNombreProducto);
        edtNota = findViewById(R.id.edtnota);
        deliveryMethodGroup = findViewById(R.id.deliveryMethodGroup);
        btnSiguiente = findViewById(R.id.btnsiguiente);
    }

    private void configurarListeners() {
        btnAtras.setOnClickListener(v -> {
            startActivity(new Intent(Donador.this, Menu.class));
            finish();
        });

        deliveryMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.recojerenubi) {
                metodoEntrega = "Recoger en ubicación";
            } else if (checkedId == R.id.hacerenvio) {
                metodoEntrega = "Envío a domicilio";
            }
        });

        btnSiguiente.setOnClickListener(v -> {
            if (validarCampos()) {
                guardarDonacionEnDB();
            }
        });
    }

    private boolean validarCampos() {
        if (edtNombre.getText().toString().trim().isEmpty()) {
            mostrarToast("Ingrese el nombre del donante");
            return false;
        }

        if (edtContacto.getText().toString().trim().isEmpty()) {
            mostrarToast("Ingrese un número de contacto");
            return false;
        }

        if (edtTituloProducto.getText().toString().trim().isEmpty()) {
            mostrarToast("Ingrese el título de la donación");
            return false;
        }

        if (metodoEntrega.isEmpty()) {
            mostrarToast("Seleccione un método de entrega");
            return false;
        }

        return true;
    }

    private void guardarDonacionEnDB() {
        Donaciones nuevaDonacion = new Donaciones(
                edtNombre.getText().toString(),
                edtTituloProducto.getText().toString(),
                edtNota.getText().toString(),
                edtContacto.getText().toString(),
                metodoEntrega
        );

        long resultado = managerDB.InsertarDonacion(nuevaDonacion);

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