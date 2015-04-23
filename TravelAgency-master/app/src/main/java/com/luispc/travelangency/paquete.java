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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class paquete extends ActionBarActivity {

    Bundle b;
    String idPaquete;
    String Nombre;
    String Pais;
    String Precio;
    String Descripcion;
    String CupoDisponible;
    String FechaInicio;
    String FechaFinal;
    String Oferta;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> destinosList;

    private static String url_all_destinopaquete = "http://agenciadeviajes.esy.es/guticonnect/get_destinospaquete.php";

    private static final String TAG_DESTINO = "viaje";
    private static final String TAG_SUCCESS = "success";

    private static final String TAG_IDPAQUETE = "idPaquete";
    private static final String TAG_NOMBRE = "Nombre";
    private static final String TAG_DESCRIPCION = "Descripcion";

    JSONArray products = null;


    ListView listadedestinos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paquete);

        destinosList = new ArrayList<HashMap<String, String>>();

        listadedestinos = (ListView) findViewById(R.id.listViewdedestinos);

        b=getIntent().getExtras();

        idPaquete = b.getString("idpaquete");
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
            TextView txtoferta = (TextView)findViewById(R.id.textView25);
            txtoferta.setText("En Oferta");
        }

        new LoadDestinos().execute();


    }

    class LoadDestinos extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(paquete.this);
            pDialog.setMessage("Cargando. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los destinos y paquetes
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<>();


            params.add(new BasicNameValuePair("idPaquete", idPaquete));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_destinopaquete, "GET", params);



            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            // Agrega los destinos al JSON
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_DESTINO);
                    // looping through All Products

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable

                        String name = c.getString(TAG_NOMBRE);
                        String descripcion = c.getString(TAG_DESCRIPCION);


                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value


                        map.put(TAG_NOMBRE, name);
                        map.put(TAG_DESCRIPCION, descripcion);


                        destinosList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            paquete.this,
                            destinosList,
                            R.layout.single_destino,
                            new String[] {TAG_NOMBRE,TAG_DESCRIPCION},new int[] {R.id.single_post_tv_nombre,R.id.single_post_tv_descripcion});

                    listadedestinos.setAdapter(adapter);


                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paquete, menu);
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
                Intent loginAct = new Intent(paquete.this, GmailAuth.class);
                startActivity(loginAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void pasarReserva(View v){



        String email = ((global) this.getApplication()).getEmail();

        if (email == ""){
            Toast toast1 = Toast.makeText(getApplicationContext(),"No te encuentras autenticado", Toast.LENGTH_SHORT);
            toast1.show();

        }
        else {

            String paquete = "paquete";
            Intent i;
            i = new Intent(getApplicationContext() , reservacion.class);
            i.putExtra("ID",idPaquete);
            i.putExtra("Indicador",paquete);
            i.putExtra("Nombre",Nombre);
            i.putExtra("Precio",Precio);
            i.putExtra("FechaInicio",FechaInicio);
            i.putExtra("FechaFinal",FechaFinal);
            i.putExtra("Oferta",Oferta);
            startActivity(i);



        }

    }

}
