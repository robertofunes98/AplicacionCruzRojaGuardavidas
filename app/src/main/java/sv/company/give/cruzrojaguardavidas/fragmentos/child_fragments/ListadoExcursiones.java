package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;

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

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.Asistencia;
import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.Funciones;
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapterExcursiones;
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapterReuniones;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListadoExcursiones extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie = "", filtrado = "pendientes";
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta;

    RecyclerView rvExcursion;
    ArrayList<String[]> listArraysExcursiones;
    Button btnAsignarExcursion, btnBorrarExcursion, btnModificarExcursion;
    Spinner spFiltradoExcursiones;


    public static int itemSeleccionado;


    public ListadoExcursiones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.child_fragment_listado_excursiones, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        //vinculando los objetos de la vista
        rvExcursion = rootview.findViewById(R.id.rvExcursion);
        btnModificarExcursion = rootview.findViewById(R.id.btnModificarExcursion);
        btnBorrarExcursion=rootview.findViewById(R.id.btnBorrarExcursion);
        btnAsignarExcursion=rootview.findViewById(R.id.btnAsignarExcursion);
        spFiltradoExcursiones = rootview.findViewById(R.id.spFiltradoExcursiones);

        //Click listeners
        btnBorrarExcursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion = new ConexionWebService();
                try {
                    //conexion.execute(url,parametros,cookie)
                    String resultado = conexion.execute(Variables.url + "excursiones.php",
                            "accion=borrarExcursion&idExcursion=" + listArraysExcursiones.get(itemSeleccionado)[8], cookie).get();

                    Toast.makeText(getContext(),listArraysExcursiones.get(itemSeleccionado)[8],Toast.LENGTH_LONG).show();

                    JSONArray jsonRespuesta = new JSONArray(resultado);

                    jsonObjeto = jsonRespuesta.getJSONObject(0);

                    if (jsonObjeto.has("error"))
                        Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                    }

                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

//        btnAsistenciaReunion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Asistencia asistencia=new Asistencia(cookie,listArraysReuniones.get(itemSeleccionado)[0]);
//                asistencia.show(getFragmentManager(),"Asistencia");
//            }
//        });

        spFiltradoExcursiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    filtrado = "pendientes";
                else if (position == 1)
                    filtrado = "finalizadas";
                else if (position == 2)
                    filtrado = "pagadas";
                else if (position == 3)
                    filtrado = "borradas";
                else
                    filtrado = "todas";

                cargarRecycler(rootview);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return rootview;
    }

    private void cargarRecycler(View rootview) {
        //Inicializacion de variables
        listArraysExcursiones = new ArrayList<>();

        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "excursiones.php",
                    "accion=obtenerExcursiones&tipoFiltrado=" + filtrado, cookie).get();

            //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

            jsonRespuesta = new JSONArray(resultado);
            jsonObjeto = jsonRespuesta.getJSONObject(0);
            int cantidad= jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
            else if(jsonObjeto.has("errorExcursionesCero"))
                Toast.makeText(getContext(), "No hay excursiones "+filtrado, Toast.LENGTH_LONG).show();
            else {
                //LLenando el recycler on los datops de la DB
                //Se establece la forma de llenado como linear
                rvExcursion.setLayoutManager(new LinearLayoutManager(getContext()));

                //se llena la lista con los datos
                for (int i = 0; i < cantidad; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);

                    String[] fechaHora = Funciones.separarFechaHora(jsonObjeto.getString("fechaInicio")+" "+jsonObjeto.getString("horaSalida"));

                    listArraysExcursiones.add(new String[]{jsonObjeto.getString("lugarExcursion"),fechaHora[0]+" a las "+ fechaHora[1],
                            jsonObjeto.getString("encargadoExcursion"), jsonObjeto.getString("telefonoEncargado"),
                            jsonObjeto.getString("encargadoExcursion")+" NO", jsonObjeto.getString("estado"), jsonObjeto.getString("cantidadDias"),
                            jsonObjeto.getString("motivoExtraordinario"),jsonObjeto.getString("idExcursion") });
                }

                //se crea el adaptador y se manda la lista con los datos
                final RecyclerViewAdapterExcursiones adaptador = new RecyclerViewAdapterExcursiones(getContext(), listArraysExcursiones, rootview);

                //se Añade el adaptador al recycler
                rvExcursion.setAdapter(adaptador);
            }
        } catch (ExecutionException | InterruptedException | JSONException | ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
