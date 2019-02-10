package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Asignatura;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Evaluacion;
import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;


//Esta clase representa el menu lateral y el toolbar



//Despues de implements hay que agregar los fragmentos nuevos
public class NavigationActivity extends AppCompatActivity
        implements Comunicador, NavigationView.OnNavigationItemSelectedListener, TareasFragment.OnFragmentInteractionListener, NuevaTareaFragment.OnFragmentInteractionListener
                    , AsignaturaFragment.OnFragmentInteractionListener, NuevaAsignaturaFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener, FotografiasFragment.OnFragmentInteractionListener,
                    EvaluacionFragment.OnFragmentInteractionListener, VerEvaluacionesFragment.OnFragmentInteractionListener, IngresarNotaEvFragment.OnFragmentInteractionListener, PromediosFragment.OnFragmentInteractionListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //La primera vez que ejecuto la aplicacion me voy a Asignaturas
        Fragment fragment = new AsignaturaFragment();
        getSupportActionBar().setTitle("Asignaturas");
        getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


    //Boton de configuracion
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Funcionalidad en desarollo", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //Este metodo sirve para cuando el usuario toca un boton del menu lateral

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        Fragment fragment = null;
        Boolean fragmentoSeleccionado = false;
        int id = item.getItemId();

        if (id == R.id.navAsignaturas) {  //Boton Asignaturas
            fragmentoSeleccionado=true;

            fragment = new AsignaturaFragment();

        } else if (id == R.id.navProfesores) { //Boton Profesores
            Toast.makeText(this,"Funcionalidad en desarollo", Toast.LENGTH_LONG).show();

        } else if (id == R.id.navTareas) { //Boton Tareas
            fragmentoSeleccionado=true;
            fragment = new TareasFragment();

        } else if (id == R.id.navPromedios) { //Boton Promedios
            fragmentoSeleccionado=true;
            getSupportActionBar().setTitle("Promedios");
            fragment = new PromediosFragment();

        } else if (id == R.id.navFotografias) { //Boton Fotografías
            fragmentoSeleccionado=true;
            getSupportActionBar().setTitle("Fotografías");
            fragment = new FotografiasFragment();

        } else if (id == R.id.navAbout) { //Boton Acerca de
            fragmentoSeleccionado=true;
            getSupportActionBar().setTitle("Acerca de");
            fragment = new AboutFragment();
        }



        //Si el usuario selecciono un boton, me muevo al fragmento correspondiente
        if(fragmentoSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void responder(Tarea tarea) {
        NuevaTareaFragment fragment = (NuevaTareaFragment) getSupportFragmentManager().findFragmentByTag("editarTarea");
        fragment.editarTarea(tarea);
    }

    @Override
    public void pasarAsignatura(Asignatura asignatura) {
        NuevaAsignaturaFragment fragment = (NuevaAsignaturaFragment) getSupportFragmentManager().findFragmentByTag("editarAsignatura");
        fragment.editarAsignatura(asignatura);
    }

    @Override
    public void pasarAsignaturaEvaluacion(Asignatura asignatura) {
        EvaluacionFragment fragment = (EvaluacionFragment) getSupportFragmentManager().findFragmentByTag("agregarEvaluacion");
        fragment.agregarEvaluacion(asignatura);
    }

    @Override
    public void pasarAsignaturasListaEvaluacion(Asignatura asignatura) {
        VerEvaluacionesFragment fragment = (VerEvaluacionesFragment) getSupportFragmentManager().findFragmentByTag("verEvaluacion");
        fragment.agregarAsignatura(asignatura);
    }

    @Override
    public void pasarEvaluacion(Evaluacion evaluacion) {
        IngresarNotaEvFragment fragment = (IngresarNotaEvFragment) getSupportFragmentManager().findFragmentByTag("agregarNota");
        fragment.agregarNota(evaluacion);
    }


    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/

}
