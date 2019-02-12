package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.EvaluacionesDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EvaluacionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EvaluacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluacionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EvaluacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluacionFragment.
     */


    private EditText edtNombreEvaluacion;
    private EditText edtFechaEvaluacion;
    private EditText edtNotaPromocion;
    private EditText edtNotaRegularidad;
    private EditText edtHoraEvaluacion;
    private Switch swtAvisarEvaluacion;
    private Button btnGuardarEvaluacion;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int minutos;
    private int avisar;
    private EvaluacionesDao edao;
    private Intent myIntent;
    private AlarmManager manager;
    private PendingIntent myPendingIntent;
    private Calendar cal;
    private Asignatura asignatura;

    // TODO: Rename and change types and number of parameters
    public static EvaluacionFragment newInstance(String param1, String param2) {
        EvaluacionFragment fragment = new EvaluacionFragment();
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
        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Agregar Evaluación");
        View view = inflater.inflate(R.layout.fragment_evaluacion, container, false);

        edtNombreEvaluacion = (EditText) view.findViewById(R.id.edtNombreEvaluacion);
        edtFechaEvaluacion = (EditText) view.findViewById(R.id.edtFechaEvaluacion);
        edtHoraEvaluacion = (EditText) view.findViewById(R.id.edtHoraEvaluacion);
        edtNotaPromocion = (EditText) view.findViewById(R.id.edtNotaPromocion);
        edtNotaRegularidad = (EditText) view.findViewById(R.id.edtNotaRegularidad);
        swtAvisarEvaluacion = (Switch) view.findViewById(R.id.swtAvisarEvaluacion);
        btnGuardarEvaluacion = (Button) view.findViewById(R.id.btnGuardarEvaluacion);
        manager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        myIntent = new Intent(getActivity().getApplicationContext(), AlarmNotificationReceiver.class);



        edtFechaEvaluacion.setOnClickListener(new View.OnClickListener() {
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
                        edtFechaEvaluacion.setText(day+"/"+month+"/"+year);
                        /*mcurrentDate.set(Calendar.YEAR, year);
                        mcurrentDate.set(Calendar.MONTH, month-1);
                        mcurrentDate.set(Calendar.DATE, day);
                        dia = mcurrentDate.getTime();/*/
                        dia = day;
                        mes = month;
                        anio = year;
                    }
                },mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
                mDatePicker.show();
            }
        });

        edtHoraEvaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        if (selectedMinute<10)
                            edtHoraEvaluacion.setText( selectedHour + ":0" + selectedMinute);
                        else
                            edtHoraEvaluacion.setText( selectedHour + ":" + selectedMinute);
                        // hora = new Time(selectedHour,selectedMinute,0 );
                        hora = selectedHour;
                        minutos = selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione una hora");
                mTimePicker.show();

            }
        });

        btnGuardarEvaluacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(edtNombreEvaluacion.getText().toString().isEmpty() || edtFechaEvaluacion.getText().toString().isEmpty() || edtHoraEvaluacion.getText().toString().isEmpty()
                        || edtNotaPromocion.getText().toString().isEmpty() || edtNotaRegularidad.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Asegúrese de completar todos los campos",Toast.LENGTH_SHORT).show();
                }else {
                    //Obtengo el valor del switch
                    if(swtAvisarEvaluacion.isChecked()) avisar=1;
                    else avisar=0;
                        //Obtengo la fecha que selecciono el usuario
                        cal = Calendar.getInstance();
                        cal.setTimeInMillis(System.currentTimeMillis());
                        cal.clear();
                        cal.set(anio,mes-1,dia,hora,minutos);
                        //Veo si la fecha/hora seleccionada es anterior a la actual
                        if(cal.before(Calendar.getInstance())){
                            Toast.makeText(getActivity().getApplicationContext(),"El horario es incorrecto",Toast.LENGTH_SHORT).show();
                        }else{
                            if(asignatura!=null){
                                Thread r = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            Evaluacion evaluacion = new Evaluacion(edtNombreEvaluacion.getText().toString(), dia, mes, anio, hora, minutos, asignatura,
                                                    Integer.parseInt(edtNotaRegularidad.getText().toString()), Integer.parseInt(edtNotaPromocion.getText().toString()),avisar,(int) (long)System.currentTimeMillis());
                                            edao = MyDatabase.getInstance(getActivity().getApplicationContext()).getEvaluacionesDao();
                                            edao.insert(evaluacion);
                                            if(avisar==1){ //Creo la alarma
                                                myIntent.putExtra("nombre", evaluacion.getNombre());
                                                myPendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),evaluacion.getIdAlarma(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                                //Seteo la alarma el dia y hora seleccionado
                                                manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),myPendingIntent);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity().getApplicationContext(), "La evaluación se agregó exitosamente", Toast.LENGTH_LONG).show();
                                                edtNombreEvaluacion.setText("");
                                                edtFechaEvaluacion.setText("");
                                                edtHoraEvaluacion.setText("");
                                                edtNotaPromocion.setText("");
                                                edtNotaRegularidad.setText("");
                                                swtAvisarEvaluacion.setChecked(false);
                                            }
                                        });
                                    }
                                };
                                r.start();
                            }


                    }

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

    public void agregarEvaluacion(Asignatura asignatura){
        this.asignatura=asignatura;

    }
}
