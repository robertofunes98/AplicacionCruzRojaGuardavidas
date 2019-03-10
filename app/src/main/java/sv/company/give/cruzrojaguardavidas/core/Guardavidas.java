package sv.company.give.cruzrojaguardavidas.core;


import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.R;

//Este SuppresLint se agrega para pode5r pedir datos
@SuppressLint("ValidFragment")
public class Guardavidas extends DialogFragment {

    String cookie = "";
    Button btnCancelar, btnConfirmar;
    ArrayList<String[]> listGuardavidas;
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta;
    RecyclerView rvGuardavidas;


    public static int itemSeleccionado;

    public Guardavidas(String cookieR) {
        // Required empty public constructor
        cookie = cookieR;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.dialog_fragment_guardavidas, container, false);


        //vinculando variables a los objetos corresponmdientes
        btnCancelar = rootview.findViewById(R.id.btnCancelar);
        btnConfirmar = rootview.findViewById(R.id.btnConfirmar);

        rvGuardavidas = rootview.findViewById(R.id.rvGuardavidas);


        //Inicializacion de variables
        listGuardavidas = new ArrayList<>();

        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "usuarios.php",
                    "accion=obtenerGuardavidas", cookie).get();

            //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();

            jsonRespuesta = new JSONArray(resultado);
            jsonObjeto = jsonRespuesta.getJSONObject(0);
            int cantidadAsistentes = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
            else {
                //LLenando el recycler on los datops de la DB
                //Se establece la forma de llenado como linear
                rvGuardavidas.setLayoutManager(new LinearLayoutManager(getContext()));

                //se llena la lista con los datos
                for (int i = 0; i < cantidadAsistentes; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);
                    listGuardavidas.add(new String[]{jsonObjeto.getString("nombres") + " "
                            + jsonObjeto.getString("apellidos"), jsonObjeto.getString("carnet")});
                }

                //se crea el adaptador y se manda la lista con los datos
                final RecyclerViewAdapterGuardavidas adaptador = new RecyclerViewAdapterGuardavidas(getContext(), listGuardavidas, rootview);

                //se Añade el adaptador al recycler
                rvGuardavidas.setAdapter(adaptador);
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //onclick listeners
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obteniendo datos de la DB
                conexion = new ConexionWebService();
                try {
                    //conexion.execute(url,parametros,cookie)
                    String resultado = conexion.execute(Variables.url + "asistencia.php",
                            "accion=agregarAsistencia&carnet=" + listGuardavidas.get(itemSeleccionado)[1] + "&idReunion=" + Asistencia.idReunion, cookie).get();

                    //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();

                    jsonRespuesta = new JSONArray(resultado);
                    jsonObjeto = jsonRespuesta.getJSONObject(0);

                    if (jsonObjeto.has("error"))
                        Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
                    else {
                        Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                        dismiss();
                        asistenteSelected.recargarRecycler();
                    }
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        return rootview;
    }


    public interface OnAsistenteSelected{
        void recargarRecycler();
    }
    public OnAsistenteSelected asistenteSelected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        asistenteSelected=(OnAsistenteSelected) getTargetFragment();
    }
}
