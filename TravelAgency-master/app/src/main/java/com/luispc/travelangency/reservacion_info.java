package com.luispc.travelangency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class reservacion_info extends ActionBarActivity {


    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> destinosList;

    Bundle b;
    String idreservacion;
    String cantpersonas;
    String Nombre;
    String Descripcion;
    String diarealizado;
    String diareservado;
    String diafinal;
    String preciofinal;
    String tipo;

    // url to get all products list
    private static String url_all_destinos = "http://agenciadeviajes.esy.es/guticonnect/get_all_destinos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_info);

        b=getIntent().getExtras();

        idreservacion = b.getString("idreservacion");
        Nombre = b.getString("Nombre");
        cantpersonas = b.getString("cantpersonas");
        Descripcion = b.getString("Descripcion");
        diarealizado = b.getString("diarealizado");
        diareservado = b.getString("diareservado");
        diafinal = b.getString("diafinal");
        preciofinal = b.getString("preciofinal");
        tipo = b.getString("tipo");


        if (tipo.equals("paquete")){
            TextView txttipo = (TextView)findViewById(R.id.textView9);
            txttipo.setText("Paquete: ");
        }
        else {
            TextView txttipo2 = (TextView)findViewById(R.id.textView9);
            txttipo2.setText("Destino: ");
        }

        TextView txtNombre = (TextView)findViewById(R.id.textView18);
        txtNombre.setText(Nombre);

        TextView txtFechaIni = (TextView)findViewById(R.id.textView19);
        txtFechaIni.setText("Fecha: "+diareservado +" al "+ diafinal);

        TextView txtPersonas = (TextView)findViewById(R.id.textView21);
        txtPersonas.setText("Personas: "+" "+ cantpersonas);

        TextView txtFechaI = (TextView)findViewById(R.id.textView22);
        txtFechaI.setText("Precio: "+" "+ preciofinal);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reservacion_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Handle presses on the action bar items
        switch (id) {
            case R.id.action_login:
                Intent loginAct = new Intent(reservacion_info.this, GmailAuth.class);
                startActivity(loginAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void cancelarReserva(View v){

        Intent i;
        i = new Intent(getApplicationContext() , reservacioncancelar.class);
        i.putExtra("idreservacion",idreservacion);
        i.putExtra("cantpersonas",cantpersonas);
        i.putExtra("Nombre",Nombre);
        i.putExtra("Precio",preciofinal);
        i.putExtra("tipo",tipo);
        startActivity(i);


    }

    public void cambiarReserva(View v){

        Intent i;
        i = new Intent(getApplicationContext() , reservacioncambiar.class);
        i.putExtra("idreservacion",idreservacion);
        i.putExtra("Nombre",Nombre);
        i.putExtra("cantpersonas",cantpersonas);
        i.putExtra("tipo",tipo);
        i.putExtra("Precio",preciofinal);
        i.putExtra("fechinicio",diareservado);
        i.putExtra("fechfinal",diafinal);
        startActivity(i);


    }
}
