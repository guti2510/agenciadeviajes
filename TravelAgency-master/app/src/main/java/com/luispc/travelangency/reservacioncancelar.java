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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class reservacioncancelar extends ActionBarActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();

    private static String url_cancelarreserv = "http://agenciadeviajes.esy.es/guticonnect/get_cancelarreserv.php";

    private static final String TAG_SUCCESS = "success";

    Bundle b;

    String idreservacion;
    String Nombre;
    String tipo;

    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacioncancelar);

        b=getIntent().getExtras();

        idreservacion = b.getString("idreservacion");
        Nombre = b.getString("Nombre");
        tipo = b.getString("tipo");

        correo = ((global) this.getApplication()).getEmail();



    }

    class cancelarReserv extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(reservacioncancelar.this);
            pDialog.setMessage("Cargando. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            // Building Parameters


            JSONObject json;
            // getting JSON string from URL



            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Nombre", Nombre));
            params.add(new BasicNameValuePair("idreservacion", idreservacion));
            params.add(new BasicNameValuePair("tipo", tipo));
            params.add(new BasicNameValuePair("correo", correo));

            json = jParser.makeHttpRequest(url_cancelarreserv, "GET", params);


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
                Toast toast3 = Toast.makeText(getApplicationContext(),"No se ha podido cambiar la reservacion", Toast.LENGTH_SHORT);
                toast3.show();

            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

        }
    }

    public void cancelarReserva2(View v){

        new cancelarReserv().execute();


    }

}
