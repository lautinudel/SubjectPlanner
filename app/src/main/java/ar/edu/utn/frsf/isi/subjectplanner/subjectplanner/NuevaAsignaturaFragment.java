package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.AsignaturaDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.EvaluacionesDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaAsignaturaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaAsignaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaAsignaturaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NuevaAsignaturaFragment() {
        // Required empty public constructor
    }

    private Spinner nivelSpinner;
    private Spinner periodoSpinner;
    private Asignatura asignatura;
    private EditText edtNombreAsignatura;
    private EditText edtAnioAsignatura;
    private EditText edtProfesorAsignatura;
    private EditText edtEmailAsignatura;
    private EditText edtObservacionesAsignatura;
    private AsignaturaDao aDao;
    private EvaluacionesDao edao;
    private ArrayList<Evaluacion> evaluaciones =  new ArrayList<Evaluacion>();
    private Button btnModificar;
    private Button btnEliminar;
    private Button btnEvaluaciones;
    private Button btnEstado;
    private Button btnGuardarAsignatura;
    private Boolean editar;
    private Comunicador comunicador;
    private String estadoAsignatura;
    private int flag;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaAsignaturaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaAsignaturaFragment newInstance(String param1, String param2) {
        NuevaAsignaturaFragment fragment = new NuevaAsignaturaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        comunicador = (Comunicador) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nueva_asignatura, container, false);
        cargarSpinnerNivel(view);
        cargarSpinnerPeriodo(view);
        edtNombreAsignatura = (EditText) view.findViewById(R.id.edtNombre);
        edtAnioAsignatura = (EditText) view.findViewById(R.id.edtAnio);
        edtEmailAsignatura = (EditText) view.findViewById(R.id.edtEmail);
        edtProfesorAsignatura = (EditText) view.findViewById(R.id.edtProfesor);
        edtObservacionesAsignatura = (EditText) view.findViewById(R.id.edtObservaciones);
        btnEliminar = (Button) view.findViewById(R.id.btnEliminar);
        btnEvaluaciones = (Button) view.findViewById(R.id.btnEvaluacion);
        btnEstado = (Button) view.findViewById(R.id.btnEstado);
        btnModificar = (Button) view.findViewById(R.id.btnModificar);
        btnGuardarAsignatura = (Button) view.findViewById(R.id.btnGuardarAsignatura);
        if(asignatura==null){
            ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Nueva Asignatura");
            editar=false;
            btnEliminar.setVisibility(View.INVISIBLE);
            btnEvaluaciones.setVisibility(View.INVISIBLE);
            btnEstado.setVisibility(View.INVISIBLE);
            btnModificar.setVisibility(View.INVISIBLE);
        }else{
            this.editarAsignatura(asignatura);
            btnGuardarAsignatura.setEnabled(false);
        }




        btnGuardarAsignatura.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(edtNombreAsignatura.getText().toString().isEmpty() || edtAnioAsignatura.getText().toString().isEmpty() || edtEmailAsignatura.getText().toString().isEmpty() || edtProfesorAsignatura.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Asegúrese de completar todos los campos",Toast.LENGTH_SHORT).show();
                }else {
                    if(editar==false) {
                        //Se guarda la asignatura en la base de datos
                        Thread r = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    int auxPeriodo;
                                    if (periodoSpinner.getSelectedItem().toString() == "1° Cuatrimestre") {
                                        auxPeriodo = 1;
                                    } else if (periodoSpinner.getSelectedItem().toString() == "2° Cuatrimestre") {
                                        auxPeriodo = 2;
                                    } else {
                                        auxPeriodo = 3;
                                    }
                                    Asignatura nueva = new Asignatura(
                                            edtNombreAsignatura.getText().toString(), Integer.parseInt(edtAnioAsignatura.getText().toString()),
                                            Integer.parseInt(nivelSpinner.getSelectedItem().toString()), auxPeriodo,
                                            edtProfesorAsignatura.getText().toString(), edtEmailAsignatura.getText().toString(), edtObservacionesAsignatura.getText().toString()
                                    );
                                    aDao = MyDatabase.getInstance(getActivity().getApplicationContext()).getAsignaturaDao();
                                    aDao.insert(nueva);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(), "La asignatura se agregó exitosamente", Toast.LENGTH_LONG).show();
                                        edtNombreAsignatura.setText("");
                                        edtAnioAsignatura.setText("");
                                        edtEmailAsignatura.setText("");
                                        edtProfesorAsignatura.setText("");
                                        edtObservacionesAsignatura.setText("");
                                    }
                                });
                            }
                        };
                        r.start();
                    }else{

                        Thread r = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    int auxPeriodo;
                                    if (periodoSpinner.getSelectedItem().toString() == "1° Cuatrimestre") {
                                        auxPeriodo = 1;
                                    } else if (periodoSpinner.getSelectedItem().toString() == "2° Cuatrimestre") {
                                        auxPeriodo = 2;
                                    } else {
                                        auxPeriodo = 3;
                                    }
                                    asignatura.setValues(
                                            edtNombreAsignatura.getText().toString(), Integer.parseInt(edtAnioAsignatura.getText().toString()),
                                            Integer.parseInt(nivelSpinner.getSelectedItem().toString()), auxPeriodo,
                                            edtProfesorAsignatura.getText().toString(), edtEmailAsignatura.getText().toString(), edtObservacionesAsignatura.getText().toString()
                                    );
                                    aDao = MyDatabase.getInstance(getActivity().getApplicationContext()).getAsignaturaDao();
                                    aDao.update(asignatura);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity().getApplicationContext(), "La asignatura se modificó exitosamente", Toast.LENGTH_LONG).show();
                                        edtNombreAsignatura.setEnabled(false);
                                        edtAnioAsignatura.setEnabled(false);
                                        edtProfesorAsignatura.setEnabled(false);
                                        edtEmailAsignatura.setEnabled(false);
                                        periodoSpinner.setEnabled(false);
                                        nivelSpinner.setEnabled(false);
                                        edtObservacionesAsignatura.setEnabled(false);
                                        btnModificar.setEnabled(true);
                                        btnEvaluaciones.setEnabled(true);
                                        btnEstado.setEnabled(true);
                                        btnEliminar.setEnabled(true);
                                        btnModificar.setVisibility(View.VISIBLE);
                                        btnEvaluaciones.setVisibility(View.VISIBLE);
                                        btnEstado.setVisibility(View.VISIBLE);
                                        btnEliminar.setVisibility(View.VISIBLE);
                                        btnGuardarAsignatura.setEnabled(false);
                                    }
                                });
                            }
                        };
                        r.start();
                    }
                }

            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edtNombreAsignatura.setEnabled(true);
                edtAnioAsignatura.setEnabled(true);
                edtProfesorAsignatura.setEnabled(true);
                edtEmailAsignatura.setEnabled(true);
                periodoSpinner.setEnabled(true);
                nivelSpinner.setEnabled(true);
                edtObservacionesAsignatura.setEnabled(true);
                btnGuardarAsignatura.setEnabled(true);
                btnModificar.setEnabled(false);
                btnEvaluaciones.setEnabled(false);
                btnEstado.setEnabled(false);
                btnEliminar.setEnabled(false);
                editar=true;
            }
        });


        btnEvaluaciones.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new VerEvaluacionesFragment();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment,"verEvaluacion").addToBackStack(null).commit();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().executePendingTransactions();
                comunicador.pasarAsignaturasListaEvaluacion(asignatura);
            }
        });

        btnEstado.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Date dateActual = Calendar.getInstance().getTime();
                ArrayList<Date> rangosInfYSup = obtenerRangos(asignatura.getAnio(), asignatura.getPeriodo());

                //Si la fecha actual esta entre los rangos inferiores y superiores del período -> ASIGNATURA EN CURSO.
                if (dateActual.after(rangosInfYSup.get(0)) && dateActual.before(rangosInfYSup.get(1))) {
                    estadoAsignatura = "En curso";
                    Toast.makeText(getActivity().getApplicationContext(),"Estado de la asignatura " + asignatura.getNombre() + ": " + estadoAsignatura, Toast.LENGTH_LONG).show();
                }

                //Si la asignatura no está en curso, necesitamos obtener la lista de sus evaluaciones para determinar el estado de dicha aaignatura.
                else {

                    Thread r = new Thread() {
                        @Override
                        public void run() {
                            try {
                                //Conseguimos la lista de evaluaciones asociadas a la asignatura actual.
                                List<Evaluacion> aux;
                                edao = MyDatabase.getInstance(getActivity().getApplicationContext()).getEvaluacionesDao();
                                aux = edao.getAll();

                                for(Evaluacion e : aux){
                                    if(e.getAsignatura().getId() == asignatura.getId()){
                                        evaluaciones.add(e);
                                    }
                                }

                                //flag vale 1 si se han ingresado todos los resultados de las evaluaciones, 2 si falta ingresar el resultado de una
                                //o mas evaluaciones, y vale 3 si aun no hay evaluaciones asociadas a la asignatura.
                                flag = 1;
                                int k = 0;
                                while (k<evaluaciones.size() && flag==1) {
                                    if (evaluaciones.get(k).getNotaObtenida() == -1)
                                        flag = 2;
                                    k++;
                                }
                                if (evaluaciones.size()==0)
                                    flag = 3;

                                //Si no esta en curso y todas las evaluaciones estan promocionadas -> ASIGNATURA PROMOCIONADA.
                                int i = 0;
                                int contEvalPromocionadas = 0;
                                while (i<evaluaciones.size() && evaluaciones.get(i).getNotaObtenida() >= evaluaciones.get(i).getNotaPromocion()) {
                                    contEvalPromocionadas++;
                                    i++;
                                }
                                if (contEvalPromocionadas == evaluaciones.size())
                                    estadoAsignatura = "Promocionada";


                                //Si no esta en curso y todas las evaluaciones estan regularizadas -> ASIGNATURA REGULARIZADA.
                                if (!compararPorVerdadero(estadoAsignatura,"Promocionada")) {
                                    int j = 0;
                                    int contEvalRegularizadas = 0;
                                    System.out.println("EVAL SIZE: "+evaluaciones.size());
                                    while (j<evaluaciones.size() && evaluaciones.get(j).getNotaObtenida() >= evaluaciones.get(j).getNotaRegularidad()) {
                                        contEvalRegularizadas++;
                                        j++;
                                    }
                                    if (contEvalRegularizadas == evaluaciones.size())
                                        estadoAsignatura = "Regularizada";
                                }

                                //Si no esta en curso y no se promocionó ni regularizó -> ASIGNATURA LIBRE.
                                if (!compararPorVerdadero(estadoAsignatura,"Promocionada") && !compararPorVerdadero(estadoAsignatura,"Regularizada")) {
                                    estadoAsignatura="Libre";
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (flag == 1)
                                        Toast.makeText(getActivity().getApplicationContext(),"Estado de la asignatura " + asignatura.getNombre() + ": " + estadoAsignatura, Toast.LENGTH_LONG).show();
                                    else if (flag == 2)
                                        Toast.makeText(getActivity().getApplicationContext(),"No se han ingresado los resultados de todas las evaluaciones", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(getActivity().getApplicationContext(),"No se han ingresado evaluaciones aún.", Toast.LENGTH_LONG).show();

                                }
                            });
                        }};
                    r.start();

                }

            }
        });



        btnEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread r = new Thread() {
                    @Override
                    public void run() {
                        try {

                            List<Evaluacion> aux;
                            List<Evaluacion> listaEvaluaciones = new ArrayList<Evaluacion>();
                            edao = MyDatabase.getInstance(getActivity().getApplicationContext()).getEvaluacionesDao();
                            aux = edao.getAll();
                            for(Evaluacion e : aux){
                                if(e.getAsignatura().getId()==asignatura.getId()){
                                    listaEvaluaciones.add(e);
                                }
                            }
                            for(Evaluacion e : listaEvaluaciones){//Borro todas las evaluaciones de la asignatura
                                edao.delete(e);
                            }
                            aDao = MyDatabase.getInstance(getActivity().getApplicationContext()).getAsignaturaDao();
                            aDao.delete(asignatura);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "La asignatura se eliminó exitosamente", Toast.LENGTH_LONG).show();
                                edtNombreAsignatura.setText("");
                                edtAnioAsignatura.setText("");
                                edtEmailAsignatura.setText("");
                                edtProfesorAsignatura.setText("");
                                edtObservacionesAsignatura.setText("");
                            }
                        });
                    }
                };
                r.start();
            }
        });

        return view;
    }

    private ArrayList<Date> obtenerRangos (int anio, int periodo) {

        ArrayList<Date> ret = new ArrayList<Date>();
        Calendar calActual = Calendar.getInstance();
        Date dateActual = calActual.getTime();
        Calendar calInfPeriodo = Calendar.getInstance();
        Calendar calSupPeriodo = Calendar.getInstance();
        Date rangoInfPeriodo, rangoSupPeriodo;

        //Asumimos que las materias del 1° cuatrimestre inician sus actividades el 01/03, y finalizan el 30/06.
        if (periodo == 1) {
            calInfPeriodo.set(Calendar.YEAR, anio);
            calInfPeriodo.set(Calendar.MONTH, 2);
            calInfPeriodo.set(Calendar.DAY_OF_MONTH, 1);
            rangoInfPeriodo = calInfPeriodo.getTime();
            calSupPeriodo.set(Calendar.YEAR, anio);
            calSupPeriodo.set(Calendar.MONTH, 5);
            calSupPeriodo.set(Calendar.DAY_OF_MONTH, 30);
            rangoSupPeriodo = calSupPeriodo.getTime();

         //Asumimos que las materias del 2° cuatrimestre inician sus actividades el 01/08, y finalizan el 30/11.
        } else if (periodo == 2) {
            calInfPeriodo.set(Calendar.YEAR, anio);
            calInfPeriodo.set(Calendar.MONTH, 7);
            calInfPeriodo.set(Calendar.DAY_OF_MONTH, 1);
            rangoInfPeriodo = calInfPeriodo.getTime();
            calSupPeriodo.set(Calendar.YEAR, anio);
            calSupPeriodo.set(Calendar.MONTH, 10);
            calSupPeriodo.set(Calendar.DAY_OF_MONTH, 30);
            rangoSupPeriodo = calSupPeriodo.getTime();

         //Asumimos que las materias anuales inician sus actividades el 01/02 (con fines de poder agregar materias en curso en el momento en el
         // que de desarrolla la app, sin embargo esto podría implementarse como parte de la Configuración de la app), y finalizan el 30/11.
        } else {
            calInfPeriodo.set(Calendar.YEAR, anio);
            calInfPeriodo.set(Calendar.MONTH, 1);
            calInfPeriodo.set(Calendar.DAY_OF_MONTH, 1);
            rangoInfPeriodo = calInfPeriodo.getTime();
            calSupPeriodo.set(Calendar.YEAR, anio);
            calSupPeriodo.set(Calendar.MONTH, 10);
            calSupPeriodo.set(Calendar.DAY_OF_MONTH, 30);
            rangoSupPeriodo = calSupPeriodo.getTime();
        }

        ret.add(rangoInfPeriodo);
        ret.add(rangoSupPeriodo);
        return ret;
    }

    private boolean compararPorVerdadero(String str1, String str2) {
        if (str1 == null)
            return false;

        return str1.equals(str2);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void cargarSpinnerNivel(View view){
        ArrayList<String> niveles = new ArrayList<>();
        niveles.add("Niveles");
        niveles.add("1");
        niveles.add("2");
        niveles.add("3");
        niveles.add("4");
        niveles.add("5");
        nivelSpinner = (Spinner) view.findViewById(R.id.spnNivel);
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
        //nivelAdapter.setDropDownViewResource(R.layout.spinner_item);
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

    public void cargarSpinnerPeriodo(View view){
        ArrayList<String> periodos = new ArrayList<>();
        periodos.add("Periodo");
        periodos.add("1° Cuatrimestre");
        periodos.add("2° Cuatrimestre");
        periodos.add("Anual");
        periodoSpinner = (Spinner) view.findViewById(R.id.spnPeriodo);
        ArrayAdapter<String> periodoAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_style, periodos){
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
        //nivelAdapter.setDropDownViewResource(R.layout.spinner_item);
        periodoSpinner.setAdapter(periodoAdapter);

        periodoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                   /* Toast.makeText
                            (getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void editarAsignatura(Asignatura asignatura){
        this.asignatura=asignatura;
        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle(asignatura.getNombre());
        edtNombreAsignatura.setText(asignatura.getNombre());
        edtAnioAsignatura.setText(String.valueOf(asignatura.getAnio()));
        edtProfesorAsignatura.setText(asignatura.getProfesor());
        edtEmailAsignatura.setText(asignatura.getEmail());
        edtObservacionesAsignatura.setText(asignatura.getObservaciones());
        nivelSpinner.setSelection(getIndex(nivelSpinner, String.valueOf(asignatura.getNivel())));
        String aux="";
        switch (asignatura.getPeriodo()){
            case 1:
                aux="1° Cuatrimestre";
                break;
            case 2:
                aux="2° Cuatrimestre";
                break;
            case 3:
                aux="Anual";
                break;
        }
        periodoSpinner.setSelection(getIndex(periodoSpinner, aux));
        edtNombreAsignatura.setEnabled(false);
        edtAnioAsignatura.setEnabled(false);
        edtProfesorAsignatura.setEnabled(false);
        edtEmailAsignatura.setEnabled(false);
        periodoSpinner.setEnabled(false);
        nivelSpinner.setEnabled(false);
        edtObservacionesAsignatura.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEvaluaciones.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificar.setVisibility(View.VISIBLE);
        btnEvaluaciones.setVisibility(View.VISIBLE);
        btnEstado.setVisibility(View.VISIBLE);
        btnEliminar.setVisibility(View.VISIBLE);
        btnGuardarAsignatura.setEnabled(false);

    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
}
