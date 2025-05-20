package com.example.refood;

import com.google.firebase.firestore.DocumentId;

// Clase modelo que representa una donación de alimentos en la aplicación
public class Donacion {

    @DocumentId
    private String id;                 // ID del documento en Firestore
    private String nombreDonante;      // Nombre de la persona o establecimiento que dona
    private String contacto;           // Número de teléfono o forma de contacto
    private String descripcion;        // Descripción de los productos donados
    private String nota;               // Notas adicionales sobre la donación
    private String metodoEntrega;      // Método de entrega: "Recoger" o "Envío"
    private String imagenUrl;          // URL de la imagen (opcional)
    private long timestamp;            // Timestamp para ordenar donaciones

    // Constructor vacío requerido para Firestore
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

    // Métodos getter y setter para acceder a los atributos privados
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