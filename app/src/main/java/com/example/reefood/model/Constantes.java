package com.example.reefood.model;


public class Constantes {
    // Nombre de la base de datos de la aplicación.
    public static String NAME_BD = "ReFood_BD";
    // Versión de la base de datos. Se incrementa cuando se realizan cambios en el esquema de la BD.
    public static int VERSION_BD= 1;

    // Sentencia SQL para crear la tabla "Usuarios" si no existe.
    // Esta tabla almacena la información de los usuarios registrados en la aplicación.
    public static String SENTENCIA_TABLA_USUARIOS=
            "CREATE TABLE IF NOT EXISTS Usuarios (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Nombres TEXT NOT NULL, " +
                    "Apellidos TEXT NOT NULL, " +
                    "Correo TEXT NOT NULL," +
                    "Contraseña TEXT NOT NULL," +
                    "Telefono TEXT NOT NULL, " +
                    "FechaNacimiento TEXT NOT NULL," +
                    "Genero TEXT NOT NULL)";

    // Sentencia SQL para crear la tabla "Donaciones" si no existe.
    // Esta tabla almacena la información sobre las donaciones realizadas o disponibles.
    public static String SENTENCIA_TABLA_DONACIONES =
            "CREATE TABLE IF NOT EXISTS Donaciones (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "telefono TEXT NOT NULL, " +
                    "titulo TEXT NOT NULL, " +
                    "descripcion TEXT NOT NULL, " +
                    "entrega TEXT NOT NULL)";

    // Esta clase se utiliza típicamente en conjunto con una clase `SQLiteOpenHelper`
    // para crear y actualizar la base de datos de la aplicación.
    // Por ejemplo, `SENTENCIA_TABLA_USUARIOS` se ejecutaría en el método `onCreate` del `SQLiteOpenHelper`.
}
