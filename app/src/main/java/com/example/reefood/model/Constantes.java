package com.example.reefood.model;

public class Constantes {
    public static String NAME_BD = "ReFood_BD";
    public static int VERSION_BD= 1;

    public static String SENTENCIA_TABLA_USUARIOS=
            "CREATE TABLE IF NOT EXISTS Usuarios (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Nombres TEXT NOT NULL, " +
                    "Apellidos TEXT NOT NULL, " +
                    "Correo TEXT NOT NULL," +
                    "Contraseña TEXT NOT NULL," +
                    "Telefono TEXT NOT NULL, " +
                    "FechaNacimiento TEXT NOT NULL,)" +
                    "Genero TEXT NOT NULL,";

    public static String SENTENCIA_TABLA_DONACIONES =
            "CREATE TABLE IF NOT EXISTS Donaciones (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "telefono TEXT NOT NULL, " +
                    "titulo TEXT NOT NULL, " +
                    "descripcion TEXT NOT NULL, " +
                    "entrega TEXT NOT NULL)";


}
