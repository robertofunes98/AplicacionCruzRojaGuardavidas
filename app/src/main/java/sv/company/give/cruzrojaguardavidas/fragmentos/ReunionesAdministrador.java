package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import sv.company.give.cruzrojaguardavidas.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReunionesAdministrador extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie="";

    ConexionWebService conexion;
    JSONObject jsonObjeto=null;

    Button btnGuardar;

    public ReunionesAdministrador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reuniones_administrador, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        btnGuardar=rootView.findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return  rootView;
    }

}
