package sv.company.give.cruzrojaguardavidas.core;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.R;

//Este SuppresLint se agrega para pode5r pedir datos
@SuppressLint("ValidFragment")
public class Excepciones extends DialogFragment {


    Button btnCancelar, btnEliminarExcepciones;
    ArrayList<String[]> arrayListExcepciones;
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta;
    RecyclerView rvExcepciones;

    RecyclerViewAdapterExcepciones adaptador = null;

    public Excepciones() {
        // Required empty public constructor
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.dialog_fragment_excepciones, container, false);


        //vinculando variables a los objetos corresponmdientes
        btnCancelar = rootview.findViewById(R.id.btnCancelar);
        btnEliminarExcepciones = rootview.findViewById(R.id.btnEliminarExcepciones);

        rvExcepciones = rootview.findViewById(R.id.rvExcepciones);

        cargarDatos(rootview);

        //onclick listeners
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnEliminarExcepciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setMessage("¿Esta seguro que quiere eliminar estas excepciones?")
                        .setTitle("Eliminacion de excepciones").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Obteniendo datos de la DB
                                conexion = new ConexionWebService();
                                try {
                                    //conexion.execute(url,parametros,cookie)
                                    String resultado = conexion.execute(Variables.url + "entrenos.php",
                                            "accion=eliminarExcepciones&idsDiaSinEntreno=" + obtenerStringIds(), Variables.cookie).get();

                                    //Toast.makeText(getContext(),obtenerStringIds(),Toast.LENGTH_LONG).show();

                                    jsonRespuesta = new JSONArray(resultado);
                                    jsonObjeto = jsonRespuesta.getJSONObject(0);

                                    if (jsonObjeto.has("error"))
                                        Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
                                    else {
                                        Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                                        dismiss();
                                    }
                                } catch (ExecutionException | InterruptedException | JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("cancelar", null).setCancelable(false);

                builder.create().show();
            }
        });

        return rootview;
    }

    private void cargarDatos(View rootview) {
        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "entrenos.php",
                    "accion=obtenerExcepciones", Variables.cookie).get();

            //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();


            jsonRespuesta = new JSONArray(resultado);

            jsonObjeto = jsonRespuesta.getJSONObject(0);

            int cantidadExcepciones = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay excepciones
            else {
                //LLenando el recycler on los datops de la DB
                //Se establece la forma de llenado como linear
                rvExcepciones.setLayoutManager(new LinearLayoutManager(getContext()));

                //Inicializacion de variables y al mismo tiempo limpiando
                arrayListExcepciones = new ArrayList<>();

                //se llena la lista con los datos
                for (int i = 0; i < cantidadExcepciones; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);
                    arrayListExcepciones.add(new String[]{jsonObjeto.getString("fecha"), jsonObjeto.getString("lugar")
                            , jsonObjeto.getString("idDiaSinEntreno")});
                }

                //se crea el adaptador y se manda la lista con los datos
                //borrar
                //final
                adaptador = new RecyclerViewAdapterExcepciones(getContext(), arrayListExcepciones, rootview);

                //se Añade el adaptador al recycler
                rvExcepciones.setAdapter(adaptador);
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private String obtenerStringIds() {
        LinkedList<String[]> linkedListSeleccionados = adaptador.obtenerSeleccionados();
        StringBuilder respuesta = new StringBuilder();

        for (int i = 0; i < linkedListSeleccionados.size(); i++) {
            if (i == linkedListSeleccionados.size() - 1)
                respuesta.append(linkedListSeleccionados.get(i)[2]);
            else
                respuesta.append(linkedListSeleccionados.get(i)[2]).append(",");
        }

        return respuesta.toString();
    }

}
