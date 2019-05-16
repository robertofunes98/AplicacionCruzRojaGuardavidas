package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.CalendarioPersonalizadoDialog;
import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.Funciones;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MiDisponibilidad extends Fragment implements CalendarioPersonalizadoDialog.OnDiasSelected {
    TextView tvMesAnyo;
    ArrayList<TextView> listTexctViews;

    //Dias
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18,
            tv19, tv20, tv21, tv22, tv23, tv24, tv25, tv26, tv27, tv28, tv29, tv30, tv31, tv32, tv33, tv34, tv35, tv36, tv37, tv38, tv39, tv40, tv41, tv42;

    String cookie = "";
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta;
    ImageButton ibFlechaIzquierda, ibFlechaDerecha;
    Button btnEditarDisponibilidad;

    int mes = 1;

    CalendarioPersonalizadoDialog objCalendarioPersonalizado;

    public MiDisponibilidad(String cookieR) {
        // Required empty public constructor
        cookie = cookieR;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_mi_disponibilidad, container, false);

        //inicializando variables
        listTexctViews = new ArrayList<>();

        //Vinculando tvs
        tvMesAnyo = rootview.findViewById(R.id.tvMesAnyo);

        tv1 = rootview.findViewById(R.id.tv1);
        tv2 = rootview.findViewById(R.id.tv2);
        tv3 = rootview.findViewById(R.id.tv3);
        tv4 = rootview.findViewById(R.id.tv4);
        tv5 = rootview.findViewById(R.id.tv5);
        tv6 = rootview.findViewById(R.id.tv6);
        tv7 = rootview.findViewById(R.id.tv7);
        tv8 = rootview.findViewById(R.id.tv8);
        tv9 = rootview.findViewById(R.id.tv9);
        tv10 = rootview.findViewById(R.id.tv10);
        tv11 = rootview.findViewById(R.id.tv11);
        tv12 = rootview.findViewById(R.id.tv12);
        tv13 = rootview.findViewById(R.id.tv13);
        tv14 = rootview.findViewById(R.id.tv14);
        tv15 = rootview.findViewById(R.id.tv15);
        tv16 = rootview.findViewById(R.id.tv16);
        tv17 = rootview.findViewById(R.id.tv17);
        tv18 = rootview.findViewById(R.id.tv18);
        tv19 = rootview.findViewById(R.id.tv19);
        tv20 = rootview.findViewById(R.id.tv20);
        tv21 = rootview.findViewById(R.id.tv21);
        tv22 = rootview.findViewById(R.id.tv22);
        tv23 = rootview.findViewById(R.id.tv23);
        tv24 = rootview.findViewById(R.id.tv24);
        tv25 = rootview.findViewById(R.id.tv25);
        tv26 = rootview.findViewById(R.id.tv26);
        tv27 = rootview.findViewById(R.id.tv27);
        tv28 = rootview.findViewById(R.id.tv28);
        tv29 = rootview.findViewById(R.id.tv29);
        tv30 = rootview.findViewById(R.id.tv30);
        tv31 = rootview.findViewById(R.id.tv31);
        tv32 = rootview.findViewById(R.id.tv32);
        tv33 = rootview.findViewById(R.id.tv33);
        tv34 = rootview.findViewById(R.id.tv34);
        tv35 = rootview.findViewById(R.id.tv35);
        tv36 = rootview.findViewById(R.id.tv36);
        tv37 = rootview.findViewById(R.id.tv37);
        tv38 = rootview.findViewById(R.id.tv38);
        tv39 = rootview.findViewById(R.id.tv39);
        tv40 = rootview.findViewById(R.id.tv40);
        tv41 = rootview.findViewById(R.id.tv41);
        tv42 = rootview.findViewById(R.id.tv42);

        //vinculando botnes
        ibFlechaIzquierda = rootview.findViewById(R.id.ibFlechaIzquierda);
        ibFlechaDerecha = rootview.findViewById(R.id.ibFlechaDerecha);
        btnEditarDisponibilidad = rootview.findViewById(R.id.btnEditarDisponibilidad);

        //borrar
        tvRespuestaPrueba=rootview.findViewById(R.id.tvRespuestPrueba);

        //llenando arraylist

        Collections.addAll(listTexctViews, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18,
                tv19, tv20, tv21, tv22, tv23, tv24, tv25, tv26, tv27, tv28, tv29, tv30, tv31, tv32, tv33, tv34, tv35, tv36, tv37, tv38, tv39, tv40, tv41, tv42);

        jsonRespuesta = obtenerDatos();

        try {
            llenarCalendario(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ibFlechaIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    llenarCalendario(0);
                    ibFlechaIzquierda.setVisibility(View.INVISIBLE);
                    ibFlechaDerecha.setVisibility(View.VISIBLE);
                    mes = 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        ibFlechaDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    llenarCalendario(1);
                    ibFlechaDerecha.setVisibility(View.INVISIBLE);
                    ibFlechaIzquierda.setVisibility(View.VISIBLE);
                    mes = 2;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnEditarDisponibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objCalendarioPersonalizado = new CalendarioPersonalizadoDialog(CalendarioPersonalizadoDialog.EDITAR, mes);
                objCalendarioPersonalizado.setTargetFragment(MiDisponibilidad.this, 1);
                objCalendarioPersonalizado.show(getFragmentManager(), "Seleccion de disponibilidad");


            }
        });

        return rootview;
    }

    private JSONArray obtenerDatos() {
        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "disponibilidad.php",
                    "accion=obtenerDisponibilidadGuardavidas&carnet=" + Variables.carnetGlobal, cookie).get();

            //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

            JSONArray jsonRespuestaAux = new JSONArray(resultado);
            JSONObject jsonObjetoAux = jsonRespuestaAux.getJSONObject(0);

            if (jsonObjetoAux.has("error")) {
                Toast.makeText(getContext(), jsonObjetoAux.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay disponibilidad
                return null;
            } else {
                return jsonRespuestaAux;
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void llenarCalendario(int posicion) throws JSONException {

        jsonObjeto = jsonRespuesta.getJSONObject(posicion);

        Calendar calendarioFecha = Calendar.getInstance();

        calendarioFecha.set(Calendar.MONTH, (Integer.parseInt(jsonObjeto.getString("mes")) - 1));

        tvMesAnyo.setText(Funciones.ucFirst(String.valueOf(calendarioFecha.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("es-ES"))))
                + " - " + calendarioFecha.get(Calendar.YEAR));


        Calendar auxFecha = new GregorianCalendar(calendarioFecha.get(Calendar.YEAR), calendarioFecha.get(Calendar.MONTH), 1);

        int j = auxFecha.get(Calendar.DAY_OF_WEEK) - 1, contadorDias = 0;

        for (int i = 0; i < listTexctViews.size(); i++) {
            if (i < j || contadorDias >= (auxFecha.getActualMaximum(Calendar.DAY_OF_MONTH))) {
                listTexctViews.get(i).setVisibility(View.INVISIBLE);
            } else {
                listTexctViews.get(i).setVisibility(View.VISIBLE);
                listTexctViews.get(i).setText(String.valueOf(contadorDias + 1));
                j++;
                contadorDias++;
            }
            listTexctViews.get(i).setBackground(null);
            listTexctViews.get(i).setTextColor(Color.BLACK);
        }

        if(jsonObjeto.getString("dias").isEmpty())
            Toast.makeText(getContext(),"No hay disponibilidad para este mes",Toast.LENGTH_LONG).show();
        else
        {
            String[] diasDisponibles = jsonObjeto.getString("dias").split(",");
            for (String aux : diasDisponibles) {
                int i = Integer.parseInt(aux) + auxFecha.get(Calendar.DAY_OF_WEEK) - 2;
                listTexctViews.get(i).setBackground(getResources().getDrawable(R.drawable.seleccion_dia));
                listTexctViews.get(i).setTextColor(Color.WHITE);
            }
        }
    }

    //borrar
    TextView tvRespuestaPrueba;

    @Override
    public void guardarDisponibilidadEditada(int mes, String dias) {
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "disponibilidad.php",
                    "accion=editarDisponibilidad&carnet=" + Variables.carnetGlobal + "&mes=" + mes + "&dias=" + dias, cookie).get();


            JSONArray jsonRespuestaAux = new JSONArray(resultado);
            JSONObject jsonObjetoAux = jsonRespuestaAux.getJSONObject(0);

            if (jsonObjetoAux.has("error")) {
                Toast.makeText(getContext(), jsonObjetoAux.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay disponibilidad
            } else {
                Toast.makeText(getContext(), jsonObjetoAux.getString("resultado"), Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
