package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Evaluacion {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public String nombre;
    public int dia;
    public int mes;
    public int anio;
    public int hora;
    public int minutos;
    @Embedded(prefix = "asig")
    public Asignatura asignatura;
    public int notaRegularidad;
    public int notaPromocion;
    public int notaObtenida;

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public void setNotaObtenida(int notaObtenida) {
        this.notaObtenida = notaObtenida;
    }

    public int getNotaObtenida() {
        return notaObtenida;
    }

    public int getMinutos() {
        return minutos;
    }

    public int avisar;
    public int idAlarma;

    public Evaluacion( String nombre, int dia, int mes, int anio, int hora, int minutos, Asignatura asignatura, int notaRegularidad, int notaPromocion, int avisar, int idAlarma) {
        this.nombre = nombre;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minutos=minutos;
        this.asignatura = asignatura;
        this.notaRegularidad = notaRegularidad;
        this.notaPromocion = notaPromocion;
        this.notaObtenida=-1;
    }

    public int getAvisar() {
        return avisar;
    }

    public void setAvisar(int avisar) {
        this.avisar = avisar;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
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
