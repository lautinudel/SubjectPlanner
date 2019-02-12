package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.EvaluacionesDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;


public class PromediosFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EvaluacionesDao edao;
    private TextView tvIngresar;
    private Spinner nivelSpinner;
    private Button btnCalcularPromedio;
    private float prom;
    private int cant = 0;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Promedios");
        View view = inflater.inflate(R.layout.fragment_promedios, container, false);
        tvIngresar = (TextView) view.findViewById(R.id.tvIngresar);
        cargarSpinnerNivel(view);

        btnCalcularPromedio = (Button) view.findViewById(R.id.btnCacularPromedio);
        btnCalcularPromedio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Thread r = new Thread() {
                    @Override
                    public void run() {
                        try {
                            //Conseguimos la lista de evaluaciones.
                            List<Evaluacion> aux;
                            edao = MyDatabase.getInstance(getActivity().getApplicationContext()).getEvaluacionesDao();
                            aux = edao.getAll();
                            ArrayList<Integer> calificaciones = new ArrayList<Integer>();
                            int suma = 0;

                            if (nivelSpinner.getSelectedItem().toString().equals("Todos (promedio general)")) {
                                for(Evaluacion e : aux){
                                    if(e.getNotaObtenida() != -1)
                                        calificaciones.add(e.getNotaObtenida());
                                }
                            }
                            else {
                                for (Evaluacion e : aux) {
                                    if (e.getAsignatura().getNivel() == Integer.parseInt(nivelSpinner.getSelectedItem().toString()))
                                        if(e.getNotaObtenida() != -1)
                                            calificaciones.add(e.getNotaObtenida());
                                }
                            }

                            cant = calificaciones.size();
                            if (cant != 0) {
                                for (Integer i : calificaciones) {
                                    suma = suma + i;
                                }
                                float promAux =  (float) suma/cant;
                                prom = roundTwoDecimals(promAux);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (cant == 0 && !nivelSpinner.getSelectedItem().toString().equals("Niveles"))
                                    Toast.makeText(getActivity().getApplicationContext(), "No existen calificaciones asociadas a ese nivel", Toast.LENGTH_LONG).show();
                                else if (nivelSpinner.getSelectedItem().toString().equals("Niveles"))
                                    Toast.makeText(getActivity().getApplicationContext(), "Asegúrese de seleccionar un nivel", Toast.LENGTH_LONG).show();
                                else if (nivelSpinner.getSelectedItem().toString().equals("Todos (promedio general)"))
                                    Toast.makeText(getActivity().getApplicationContext(), "Su promedio general es: " + prom, Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity().getApplicationContext(), "El promedio del Nivel " + nivelSpinner.getSelectedItem().toString() + " es: "+prom, Toast.LENGTH_LONG).show();

                            }
                        });
                    }};
                r.start();

            }
        });

        return view;
    }

    public float roundTwoDecimals(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");

        return Float.valueOf(twoDForm.format(d));
    }
    public void cargarSpinnerNivel(View view){
        ArrayList<String> niveles = new ArrayList<>();
        niveles.add("Niveles");
        niveles.add("1");
        niveles.add("2");
        niveles.add("3");
        niveles.add("4");
        niveles.add("5");
        niveles.add("Todos (promedio general)");
        nivelSpinner = (Spinner) view.findViewById(R.id.spnNivelProm);
        ArrayAdapter<String> nivelAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_style, niveles){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner

                    return false; // First item will be use for hint
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        nivelSpinner.setAdapter(nivelAdapter);

        nivelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    /*Toast.makeText
                            (getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
