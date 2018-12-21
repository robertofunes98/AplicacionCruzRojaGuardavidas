package sv.company.give.cruzrojaguardavidas.fragmentos;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
public class Notificacion extends Fragment{
    String titulo,contenido,cookie,referencia;
    int icono,tipo,idNotificacion;

    ImageView ivIcono;
    TextView tvTitulo,tvContenido;
    LinearLayout notificacionPrincipal;

    ConexionWebService conexion;
    JSONObject jsonObjeto=null;

    public Notificacion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notificacion, container, false);

        cookie=getArguments().getString("cookie");
        titulo=getArguments().getString("titulo");
        contenido=getArguments().getString("contenido");
        icono=getArguments().getInt("icono");
        tipo=Integer.parseInt(getArguments().getString("tipo"));
        referencia=getArguments().getString("referencia");
        idNotificacion=Integer.parseInt(getArguments().getString("idNotificacion"));

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
                Toast.makeText(getContext(),String.valueOf(tipo),Toast.LENGTH_LONG).show();
                switch (tipo)
                {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("¿Aceptas la excursion?")
                                .setTitle("Excursion").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                conexion=new ConexionWebService();
                                try {
                                    //conexion.execute(url,parametros,cookie)
                                    String resultado=conexion.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/confirmacionNotificaciones.php",
                                            "accion=ConfirmarExcursion&referencia="+referencia+"&carnet="
                                                    +Principal.carnetGlobal+"&idNotificacion="+idNotificacion,cookie).get();

                                    Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

                                    JSONArray jsonRespuesta= new JSONArray(resultado);

                                    jsonObjeto=jsonRespuesta.getJSONObject(0);



                                } catch (ExecutionException | InterruptedException | JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setCancelable(false).setNegativeButton("Cancelar",null).create().show();
                        break;
                    case 1:
                        break;
                    case 3:
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(getContext());
                        View dialogView =inflater.inflate(R.layout.dialogo_cambiar_clave,null);
                        builder3.setView(dialogView);

                        final EditText clave=dialogView.findViewById(R.id.nuevaClaveDialogo);
                        String mensaje="";

                        conexion=new ConexionWebService();
                        try {
                            //conexion.execute(url,parametros,cookie)
                           String resultado=conexion.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/cambiarClave.php","accion=obtenerNombre&carnet="
                                    +Principal.carnetGlobal,cookie).get();

                            //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

                            JSONArray jsonRespuesta= new JSONArray(resultado);

                            jsonObjeto=jsonRespuesta.getJSONObject(0);

                            if(jsonObjeto.has("error"))
                                Toast.makeText(getContext(),jsonObjeto.getString("error"),Toast.LENGTH_LONG).show();
                            else
                            {
                                mensaje="Asigne la nueva clave para el usuario "+jsonObjeto.getString("nombres")
                                        +" "+jsonObjeto.getString("apellidos")+" con carnet "+Principal.carnetGlobal;
                            }

                        } catch (ExecutionException | InterruptedException | JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                        builder3.setMessage(mensaje)
                                .setTitle("Cambio de clave").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                conexion=new ConexionWebService();
                                try {
                                    //conexion.execute(url,parametros,cookie)
                                    String resultado=conexion.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/confirmacionNotificaciones.php",
                                            "accion=CambiarClave&carnet="
                                                    +Principal.carnetGlobal+"&idNotificacion="+idNotificacion+"&clave="+clave.getText().toString(),cookie).get();

                                    //Toast.makeText(getContext(),resultado,Toast.LENGTH_LONG).show();

                                    JSONArray jsonRespuesta= new JSONArray(resultado);

                                    jsonObjeto=jsonRespuesta.getJSONObject(0);

                                    Toast.makeText(getContext(),jsonObjeto.getString("resultado"),Toast.LENGTH_LONG).show();

                                } catch (ExecutionException | InterruptedException | JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setCancelable(false).setNegativeButton("Cancelar",null).create().show();
                        break;
                }


            }
        });


        return rootView;
    }

}