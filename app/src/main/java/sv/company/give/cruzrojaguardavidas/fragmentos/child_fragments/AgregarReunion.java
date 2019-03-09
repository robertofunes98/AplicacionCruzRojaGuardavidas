package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.DatePickerFragment;
import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarReunion extends Fragment {

    //Variable que guardara la cookie en el fragment al recibirla
    String cookie = "";
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;

    Button btnGuardar;
    EditText etFecha, etHora, etLugar;

    public AgregarReunion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.child_fragment_agregar_reunion, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");


        btnGuardar = rootView.findViewById(R.id.btnGuardar);
        etFecha = rootView.findViewById(R.id.etFecha);
        etHora = rootView.findViewById(R.id.etHora);
        etLugar = rootView.findViewById(R.id.etLugar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion = new ConexionWebService();
                try {
                    //conexion.execute(url,parametros,cookie)
                    String resultado = conexion.execute(Variables.url + "reuniones.php",
                            "accion=guardarReunion&fecha=" + etFecha.getText() + "&hora=" + etHora.getText() + ":00" + "&lugar=" + etLugar.getText(), cookie).get();

                    //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

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

        etHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hora, minutos;
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute == 0)
                            etHora.setText(hourOfDay + ":" + minute + "0");
                        else
                            etHora.setText(hourOfDay + ":" + minute);
                    }
                }, hora, minutos, false);

                timePickerDialog.show();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


}
