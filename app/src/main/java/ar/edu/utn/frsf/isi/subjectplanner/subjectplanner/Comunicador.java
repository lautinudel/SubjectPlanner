package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

public interface Comunicador {
    public void responder(Tarea tarea);
    public void pasarAsignatura(Asignatura asignatura);
    public void pasarAsignaturaEvaluacion(Asignatura asignatura);

}
