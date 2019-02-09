package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class Asignatura {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public int anio;
    public int nivel;
    public int periodo;
    public String profesor;
    public String email;
    public String observaciones;


    public Asignatura(String nombre, int anio, int nivel, int periodo, String profesor, String email, String observaciones) {
        this.nombre = nombre;
        this.anio = anio;
        this.nivel = nivel;
        this.periodo = periodo;
        this.profesor = profesor;
        this.email = email;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setValues(String nombre, int anio, int nivel, int periodo, String profesor, String email, String observaciones) {
        this.nombre = nombre;
        this.anio = anio;
        this.nivel = nivel;
        this.periodo = periodo;
        this.profesor = profesor;
        this.email = email;
        this.observaciones = observaciones;
    }

}