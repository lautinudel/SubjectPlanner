package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo;

import android.arch.persistence.room.PrimaryKey;

public class Evaluacion {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public String nombre;
    public int dia;
    public int mes;
    public int anio;
    public int hora;
    public Asignatura asignatura;
    public int notaRegularidad;
    public int notaPromocion;

    public Evaluacion(int id, String nombre, int dia, int mes, int anio, int hora, Asignatura asignatura, int notaRegularidad, int notaPromocion) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.asignatura = asignatura;
        this.notaRegularidad = notaRegularidad;
        this.notaPromocion = notaPromocion;
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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public int getNotaRegularidad() {
        return notaRegularidad;
    }

    public void setNotaRegularidad(int notaRegularidad) {
        this.notaRegularidad = notaRegularidad;
    }

    public int getNotaPromocion() {
        return notaPromocion;
    }

    public void setNotaPromocion(int notaPromocion) {
        this.notaPromocion = notaPromocion;
    }
}
