package com.roberto.aplicacionalumnosv2;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Listado_alumnos extends AppCompatActivity {


    //componente visual que representa la Lista de alumnos
    RecyclerView vista_listaalumnos;
    //Lista de datos de los alumnos
     ArrayList<Alumno> datos_listaalumnos;
     ArrayList<Alumno> alumnosseleccionados=new ArrayList<>();
     //Adaptador que introduce los datos en la vista
    Adaptador_alumnos adaptadorAlumnos;
    //Variable para controlar si el ActionMode esta activad
    public boolean actionModeactivado=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumnos);
        preparar_ReciclerView();


    }




    private void preparar_ReciclerView() {

        vista_listaalumnos=(RecyclerView) findViewById(R.id.listaalumnos);

        TypedArray arrayalumnos=null;
        datos_listaalumnos=new ArrayList<Alumno>();
//AQUI CARGO LA LISTA DE ALUMNOS PARA ELLO AVERIGUO
// QUE LISTA DE ALUMNOS DEBO CARGAR RECOGIENDO INFORMACIÓN DEL INTENT
        switch (getIntent().getStringExtra("curso"))
        {
            case "GBD":
                arrayalumnos=getResources().obtainTypedArray(R.array.array_datos_alumnos_GBD);
                break;
            case "IAW":
                //Asocio al recurso de alumnos de IAW
                break;
            case "PMDM":
                //Asocio el recurso de alumnos de PMDM
                break;

        }
        if(arrayalumnos!=null)
        {
            rellenarLista_alumnos(arrayalumnos);
        }
        vista_listaalumnos.setLayoutManager(new LinearLayoutManager(this));
        adaptadorAlumnos= new Adaptador_alumnos(this,R.layout.alumno_layout,this.datos_listaalumnos);
        adaptadorAlumnos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Para conocer en que elemento se ha hecho click
                //El objeto View representa un elemento de la lista
                int posicion=vista_listaalumnos.getChildAdapterPosition(v);
                if(actionModeactivado)
                {
                    //Compruebo si el elemento esta seleccionado
                    if(alumnosseleccionados.contains(datos_listaalumnos.get(posicion)))
                    {
                        alumnosseleccionados.remove(datos_listaalumnos.get(posicion));
                        //Cambio el color de fondo de la vista
                        v.setBackgroundResource(R.color.colorFondoElemento);

                    }
                    else
                    {
                        alumnosseleccionados.add(datos_listaalumnos.get(posicion));
                        v.setBackgroundResource(R.color.colorFondoElementoSeleccionado);
                    }
                    adaptadorAlumnos.notifyItemChanged(posicion);
                }
            }
        });
        vista_listaalumnos.setAdapter(adaptadorAlumnos);


    }


    private void rellenarLista_alumnos(TypedArray a)
    {
        int id;
        for(int i=0;i<a.length();i++)
        {
            id=a.getResourceId(i,0);
            if(id!=0)
            {
                TypedArray al=getResources().obtainTypedArray(id);

                this.datos_listaalumnos.add(new Alumno(al.getInt(0,0),al.getString((int)1),al.getResourceId((int)2,R.drawable.ic_anonymous)));

            }
        }
    }
}
