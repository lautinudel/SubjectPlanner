package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.FotografiaDao;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Dao.MyDatabase;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Fotografia;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsignaturaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsignaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FotografiasFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_SAVE = 2;
    private List<Fotografia> listaFotografias = new ArrayList<Fotografia>();
    private List<Drawable> listaDrawables = new ArrayList<Drawable>();
    private ArrayList<File> imageFiles = new ArrayList<>();
    private ArrayList<String> filesPaths;
    private Fotografia foto = new Fotografia();
    private FotografiaDao fdao;

    public FotografiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fotografias, container, false);
        final GridView gv = (GridView) v.findViewById(R.id.gvFotos);

        filesPaths = new ArrayList<String>();
        final AdaptadorImagenes adapter = new AdaptadorImagenes(getActivity().getApplicationContext(), filesPaths);
        Thread r = new Thread() {
            @Override
            public void run() {
                try {

                    //Obtenemos la lista de fotografías de la BDD.
                    FotografiasFragment.this.listaFotografias = MyDatabase.getInstance(getActivity()).getFotografiaDao().getAll();

 /*
                    for (Fotografia elem : listaFotografias) {
                        File f = new File(elem.pathFoto);
                        imageFiles.add(f);
                    }
 */

                    //filesPaths = new String[listaFotografias.size()];
                    for (int i=0; i<listaFotografias.size();i++) {
                        filesPaths.add(listaFotografias.get(i).getPathFoto());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        };
        r.start();
        gv.setAdapter(adapter);



        //Boton tomar foto.
        FloatingActionButton fabFoto = (FloatingActionButton) v.findViewById(R.id.addFoto);
        fabFoto.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 123);
                    } else {
                        sacarFoto();
                    }
                }
            }
        });

        //Seleccion de un single_grid.
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), ViewImage.class).putExtra("img",filesPaths.get(position).toString()));
            }
        });



        return v;
    }


    //Método sacar foto.
    private void sacarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this.getContext(), "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_SAVE);
            }
        }
    }

    //Creamos el file de la foto.
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName,
                /* prefix */ ".jpg",
                /* suffix */ dir
                /* directory */ );
        foto.setPathFoto(image.getAbsolutePath());
        return image;
    }


    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {

        if (reqCode==REQUEST_IMAGE_SAVE && resCode == RESULT_OK) {

            Thread r = new Thread() {
                @Override
                public void run() {
                    try {
                        fdao = MyDatabase.getInstance(getActivity().getApplicationContext()).getFotografiaDao();
                        fdao.insert(foto);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            };
            r.start();
            Toast.makeText(getActivity().getApplicationContext(),"La fotografía se agregó exitosamente",Toast.LENGTH_SHORT).show();
        }
    }


/*
    public void generarDrawables () {

        Thread r = new Thread() {
            @Override
            public void run() {
                try {
                    String path;
                    Drawable d;

                    //Obtenemos la lista de fotografías de la BDD
                    FotografiasFragment.this.listaFotografias = MyDatabase.getInstance(getActivity()).getFotografiaDao().getAll();


                    for (Fotografia elem : listaFotografias) {
                        path = elem.pathFoto;
                        d = Drawable.createFromPath(path);
                        FotografiasFragment.this.listaDrawables.add(d);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        r.start();
    }
*/



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