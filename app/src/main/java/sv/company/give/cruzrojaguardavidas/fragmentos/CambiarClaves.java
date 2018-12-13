package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CambiarClaves extends Fragment {
    ConexionWebService conexion;
    String cookie="";
    Button btnCambiarClave;

    //contenedores de datos
    EditText etCarnet;
    JSONObject jsonObjeto=null;

    public CambiarClaves() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cambiar_claves, container, false);
        cookie = getArguments().getString("cookie");

        btnCambiarClave=rootView.findViewById(R.id.btnCambiarClave);

        etCarnet=rootView.findViewById(R.id.etCarnet);

        btnCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexion=new ConexionWebService();
                try {
                    String resultado=conexion.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/cambiarClave.php","accion=obtenerNombre&carnet="
                            +etCarnet.getText(),cookie).get();

                    //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

                    JSONArray jsonRespuesta= new JSONArray(resultado);

                    jsonObjeto= jsonRespuesta.getJSONObject(0);

                    if(jsonObjeto.has("error"))
                        Toast.makeText(getContext(),jsonObjeto.getString("error"),Toast.LENGTH_LONG).show();
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("¿Desea cambiar clave al usuario "+jsonObjeto.getString("nombres")+" "+jsonObjeto.getString("apellidos")+"?")
                                .setTitle("Confirmacion").setPositiveButton("Aceptar", null)
                                .setCancelable(false).setNegativeButton("Cancelar",null).create().show();
                    }

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
