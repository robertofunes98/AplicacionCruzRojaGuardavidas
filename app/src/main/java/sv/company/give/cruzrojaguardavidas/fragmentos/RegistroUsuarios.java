package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.DatePickerFragment;
import sv.company.give.cruzrojaguardavidas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroUsuarios extends Fragment {
    Button btnRegistrar;
    ConexionWebService conexion;

    //contenedores de datos a guardar en DB
    EditText etCarnet,etNombres,etApellidos,etClave,etCorreo,etTelefono,etFechaNacimiento,etCargo;
    Spinner spRango,spSexo,spPermisos;

    public RegistroUsuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registro_usuarios, container, false);

        btnRegistrar=rootView.findViewById(R.id.btnRegistrar);

        //contenedores de datos a guardar en DB
        etCarnet=(rootView.findViewById(R.id.etCarnet));
        etNombres=(rootView.findViewById(R.id.etNombres));
        etApellidos=(rootView.findViewById(R.id.etApellidos));
        etClave=(rootView.findViewById(R.id.etClave));
        etCorreo=(rootView.findViewById(R.id.etCorreo));
        etTelefono=(rootView.findViewById(R.id.etTelefono));
        etFechaNacimiento=(rootView.findViewById(R.id.etFechaNacimiento));
        etCargo=(rootView.findViewById(R.id.etCargo));
        spRango=(rootView.findViewById(R.id.spRango));
        spSexo=(rootView.findViewById(R.id.spSexo));
        spPermisos=(rootView.findViewById(R.id.spPermisos));


        etFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = year + "-" + (month+1) + "-" + day;
                        etFechaNacimiento.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObjeto=null;
                conexion=new ConexionWebService();
                try {
                    String resultado=conexion.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/registroUsuarios.php","carnet="+etCarnet.getText()+"&nombres="+etNombres.getText()
                            +"&apellidos="+etApellidos.getText()+"&clave="+etClave.getText()+"&correo="+etCorreo.getText()
                            +"&telefono="+etTelefono.getText()+"&fechaNacimiento="+etFechaNacimiento.getText()
                            +"&cargo="+etCargo.getText()+"&rango="+spRango.getSelectedItem().toString()
                            +"&sexo="+spSexo.getSelectedItem().toString()
                            +"&permisos="+Long.toString((spPermisos.getSelectedItemId()+1))).get();

                    JSONArray jsonRespuesta= new JSONArray(resultado);

                    jsonObjeto= jsonRespuesta.getJSONObject(0);

                    if(jsonObjeto.getString("resultado").equals("true"))
                        Toast.makeText(getContext(),"Usuario registrado",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(),"Fallo al registrar usuario",Toast.LENGTH_LONG).show();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        });
        return rootView;
    }


}
