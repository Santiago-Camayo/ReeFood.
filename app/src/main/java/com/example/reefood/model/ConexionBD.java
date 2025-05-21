package com.example.reefood.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Clase helper para gestionar la creación y actualización de la base de datos SQLite de la aplicación.
// Hereda de SQLiteOpenHelper, que provee el framework para estas operaciones.

public class ConexionBD extends SQLiteOpenHelper {
    // Constructor de la clase.
    public ConexionBD(@Nullable Context context) {
        super(context, Constantes.NAME_BD, null, Constantes.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecuta la sentencia SQL para crear la tabla de usuarios.
        db.execSQL(Constantes.SENTENCIA_TABLA_USUARIOS);
        // Ejecuta la sentencia SQL para crear la tabla de donaciones.
        db.execSQL(Constantes.SENTENCIA_TABLA_DONACIONES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
