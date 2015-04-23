package com.luispc.travelangency;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class destino extends ActionBarActivity {

    Bundle b;
    String idDestino;
    String Nombre;
    String Pais;
    String Precio;
    String Descripcion;
    String CupoDisponible;
    String FechaInicio;
    String FechaFinal;
    String Oferta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destino);

        b=getIntent().getExtras();
        idDestino = b.getString("idDestino");
        Nombre = b.getString("Nombre");
        Pais = b.getString("Pais");
        Precio = b.getString("Precio");
        Descripcion = b.getString("Descripcion");
        CupoDisponible = b.getString("CupoDisponible");
        FechaInicio = b.getString("FechaInicio");
        FechaFinal = b.getString("FechaFinal");
        Oferta = b.getString("Oferta");

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

        if (!Oferta.equals("1")){
            TextView txtoferta = (TextView)findViewById(R.id.textView24);
            txtoferta.setText("En Oferta");
        }



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

    public void pasarReserva2(View v) {
        String email = ((global) this.getApplication()).getEmail();

        if (email == ""){
            Toast toast1 = Toast.makeText(getApplicationContext(),"No te encuentras autenticado", Toast.LENGTH_SHORT);
            toast1.show();

        }
        else {

            String destino = "destino";
            Intent i;
            i = new Intent(getApplicationContext() , reservacion.class);
            i.putExtra("ID",idDestino);
            i.putExtra("Indicador",destino);
            i.putExtra("Nombre",Nombre);
            i.putExtra("Precio",Precio);
            i.putExtra("FechaInicio",FechaInicio);
            i.putExtra("FechaFinal",FechaFinal);
            i.putExtra("Oferta",Oferta);
            startActivity(i);

        }
    }
}
