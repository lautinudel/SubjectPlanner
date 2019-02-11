package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;

public class AdaptadorProfesores extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<Asignatura> datos;

    public AdaptadorProfesores(Context contexto, ArrayList<Asignatura> datos){
        this.contexto=contexto;
        this.datos=datos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final int position =i;
        final View vista = inflater.inflate(R.layout.elemento_lista_profesores, null);
        TextView nombre = (TextView) vista.findViewById(R.id.textViewNombre);
        TextView materia = (TextView) vista.findViewById(R.id.textViewMateria);
        TextView email = (TextView) vista.findViewById(R.id.textViewEmail);


        nombre.setText(datos.get(i).getProfesor());
        materia.setText(datos.get(i).getNombre());
        email.setText(datos.get(i).getEmail());


        return vista;
    }
}
