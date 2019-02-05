package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;

@Dao
public interface AsignaturaDao {

    @Query("SELECT * FROM Asignatura")
    List<Asignatura> getAll();

    @Insert
    long insert(Asignatura a);

    @Update
    void update(Asignatura a);

    @Delete
    void delete(Asignatura a);
}
