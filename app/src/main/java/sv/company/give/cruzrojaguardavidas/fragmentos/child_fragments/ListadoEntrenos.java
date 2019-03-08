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
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapter;
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
                    String dias = patron_a_dias(jsonObjeto.getString("patron"));
                    //Convirtiendo la hora a formato 12 horas
                    String hora = formatearHora(jsonObjeto.getString("hora"));

                    listArraysEntrenos.add(new String[]{dias, hora, jsonObjeto.getString("lugar")});
                }

                //se crea el adaptador y se manda la lista con los datos
                final RecyclerViewAdapter adaptador = new RecyclerViewAdapter(getContext(), listArraysEntrenos, rootview);

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

    private String formatearHora(String horaR) {

        String[] horaSeparada = horaR.split(":");
        String tipoHora, horaFormateada = "";

        int hora = Integer.parseInt(horaSeparada[0]);
        String minutos = horaSeparada[1];

        if (hora == 0) {
            horaFormateada += "12";
            tipoHora = "AM";
        } else if (hora > 12) {
            horaFormateada += String.valueOf(hora - 12);
            tipoHora = "PM";
        } else {
            horaFormateada = String.valueOf(hora);
            tipoHora = "AM";
        }

        return horaFormateada + ":" + minutos + " " + tipoHora;
    }

    private String patron_a_dias(String patron) {
        StringBuilder dias = new StringBuilder();
        for (int i = 0; i < patron.length(); i++) {
            if (dias.length() > 0)
                dias.append(", ");
            switch (patron.charAt(i)) {
                case '0':
                    dias.append("Lunes");
                    break;
                case '1':
                    dias.append("Martes");
                    break;
                case '2':
                    dias.append("Miercoles");
                    break;
                case '3':
                    dias.append("Jueves");
                    break;
                case '4':
                    dias.append("Viernes");
                    break;
                case '5':
                    dias.append("Sabado");
                    break;
                case '6':
                    dias.append("Domingo");
                    break;
            }
        }
        return dias.toString() + ".";
    }


}
