package com.example.reefood.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManagerDB {
    // Variables de instancia: gestionan la conexión y la base de datos
    private ConexionBD conection; // Clase personalizada para configurar/crear la base de datos
    private SQLiteDatabase db;    // Instancia de SQLiteDatabase para operaciones en la base de datos

    // Constructor: Inicializa la conexión a la base de datos
    public ManagerDB(Context context) {
        this.conection = new ConexionBD(context); // Crea instancia de ConexionBD con el contexto de Android
        this.db = conection.getWritableDatabase(); // Obtiene una instancia de la base de datos en modo escritura
    }

    // Abre la base de datos en modo escritura
    private void openBDWrite() {
        db = conection.getWritableDatabase(); // Reasigna db a una instancia de base de datos en modo escritura
    }

    // Abre la base de datos en modo solo lectura
    private void openBDRead() {
        db = conection.getReadableDatabase(); // Reasigna db a una instancia de base de datos en modo lectura
    }

    // Obtiene el nombre del primer usuario de la tabla Usuarios
    public String NombreUsuario() {
        SQLiteDatabase db = conection.getReadableDatabase(); // Obtiene base de datos en modo lectura (variable local, no usa el campo de clase)
        String nombre = ""; // Valor por defecto si no hay datos
        Cursor cursor = db.rawQuery("SELECT * FROM Usuarios", null); // Consulta todas las columnas de la tabla Usuarios

        if (cursor.moveToNext()) { // Avanza al primer registro (si existe)
            nombre = cursor.getString(1); // Obtiene el valor de la primera columna (se asume que es el nombre)
        }
        cursor.close(); // Cierra el cursor para liberar recursos
        return nombre; // Devuelve el nombre o cadena vacía si no hay datos
    }

    // Inserta un registro de usuario en la tabla Usuarios
    public long insert(Registro_Usuario usuario) {
        openBDWrite(); // Asegura que la base de datos esté en modo escritura
        ContentValues values = new ContentValues(); // Contenedor para mapear valores a columnas
        // Mapea los campos de Registro_Usuario a las columnas de la tabla Usuarios
        values.put("Nombres", usuario.getNombre());
        values.put("Apellidos", usuario.getApellido());
        values.put("Correo", usuario.getCorreo());
        values.put("Contraseña", usuario.getContrasena());
        values.put("Telefono", usuario.getTelefono());
        values.put("FechaNacimiento", usuario.getFechaNacimiento());
        values.put("Genero", usuario.getGenero());

        long resul = db.insert("Usuarios", null, values); // Inserta datos en la tabla Usuarios; devuelve ID de fila o -1 si falla
        return resul; // Devuelve el resultado de la inserción
    }

    // Verifica si existe un usuario con el correo y contraseña proporcionados
    public boolean verificarlogin(String Correo, String contrasena) {
        openBDRead(); // Abre la base de datos en modo lectura
        String query = "SELECT * FROM Usuarios WHERE Correo=? AND Contraseña=?"; // Consulta parametrizada para evitar inyección SQL
        Cursor cursor = db.rawQuery(query, new String[]{Correo, contrasena}); // Ejecuta consulta con correo y contraseña

        boolean existe = cursor.getCount() > 0; // Verdadero si hay al menos un registro coincidente
        cursor.close(); // Cierra el cursor para liberar recursos
        return existe; // Devuelve el resultado de la verificación
    }

    // Verifica si un correo ya existe en la tabla Usuarios
    public boolean correoexiste(String Correo) {
        openBDRead(); // Abre la base de datos en modo lectura
        String query = "SELECT * FROM Usuarios WHERE Correo=?"; // Consulta parametrizada para verificar el correo
        Cursor cursor = db.rawQuery(query, new String[]{Correo}); // Ejecuta consulta con el correo

        boolean existe = cursor.getCount() > 0; // Verdadero si el correo existe
        cursor.close(); // Cierra el cursor para liberar recursos
        return existe; // Devuelve el resultado de la verificación
    }

    // Cierra la conexión a la base de datos si está abierta
    public void cerrarDB() {
        if (db != null && db.isOpen()) { // Verifica si db está inicializada y abierta
            db.close(); // Cierra la base de datos para liberar recursos
        }
    }

    // Inserta un registro de donación en la tabla Donaciones
    public long InsertarDonacion(Registro_Donaciones donacion) {
        openBDWrite(); // Asegura que la base de datos esté en modo escritura
        ContentValues values = new ContentValues(); // Contenedor para mapear valores a columnas
        // Mapea los campos de Registro_Donaciones a las columnas de la tabla Donaciones
        values.put("nombre", donacion.getNombre());
        values.put("telefono", donacion.getTelefono());
        values.put("titulo", donacion.getTitulo());
        values.put("descripcion", donacion.getDescripcion());
        values.put("entrega", donacion.getEntrega());

        long resultado = db.insert("Donaciones", null, values); // Inserta datos en la tabla Donaciones; devuelve ID de fila o -1 si falla
        cerrarDB(); // Cierra la base de datos tras la inserción
        return resultado; // Devuelve el resultado de la inserción
    }

    // Obtiene todos los registros de la tabla Donaciones
    public ArrayList<Registro_Donaciones> obtenerdonaciones() {
        openBDRead(); // Abre la base de datos en modo lectura
        ArrayList<Registro_Donaciones> listaDonaciones = new ArrayList<>(); // Lista para almacenar objetos de donaciones
        Cursor cursor = db.rawQuery("SELECT * FROM Donaciones", null); // Consulta todas las columnas de la tabla Donaciones

        if (cursor.moveToFirst()) { // Avanza al primer registro (si existe)
            do {
                Registro_Donaciones donacion = new Registro_Donaciones();

                donacion.setNombre(cursor.getString(1));
                donacion.setTelefono(cursor.getString(2));
                donacion.setTitulo(cursor.getString(3));
                donacion.setDescripcion(cursor.getString(4));
                donacion.setEntrega(cursor.getString(5));
                listaDonaciones.add(donacion);
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cierra el cursor para liberar recursos
        return listaDonaciones; // Devuelve la lista de donaciones (vacía si no hay datos)
    }
}
