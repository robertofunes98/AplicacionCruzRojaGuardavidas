package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sv.company.give.cruzrojaguardavidas.Principal;
import sv.company.give.cruzrojaguardavidas.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notificacion extends Fragment {
    String titulo,contenido;
    int icono;

    ImageView ivIcono;
    TextView tvTitulo,tvContenido;
    LinearLayout notificacionPrincipal;

    public Notificacion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notificacion, container, false);

        titulo=getArguments().getString("titulo");
        contenido=getArguments().getString("contenido");
        icono=getArguments().getInt("icono");

        ivIcono=rootView.findViewById(R.id.ivIcono);
        tvTitulo=rootView.findViewById(R.id.tvTitulo);
        tvContenido=rootView.findViewById(R.id.tvContenido);
        notificacionPrincipal=rootView.findViewById(R.id.notificacion_principal);

        ivIcono.setImageResource(icono);
        tvTitulo.setText(titulo);
        tvContenido.setText(contenido);

        notificacionPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"prueba de notificacion",Toast.LENGTH_SHORT).show();

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
        });

        return rootView;
    }

}
