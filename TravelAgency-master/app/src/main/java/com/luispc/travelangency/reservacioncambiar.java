package com.luispc.travelangency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class reservacioncambiar extends ActionBarActivity {


    String[] opciones = {"1","2","3","4","5"};
    String posicion;
    Spinner personas2;

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();

    private static String url_cambiarreserv = "http://agenciadeviajes.esy.es/guticonnect/get_cambiarreserv.php";

    private static final String TAG_SUCCESS = "success";

    Bundle b;
    String idreservacion;
    String Nombre;
    String preciofinal;
    String cantpersonas;
    String tipo;
    String fechainicio;
    String fechafinal;

    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacioncambiar);


        b=getIntent().getExtras();

        idreservacion = b.getString("idreservacion");
        Nombre = b.getString("Nombre");
        cantpersonas = b.getString("cantpersonas");
        preciofinal = b.getString("Precio");
        tipo = b.getString("tipo");
        fechainicio = b.getString("fechinicio");
        fechafinal = b.getString("fechfinal");
        correo = ((global) this.getApplication()).getEmail();


        TextView txtNombre = (TextView)findViewById(R.id.textView12);
        txtNombre.setText(Nombre);

        TextView txtPrecio = (TextView)findViewById(R.id.textView17);
        txtPrecio.setText(preciofinal);

        TextView txtFechaI = (TextView)findViewById(R.id.textView13);
        txtFechaI.setText(fechainicio);

        TextView txtFechaF = (TextView)findViewById(R.id.textView15);
        txtFechaF.setText("  " + fechafinal);



        personas2 = (Spinner)findViewById(R.id.spinnerPersonas);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        personas2.setAdapter(adaptador);

        personas2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        posicion = "1";
                        break;
                    case 1:
                        posicion = "2";
                        break;
                    case 2:
                        posicion = "3";
                        break;
                    case 3:
                        posicion = "4";
                        break;
                    case 4:
                        posicion = "5";
                        break;

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posicion = "1";
            }


        });



    }


    class cambiarReservcion extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(reservacioncambiar.this);
            pDialog.setMessage("Cargando. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            // Building Parameters

            String cantidadnueva = posicion;

            int cantidadnue = Integer.parseInt(posicion);
            int calcprecio = Integer.parseInt(preciofinal);
            int posici = Integer.parseInt(cantpersonas);
            int intpreci = calcprecio / posici ;
            int prectotal = intpreci * cantidadnue;

            String precioFinal = Integer.toString(prectotal);



            JSONObject json;
            // getting JSON string from URL



            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Nombre", Nombre));
            params.add(new BasicNameValuePair("cantidadnueva", cantidadnueva));
            params.add(new BasicNameValuePair("idreservacion", idreservacion));
            params.add(new BasicNameValuePair("tipo", tipo));
            params.add(new BasicNameValuePair("precioFinal", precioFinal));
            params.add(new BasicNameValuePair("correo", correo));

            json = jParser.makeHttpRequest(url_cambiarreserv, "GET", params);


            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            // Agrega los destinos al JSON
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Intent i2;
                    i2 = new Intent(getApplicationContext() , my.class);
                    startActivity(i2);
                }
            } catch (JSONException e) {
                e.printStackTrace();


            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

        }
    }

    public void confirmReserva(View v){

        new cambiarReservcion().execute();


    }


}
