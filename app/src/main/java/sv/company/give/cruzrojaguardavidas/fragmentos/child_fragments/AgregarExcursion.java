package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.DatePickerFragment;
import sv.company.give.cruzrojaguardavidas.core.Variables;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarExcursion extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie = "";
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;

    EditText etFecha, etFechaFin, etHoraSalida, etLugarExcursion, etLugarLlegadaGv, etExtraordinaria, etEncargadoExcursion, etTelefonoEncargado;
    CheckBox cbDiaMultiple, cbExtraordinaria;
    TextView tvFechaFin, tvExtraordinaria;

    Button btnGuardarExcursion;

    Calendar fecha, fechaFin;


    Spinner spEstadoExcursion;

    public AgregarExcursion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.child_fragment_agregar_excursion, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        btnGuardarExcursion = rootView.findViewById(R.id.btnGuardarExcursion);

        etFecha= rootView.findViewById(R.id.etFecha);
        etFechaFin = rootView.findViewById(R.id.etFechaFin);
        etHoraSalida = rootView.findViewById(R.id.etHoraSalida);
        etLugarExcursion = rootView.findViewById(R.id.etLugarExcursion);
        etLugarLlegadaGv = rootView.findViewById(R.id.etLugarLlegadaGv);
        etExtraordinaria=rootView.findViewById(R.id.etExtraordinaria);
        etEncargadoExcursion=rootView.findViewById(R.id.etEncargadoExcursion);
        etTelefonoEncargado=rootView.findViewById(R.id.etTelefonoEncargado);
        tvFechaFin=rootView.findViewById(R.id.tvFechaFin);
        tvExtraordinaria=rootView.findViewById(R.id.tvExtraordinaria);

        //Inicialiacion de checkboxs
        cbDiaMultiple = rootView.findViewById(R.id.cbDiaMultiple);
        cbExtraordinaria=rootView.findViewById(R.id.cbExtraordinaria);

        //Inicializacion de sppiners
        spEstadoExcursion=rootView.findViewById(R.id.spEstadoExcursion);


        //edittext modificados para fecha
        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = year + "-" + (month + 1) + "-" + day;
                        etFecha.setText(selectedDate);
                        fecha=new GregorianCalendar(year,month,day);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        etFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = year + "-" + (month + 1) + "-" + day;
                        etFechaFin.setText(selectedDate);
                        fechaFin=new GregorianCalendar(year,month,day);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        //edittext modificados para obtener hora
        etHoraSalida.setOnClickListener(new View.OnClickListener() {
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
                            etHoraSalida.setText(hourOfDay + ":" + minute + "0");
                        else if(minute<10)
                            etHoraSalida.setText(hourOfDay + ":0" + minute);
                        else
                            etHoraSalida.setText(hourOfDay + ":" + minute);
                    }
                }, hora, minutos, false);
                timePickerDialog.show();
            }
        });

        //comboxes
        cbDiaMultiple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    etFechaFin.setVisibility(View.VISIBLE);
                    tvFechaFin.setVisibility(View.VISIBLE);
                } else {
                    etFechaFin.setVisibility(View.GONE);
                    tvFechaFin.setVisibility(View.GONE);
                }
            }
        });

        cbExtraordinaria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    etExtraordinaria.setVisibility(View.VISIBLE);
                    tvExtraordinaria.setVisibility(View.VISIBLE);
                } else {
                    etExtraordinaria.setVisibility(View.GONE);
                    tvExtraordinaria.setVisibility(View.GONE);
                }
            }
        });

        //botones
        btnGuardarExcursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etFecha.getText().toString().isEmpty() || (cbDiaMultiple.isChecked() && etFechaFin.getText().toString().isEmpty()) ||
                        (cbDiaMultiple.isChecked() && etExtraordinaria.getText().toString().isEmpty()) || etHoraSalida.getText().toString().isEmpty()
                        || etLugarExcursion.getText().toString().isEmpty() || etLugarLlegadaGv.getText().toString().isEmpty()
                        || etEncargadoExcursion.getText().toString().isEmpty() || etTelefonoEncargado.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                else
                    guardarExcursion(cbDiaMultiple.isChecked(), cbExtraordinaria.isChecked());
            }
        });
        return rootView;
    }

    public void guardarExcursion(boolean diaMultiple, boolean extraordinaria)
    {
        conexion = new ConexionWebService();
        String datos="";
        try {
            if(diaMultiple && extraordinaria) {
                Date d1=fecha.getTime();
                Date d2=fechaFin.getTime();
                long diff = d2.getTime() - d1.getTime();
                int cantidadDias=(int) TimeUnit.MILLISECONDS.toDays(diff);
                datos="accion=guardarExcursion&tipo=0&cantidadDias="+(cantidadDias+1)+"&lugarExcursion="+etLugarExcursion.getText()+"&fechaInicio="+etFecha.getText()
                        +"&fechaFin="+etFechaFin.getText()+ "&horaSalida=" + etHoraSalida.getText()+"&motivoExtraordinario="+etExtraordinaria.getText()
                        +"&lugarLLegadaGuardavidas="+etLugarLlegadaGv.getText()+"&encargadoExcursion="+etEncargadoExcursion.getText()+"&telefonoEncargado="
                        +etTelefonoEncargado.getText()+"&estado="+spEstadoExcursion.getSelectedItem().toString();
            } else if(diaMultiple){
                Date d1=fecha.getTime();
                Date d2=fechaFin.getTime();
                long diff = d2.getTime() - d1.getTime();
                int cantidadDias=(int) TimeUnit.MILLISECONDS.toDays(diff);
                datos="accion=guardarExcursion&tipo=1&diaMultiple=1&cantidadDias="+(cantidadDias+1)+"&lugarExcursion="+etLugarExcursion.getText()+"&fechaInicio="
                        +etFecha.getText()+"&fechaFin="+etFechaFin.getText()+ "&horaSalida=" + etHoraSalida.getText()+"&lugarLLegadaGuardavidas="
                        +etLugarLlegadaGv.getText()+"&encargadoExcursion="+etEncargadoExcursion.getText()+"&telefonoEncargado="
                        +etTelefonoEncargado.getText()+"&estado="+spEstadoExcursion.getSelectedItem().toString();
            } else if(extraordinaria){
                datos="accion=guardarExcursion&tipo=2&diaMultiple=0&lugarExcursion="+etLugarExcursion.getText()+"&fechaInicio="
                        +etFecha.getText()+"&horaSalida=" + etHoraSalida.getText()+"&extraordinaria=1"+"&motivoExtraordinario="+etExtraordinaria.getText()
                        +"&lugarLLegadaGuardavidas="+etLugarLlegadaGv.getText()+"&encargadoExcursion="+etEncargadoExcursion.getText()+"&telefonoEncargado="
                        +etTelefonoEncargado.getText()+"&estado="+spEstadoExcursion.getSelectedItem().toString();
            }
            else{
                datos="accion=guardarExcursion&tipo=3&diaMultiple=0&lugarExcursion="+etLugarExcursion.getText()+"&fechaInicio="
                        +etFecha.getText()+"&horaSalida=" + etHoraSalida.getText()+"&extraordinaria=0"
                        +"&lugarLLegadaGuardavidas="+etLugarLlegadaGv.getText()+"&encargadoExcursion="+etEncargadoExcursion.getText()+"&telefonoEncargado="
                        +etTelefonoEncargado.getText()+"&estado="+spEstadoExcursion.getSelectedItem().toString();
            }

            //conexion.execute(url,parametros,cookie)
            String resultado= conexion.execute(Variables.url + "excursiones.php", datos, cookie).get();

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

}
