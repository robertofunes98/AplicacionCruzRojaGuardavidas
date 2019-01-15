package sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import sv.company.give.cruzrojaguardavidas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarEntreno extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    static String cookie="";

    Spinner spPatron;
    RelativeLayout rlPatron, rlDiaUnico;

    public AgregarEntreno() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_agregar_entreno, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");

        spPatron=rootView.findViewById(R.id.spPatron);
        rlDiaUnico=rootView.findViewById(R.id.rlContenidoDiaUnico);
        rlPatron=rootView.findViewById(R.id.rlContenidoPatron);

        spPatron.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    rlDiaUnico.setVisibility(View.VISIBLE);
                    rlPatron.setVisibility(View.GONE);
                }
                else if(position==1)
                {
                    rlDiaUnico.setVisibility(View.GONE);
                    rlPatron.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

}
