package com.example.reefood.model;


public class Constantes {
    // Nombre de la base de datos de la aplicación.
    public static String NAME_BD = "ReFood_BD";
    // Versión de la base de datos. Se incrementa cuando se realizan cambios en el esquema de la BD.
    public static int VERSION_BD= 2;

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

    // --- Tabla Donaciones ---
    public static String TABLA_DONACIONES = "Donaciones"; // <-- Nombre de la tabla
    // Nombres de las columnas (¡Buena práctica!)
    public static String COL_DON_ID = "id";
    public static String COL_DON_NOMBRE = "nombre";
    public static String COL_DON_TELEFONO = "telefono";
    public static String COL_DON_TITULO = "titulo";
    public static String COL_DON_DESCRIPCION = "descripcion";
    public static String COL_DON_ENTREGA = "entrega";
    public static String COL_DON_IMAGEN_URI = "imagen_uri"; // <-- Nombre de columna para la imagen

    // Sentencia SQL para crear la tabla "Donaciones" ¡¡CON LA NUEVA COLUMNA!!
    public static String SENTENCIA_TABLA_DONACIONES =
            "CREATE TABLE IF NOT EXISTS " + TABLA_DONACIONES + " (" +
                    COL_DON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_DON_NOMBRE + " TEXT NOT NULL, " +
                    COL_DON_TELEFONO + " TEXT NOT NULL, " +
                    COL_DON_TITULO + " TEXT NOT NULL, " +
                    COL_DON_DESCRIPCION + " TEXT NOT NULL, " +
                    COL_DON_ENTREGA + " TEXT NOT NULL, " + // <-- Coma añadida
                    COL_DON_IMAGEN_URI + " TEXT)"; // <-- Columna añadida


    // Esta clase se utiliza típicamente en conjunto con una clase `SQLiteOpenHelper`
    // para crear y actualizar la base de datos de la aplicación.
    // Por ejemplo, `SENTENCIA_TABLA_USUARIOS` se ejecutaría en el método `onCreate` del `SQLiteOpenHelper`.


    }

