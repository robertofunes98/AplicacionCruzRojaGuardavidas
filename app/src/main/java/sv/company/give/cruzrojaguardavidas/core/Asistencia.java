package sv.company.give.cruzrojaguardavidas.core;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
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

//Este SuppresLint se agrega para pode5r pedir datos
@SuppressLint("ValidFragment")
public class Asistencia extends DialogFragment implements Guardavidas.OnAsistenteSelected{

    String cookie = "";
    static String idReunion;
    Button btnAgregarAsistente;
    ArrayList<String[]> listAsistentes;
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta;
    RecyclerView rvAsistencia;
    public static boolean agregado=false;

    View rootview;


    public static int itemSeleccionado;

    public Asistencia(String cookieR, String id) {
        // Required empty public constructor
        cookie=cookieR;
        idReunion=id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.dialog_fragment_asistencia, container, false);



        //vinculando variables a los objetos corresponmdientes
        btnAgregarAsistente=rootview.findViewById(R.id.btnAgregarAsistente);
        rvAsistencia=rootview.findViewById(R.id.rvAsistencia);

        cargarRecyclerAsistencia();

        //onclick listeners
        btnAgregarAsistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardavidas guardavidas=new Guardavidas(cookie);
                guardavidas.setTargetFragment(Asistencia.this, 1);
                guardavidas.show(getFragmentManager(),"Guardavidas");
            }
        });

        return rootview;
    }

    public void cargarRecyclerAsistencia()
    {
        //Inicializacion de variables
        listAsistentes = new ArrayList<>();

        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "asistencia.php",
                    "accion=obtenerAsistenciaReunion&idReunion="+idReunion, cookie).get();

            //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();

            jsonRespuesta = new JSONArray(resultado);
            jsonObjeto = jsonRespuesta.getJSONObject(0);
            int cantidadAsistentes = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
            else {
                //LLenando el recycler on los datops de la DB
                //Se establece la forma de llenado como linear
                rvAsistencia.setLayoutManager(new GridLayoutManager(getContext(),1));


                //se llena la lista con los datos
                for (int i = 0; i < cantidadAsistentes; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);
                    listAsistentes.add(new String[]{jsonObjeto.getString("nombres") +" "+ jsonObjeto.getString("apellidos")});
                }

                //se crea el adaptador y se manda la lista con los datos
                final RecyclerViewAdapterAsistencia adaptador = new RecyclerViewAdapterAsistencia(getContext(), listAsistentes, rootview);

                //se Añade el adaptador al recycler
                rvAsistencia.setAdapter(adaptador);

            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void recargarRecycler() {
        cargarRecyclerAsistencia();
    }
}
