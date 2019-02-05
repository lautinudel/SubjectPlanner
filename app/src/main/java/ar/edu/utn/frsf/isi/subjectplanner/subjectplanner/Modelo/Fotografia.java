package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Fotografia {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String pathFoto;

    public Fotografia(String pathFoto) {
        this.pathFoto = pathFoto;
    }
    public Fotografia() {

    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

}
