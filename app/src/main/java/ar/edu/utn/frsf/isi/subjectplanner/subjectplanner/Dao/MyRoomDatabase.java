package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

@Database(entities = {Tarea.class, Asignatura.class},version = 2)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract TareaDao tareaDao();
    public abstract AsignaturaDao asignaturaDao();

}