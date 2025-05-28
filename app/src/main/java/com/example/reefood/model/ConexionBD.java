package com.example.reefood.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast; // Para depuración (opcional)

import androidx.annotation.Nullable;

public class ConexionBD extends SQLiteOpenHelper {

    private Context context; // Guardar contexto para Toasts si es necesario

    public ConexionBD(@Nullable Context context) {
        // Usa la versión actualizada de Constantes (VERSION_BD = 2)
        super(context, Constantes.NAME_BD, null, Constantes.VERSION_BD);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecuta las sentencias de creación
        db.execSQL(Constantes.SENTENCIA_TABLA_USUARIOS);
        db.execSQL(Constantes.SENTENCIA_TABLA_DONACIONES); // Usa la sentencia corregida
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si la versión antigua es 1 (y la nueva es 2), añade la columna.
        if (oldVersion < 2) {
            try {
                // Sentencia SQL CORRECTA para añadir la columna
                String sqlAlterTable = "ALTER TABLE " + Constantes.TABLA_DONACIONES + // <-- Usa el NOMBRE de la tabla
                        " ADD COLUMN " + Constantes.COL_DON_IMAGEN_URI + " TEXT;"; // <-- Usa el NOMBRE de la columna
                db.execSQL(sqlAlterTable);
                // Opcional: Muestra un mensaje para saber que se actualizó
                // Toast.makeText(context, "Base de datos actualizada a v2", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Manejar posibles errores durante la actualización
                e.printStackTrace();

            }
        }
        // Aquí irían futuras actualizaciones (if (oldVersion < 3), etc.)
    }
}
