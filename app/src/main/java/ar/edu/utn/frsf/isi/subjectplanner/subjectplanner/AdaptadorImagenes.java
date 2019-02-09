package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Fotografia;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

public class AdaptadorImagenes extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    private ArrayList<File> datos;

    public AdaptadorImagenes(Context contexto, ArrayList<File> datos){
        this.contexto = contexto;
        this.datos = datos;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.single_grid, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.ivGrid);

        iv.setImageURI(Uri.parse(getItem(position).toString()));

        return convertView;
    }
}