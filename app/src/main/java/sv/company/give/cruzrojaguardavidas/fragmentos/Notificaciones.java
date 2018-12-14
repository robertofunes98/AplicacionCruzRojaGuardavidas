package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

    public void MostrarNotificacion()
    {
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr =(NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        int icono = R.mipmap.ic_launcher;
        Intent i=new Intent(getContext(), Principal.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, i, 0);

        mBuilder =new NotificationCompat.Builder(getContext(),"M_CH_ID")
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Titulo")
                .setContentText("Hola que tal?")
                .setVibrate(new long[] {100, 250, 100, 500})
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);



        mNotifyMgr.notify(1, mBuilder.build());
    }

    private void cargarFragment(Fragment fragmento, String titulo, String contenido,int icono)
    {
        // Creamos un nuevo Bundle
        Bundle args = new Bundle();

        // Colocamos el String
        args.putString("titulo", titulo);
        args.putString("contenido", contenido);
        args.putInt("icono", icono);

        //Colocamos este nuevo Bundle como argumento en el fragmento.

        fragmento.setArguments(args);

        FragmentManager manejador=getChildFragmentManager();
        manejador.beginTransaction().add(R.id.inclusionlayout,fragmento).commit();
    }

}
