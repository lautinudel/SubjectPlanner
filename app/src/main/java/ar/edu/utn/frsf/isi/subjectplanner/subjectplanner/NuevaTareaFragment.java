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
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.TareaDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;




public class NuevaTareaFragment extends Fragment {


    public String nombre;
    public int dia;
    public int mes;
    public int anio;
    public int hora;
    public int minutos;
    private int avisar;
    private Tarea tarea;
    private OnFragmentInteractionListener mListener;
    private TareaDao tdao;

    public NuevaTareaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Elementos de la pantalla
    private EditText edtnombre;
    private TextInputLayout nombreLayout;
    private Switch swAvisar;
    private EditText edtDia;
    private TextInputLayout diaLayout;
    private EditText edtHora;
    private TextInputLayout horaLayout;
    private boolean tocoDia;
    private boolean tocoHora;
    private Button buttonEliminar;
    private Intent myIntent;
    private PendingIntent myPendingIntent;
    private AlarmManager manager;
    private Calendar cal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Crear nueva tarea");
        View view = inflater.inflate(R.layout.fragment_nueva_tarea, container, false);
        swAvisar = (Switch) view.findViewById(R.id.switchAvisar);
        edtnombre = (EditText) view.findViewById(R.id.TextInputEditTextNombre);
        edtDia = (EditText) view.findViewById(R.id.TextInputEditTextDia);
        edtHora = (EditText) view.findViewById(R.id.TextInputEditTextHora);
        nombreLayout = view.findViewById(R.id.TextInputLayoutNombre);
        diaLayout = view.findViewById(R.id.TextInputLayoutDia);
        horaLayout = view.findViewById(R.id.TextInputLayoutHora);
        buttonEliminar = view.findViewById(R.id.buttonEliminar);
        tocoDia=false;
        tocoHora = false;
        buttonEliminar.setVisibility(View.INVISIBLE);


        manager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        myIntent = new Intent(getActivity().getApplicationContext(), AlarmNotificationReceiver.class);


        //Cambio el icono del menu lateral por una X  NO ANDA
        /*((NavigationActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((NavigationActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((NavigationActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);*/



        //Muestro el calendario para elegir el dia de la tarea
        edtDia.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                tocoDia = true;
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



       //Muestro un reloj para que el usuario seleccione un horario
        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tocoHora = true;
                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtHora.setText( selectedHour + ":" + selectedMinute);
                       // hora = new Time(selectedHour,selectedMinute,0 );
                        hora = selectedHour;
                        minutos = selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione una hora");
                mTimePicker.show();

            }
        });





        //Acciones cuando se presiona el boton guardar
        final Button buttonGuardar = (Button) view.findViewById(R.id.buttonGuardar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(edtnombre.getText().toString().isEmpty() || edtDia.getText().toString().isEmpty() || edtHora.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT).show();
                }else {

                    //Obtengo el valor del switch
                    if(swAvisar.isChecked()) avisar=1;
                    else avisar=0;

                    if(tarea!=null){//Tarea editada
                        if(!tocoDia){
                         dia = tarea.getDia();
                         mes = tarea.getMes();
                         anio = tarea.getAnio();
                        }
                        if(!tocoHora){
                            hora = tarea.getHora();
                            minutos = tarea.getMinutos();
                        }

                        //Obtengo la fecha que selecciono el usuario
                        cal = Calendar.getInstance();
                        cal.setTimeInMillis(System.currentTimeMillis());
                        cal.clear();
                        cal.set(anio,mes-1,dia,hora,minutos);
                        //Veo si la fecha/hora seleccionada es anterior a la actual
                        if(cal.before(Calendar.getInstance())){
                            Toast.makeText(getActivity().getApplicationContext(),"El horario es incorrecto",Toast.LENGTH_SHORT).show();
                        }else{
                            buttonGuardar.setEnabled(false);
                            buttonEliminar.setEnabled(false);
                            //Guardo la tarea editada
                            Thread r = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        int avisarAntes = tarea.getAvisar();
                                        tarea.setearDatos(edtnombre.getText().toString(), dia, mes, anio, hora, minutos, avisar);
                                        tdao = MyDatabase.getInstance(getActivity().getApplicationContext()).getTareaDao();
                                        tdao.update(tarea);
                                        myIntent.putExtra("nombre", tarea.getNombre());
                                        myPendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), tarea.getTiempoCreacion(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        if(avisarAntes==0 && avisar==1){ //Crear alarma
                                            manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),myPendingIntent);
                                        }else if(avisarAntes==1 && avisar==0){ //Borrar alarma
                                            manager.cancel(myPendingIntent);
                                        }else if(avisarAntes ==1 && avisar==1){ //Puede cambiar el dia o la hora => tengo que editar la alarma
                                            manager.cancel(myPendingIntent);
                                            manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),myPendingIntent);
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity().getApplicationContext(), "La tarea se edito correctamente", Toast.LENGTH_LONG).show();
                                            edtnombre.setText("");
                                            edtDia.setText("");
                                            edtHora.setText("");
                                        }
                                    });
                                }
                            };
                            r.start();
                        }
                    }else{  //Tarea nueva
                        //Obtengo la fecha que selecciono el usuario
                        cal = Calendar.getInstance();
                        cal.setTimeInMillis(System.currentTimeMillis());
                        cal.clear();
                        cal.set(anio,mes-1,dia,hora,minutos);
                        //Veo si la fecha/hora seleccionada es anterior a la actual
                        if(cal.before(Calendar.getInstance())){
                            Toast.makeText(getActivity().getApplicationContext(),"El horario es incorrecto",Toast.LENGTH_SHORT).show();
                        }else{
                            buttonGuardar.setEnabled(false);
                            buttonEliminar.setEnabled(false);
                            //Guardo la tarea en la bd
                            Thread r = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        Tarea nuevaTarea = new Tarea(edtnombre.getText().toString(), dia, mes, anio, hora, minutos, avisar,(int) (long)System.currentTimeMillis());
                                        tdao = MyDatabase.getInstance(getActivity().getApplicationContext()).getTareaDao();
                                        tdao.insert(nuevaTarea);
                                        if(avisar==1){ //Creo la alarma
                                            myIntent.putExtra("nombre", nuevaTarea.getNombre());
                                            myPendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),nuevaTarea.getTiempoCreacion(), myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                            //Seteo la alarma el dia y hora seleccionado
                                            manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),myPendingIntent);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity().getApplicationContext(), "La tarea se agrego correctamente", Toast.LENGTH_LONG).show();
                                            edtnombre.setText("");
                                            edtDia.setText("");
                                            edtHora.setText("");
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


        //Acciones si se presiona el boton eliminar
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonGuardar.setEnabled(false);
                if(tarea!=null){
                    Thread r = new Thread() {
                        @Override
                        public void run() {
                            try {
                                tdao = MyDatabase.getInstance(getActivity().getApplicationContext()).getTareaDao();
                                tdao.delete(tarea);
                                tarea=null;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), "La tarea se elimino correctamente", Toast.LENGTH_LONG).show();
                                    edtnombre.setText("");
                                    edtDia.setText("");
                                    edtHora.setText("");
                                    buttonEliminar.setEnabled(false);
                                }
                            });
                        }
                    };
                    r.start();
                }else  Toast.makeText(getActivity().getApplicationContext(), "Error, la tarea no existe", Toast.LENGTH_LONG).show();



            }});


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

    public void editarTarea(Tarea t) {
        this.tarea = t;
        ((NavigationActivity)getActivity()).getSupportActionBar().setTitle("Editar tarea");
        //Muestro los datos de la tarea por pantalla
        nombreLayout.setHintAnimationEnabled(false);
        edtnombre.setText(tarea.getNombre());
        diaLayout.setHintAnimationEnabled(false);
        edtDia.setText(tarea.getDia()+"/"+tarea.getMes()+"/"+tarea.getAnio());
        horaLayout.setHintAnimationEnabled(false);
        edtHora.setText(tarea.getHora()+":"+tarea.getMinutos());
        if(tarea.getAvisar()==1){
            swAvisar.setChecked(true);
        }
        buttonEliminar.setVisibility(View.VISIBLE);


    }
}
