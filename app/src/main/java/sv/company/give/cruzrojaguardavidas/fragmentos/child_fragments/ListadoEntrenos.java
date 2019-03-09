package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.Funciones;
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapterEntrenos;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoEntrenos extends Fragment {

    RecyclerView rvEntreno;
    ArrayList<String[]> listArraysEntrenos;
    String cookie = "";
    Button btnNotificarEntreno;
    ConexionWebService conexion;

    JSONObject jsonObjeto = null;
    public static int itemSeleccionado;

    public ListadoEntrenos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.child_fragment_listado_entrenos, container, false);

        //vinculando los objetos de la vista
        rvEntreno = rootview.findViewById(R.id.rvEntrenos);
        btnNotificarEntreno=rootview.findViewById(R.id.btnNotificarEntreno);

        //Inicializacion de variables
        listArraysEntrenos = new ArrayList<>();

        //Obteniendo Cookie
        cookie = getArguments().getString("cookie");

        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "entrenos.php",
                    "accion=obtenerEntrenos", cookie).get();

            //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();

            JSONArray jsonRespuesta = new JSONArray(resultado);
            jsonObjeto = jsonRespuesta.getJSONObject(0);
            int cantidadEntrenos = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay entrenos
            else {
                //LLenando el recycler on los datops de la DB
                //Se establece la forma de llenado como linear
                rvEntreno.setLayoutManager(new LinearLayoutManager(getContext()));

                //se llena la lista con los datos
                for (int i = 0; i < cantidadEntrenos; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);
                    //Convirtiendo el patron a dias
                    String dias = Funciones.patron_a_dias(jsonObjeto.getString("patron"));
                    //Convirtiendo la hora a formato 12 horas
                    String hora = Funciones.formatearHora(jsonObjeto.getString("hora"));
                    listArraysEntrenos.add(new String[]{dias, hora, jsonObjeto.getString("lugar")});
                }

                //se crea el adaptador y se manda la lista con los datos
                final RecyclerViewAdapterEntrenos adaptador = new RecyclerViewAdapterEntrenos(getContext(), listArraysEntrenos, rootview);

                //se Añade el adaptador al recycler
                rvEntreno.setAdapter(adaptador);
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


        //Click listeners
        btnNotificarEntreno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion=new ConexionWebService();
                try {
                    //conexion.execute(url,parametros,cookie)
                    String resultado=conexion.execute(Variables.url+"entrenos.php",
                            "accion=crearNotificacion&carnet=general&dias="+listArraysEntrenos.get(itemSeleccionado)[0]+"&lugar="
                                    +listArraysEntrenos.get(itemSeleccionado)[2],cookie).get();

                    //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

                    JSONArray jsonRespuesta= new JSONArray(resultado);

                    jsonObjeto=jsonRespuesta.getJSONObject(0);

                    if(jsonObjeto.has("error"))
                        Toast.makeText(getContext(),jsonObjeto.getString("error"),Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                    }

                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootview;
    }



}
