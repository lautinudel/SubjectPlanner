package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AsignaturaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura);

        //Cofiguramos el toolbar como action bar principal con el titulo correspondiente al activity.
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Asignaturas");
        //ListView
        ArrayList<String> datos = new ArrayList<>();
        datos.add("Asignatura ejemplo 1");
        datos.add("Asignatura ejemplo 2");
        datos.add("Asignatura ejemplo 3");
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_layout,R.id.tvFila,datos);
        listView.setAdapter(adapter);

        //Añadimos el boton de menu para habilitar al pulsar el drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        //Botón flotante para añadir asignaturas
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Inicio activityForResult de Nueva Asignatura", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),NuevaAsignaturaActivity.class);
                startActivityForResult(i,1);
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this, "A implementar", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_buscar:
                Toast.makeText(getApplicationContext(), "Buscar segun coincidencia", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
