package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;

public class AdaptadorEvaluaciones extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<Evaluacion> datos;

    public AdaptadorEvaluaciones(Context contexto, ArrayList<Evaluacion> datos){
        this.contexto=contexto;
        this.datos=datos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final int position =i;
        final View vista = inflater.inflate(R.layout.elemento_lista_evaluaciones, null);
        TextView titulo = (TextView) vista.findViewById(R.id.textViewTituloEv);
        TextView dia = (TextView) vista.findViewById(R.id.textViewDiaEv);
        TextView hora = (TextView) vista.findViewById(R.id.textViewHoraEv);
        TextView avisar = (TextView) vista.findViewById(R.id.textViewAvisarEv);
        
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
