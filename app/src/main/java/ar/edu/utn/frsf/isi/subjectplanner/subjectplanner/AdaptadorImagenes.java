package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Fotografia;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

public class AdaptadorImagenes extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    private ArrayList<String> filesPaths;

    public AdaptadorImagenes(Context contexto, ArrayList<String> filesPaths){
        this.contexto = contexto;
        this.filesPaths = filesPaths;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return filesPaths.size();
    }

    @Override
    public Object getItem(int position) {
        //return datos.get(position);
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        convertView = inflater.inflate(R.layout.single_grid, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.ivGrid);
        iv.setImageURI(Uri.parse(getItem(position).toString()));
        return convertView;
        */

        View grid;
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = inflater.inflate(R.layout.single_grid, null);

            ImageView imageView = (ImageView)grid.findViewById(R.id.ivGrid);

            Bitmap bmp = BitmapFactory.decodeFile(filesPaths.get(position));
            imageView.setImageBitmap(bmp);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}