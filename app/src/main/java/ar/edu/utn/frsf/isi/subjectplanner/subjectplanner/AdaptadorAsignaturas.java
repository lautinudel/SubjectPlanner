package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;

public class AdaptadorAsignaturas extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<Asignatura> datos;


    public AdaptadorAsignaturas(Context contexto, ArrayList<Asignatura> datos){
        this.contexto=contexto;
        this.datos=datos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final int position =i;
        final View vista = inflater.inflate(R.layout.list_layout, null);
        TextView nombreAsignatura = (TextView) vista.findViewById(R.id.tvFila);

        nombreAsignatura.setText(datos.get(i).getNombre().toString());


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