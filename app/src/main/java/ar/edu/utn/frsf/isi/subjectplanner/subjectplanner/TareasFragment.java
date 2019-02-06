package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.TareaDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;


public class TareasFragment extends Fragment {

    private ListView lista;
    private TareaDao tdao;
    private Comunicador comunicador;


    private OnFragmentInteractionListener mListener;

    public TareasFragment() {
    }

    public static TareasFragment newInstance(String param1, String param2) {
        TareasFragment fragment = new TareasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comunicador = (Comunicador) getActivity();

    }


    //Lo uso para mostrar el listView con todas las tareas
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tareas, container, false);

        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Tareas");
        lista = view.findViewById(R.id.listViewTareas);
        lista.setEmptyView(view.findViewById(R.id.textViewVacio));
        final ArrayList<Tarea> datos = new ArrayList<Tarea>();
        final AdaptadorTareas adapter = new AdaptadorTareas(getActivity().getApplicationContext(), datos);
        Thread r = new Thread() {
            @Override
            public void run() {
                try {
                    tdao = MyDatabase.getInstance(getActivity().getApplicationContext()).getTareaDao();
                    List<Tarea> listaTareas = tdao.getAll();
                    datos.addAll(listaTareas);
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

        lista.setAdapter(adapter);

        //Acciones si toco una fila de la lista (una tarea en particular)
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new NuevaTareaFragment();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment, "editarTarea").addToBackStack(null).commit();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().executePendingTransactions();
                comunicador.responder(datos.get(position));
            }
        });







        //Boton flotante de nueva tarea
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addTarea);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity().getApplicationContext(), "ME MUEVO A NUEVA TAREA",Toast.LENGTH_SHORT).show();
                Fragment fragment = new NuevaTareaFragment();
                ((NavigationActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).addToBackStack(null).commit();

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
