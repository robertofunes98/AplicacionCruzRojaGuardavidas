package sv.company.give.cruzrojaguardavidas.core;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.R;

//Este SuppresLint se agrega para pode5r pedir datos
@SuppressLint("ValidFragment")
public class Guardavidas extends DialogFragment {

    String cookie = "";
    String[] excursion;
    Button btnCancelar, btnConfirmar, btnCambiarVista, btnAsignarForzadamente;
    ArrayList<String[]> listGuardavidas;
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta;
    RecyclerView rvGuardavidas;
    int tipoDialogo;
    boolean mostrarTodo = false;
    public final static int SELECCION_ASISTENCIA = 1, ASIGNAR_GUARDAVIDAS = 2;

    public static int itemSeleccionado;


    RecyclerViewAdapterGuardavidas adaptador = null;

    public Guardavidas(String cookieR, int tipoDialogoR, String[] excursionR, boolean mostrar) {
        // Required empty public constructor
        cookie = cookieR;
        tipoDialogo = tipoDialogoR;
        excursion = excursionR;
        mostrarTodo = mostrar;
    }

    public Guardavidas(String cookieR, int tipoDialogoR, String[] excursionR) {
        // Required empty public constructor
        cookie = cookieR;
        tipoDialogo = tipoDialogoR;
        excursion = excursionR;
        mostrarTodo = true;
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.dialog_fragment_guardavidas, container, false);


        //vinculando variables a los objetos corresponmdientes
        btnCancelar = rootview.findViewById(R.id.btnCancelar);
        btnConfirmar = rootview.findViewById(R.id.btnConfirmar);
        btnCambiarVista = rootview.findViewById(R.id.btnCambiarVista);
        btnAsignarForzadamente = rootview.findViewById(R.id.btnAsignarForzadamente);

        rvGuardavidas = rootview.findViewById(R.id.rvGuardavidas);


        cargarDatos(rootview);

        if (tipoDialogo == SELECCION_ASISTENCIA)
            btnAsignarForzadamente.setVisibility(View.INVISIBLE);
        else if (tipoDialogo == ASIGNAR_GUARDAVIDAS)
            btnAsignarForzadamente.setVisibility(View.VISIBLE);


        //onclick listeners
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnCambiarVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mostrarTodo) {
                    mostrarTodo = false;
                    btnCambiarVista.setText("Mostrar todos los guardavidas");
                    cargarDatos(rootview);
                } else {
                    mostrarTodo = true;
                    btnCambiarVista.setText("Mostrar guardavidas filtrados");
                    cargarDatos(rootview);
                }
            }
        });

        btnAsignarForzadamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cantidadSeleccioados = adaptador.cantidadSeleccionados();

                if (cantidadSeleccioados > 1)
                    Toast.makeText(getContext(), "Solo puede seleccionar 1 guardavidas a la vez para esta funcion", Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setMessage("¿Esta seguro que quiere asignar forzadamente a este guardavidas?")
                            .setTitle("Asignacion forzada").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Obteniendo datos de la DB
                                    conexion = new ConexionWebService();
                                    try {
                                        String[] fecha = Funciones.separarFechaHora(excursion[1] + " " + excursion[2]);

                                        //conexion.execute(url,parametros,cookie)
                                        String resultado = conexion.execute(Variables.url + "excursiones.php",
                                                "accion=asignarGuardavidasForzadamente&carnet=" + obtenerStringCarnets()+"&idExcursion="+excursion[9], cookie).get();

                                        //Toast.makeText(getContext(),obtenerStringCarnets(),Toast.LENGTH_LONG).show();

                                        jsonRespuesta = new JSONArray(resultado);
                                        jsonObjeto = jsonRespuesta.getJSONObject(0);

                                        if (jsonObjeto.has("error"))
                                            Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
                                        else {
                                            Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                                            dismiss();
                                        }
                                    } catch (ExecutionException | InterruptedException | JSONException | ParseException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).setNegativeButton("cancelar", null).setCancelable(false);

                    builder.create().show();
                }

            }
        });

        //Diferenciando los tipos de dialogos

        if (tipoDialogo == SELECCION_ASISTENCIA) {
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
        } else if (tipoDialogo == ASIGNAR_GUARDAVIDAS) {
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Obteniendo datos de la DB
                    conexion = new ConexionWebService();
                    try {
                        String[] fecha = Funciones.separarFechaHora(excursion[1] + " " + excursion[2]);

                        //conexion.execute(url,parametros,cookie)
                        String resultado = conexion.execute(Variables.url + "excursiones.php",
                                "accion=asignarGuardavidas&carnets=" + obtenerStringCarnets() + "&lugarExcursion=" + excursion[0] + "&fechaHora=" + fecha[0] + " a las " + fecha[1]
                                        + "&lugarLlegada=" + excursion[13]+"&referencia="+excursion[9], cookie).get();

                        //Toast.makeText(getContext(),obtenerStringCarnets(),Toast.LENGTH_LONG).show();

                        jsonRespuesta = new JSONArray(resultado);
                        jsonObjeto = jsonRespuesta.getJSONObject(0);

                        if (jsonObjeto.has("error"))
                            Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
                        else {
                            Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                            dismiss();
                        }
                    } catch (ExecutionException | InterruptedException | JSONException | ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });
        }


        return rootview;
    }

    private void cargarDatos(View rootview) {
        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            String resultado;

            if (tipoDialogo == ASIGNAR_GUARDAVIDAS && !mostrarTodo) {

                //si es asignacion se dee buscar la disponibilidad
                //conexion.execute(url,parametros,cookie)
                resultado = conexion.execute(Variables.url + "usuarios.php",
                        "accion=obtenerGuardavidasFiltrados&fecha=" + excursion[1], cookie).get();
            } else {
                //si no pues se obtienen todos los guardavidas
                //conexion.execute(url,parametros,cookie)
                resultado = conexion.execute(Variables.url + "usuarios.php",
                        "accion=obtenerGuardavidas", cookie).get();
            }


            //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

            jsonRespuesta = new JSONArray(resultado);
            jsonObjeto = jsonRespuesta.getJSONObject(0);
            int cantidadAsistentes = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay guardavidas
            else {
                //LLenando el recycler on los datops de la DB
                //Se establece la forma de llenado como linear
                rvGuardavidas.setLayoutManager(new LinearLayoutManager(getContext()));

                //Inicializacion de variables y al mismo tiempo limpiando
                listGuardavidas = new ArrayList<>();

                //se llena la lista con los datos
                for (int i = 0; i < cantidadAsistentes; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);
                    listGuardavidas.add(new String[]{jsonObjeto.getString("nombres") + " "
                            + jsonObjeto.getString("apellidos"), jsonObjeto.getString("carnet")});
                }

                //se crea el adaptador y se manda la lista con los datos
                //borrar
                //final
                adaptador = new RecyclerViewAdapterGuardavidas(getContext(), listGuardavidas, rootview);


                //se Añade el adaptador al recycler
                rvGuardavidas.setAdapter(adaptador);
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    private String obtenerStringCarnets() {
        LinkedList<String[]> linkedListSeleccionados = adaptador.obtenerSeleccionados();
        StringBuilder respuesta = new StringBuilder();

        for (int i = 0; i < linkedListSeleccionados.size(); i++) {
            if (i == linkedListSeleccionados.size() - 1)
                respuesta.append(linkedListSeleccionados.get(i)[1]);
            else
                respuesta.append(linkedListSeleccionados.get(i)[1]).append(",");
        }

        return respuesta.toString();
    }


    public interface OnAsistenteSelected {
        void recargarRecycler();
    }

    public OnAsistenteSelected asistenteSelected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        asistenteSelected = (OnAsistenteSelected) getTargetFragment();
    }
}
