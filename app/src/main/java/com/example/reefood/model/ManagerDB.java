package com.example.reefood.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ManagerDB {
    private  ConexionBD conection;
    private SQLiteDatabase db;


    //Constructor para inicializar la base datos
    public ManagerDB(Context context){
        this.conection = new ConexionBD(context);
        this.db = conection.getWritableDatabase();
    }

    private void openBDWrite (){
        db = conection.getWritableDatabase();
    }
    private void openBDRead(){
        db = conection.getReadableDatabase();
    }

    public long insert (Usuario usuario){
        openBDWrite();
        ContentValues values = new ContentValues();
        values.put("Nombres", usuario.getNombre());
        values.put("Apellidos", usuario.getApellido());
        values.put("Correo", usuario.getCorreo());
        values.put("Contraseña", usuario.getContrasena());
        values.put("Telefono", usuario.getTelefono());
        values.put("FechaNacimiento", usuario.getFechaNacimiento());
        values.put("Genero", usuario.getGenero());

        long resul = db.insert("Usuarios",null,values);
        return resul;
    }


    //verificar si el usurio esta registrado para logearlo
    public boolean verificarlogin(String Correo, String contrasena){
        openBDRead();

        //consulta para saber si el usuario ya existe
        String query = " SELECT * FROM Usuarios WHERE Correo=? AND Contraseña=?";
        Cursor cursor = db.rawQuery(query, new  String[]{Correo, contrasena});

        boolean existe = cursor.getCount()>0;
        cursor.close();
        return  existe;
    }

    //verificar si el correo ya esta en uso para validar en el registro
    public boolean correoexiste(String Correo){
        openBDRead();

        //consulta para saber si el usuario ya existe
        String query = " SELECT * FROM Usuarios WHERE Correo=?";
        Cursor cursor = db.rawQuery(query, new  String[]{Correo});

        boolean existe = cursor.getCount()>0;
        cursor.close();
        return  existe;
    }
    // cerrar la base de datos
    public void cerrarDB() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }




}
