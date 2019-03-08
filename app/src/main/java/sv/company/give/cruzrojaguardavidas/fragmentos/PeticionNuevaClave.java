package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.Variables;

import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeticionNuevaClave extends Fragment {

    Spinner spTipoRecuperacion;
    RelativeLayout rlDatosRecuperacion;
    TextView tvTitulo;
    EditText etDatos;
    Button btnConfirmar;

    int tipo;
    ConexionWebService conexion;
    JSONObject jsonObjeto=null;
    String cookie;

    public PeticionNuevaClave() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_peticion_nueva_clave, container, false);

        cookie=getArguments().getString("cookie");

        spTipoRecuperacion=rootview.findViewById(R.id.spTipoRecuperacion);
        rlDatosRecuperacion=rootview.findViewById(R.id.rlDatosRecuperacion);
        tvTitulo=rootview.findViewById(R.id.tvTitulo);
        etDatos=rootview.findViewById(R.id.etInformacionRecuperacion);
        rlDatosRecuperacion.setVisibility(View.GONE);
        btnConfirmar=rootview.findViewById(R.id.btnConfirmar);

        spTipoRecuperacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    rlDatosRecuperacion.setVisibility(View.GONE);
                else if(position==1)
                {
                    rlDatosRecuperacion.setVisibility(View.VISIBLE);
                    tvTitulo.setText("Correo de recuperacion");
                    etDatos.setText("");
                    etDatos.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    tipo=1;
                }
                else if(position==2)
                {
                    rlDatosRecuperacion.setVisibility(View.VISIBLE);
                    tvTitulo.setText("Carnet");
                    etDatos.setText("");
                    etDatos.setInputType(InputType.TYPE_CLASS_TEXT);
                    tipo=2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo==1)
                {
                    conexion=new ConexionWebService();
                    try {
                        String claveNueva=generarClaveAleatoria();
                        //conexion.execute(url,parametros,cookie)
                        String resultado=conexion.execute(Variables.url+"peticionNuevaClave.php",
                                "accion=enviarCorreo&correoReceptor="+etDatos.getText()+"&asunto="
                                        +"Recuperacion de clave"+"&nuevaClave="+claveNueva+"&mensaje="+"Se ha restablecido su clave, su nueva clave es: "+claveNueva,cookie).get();

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
                else if(tipo==2)
                {
                    conexion=new ConexionWebService();
                    try {
                        //conexion.execute(url,parametros,cookie)
                        String resultado=conexion.execute(Variables.url+"peticionNuevaClave.php",
                                "accion=crearNotificacion&carnet="+etDatos.getText(),cookie).get();

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
            }
        });

        return rootview;
    }

    public static String generarClaveAleatoria() {
        SecureRandom random = new SecureRandom();
        String text = new BigInteger(130, random).toString(8);
        return text;
    }

}
