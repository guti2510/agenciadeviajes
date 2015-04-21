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
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
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


public class reservacion extends ActionBarActivity {


    Spinner personas;
    String Nombre;
    String FechaInicio;
    String FechaFinal;
    String Precio;
    String Indicador;
    String ID;
    String Email;
    Bundle b;

    String[] opciones = {"1","2","3","4","5"};
    String posicion;


    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    private static final String TAG_SUCCESS = "success";


    private static String url_reservarDestino = "http://agenciadeviajes.esy.es/guticonnect/post_reservacion_destino.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion);

        personas = (Spinner)findViewById(R.id.spinnerPersonas);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        personas.setAdapter(adaptador);

        b=getIntent().getExtras();
        Indicador = b.getString("Indicador");
        ID = b.getString("ID");
        Nombre = b.getString("Nombre");
        Precio = b.getString("Precio");
        FechaInicio = b.getString("FechaInicio");
        FechaFinal = b.getString("FechaFinal");
        Email = ((global) this.getApplication()).getEmail();



        TextView txtNombre = (TextView)findViewById(R.id.textView12);
        txtNombre.setText(Nombre);

        TextView txtPrecio = (TextView)findViewById(R.id.textView17);
        txtPrecio.setText(Precio);

        TextView txtFechaI = (TextView)findViewById(R.id.textView13);
        txtFechaI.setText(FechaInicio);

        TextView txtFechaF = (TextView)findViewById(R.id.textView15);
        txtFechaF.setText("  " + FechaFinal);

        personas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    class insertarReservacion extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(reservacion.this);
            pDialog.setMessage("Cargando. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            // Building Parameters


            String cantidadpersonas = posicion;
            String diareservado = FechaInicio;
            String idDestino;

            String correousuario = Email;
            String tipodereserv;


            int calcprecio = Integer.parseInt(Precio);
            int posici = Integer.parseInt(posicion);
            int intpreci = calcprecio * posici;
            String preciofinal = Integer.toString(intpreci);



            JSONObject json;
            // getting JSON string from URL


            if (Indicador.equals("paquete")){

                idDestino = ID;
                tipodereserv = "paquete";

                System.out.println(Nombre);
                System.out.println(cantidadpersonas);
                System.out.println(diareservado);
                System.out.println(FechaFinal);
                System.out.println(preciofinal);
                System.out.println(correousuario);
                System.out.println(tipodereserv);


                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("Nombre", Nombre));
                params.add(new BasicNameValuePair("cantidadpersonas", cantidadpersonas));
                params.add(new BasicNameValuePair("diareservado", diareservado));
                params.add(new BasicNameValuePair("FechaFinal", FechaFinal));
                params.add(new BasicNameValuePair("preciofinal", preciofinal));
                params.add(new BasicNameValuePair("idDestino", idDestino));
                params.add(new BasicNameValuePair("correousuario", correousuario));
                params.add(new BasicNameValuePair("tipodereserv", tipodereserv));

                json = jParser.makeHttpRequest(url_reservarDestino, "GET", params);




            }
            else {

                idDestino = ID;
                tipodereserv = "destino";

                System.out.println(Nombre);
                System.out.println(cantidadpersonas);
                System.out.println(diareservado);
                System.out.println(FechaFinal);
                System.out.println(preciofinal);
                System.out.println(idDestino);
                System.out.println(correousuario);
                System.out.println(tipodereserv);

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("Nombre", Nombre));
                params.add(new BasicNameValuePair("cantidadpersonas", cantidadpersonas));
                params.add(new BasicNameValuePair("diareservado", diareservado));
                params.add(new BasicNameValuePair("FechaFinal", FechaFinal));
                params.add(new BasicNameValuePair("preciofinal", preciofinal));
                params.add(new BasicNameValuePair("idDestino", idDestino));
                params.add(new BasicNameValuePair("correousuario", correousuario));
                params.add(new BasicNameValuePair("tipodereserv", tipodereserv));
                json = jParser.makeHttpRequest(url_reservarDestino, "GET", params);


            }

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            // Agrega los destinos al JSON
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Intent i;
                    i = new Intent(getApplicationContext() , my.class);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast toast3 = Toast.makeText(getApplicationContext(),"No se ha podido realizar la reservacion", Toast.LENGTH_SHORT);
                toast3.show();

            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reservacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmarReserva(View v){

        new insertarReservacion().execute();


    }
}
