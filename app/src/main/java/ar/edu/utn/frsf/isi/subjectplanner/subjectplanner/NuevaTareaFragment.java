package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaTareaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaTareaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaTareaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private String nombre=null;
    private Date dia=null;
    private Time hora=null;

    private OnFragmentInteractionListener mListener;

    public NuevaTareaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaTareaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaTareaFragment newInstance(String param1, String param2) {
        NuevaTareaFragment fragment = new NuevaTareaFragment();
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
        View view = inflater.inflate(R.layout.fragment_nueva_tarea, container, false);
        //Cambio el icono del menu lateral por una X  NO ANDA
        /*((NavigationActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((NavigationActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);*/

        //Obtengo el nombre de la tarea
        final EditText edtnombre = (EditText) view.findViewById(R.id.TextInputEditTextNombre);
        nombre = edtnombre.getText().toString();


        //Muestro el calendario para elegir el dia de la tarea

        final EditText edtDia = (EditText) view.findViewById(R.id.TextInputEditTextDia);
        edtDia.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate=Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //System.out.println("Año: "+year+" mes: "+month+" dia: "+day);
                        month = month+1;
                        edtDia.setText(day+"/"+month+"/"+year);
                        mcurrentDate.set(Calendar.YEAR, year);
                        mcurrentDate.set(Calendar.MONTH, month-1);
                        mcurrentDate.set(Calendar.DATE, day);
                        dia = mcurrentDate.getTime();
                    }
                },mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
                mDatePicker.show();
            }
        });

       //Muestro un reloj para que el usuario seleccione un horario

        final EditText edtHora = (EditText) view.findViewById(R.id.TextInputEditTextHora);
        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtHora.setText( selectedHour + ":" + selectedMinute);
                        hora = new Time(selectedHour,selectedMinute,0 );
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        //Acciones cuando se presiona el boton guardar

        Button button = (Button) view.findViewById(R.id.buttonGuardar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if(edtnombre.getText().toString().isEmpty() || edtDia.getText().toString().isEmpty() || edtHora.getText().toString().isEmpty()){
               Toast.makeText(getActivity().getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
            }else{
                //Hacer algo
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
}
