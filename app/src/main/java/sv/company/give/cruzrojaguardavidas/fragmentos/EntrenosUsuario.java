package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.Funciones;
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapterEntrenosAdministrador;
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapterEntrenosUsuario;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntrenosUsuario extends Fragment {

    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta,jsonExcepciones;
    ArrayList<String[]> listArraysEntrenos,listArraysExcepciones;
    

    Spinner spFiltroEntrenos;
    RecyclerView rvEntrenosUsuario;

    public EntrenosUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_entrenos_usuario, container, false);

        spFiltroEntrenos = rootview.findViewById(R.id.spFiltroEntrenos);
        rvEntrenosUsuario=rootview.findViewById(R.id.rvEntrenosUsuario);

        spFiltroEntrenos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarRecycler(rootview);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        obtenerLugares();

        obtenerExcepciones();
        
        cargarRecycler(rootview);

        return rootview;
    }

    private void obtenerExcepciones() {
        listArraysExcepciones=new ArrayList<>();
        int cantidadExcepciones=jsonExcepciones.length();
        JSONObject jsonObjetoExcepciones;

        try {
            for (int i = 0; i < cantidadExcepciones; i++) {
                jsonObjetoExcepciones = jsonExcepciones.getJSONObject(i);

                String[] fecha=jsonObjetoExcepciones.getString("fecha").split(" ");

                listArraysExcepciones.add(new String[]{jsonObjetoExcepciones.getString("idDiaSinEntreno"), fecha[0]
                        , jsonObjetoExcepciones.getString("idEntreno")});
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void cargarRecycler(View rootview) {

        listArraysEntrenos=new ArrayList<>();
        int posicion = spFiltroEntrenos.getSelectedItemPosition();

        //LLenando el recycler on los datops de la DB
        //Se establece la forma de llenado como linear
        rvEntrenosUsuario.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            if (posicion == 0) {
                int cantidadEntrenos = jsonRespuesta.length();
                //se llena la lista con los datos
                for (int i = 0; i < cantidadEntrenos; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);
                    //Convirtiendo la hora a formato 12 horas
                    String hora = Funciones.formatearHora(jsonObjeto.getString("hora"));
                    listArraysEntrenos.add(new String[]{jsonObjeto.getString("patron"), hora, jsonObjeto.getString("lugar")
                            ,jsonObjeto.getString("idEntreno")});
                }
            } else {
                jsonObjeto = jsonRespuesta.getJSONObject((posicion - 1));
                //Convirtiendo la hora a formato 12 horas
                String hora = Funciones.formatearHora(jsonObjeto.getString("hora"));
                listArraysEntrenos.add(new String[]{jsonObjeto.getString("patron"), hora, jsonObjeto.getString("lugar")
                        ,jsonObjeto.getString("idEntreno")});
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        //se crea el adaptador y se manda la lista con los datos
        final RecyclerViewAdapterEntrenosUsuario adaptador = new RecyclerViewAdapterEntrenosUsuario(getContext(), listArraysEntrenos,listArraysExcepciones, rootview);

        //se Añade el adaptador al recycler
        rvEntrenosUsuario.setAdapter(adaptador);
    }

    private void obtenerLugares() {
        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "entrenos.php",
                    "accion=obtenerEntrenos", Variables.cookie).get();

            //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

            //separando JsoonArrays(entrenos y excepciones respectivamente)
            String[] resultados=resultado.split("xxxx");


            jsonRespuesta = new JSONArray(resultados[0]);
            jsonExcepciones = new JSONArray(resultados[1]);

            jsonObjeto = jsonRespuesta.getJSONObject(0);
            int cantidadLugares = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay entrenos
            else {
                ArrayList<String> datos = new ArrayList<>();
                datos.add("Todos los entrenos");

                for (int i = 0; i < cantidadLugares; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);

                    datos.add(jsonObjeto.getString("lugar"));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, datos);

                spFiltroEntrenos.setAdapter(adapter);
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
