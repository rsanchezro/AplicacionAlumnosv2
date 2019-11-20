package com.roberto.aplicacionalumnosv2.viewmodel;

import androidx.lifecycle.ViewModel;

import com.roberto.aplicacionalumnosv2.Alumno;

import java.util.ArrayList;

public class AlumnoViewModel extends ViewModel {
    ArrayList<Alumno> lista_alumnos=null;

    public AlumnoViewModel(ArrayList<Alumno> l)
    {
        this.lista_alumnos=l;
    }

    public AlumnoViewModel(){
        super();
    }

    public void anadirAlumno(Alumno a)
    {
        this.lista_alumnos.add(a);
    }
    public void eliminarAlumno(Alumno a)
    {
        this.lista_alumnos.remove(a);
    }

    public ArrayList<Alumno> getLista_alumnos() {
        return lista_alumnos;
    }

    public void setLista_alumnos(ArrayList<Alumno> lista_alumnos) {
        this.lista_alumnos = lista_alumnos;
    }
}
