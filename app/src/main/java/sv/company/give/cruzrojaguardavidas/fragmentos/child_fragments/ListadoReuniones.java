package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.Funciones;
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapterReuniones;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoReuniones extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie="", filtrado="pendientes";
    ConexionWebService conexion;
    JSONObject jsonObjeto=null;
    JSONArray jsonRespuesta;

    RecyclerView rvReunion;
    ArrayList<String[]> listArraysReuniones;
    Button btnNotificarReunion;
    Spinner spFiltradoReuniones;


    public static int itemSeleccionado;


    public ListadoReuniones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.child_fragment_listado_reuniones, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        //vinculando los objetos de la vista
        rvReunion = rootview.findViewById(R.id.rvReuniones);
        btnNotificarReunion=rootview.findViewById(R.id.btnNotificarReunion);
        spFiltradoReuniones=rootview.findViewById(R.id.spFiltradoReuniones);

        //Click listeners
        btnNotificarReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion=new ConexionWebService();
                try {
                    //conexion.execute(url,parametros,cookie)
                    String resultado=conexion.execute(Variables.url+"reuniones.php",
                            "accion=crearNotificacion&tipoReunion="+listArraysReuniones.get(itemSeleccionado)[3]+"&lugar="
                                    +listArraysReuniones.get(itemSeleccionado)[2]+"&fecha="
                                    +jsonRespuesta.getJSONObject(itemSeleccionado).getString("fechaHora"),cookie).get();

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

        spFiltradoReuniones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    filtrado="pendientes";
                else
                    filtrado="todas";

                cargarRecyclerReuniones(rootview);
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return rootview;
    }

    private void cargarRecyclerReuniones(View rootview)
    {
        //Inicializacion de variables
        listArraysReuniones = new ArrayList<>();

        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "reuniones.php",
                    "accion=obtenerReuniones&tipoFiltrado="+filtrado, cookie).get();

            //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();

            jsonRespuesta = new JSONArray(resultado);
            jsonObjeto = jsonRespuesta.getJSONObject(0);
            int cantidadReuniones = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
            else {
                //LLenando el recycler on los datops de la DB
                //Se establece la forma de llenado como linear
                rvReunion.setLayoutManager(new LinearLayoutManager(getContext()));

                //se llena la lista con los datos
                for (int i = 0; i < cantidadReuniones; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);

                    String[] fechaHora=Funciones.separarFechaHora(jsonObjeto.getString("fechaHora"));

                    listArraysReuniones.add(new String[]{fechaHora[0], fechaHora[1], jsonObjeto.getString("lugar"),jsonObjeto.getString("tipoReunion")});
                }

                //se crea el adaptador y se manda la lista con los datos
                final RecyclerViewAdapterReuniones adaptador = new RecyclerViewAdapterReuniones(getContext(), listArraysReuniones, rootview);

                //se Añade el adaptador al recycler
                rvReunion.setAdapter(adaptador);
            }
        } catch (ExecutionException | InterruptedException | JSONException | ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
