package com.roberto.aplicacionalumnosv2;

public class Alumno {

    private int nummatricula;
    private String nombre;
    private int foto;

    public Alumno(int nummatricula, String nombre, int foto) {
        this.nummatricula = nummatricula;
        this.nombre = nombre;
        this.foto = foto;
    }

    public int getNummatricula() {
        return nummatricula;
    }

    public void setNummatricula(int nummatricula) {
        this.nummatricula = nummatricula;
    }



    public String getNombre() {
        return nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }




}
