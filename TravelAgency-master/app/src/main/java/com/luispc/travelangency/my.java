package com.luispc.travelangency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import java.util.ArrayList;
import android.text.Editable;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class my extends ActionBarActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    JSONParser jParser2 = new JSONParser();
    JSONParser jParser3 = new JSONParser();

    ArrayList<HashMap<String, String>> destinosList;
    ArrayList<HashMap<String, String>> destinosbuscados;
    ArrayList<HashMap<String, String>> paquetesList;
    ArrayList<HashMap<String, String>> reservacionList;

    TabHost th;

    // url to get all products list
    private static String url_all_destinos = "http://agenciadeviajes.esy.es/guticonnect/get_all_destinos.php";
    private static String url_all_paquetes = "http://agenciadeviajes.esy.es/guticonnect/get_all_paquetes.php";
    private static String url_all_reservaciones = "http://agenciadeviajes.esy.es/guticonnect/get_all_reservaciones.php";
    private static String url_busqueda = "http://agenciadeviajes.esy.es/guticonnect/get_busqueda.php";
    private static String url_busqueda_paquete = "http://agenciadeviajes.esy.es/guticonnect/get_busqueda_paquete.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DESTINOS = "destinos";
    private static final String TAG_PAQUETES = "paquetes";
    private static final String TAG_RESERVACION = "reservacion";
    // TAGS DESTINOS

    private static final String TAG_IDDESTINO = "idDestino";
    private static final String TAG_NOMBRE = "Nombre";
    private static final String TAG_PAIS = "Pais";
    private static final String TAG_PRECIO = "Precio";
    private static final String TAG_DESCRIPCION = "Descripcion";
    private static final String TAG_CUPODISPONIBLE = "CupoDisponible";
    private static final String TAG_FECHAINICIO = "FechaInicio";
    private static final String TAG_FECHAFINAL = "FechaFin";
    private static final String TAG_OFERTA = "Oferta";

    // TAGS PAQUETES
    private static final String TAG_IDPAQUETE = "idpaquete";
    private static final String TAG_NOMBREPAQUETE = "Nombre";
    private static final String TAG_PRECIOPAQUETE = "Precio";
    private static final String TAG_DESCRIPCIONPAQUETE = "Descripcion";
    private static final String TAG_CUPODISPONIBLEPAQUETE = "CupoDisponible";
    private static final String TAG_FECHAINICIOPAQUETE = "FechaInicio";
    private static final String TAG_FECHAFINALPAQUETE = "FechaFin";
    private static final String TAG_OFERTAPAQUETE = "Oferta";

    // TAG RESERVACIONES

    private static final String TAG_IDRESERVACION = "idreservacion";
    private static final String TAG_NOMBRERESERVACION = "Nombre";
    private static final String TAG_CANTPERSONAS = "cantpersonas";
    private static final String TAG_DESCRIPCIONRESERVACION = "Descripcion";
    private static final String TAG_DIARESERVADO= "diarealizado";
    private static final String TAG_DIAINIC = "diareservado";
    private static final String TAG_DIAFIN = "diafinal";
    private static final String TAG_PRECIOFINAL = "preciofinal";
    private static final String TAG_TIPO = "tipo";
    private static final String TAG_OFERTARESERVACION = "Oferta";


    // products JSONArray
    JSONArray products = null;
    JSONArray paquetes = null;
    JSONArray reservaciones = null;

    ListView lista;
    ListView listapaquete;
    ListView listareservaciones;

    String iddestino;
    String idpaquete;
    String nombre;
    String pais;
    String precio;
    String descripcion;
    String cupodisponible;
    String fechainicio;
    String fechafinal;
    String oferta;

    String idreservacion;
    String cantpersonas;
    String diarealizado;
    String diareservado;
    String diafinal;
    String preciofinal;
    String tipo;


    private EditText et;
    private EditText et2;

    String buscar;

    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        correo = ((global) this.getApplication()).getEmail();

        // Hashmap para el ListView
        destinosList = new ArrayList<HashMap<String, String>>();
        paquetesList = new ArrayList<HashMap<String, String>>();
        reservacionList = new ArrayList<HashMap<String, String>>();
        reservacionList = new ArrayList<HashMap<String, String>>();

        et = (EditText) findViewById(R.id.EditText01);
        et2 = (EditText) findViewById(R.id.EditText02);

        // Cargar los productos en el Background Thread
        new LoadAll().execute();
        lista = (ListView) findViewById(R.id.listAllProducts);
        listapaquete = (ListView) findViewById(R.id.listAllPaquetes);
        listareservaciones = (ListView) findViewById(R.id.listAllReservas);



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Iterator<Map.Entry<String,String>> it;
                it = destinosList.get(position).entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<String,String> e = it.next();
                    if (e.getKey() == "idDestino"){
                        iddestino = e.getValue();
                    }
                    if (e.getKey() == "Nombre"){
                        nombre = e.getValue();
                    }
                    if (e.getKey() == "Pais"){
                        pais = e.getValue();
                    }
                    if (e.getKey() == "Precio"){
                        precio = e.getValue();
                    }
                    if (e.getKey() == "Descripcion"){
                        descripcion = e.getValue();
                    }
                    if (e.getKey() == "CupoDisponible"){
                        cupodisponible = e.getValue();
                    }
                    if (e.getKey() == "FechaInicio"){
                        fechainicio = e.getValue();
                    }
                    if (e.getKey() == "FechaFin"){
                        fechafinal = e.getValue();
                    }
                    if (e.getKey() == "Oferta"){
                        oferta = e.getValue();
                    }

                }

                Intent i;
                i = new Intent(getApplicationContext() , destino.class);
                i.putExtra("idDestino",iddestino);
                i.putExtra("Nombre",nombre);
                i.putExtra("Pais",pais);
                i.putExtra("Precio",precio);
                i.putExtra("Descripcion",descripcion);
                i.putExtra("CupoDisponible",cupodisponible);
                i.putExtra("FechaInicio",fechainicio);
                i.putExtra("FechaFinal",fechafinal);
                i.putExtra("Oferta",oferta);
                startActivity(i);
            }
        });


        listapaquete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Iterator<Map.Entry<String,String>> it;
                it = paquetesList.get(position).entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<String,String> e = it.next();
                    if (e.getKey() == "idpaquete"){
                        idpaquete = e.getValue();
                    }
                    if (e.getKey() == "Nombre"){
                        nombre = e.getValue();
                    }
                    if (e.getKey() == "Precio"){
                        precio = e.getValue();
                    }
                    if (e.getKey() == "Descripcion"){
                        descripcion = e.getValue();
                    }
                    if (e.getKey() == "CupoDisponible"){
                        cupodisponible = e.getValue();
                    }
                    if (e.getKey() == "FechaInicio"){
                        fechainicio = e.getValue();
                    }
                    if (e.getKey() == "FechaFin"){
                        fechafinal = e.getValue();
                    }
                    if (e.getKey() == "Oferta"){
                        oferta = e.getValue();
                    }

                }

                Intent i;
                i = new Intent(getApplicationContext() , paquete.class);
                i.putExtra("idpaquete",idpaquete);
                i.putExtra("Nombre",nombre);
                i.putExtra("Precio",precio);
                i.putExtra("Descripcion",descripcion);
                i.putExtra("CupoDisponible",cupodisponible);
                i.putExtra("FechaInicio",fechainicio);
                i.putExtra("FechaFinal",fechafinal);
                i.putExtra("Oferta",oferta);
                startActivity(i);
            }
        });


        listareservaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Iterator<Map.Entry<String,String>> it;
                it = reservacionList.get(position).entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry<String,String> e = it.next();
                    if (e.getKey() == "idreservacion"){
                        idreservacion = e.getValue();
                    }
                    if (e.getKey() == "Nombre"){
                        nombre = e.getValue();
                    }
                    if (e.getKey() == "cantpersonas"){
                        cantpersonas = e.getValue();
                    }
                    if (e.getKey() == "Descripcion"){
                        descripcion = e.getValue();
                    }
                    if (e.getKey() == "diarealizado"){
                        diarealizado = e.getValue();
                    }
                    if (e.getKey() == "diareservado"){
                        diareservado = e.getValue();
                    }
                    if (e.getKey() == "diafinal"){
                        diafinal = e.getValue();
                    }
                    if (e.getKey() == "preciofinal"){
                        preciofinal = e.getValue();
                    }
                    if (e.getKey() == "tipo"){
                        tipo = e.getValue();
                    }
                    if (e.getKey() == "Oferta"){
                        oferta = e.getValue();
                    }

                }

                Intent i;
                i = new Intent(getApplicationContext() , reservacion_info.class);
                i.putExtra("idreservacion",idreservacion);
                i.putExtra("Nombre",nombre);
                i.putExtra("cantpersonas",cantpersonas);
                i.putExtra("Descripcion",descripcion);
                i.putExtra("diarealizado",diarealizado);
                i.putExtra("diareservado",diareservado);
                i.putExtra("diafinal",diafinal);
                i.putExtra("preciofinal",preciofinal);
                i.putExtra("tipo",tipo);
                i.putExtra("Oferta",oferta);
                startActivity(i);
            }
        });




        th = (TabHost)findViewById(R.id.tabHost);

        //Tabhos 1
        th.setup();
        TabHost.TabSpec ts1 = th.newTabSpec("tab1");
        ts1.setIndicator("Destinos");
        ts1.setContent(R.id.tab1);
        th.addTab(ts1);

        //Tabhost 2
        th.setup();
        TabHost.TabSpec ts2 = th.newTabSpec("tab2");
        ts2.setIndicator("Paquetes");
        ts2.setContent(R.id.tab2);
        th.addTab(ts2);

        //Tabhost 3
        th.setup();
        TabHost.TabSpec ts3 = th.newTabSpec("tab3");
        ts3.setIndicator("Reservaciones");
        ts3.setContent(R.id.tab3);
        th.addTab(ts3);





    }


    class LoadAll extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(my.this);
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
            List params = new ArrayList();
            List params2 = new ArrayList();


            List<NameValuePair> params3 = new ArrayList<>();
            params3.add(new BasicNameValuePair("correo", correo));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_destinos, "GET", params);
            JSONObject json2 = jParser2.makeHttpRequest(url_all_paquetes, "GET", params2);
            JSONObject json3 = jParser3.makeHttpRequest(url_all_reservaciones, "GET", params3);


            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());
            Log.d("All Packages: ", json2.toString());
            Log.d("All Reservaciones: ", json3.toString());

            // Agrega los destinos al JSON
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_DESTINOS);
                    // looping through All Products

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable

                        String iddestino= c.getString(TAG_IDDESTINO);
                        String name = c.getString(TAG_NOMBRE);
                        String pais = c.getString(TAG_PAIS);
                        String precio = c.getString(TAG_PRECIO);
                        String descripcion = c.getString(TAG_DESCRIPCION);
                        String cupodisponible = c.getString(TAG_CUPODISPONIBLE);
                        String fechainicio = c.getString(TAG_FECHAINICIO);
                        String fechafinal = c.getString(TAG_FECHAFINAL);
                        String oferta = c.getString(TAG_OFERTA);

                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value

                        map.put(TAG_IDDESTINO, iddestino);
                        map.put(TAG_NOMBRE, name);
                        map.put(TAG_PAIS, pais);
                        map.put(TAG_PRECIO, precio);
                        map.put(TAG_DESCRIPCION, descripcion);
                        map.put(TAG_CUPODISPONIBLE, cupodisponible);
                        map.put(TAG_FECHAINICIO, fechainicio);
                        map.put(TAG_FECHAFINAL, fechafinal);
                        map.put(TAG_OFERTA, oferta);

                        destinosList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                // Checking for SUCCESS TAG
                int success = json2.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    paquetes = json2.getJSONArray(TAG_PAQUETES);
                    // looping through All Products

                    for (int i = 0; i < paquetes.length(); i++) {
                        JSONObject c2 = paquetes.getJSONObject(i);

                        // Storing each json item in variable
                        String idpaquete = c2.getString(TAG_IDPAQUETE);
                        String name2 = c2.getString(TAG_NOMBREPAQUETE);
                        String precio2 = c2.getString(TAG_PRECIOPAQUETE);
                        String descripcion2 = c2.getString(TAG_DESCRIPCIONPAQUETE);
                        String cupodisponible2 = c2.getString(TAG_CUPODISPONIBLEPAQUETE);
                        String fechainicio2 = c2.getString(TAG_FECHAINICIOPAQUETE);
                        String fechafinal2 = c2.getString(TAG_FECHAFINALPAQUETE);
                        String oferta2 = c2.getString(TAG_OFERTAPAQUETE);
                        // creating new HashMap
                        HashMap map2 = new HashMap();

                        // adding each child node to HashMap key => value

                        map2.put(TAG_IDPAQUETE, idpaquete);
                        map2.put(TAG_NOMBREPAQUETE, name2);
                        map2.put(TAG_PRECIOPAQUETE, precio2);
                        map2.put(TAG_DESCRIPCIONPAQUETE, descripcion2);
                        map2.put(TAG_CUPODISPONIBLEPAQUETE, cupodisponible2);
                        map2.put(TAG_FECHAINICIOPAQUETE, fechainicio2);
                        map2.put(TAG_FECHAFINALPAQUETE, fechafinal2);
                        map2.put(TAG_OFERTAPAQUETE, oferta2);

                        paquetesList.add(map2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                // Checking for SUCCESS TAG
                int success = json3.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    reservaciones = json3.getJSONArray(TAG_RESERVACION);
                    // looping through All Products

                    for (int i = 0; i < reservaciones.length(); i++) {
                        JSONObject c3 = reservaciones.getJSONObject(i);

                        // Storing each json item in variable
                        String idreservacion = c3.getString(TAG_IDRESERVACION);
                        String nombre3 = c3.getString(TAG_NOMBRERESERVACION);
                        String cantpersonas = c3.getString(TAG_CANTPERSONAS);
                        String descripcion3 = c3.getString(TAG_DESCRIPCIONRESERVACION);
                        String diareserv = c3.getString(TAG_DIARESERVADO);
                        String diainic = c3.getString(TAG_DIAINIC);
                        String diafin = c3.getString(TAG_DIAFIN);
                        String preciofinal = c3.getString(TAG_PRECIOFINAL);
                        String tipo = c3.getString(TAG_TIPO);
                        String oferta3 = c3.getString(TAG_OFERTARESERVACION);
                        // creating new HashMap
                        HashMap map3 = new HashMap();

                        // adding each child node to HashMap key => value

                        map3.put(TAG_IDRESERVACION, idreservacion);
                        map3.put(TAG_NOMBRERESERVACION, nombre3);
                        map3.put(TAG_CANTPERSONAS, cantpersonas);
                        map3.put(TAG_DESCRIPCIONRESERVACION, descripcion3);
                        map3.put(TAG_DIARESERVADO, diareserv);
                        map3.put(TAG_DIAINIC, diainic);
                        map3.put(TAG_DIAFIN, diafin);
                        map3.put(TAG_PRECIOFINAL, preciofinal);
                        map3.put(TAG_TIPO, tipo);
                        map3.put(TAG_OFERTARESERVACION, oferta3);

                        reservacionList.add(map3);
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
                            my.this,
                            destinosList,
                            R.layout.single_post,
                            new String[] {

                                    TAG_NOMBRE,
                            },
                            new int[] {

                                    R.id.single_post_tv_nombre,

                            });

                    lista.setAdapter(adapter);

                    ListAdapter adapter2 = new SimpleAdapter(
                            my.this,
                            paquetesList,
                            R.layout.single_post,
                            new String[] {
                                    TAG_NOMBREPAQUETE,TAG_DESCRIPCIONRESERVACION
                            },
                            new int[] {
                                    R.id.single_post_tv_nombre,
                            });

                    listapaquete.setAdapter(adapter2);

                    ListAdapter adapter3 = new SimpleAdapter(
                            my.this,
                            reservacionList,
                            R.layout.single_reservacion,
                            new String[] {
                                    TAG_NOMBRERESERVACION,TAG_DESCRIPCIONRESERVACION
                            },
                            new int[] {
                                    R.id.single_post_tv_nombre,R.id.single_post_tv_descripcion
                            });

                    listareservaciones.setAdapter(adapter3);

                }
            });
        }
    }

    class buscar extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * obteniendo todos los destinos y paquetes
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("buscar", buscar));

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_busqueda, "GET", params);



            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            // Agrega los destinos al JSON
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_DESTINOS);
                    // looping through All Products

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable

                        String iddestino= c.getString(TAG_IDDESTINO);
                        String name = c.getString(TAG_NOMBRE);
                        String pais = c.getString(TAG_PAIS);
                        String precio = c.getString(TAG_PRECIO);
                        String descripcion = c.getString(TAG_DESCRIPCION);
                        String cupodisponible = c.getString(TAG_CUPODISPONIBLE);
                        String fechainicio = c.getString(TAG_FECHAINICIO);
                        String fechafinal = c.getString(TAG_FECHAFINAL);
                        String oferta = c.getString(TAG_OFERTA);


                        // creating new HashMap
                        HashMap map = new HashMap();

                        // adding each child node to HashMap key => value

                        map.put(TAG_IDDESTINO, iddestino);
                        map.put(TAG_NOMBRE, name);
                        map.put(TAG_PAIS, pais);
                        map.put(TAG_PRECIO, precio);
                        map.put(TAG_DESCRIPCION, descripcion);
                        map.put(TAG_CUPODISPONIBLE, cupodisponible);
                        map.put(TAG_FECHAINICIO, fechainicio);
                        map.put(TAG_FECHAFINAL, fechafinal);
                        map.put(TAG_OFERTA, oferta);

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
                            my.this,
                            destinosList,
                            R.layout.single_post,
                            new String[] {

                                    TAG_NOMBRE,
                            },
                            new int[] {

                                    R.id.single_post_tv_nombre,

                            });

                    lista.setAdapter(adapter);


                }
            });
        }
    }


    class buscarpaquete extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * obteniendo todos los destinos y paquetes
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("buscar", buscar));

            // getting JSON string from URL
            JSONObject json2 = jParser.makeHttpRequest(url_busqueda_paquete, "GET", params);



            // Check your log cat for JSON reponse
            Log.d("All Products: ", json2.toString());

            // Agrega los destinos al JSON
            try {
                // Checking for SUCCESS TAG
                int success = json2.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    paquetes = json2.getJSONArray(TAG_PAQUETES);
                    // looping through All Products

                    for (int i = 0; i < paquetes.length(); i++) {
                        JSONObject c2 = paquetes.getJSONObject(i);

                        // Storing each json item in variable
                        String idpaquete = c2.getString(TAG_IDPAQUETE);
                        String name2 = c2.getString(TAG_NOMBREPAQUETE);
                        String precio2 = c2.getString(TAG_PRECIOPAQUETE);
                        String descripcion2 = c2.getString(TAG_DESCRIPCIONPAQUETE);
                        String cupodisponible2 = c2.getString(TAG_CUPODISPONIBLEPAQUETE);
                        String fechainicio2 = c2.getString(TAG_FECHAINICIOPAQUETE);
                        String fechafinal2 = c2.getString(TAG_FECHAFINALPAQUETE);
                        String oferta2 = c2.getString(TAG_OFERTAPAQUETE);
                        // creating new HashMap
                        HashMap map2 = new HashMap();

                        // adding each child node to HashMap key => value

                        map2.put(TAG_IDPAQUETE, idpaquete);
                        map2.put(TAG_NOMBREPAQUETE, name2);
                        map2.put(TAG_PRECIOPAQUETE, precio2);
                        map2.put(TAG_DESCRIPCIONPAQUETE, descripcion2);
                        map2.put(TAG_CUPODISPONIBLEPAQUETE, cupodisponible2);
                        map2.put(TAG_FECHAINICIOPAQUETE, fechainicio2);
                        map2.put(TAG_FECHAFINALPAQUETE, fechafinal2);
                        map2.put(TAG_OFERTAPAQUETE, oferta2);

                        paquetesList.add(map2);
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
                    ListAdapter adapter2 = new SimpleAdapter(
                            my.this,
                            paquetesList,
                            R.layout.single_post,
                            new String[] {
                                    TAG_NOMBREPAQUETE,TAG_DESCRIPCIONRESERVACION
                            },
                            new int[] {
                                    R.id.single_post_tv_nombre,
                            });

                    listapaquete.setAdapter(adapter2);


                }
            });
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Get the event source id
        int id = item.getItemId();

        // Handle presses on the action bar items
        switch (id) {
            case R.id.action_login:
                Intent loginAct = new Intent(my.this, GmailAuth.class);
                startActivity(loginAct);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void empezarbusqueda (View v){

        destinosList = new ArrayList<HashMap<String, String>>();
        buscar = et.getText().toString();
        lista.setAdapter(null);
        new buscar().execute();

    }

    public void empezarbusquedapaquete (View v){

        paquetesList = new ArrayList<HashMap<String, String>>();
        buscar = et2.getText().toString();
        listapaquete.setAdapter(null);
        new buscarpaquete().execute();

    }
}
