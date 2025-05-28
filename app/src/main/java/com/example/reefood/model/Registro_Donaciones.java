package com.example.reefood.model;

import java.io.Serializable;

public class Registro_Donaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String titulo;
    private String descripcion;
    private String telefono;
    private String entrega;
    String imagenUri;

    // Constructor por defecto, necesario para deserialización y creación de instancias vacías
    public Registro_Donaciones() {
    }

    // Constructor
    public Registro_Donaciones(String nombre, String titulo, String descripcion, String telefono, String entrega,String imagenUri) {
        this.nombre = nombre;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.entrega = entrega;
        this.imagenUri = imagenUri;
    }

    // Getters and setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }



    public String toString() {
        return "Registro_Donaciones{" +
                "nombre='" + nombre + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", entrega='" + entrega + '\'' +
                ", imagenUri='" + imagenUri + '\'' +
                '}';
    }
}