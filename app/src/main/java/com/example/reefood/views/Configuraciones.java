package com.example.reefood.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reefood.R;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class Configuraciones extends AppCompatActivity {


    private Switch switchNotificaciones;
    private SwitchMaterial swDarkMode;


    ImageButton botonhome, btneditperfil;


    View editarperfil;
    LinearLayout btnelminarperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);


        switchNotificaciones = findViewById(R.id.switch_notificaciones);
        swDarkMode = findViewById(R.id.swmodooscuro);


        swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(Configuraciones.this, "CONFIGIGURACION PROXIMAMENTE", Toast.LENGTH_SHORT).show();
            }
        });


        switchNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    Toast.makeText(Configuraciones.this, "Notificaciones encendidas", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(Configuraciones.this, "Notificaciones apagadas", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btneditperfil = findViewById(R.id.btnperfil);
        btneditperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configuraciones.this, MisDonaciones.class);
                startActivity(intent);
            }
        });


        botonhome = findViewById(R.id.homeButton);
        botonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configuraciones.this, Menu.class);
                startActivity(intent);
            }
        });


        editarperfil = findViewById(R.id.Texteditarperfil);
        editarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Configuraciones.this, MisDonaciones.class);
                startActivity(intent);
            }
        });


        btnelminarperfil=findViewById(R.id.btnelminarperfil);
        btnelminarperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogoconfirmacion();
            }
        });
    }

    // recuadro de elminacion de cuenta(falta terminar)
    private void Dialogoconfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción es irreversible y perderás todos tus datos asociados.") // Mensaje
                .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("Configuraciones", "Usuario confirmó eliminación.");

                       //funcion para eliminar cuenta(no disponibe)

                        Toast.makeText(Configuraciones.this, "Procediendo a eliminar cuenta...", Toast.LENGTH_SHORT).show();

                        Log.i("Configuraciones", "!!! Llama a la función de eliminación del backend AQUÍ !!!");

                    }
                })
                .setNegativeButton("No, cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("Configuraciones", "Usuario canceló eliminación.");
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}