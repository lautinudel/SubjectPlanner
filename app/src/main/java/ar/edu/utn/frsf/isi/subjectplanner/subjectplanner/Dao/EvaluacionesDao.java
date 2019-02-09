package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;

@Dao
public interface EvaluacionesDao {

    @Query("SELECT * FROM Evaluacion")
    List<Evaluacion> getAll();

    @Query("SELECT * FROM Evaluacion e WHERE e.asigid = :id ")
    List<Evaluacion> getByAsignaturaId(int id);

    @Insert
    long insert(Evaluacion e);

    @Update
    void update(Evaluacion e);

    @Delete
    void delete(Evaluacion e);
}
