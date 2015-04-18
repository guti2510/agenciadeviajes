package com.luispc.travelangency;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class destino extends ActionBarActivity {

    Bundle b;
    String Nombre;
    String Pais;
    String Precio;
    String Descripcion;
    String CupoDisponible;
    String FechaInicio;
    String FechaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destino);

        b=getIntent().getExtras();

        Nombre = b.getString("Nombre");
        Pais = b.getString("Pais");
        Precio = b.getString("Precio");
        Descripcion = b.getString("Descripcion");
        CupoDisponible = b.getString("CupoDisponible");
        FechaInicio = b.getString("FechaInicio");
        FechaFinal = b.getString("FechaFinal");

        TextView txtNombre = (TextView)findViewById(R.id.textView);
        txtNombre.setText(Nombre);

        TextView txtPais = (TextView)findViewById(R.id.textView2);
        txtPais.setText( "Pais: "+" "+ Pais);

        TextView txtPrecio = (TextView)findViewById(R.id.textView3);
        txtPrecio.setText("Precio: "+" "+ Precio);

        TextView txtDescrip = (TextView)findViewById(R.id.textView4);
        txtDescrip.setText("Descripcion: "+" "+ Descripcion);

        TextView txtCupodis = (TextView)findViewById(R.id.textView5);
        txtCupodis.setText("Cupo Disponible: "+" "+ CupoDisponible);

        TextView txtFechaI = (TextView)findViewById(R.id.textView6);
        txtFechaI.setText("Fecha Inicio: "+" "+ FechaInicio);

        TextView txtFechaF = (TextView)findViewById(R.id.textView7);
        txtFechaF.setText("Fecha Final: "+" "+ FechaFinal);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_destino, menu);
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
                Intent loginAct = new Intent(destino.this, GmailAuth.class);
                startActivity(loginAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
