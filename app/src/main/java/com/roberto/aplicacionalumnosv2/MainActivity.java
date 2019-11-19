package com.roberto.aplicacionalumnosv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listado_alumnos_GBD(View view) {
        Intent i=new Intent(this,Listado_alumnos.class);
        i.putExtra("curso","GBD");
        startActivity(i);

    }
}
