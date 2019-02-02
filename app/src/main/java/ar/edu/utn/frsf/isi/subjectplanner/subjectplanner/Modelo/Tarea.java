package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo;

import java.sql.Time;
import java.util.Date;

public class Tarea {
    public String nombre;
    public Date dia;
    public Time hora;
    public boolean avisar;

    public Tarea(String nombre, Date dia, Time hora, boolean avisar) {
        this.nombre = nombre;
        this.dia = dia;
        this.hora = hora;
        this.avisar = avisar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public boolean isAvisar() {
        return avisar;
    }

    public void setAvisar(boolean avisar) {
        this.avisar = avisar;
    }
}
