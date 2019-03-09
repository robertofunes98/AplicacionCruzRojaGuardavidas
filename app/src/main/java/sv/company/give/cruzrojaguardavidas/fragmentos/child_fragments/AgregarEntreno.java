package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarEntreno extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie = "", diasPatron = "";
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;

    ConstraintLayout clPatron;

    Button btnGuardarPatron;
    EditText etHoraPatron, etLugarPatron;

    //CheckBoxs de dias
    CheckBox cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo, arrayCheckBoxDias[];

    public AgregarEntreno() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.child_fragment_agregar_entreno, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        clPatron = rootView.findViewById(R.id.clContenidoPatron);

        btnGuardarPatron = rootView.findViewById(R.id.btnGuardarPatron);


        etHoraPatron = rootView.findViewById(R.id.etHoraPatron);
        etLugarPatron = rootView.findViewById(R.id.etLugarPatron);

        //Inicialiacion de checkboxs
        cbLunes = rootView.findViewById(R.id.cbLunes);
        cbMartes = rootView.findViewById(R.id.cbMartes);
        cbMiercoles = rootView.findViewById(R.id.cbMiercoles);
        cbJueves = rootView.findViewById(R.id.cbJueves);
        cbViernes = rootView.findViewById(R.id.cbViernes);
        cbSabado = rootView.findViewById(R.id.cbSabado);
        cbDomingo = rootView.findViewById(R.id.cbDomingo);
        //El array se inicializa hasta aqui para que contenga objetos no nulos
        arrayCheckBoxDias = new CheckBox[]{cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo};

        //EditText modificados para obtener fecha
        etHoraPatron.setOnClickListener(new View.OnClickListener() {
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
                            etHoraPatron.setText(hourOfDay + ":" + minute + "0");
                        else
                            etHoraPatron.setText(hourOfDay + ":" + minute);
                    }
                }, hora, minutos, false);

                timePickerDialog.show();
            }
        });

        //botones
        btnGuardarPatron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diasPatron = "";
                for (int i = 0; i < arrayCheckBoxDias.length; i++) {
                    if (arrayCheckBoxDias[i].isChecked())
                        diasPatron += String.valueOf(i);
                }

                if (etHoraPatron.getText().toString().isEmpty() || etLugarPatron.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                else {
                    conexion = new ConexionWebService();
                    try {
                        //conexion.execute(url,parametros,cookie)
                        String resultado = conexion.execute(Variables.url + "entrenos.php",
                                "accion=guardarEntrenoPatron&hora=" + etHoraPatron.getText() + ":00" + "&lugar=" + etLugarPatron.getText()
                                        + "&diasPatron=" + diasPatron, cookie).get();

                        //Toast.makeText(getContext(),cookie,Toast.LENGTH_LONG).show();

                        JSONArray jsonRespuesta = new JSONArray(resultado);

                        jsonObjeto = jsonRespuesta.getJSONObject(0);

                        if (jsonObjeto.has("error"))
                            Toast.makeText(getContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getContext(), jsonObjeto.getString("resultado"), Toast.LENGTH_LONG).show();
                            etLugarPatron.setText("");
                            etHoraPatron.setText("");
                        }

                    } catch (ExecutionException | InterruptedException | JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return rootView;
    }

}
