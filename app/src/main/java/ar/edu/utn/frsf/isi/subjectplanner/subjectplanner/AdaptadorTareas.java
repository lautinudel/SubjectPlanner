package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


//Esta clase se usa para organizar los datos de cada fila de la actividad Tareas (organiza los datos de cada fila)



public class AdaptadorTareas extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    String[][] datos;


    public AdaptadorTareas(Context contexto, String[][] datos){
        this.contexto=contexto;
        this.datos=datos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista_tareas, null);
        TextView titulo = (TextView) vista.findViewById(R.id.textViewTitulo);
        TextView dia = (TextView) vista.findViewById(R.id.textViewDia);
        TextView hora = (TextView) vista.findViewById(R.id.textViewHora);
        TextView avisar = (TextView) vista.findViewById(R.id.textViewAvisar);

        titulo.setText(datos[i][0]);
        dia.setText(datos[i][1]);
        hora.setText(datos[i][2]);
        avisar.setText(datos[i][3]);

        return vista;
    }

    @Override
    public int getCount() {
        return datos.length;
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
