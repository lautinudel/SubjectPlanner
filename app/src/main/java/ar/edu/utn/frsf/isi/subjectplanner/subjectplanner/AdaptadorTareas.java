package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;


//Esta clase se usa para organizar los datos de cada fila de la actividad Tareas (organiza los datos de cada fila)



public class AdaptadorTareas extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<Tarea> datos;


    public AdaptadorTareas(Context contexto, ArrayList<Tarea> datos){
        this.contexto=contexto;
        this.datos=datos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final int position =i;
        final View vista = inflater.inflate(R.layout.elemento_lista_tareas, null);
        TextView titulo = (TextView) vista.findViewById(R.id.textViewTitulo);
        TextView dia = (TextView) vista.findViewById(R.id.textViewDia);
        TextView hora = (TextView) vista.findViewById(R.id.textViewHora);
        TextView avisar = (TextView) vista.findViewById(R.id.textViewAvisar);

        titulo.setText(datos.get(i).getNombre().toString());
        dia.setText(datos.get(i).getDia()+"/"+datos.get(i).getMes()+"/"+datos.get(i).anio);
        hora.setText(datos.get(i).getHora()+":"+datos.get(i).getMinutos());
        if(datos.get(i).avisar==1)avisar.setText("Avisar: Si");
        else avisar.setText("Avisar: No");



        return vista;
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


}
