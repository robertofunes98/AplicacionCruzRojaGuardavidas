package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.Principal;
import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.Notificacion;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notificaciones extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie="";

    ConexionWebService conexion;
    JSONObject jsonObjeto=null;

    Button btnVerMas;

    public Notificaciones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        btnVerMas=rootView.findViewById(R.id.btnVerMas);

        conexion=new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado=conexion.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/obtenerNotificaciones.php",
                    "accion=obtenerContenidoNotificaciones&carnet="
                            +Principal.carnetGlobal,cookie).get();

            //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

            JSONArray jsonRespuesta= new JSONArray(resultado);

            int conteo=jsonRespuesta.length();

            for (int i=0;i<conteo;i++)
            {
                jsonObjeto= jsonRespuesta.getJSONObject(i);

                if(jsonObjeto.has("error"))
                    Toast.makeText(getContext(),jsonObjeto.getString("error"),Toast.LENGTH_LONG).show();
                else
                {
                    cargarFragment(new Notificacion(),jsonObjeto.getString("titulo"),jsonObjeto.getString("contenido")
                            ,R.drawable.ic_notification,jsonObjeto.getString("tipo"),jsonObjeto.getString("referencia")
                            ,jsonObjeto.getString("idNotificacion"));
                }
            }


        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    private void cargarFragment(Fragment fragmento, String titulo, String contenido, int icono, String tipo, String referencia, String idNotificacion)
    {
        // Creamos un nuevo Bundle
        Bundle args = new Bundle();

        // Colocamos el String
        args.putString("cookie", cookie);
        args.putString("titulo", titulo);
        args.putString("contenido", contenido);
        args.putInt("icono", icono);
        args.putString("tipo", tipo);
        args.putString("referencia", referencia);
        args.putString("idNotificacion", idNotificacion);

        //Colocamos este nuevo Bundle como argumento en el fragmento.

        fragmento.setArguments(args);

        FragmentManager manejador=getChildFragmentManager();
        manejador.beginTransaction().add(R.id.inclusionlayout,fragmento).commit();
    }




}
