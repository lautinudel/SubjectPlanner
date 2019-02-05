package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

public interface Comunicador {
    public void responder(Tarea tarea, boolean nueva);
}
