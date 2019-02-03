package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.TareaDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TareasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TareasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TareasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView lista;
    private TareaDao tdao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TareasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TareasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TareasFragment newInstance(String param1, String param2) {
        TareasFragment fragment = new TareasFragment();
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


    }


    //Lo uso para mostrar el listView con todas las tareas
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tareas, container, false);
        lista = view.findViewById(R.id.listViewTareas);

        final ArrayList<Tarea> datos = new ArrayList<Tarea>();
        final AdaptadorTareas adapter = new AdaptadorTareas(getActivity().getApplicationContext(), datos);
        Thread r = new Thread() {
            @Override
            public void run() {
                try {
                    tdao = MyDatabase.getInstance(getActivity().getApplicationContext()).getTareaDao();
                    List<Tarea> listaTareas = tdao.getAll();;
                    datos.addAll(listaTareas);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }};
        r.start();

        lista.setAdapter(adapter);








        //Boton flotante de nueva tarea
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addTarea);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity().getApplicationContext(), "ME MUEVO A NUEVA TAREA",Toast.LENGTH_SHORT).show();
                Fragment fragment = new NuevaTareaFragment();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).addToBackStack(null).commit();
                ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Crear nueva tarea");
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
}
