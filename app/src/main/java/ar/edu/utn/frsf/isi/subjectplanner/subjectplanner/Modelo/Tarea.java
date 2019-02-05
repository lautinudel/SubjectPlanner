package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;
@Entity
public class Tarea {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;
    public int dia;
    public int mes;
    public int anio;
    public int hora;
    public int minutos;
    public int avisar;

    public Tarea(String nombre, int dia, int mes, int anio, int hora, int minutos, int avisar) {
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minutos = minutos;
        this.avisar = avisar;
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

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getAvisar() {
        return avisar;
    }

    public void setAvisar(int avisar) {
        this.avisar = avisar;
    }

    public void setearDatos(String nombre, int dia, int mes, int anio, int hora, int minutos, int avisar){
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minutos = minutos;
        this.avisar = avisar;
    }
}
