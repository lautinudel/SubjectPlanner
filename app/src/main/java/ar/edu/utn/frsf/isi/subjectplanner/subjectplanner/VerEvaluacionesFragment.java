package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.EvaluacionesDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerEvaluacionesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerEvaluacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerEvaluacionesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lista;
    private OnFragmentInteractionListener mListener;
    private EvaluacionesDao edao;
    private Asignatura asignatura;
    private Comunicador comunicador;
    private ArrayList<Evaluacion> datos;
    private AdaptadorEvaluaciones adapter;

    public VerEvaluacionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerEvaluacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerEvaluacionesFragment newInstance(String param1, String param2) {
        VerEvaluacionesFragment fragment = new VerEvaluacionesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ver_evaluaciones, container, false);
        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Evaluaciones");
        lista = view.findViewById(R.id.listViewEvaluaciones);
        datos = new ArrayList<Evaluacion>();
        adapter = new AdaptadorEvaluaciones(getActivity().getApplicationContext(), datos);


        lista.setAdapter(adapter);

        //Acciones si toco una fila de la lista (una evaluacion en particular)
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new IngresarNotaEvFragment();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment,"agregarNota").addToBackStack(null).commit();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().executePendingTransactions();
                comunicador.pasarEvaluacion(datos.get(position));

            }
        });





        //Boton flotante de nueva evaluacion
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addEvaluacion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity().getApplicationContext(), "ME MUEVO A NUEVA TAREA",Toast.LENGTH_SHORT).show();
                Fragment fragment = new EvaluacionFragment();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment,"agregarEvaluacion").addToBackStack(null).commit();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().executePendingTransactions();
                comunicador.pasarAsignaturaEvaluacion(asignatura);
            }
        });



        return view;
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

    public void agregarAsignatura(Asignatura a){
        this.asignatura = a;


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
                    datos.addAll(listaEvaluaciones);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

                    }
                });
            }};
        r.start();
    }
}
