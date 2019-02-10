package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.EvaluacionesDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngresarNotaEvFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngresarNotaEvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngresarNotaEvFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Evaluacion evaluacion;
    private Button btnGuardar;
    private EditText edtNotaObtenida;
    private EvaluacionesDao edao;


    private OnFragmentInteractionListener mListener;

    public IngresarNotaEvFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngresarNotaEvFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngresarNotaEvFragment newInstance(String param1, String param2) {
        IngresarNotaEvFragment fragment = new IngresarNotaEvFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ingresar_nota_ev, container, false);
        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Calificación obtenida");
        btnGuardar = view.findViewById(R.id.buttonGuardar);
        edtNotaObtenida = view.findViewById(R.id.editTextNotaObtenida);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(evaluacion!=null){
                    Thread r = new Thread() {
                        @Override
                        public void run() {
                            try {
                                evaluacion.setNotaObtenida(Integer.valueOf(edtNotaObtenida.getText().toString()));
                                edao = MyDatabase.getInstance(getActivity().getApplicationContext()).getEvaluacionesDao();
                                edao.update(evaluacion);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), "La calificación se agregó exitosamente", Toast.LENGTH_LONG).show();
                                    edtNotaObtenida.setText("");
                                }
                            });
                        }
                    };
                    r.start();
                }

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


    public void agregarNota(Evaluacion evaluacion){
        this.evaluacion=evaluacion;



    }
}
