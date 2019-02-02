package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

@Dao
public interface TareaDao {

    @Query("SELECT * FROM Tarea")
    List<Tarea> getAll();

    @Insert
    long insert(Tarea t);

    @Update
    void update(Tarea t);

    @Delete
    void delete(Tarea t);


}
