package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ViewImage extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        //Intent i = getIntent();
        //File f = i.getExtras().getParcelable("img");
        String f = getIntent().getStringExtra("img");
        iv = (ImageView) findViewById(R.id.ivFotoCompleta);
        iv.setImageURI(Uri.parse(f));


    }
}
