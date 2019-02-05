package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Fotografia;

@Dao
public interface FotografiaDao {

    @Query("SELECT * FROM Fotografia")
    List<Fotografia> getAll();


    @Insert
    long insert(Fotografia f);

    @Update
    void update(Fotografia f);

    @Delete
    void delete(Fotografia f);

}
