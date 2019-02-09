package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

public interface Comunicador {
    public void responder(Tarea tarea);
    public void pasarAsignatura(Asignatura asignatura);
    public void pasarAsignaturaEvaluacion(Asignatura asignatura);
    public void pasarAsignaturasListaEvaluacion(Asignatura asignatura);
    public void pasarEvaluacion(Evaluacion evaluacion);
}
