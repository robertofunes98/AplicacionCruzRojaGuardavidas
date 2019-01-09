package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.Principal;
import sv.company.give.cruzrojaguardavidas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioSesion extends Fragment {
    Button btnInicio;
    ConexionWebService conexion;
    String cadena1 = new String("");
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie="";

    //contenedores de datos a guardar en DB
    EditText VetCarnet,etNombres,etApellidos,VetClave,etCorreo,etTelefono,etFechaNacimiento,etCargo;
    Spinner spRango,spSexo,spPermisos;

    public InicioSesion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        btnInicio=rootView.findViewById(R.id.btnInicio);

        //contenedores de datos a guardar en DB
        VetCarnet=(rootView.findViewById(R.id.etCarnet));
        VetClave=(rootView.findViewById(R.id.etClave));

        //etCorreo=(rootView.findViewById(R.id.etCorreo));


        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObjeto=null;
                conexion=new ConexionWebService();
                try {
                    //conexion.execute(url,parametros,cookie)
                    String resultado=conexion.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/inicioSesion.php","carnet="+VetCarnet.getText()+"&clave="+VetClave.getText(),cookie).get();

                    //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

                    JSONArray jsonRespuesta= new JSONArray(resultado);

                    jsonObjeto= jsonRespuesta.getJSONObject(0);

                    if(((VetCarnet.getText().toString()).equals(jsonObjeto.getString("carnet")))&&
                            ((VetClave.getText().toString()).equals(jsonObjeto.getString("clave"))))
                    {
                        Toast.makeText(getContext(),"Usuario Encontrado y rango del usuario es "+jsonObjeto.getString("tipoUsuario"),Toast.LENGTH_LONG).show();
                        Principal.carnetGlobal=jsonObjeto.getString("carnet");
                        Principal.tipoUsuario=Integer.parseInt(jsonObjeto.getString("tipoUsuario"));
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Usuario o Contraseña incorrecto",Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }


}
