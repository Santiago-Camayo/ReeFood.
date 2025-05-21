package com.example.reefood.model;

import java.io.Serializable;

public class Donaciones implements Serializable {
    // Add the serialVersionUID for better version control

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String titulo;
    private String descripcion;
    private String telefono;
    private String entrega;


    public Donaciones() {
    }

    // Constructor
    public Donaciones(String nombre, String titulo, String descripcion, String telefono, String entrega) {
        this.nombre = nombre;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.entrega = entrega;
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
}