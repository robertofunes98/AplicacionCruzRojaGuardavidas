package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
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

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notificaciones extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    String cookie="";

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


        cargarFragment(new Notificacion(),"Prueba","todoo va bien, de momento",R.drawable.ic_menu_send);

        return rootView;
    }

    private void cargarFragment(Fragment fragmento, String titulo, String contenido, int icono)
    {
        // Creamos un nuevo Bundle
        Bundle args = new Bundle();

        // Colocamos el String
        args.putString("cookie", cookie);
        args.putString("titulo", titulo);
        args.putString("contenido", contenido);
        args.putInt("icono", icono);

        //Colocamos este nuevo Bundle como argumento en el fragmento.

        fragmento.setArguments(args);

        FragmentManager manejador=getChildFragmentManager();
        manejador.beginTransaction().add(R.id.inclusionlayout,fragmento).commit();
    }




}
