package com.example.reefood.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManagerDB {
    private  ConexionBD conection;
    private SQLiteDatabase db;



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



    public boolean verificarlogin(String Correo, String contrasena){
        openBDRead();


        String query = " SELECT * FROM Usuarios WHERE Correo=? AND Contraseña=?";
        Cursor cursor = db.rawQuery(query, new  String[]{Correo, contrasena});

        boolean existe = cursor.getCount()>0;
        cursor.close();
        return  existe;
    }


    public boolean correoexiste(String Correo){
        openBDRead();



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

    public long InsertarDonacion(Donaciones donacion) {
        openBDWrite();
        ContentValues values = new ContentValues();
        values.put("nombre", donacion.getNombre());
        values.put("telefono", donacion.getTelefono());
        values.put("titulo", donacion.getTitulo());
        values.put("descripcion", donacion.getDescripcion());
        values.put("entrega", donacion.getEntrega());

        long resultado = db.insert("Donaciones", null, values);
        cerrarDB();
        return resultado;
    }
    public ArrayList<Donaciones> obtenerdonaciones() {
        openBDRead();  // Abre la BD en modo lectura
        ArrayList<Donaciones> listaDonaciones = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Donaciones", null);

        if (cursor.moveToFirst()) {
            do {
                Donaciones donacion = new Donaciones();
                donacion.setNombre(cursor.getString(1));     // nombre
                donacion.setTelefono(cursor.getString(2));   // teléfono
                donacion.setTitulo(cursor.getString(3));     // título
                donacion.setDescripcion(cursor.getString(4)); // descripción
                donacion.setEntrega(cursor.getString(5));     // método de entrega
                listaDonaciones.add(donacion);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaDonaciones;
    }




}
