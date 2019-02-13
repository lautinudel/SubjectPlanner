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
        TextView notaPromo = (TextView) vista.findViewById(R.id.textViewNotaPromo);
        TextView notaRegu = (TextView) vista.findViewById(R.id.textViewNotaRegu);
        TextView notaObtenida = (TextView) vista.findViewById(R.id.textViewNotaObtenida);
        
        titulo.setText(datos.get(i).getNombre().toString());
        dia.setText(datos.get(i).getDia() + "/"+datos.get(i).getMes() + "/"+datos.get(i).anio);

        if (datos.get(i).getMinutos()<10)
            hora.setText(datos.get(i).getHora() + ":0"+datos.get(i).getMinutos());
        else
            hora.setText(datos.get(i).getHora() + ":"+datos.get(i).getMinutos());

        if(datos.get(i).avisar==1)
            avisar.setText("Avisar: Sí");
        else
            avisar.setText("Avisar: No");

        notaPromo.setText("Nota Promo: " + datos.get(i).getNotaPromocion());
        notaRegu.setText("Nota Regu: " + datos.get(i).getNotaRegularidad());

        if (datos.get(i).getNotaObtenida() == -1)
            notaObtenida.setText("Nota Obtenida: -");
        else
            notaObtenida.setText("Nota Obtenida: " + datos.get(i).getNotaObtenida());


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
