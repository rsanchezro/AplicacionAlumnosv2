package com.roberto.aplicacionalumnosv2;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Listado_alumnos extends AppCompatActivity {


    //componente visual que representa la Lista de alumnos
    RecyclerView vista_listaalumnos;
    private Toolbar barra;
    //Lista de datos de los alumnos
     ArrayList<Alumno> datos_listaalumnos;
     ArrayList<Alumno> alumnosseleccionados=new ArrayList<>();
     //Adaptador que introduce los datos en la vista
    Adaptador_alumnos adaptadorAlumnos;
    String Titulo;

    //Variable para controlar si el ActionMode esta activad
    public boolean actionModeactivado=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumnos);
        barra=findViewById(R.id.toolbar);
        String curso=getIntent().getStringExtra("curso");
         Titulo="LISTADO ALUMNOS "+curso;
        barra.setTitle(Titulo);

        setSupportActionBar(barra);



        Log.i("Informacion",barra.getTitle().toString());
        preparar_ReciclerView(curso);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_delete:
                //
                break;
            case R.id.item_edit:
                //
                break;
            case android.R.id.home:
                //Salir del modo action mode
                salir_actionmode();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        //Salir del modo action mode si estoy en el
        if(actionModeactivado)
        {
            Log.i("Salir","Salir");
            salir_actionmode();
        }
        else
        {
            super.onBackPressed();
        }


    }

    private void salir_actionmode()
    {
        alumnosseleccionados.clear();
        actionModeactivado=false;
        //Limpio el menu y restablezco los estilos
        barra.setBackgroundResource(R.color.colorPrimary);
        barra.setTitleTextAppearance(this,R.style.barra);
        barra.setTitle(Titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        barra.getMenu().clear();


        //notificar de los cambios en el adapatador
        adaptadorAlumnos.notifyDataSetChanged();
    }

    private void preparar_ReciclerView(String curso) {

        vista_listaalumnos=(RecyclerView) findViewById(R.id.listaalumnos);

        TypedArray arrayalumnos=null;
        datos_listaalumnos=new ArrayList<Alumno>();
//AQUI CARGO LA LISTA DE ALUMNOS PARA ELLO AVERIGUO
// QUE LISTA DE ALUMNOS DEBO CARGAR RECOGIENDO INFORMACIÓN DEL INTENT
        switch (curso)
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
        //Añadir divisor

        adaptadorAlumnos= new Adaptador_alumnos(this,R.layout.alumno_layout,this.datos_listaalumnos);

        adaptadorAlumnos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //En el click largo no voy a permitir seleccionar elementos salvo la primera vez
                int posicion = vista_listaalumnos.getChildAdapterPosition(v);
                if(!actionModeactivado)
                {//Solo inflo el menu si no esta el actionmodeactivado
                    actionModeactivado = true;
                    barra.inflateMenu(R.menu.menu_action_mode);
                    //Cambio el estilo de la barra
                    barra.setBackgroundColor(Color.BLACK);
                    barra.setTitleTextAppearance(Listado_alumnos.this,R.style.estiloActionpersonalizado);

                    //Seleccionar el elemento sobre el que se ha pulsado

                    seleccionar_Elemento(posicion);
                    //Actualizco el numero de elementos seleccionados
                    //Repinto el elemento
                    adaptadorAlumnos.notifyItemChanged(posicion);
                    //Incluir icono de flecha hacia atras
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                    actualizar_barra();
                }
                return true;
            }
        });
        adaptadorAlumnos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Para conocer en que elemento se ha hecho click
                //El objeto View representa un elemento de la lista
                int posicion=vista_listaalumnos.getChildAdapterPosition(v);
                if(actionModeactivado)
                {
                   seleccionar_Elemento(posicion);
                    adaptadorAlumnos.notifyItemChanged(posicion);
                    actualizar_barra();
                }
            }
        });
        vista_listaalumnos.setAdapter(adaptadorAlumnos);


    }

private void actualizar_barra()
{
    barra.getMenu().getItem(0).setVisible(alumnosseleccionados.size()>1?false:true);
    barra.setTitle(alumnosseleccionados.size()+" Alumnos seleccionados...");
}
    private void seleccionar_Elemento(int posicion)
    {
        if(alumnosseleccionados.contains(datos_listaalumnos.get(posicion)))
        {
            alumnosseleccionados.remove(datos_listaalumnos.get(posicion));
        }
        else
        {
            alumnosseleccionados.add(datos_listaalumnos.get(posicion));
        }
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
