package com.example.reefood.model;

public class Usuario {

    private long id;
    private String nombre, apellido, correo, contraseña, telefono, fechaNacimiento, genero;




    //Constructor vacio
    public Usuario(){

    }
    //constructor con valores
    public Usuario(long id, String nombre, String apellido, String correo, String contraseña, String telefono, String fechaNacimiento, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
    }


    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contraseña = contrasena;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
