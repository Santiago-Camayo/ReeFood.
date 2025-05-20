package com.example.reefood.model;

import com.google.firebase.firestore.DocumentId;


public class Donacion {

    @DocumentId
    private String id,nombreDonante, contacto,descripcion,metodoEntrega,imagenUrl,nota;


    private long timestamp;


    public Donacion() {

    }

    // Constructor para crear una nueva donación con todos los atributos
    public Donacion(String nombreDonante, String contacto, String descripcion, String nota, String metodoEntrega) {
        this.nombreDonante = nombreDonante;
        this.contacto = contacto;
        this.descripcion = descripcion;
        this.nota = nota;
        this.metodoEntrega = metodoEntrega;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreDonante() {
        return nombreDonante;
    }

    public String getContacto() {
        return contacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNota() {
        return nota;
    }

    public String getMetodoEntrega() {
        return metodoEntrega;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }
}