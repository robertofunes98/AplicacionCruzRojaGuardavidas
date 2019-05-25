package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.DatePickerFragment;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaSinEntreno extends Fragment {
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;


    Button btnGuardarExcepcion;
    EditText etFecha;
Spinner spEntrenos;
    private JSONArray jsonRespuesta;

    public DiaSinEntreno() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.child_fragment_dia_sin_entreno, container, false);

        etFecha=rootView.findViewById(R.id.etFecha);

        btnGuardarExcepcion=rootView.findViewById(R.id.btnGuardarExcepcion);

        spEntrenos=rootView.findViewById(R.id.spEntrenos);

        //EditText modificados para obtener fecha
        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = year + "-" + (month + 1) + "-" + day;
                        etFecha.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        //botones
        btnGuardarExcepcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etFecha.getText().toString().isEmpty() || spEntrenos.getSelectedItemPosition()==0)
                    Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                else {
                    conexion = new ConexionWebService();
                    try {
                        jsonObjeto = jsonRespuesta.getJSONObject(spEntrenos.getSelectedItemPosition()-1);
                        //conexion.execute(url,parametros,cookie)
                        String resultado = conexion.execute(Variables.url + "entrenos.php",
                                "accion=guardarExcepcion&fecha=" + etFecha.getText() + "&idEntreno=" + jsonObjeto.getString("idEntreno"), Variables.cookie).get();

                        //Toast.makeText(getContext(),cookie,Toast.LENGTH_LONG).show();

                        JSONArray jsonRespuesta = new JSONArray(resultado);

                        jsonObjeto = jsonRespuesta.getJSONObject(0);

                        if (jsonObjeto.has("error"))
                            Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                            etFecha.setText("");
                        }

                    } catch (ExecutionException | InterruptedException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        obtenerLugares();

        return rootView;
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

            jsonObjeto = jsonRespuesta.getJSONObject(0);

            int cantidadLugares = jsonRespuesta.length();

            if (jsonObjeto.has("error"))
                Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay entrenos
            else {
                ArrayList<String> datos = new ArrayList<>();

                datos.add("Seleccione un entreno");

                for (int i = 0; i < cantidadLugares; i++) {
                    jsonObjeto = jsonRespuesta.getJSONObject(i);

                    datos.add(jsonObjeto.getString("lugar"));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, datos);

                spEntrenos.setAdapter(adapter);
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
