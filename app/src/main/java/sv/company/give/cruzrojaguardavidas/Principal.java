package sv.company.give.cruzrojaguardavidas;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;
import sv.company.give.cruzrojaguardavidas.fragmentos.CambiarClaves;
import sv.company.give.cruzrojaguardavidas.fragmentos.InicioSesion;
import sv.company.give.cruzrojaguardavidas.fragmentos.Notificaciones;
import sv.company.give.cruzrojaguardavidas.fragmentos.PeticionNuevaClave;
import sv.company.give.cruzrojaguardavidas.fragmentos.RegistroUsuarios;
import sv.company.give.cruzrojaguardavidas.fragmentos.ReunionesAdministrador;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String cookie = "";
    public static String carnetGlobal = "216-258";
    public static int tipoUsuario = 1;

    public static int conectividad=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        cookie = bundle.getString("cookie");

        //Toast.makeText(getApplicationContext(),cookie+" la cookie",Toast.LENGTH_LONG).show();

        ejecutar();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.opc_notificaciones) {
            cargarFragment(new Notificaciones());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.opc_registro_usuarios) {
            cargarFragment(new RegistroUsuarios());
        } else if (id == R.id.opc_cambiar_claves) {
            cargarFragment(new CambiarClaves());
        } else if (id == R.id.opc_entrenos) {

        } else if (id == R.id.opc_eventos) {

        } else if (id == R.id.opc_excursiones) {

        } else if (id == R.id.opc_reuniones) {

        } else if (id == R.id.opc_salir) {
            finishAffinity();
        } else if (id == R.id.opc_inicio_sesion) {
            cargarFragment(new InicioSesion());
        } else if (id == R.id.opc_peticion_cambiar_clave) {
            cargarFragment(new PeticionNuevaClave());
        } else if (id == R.id.opc_reuniones_admin) {
            cargarFragment(new ReunionesAdministrador());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cargarFragment(Fragment fragmento) {

        // Creamos un nuevo Bundle
        Bundle args = new Bundle();

        // Colocamos el String
        args.putString("cookie", cookie);

        //Colocamos este nuevo Bundle como argumento en el fragmento.

        fragmento.setArguments(args);

        FragmentManager manejador = getSupportFragmentManager();
        manejador.beginTransaction().replace(R.id.contenedorFragmento, fragmento).commit();
    }


    public void MostrarNotificacion()
    {
        ConexionWebService conexionPrincipal;
        JSONObject jsonObjetoPrincipal=null;
        conexionPrincipal=new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado=conexionPrincipal.execute("http://hangbor.byethost24.com/WebServiceCruzRoja/obtenerNotificaciones.php",
                    "accion=obtenerNotificacion&carnet="
                            +Principal.carnetGlobal,cookie).get();

            //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();

            JSONArray jsonRespuestaPrincipal= new JSONArray(resultado);

            jsonObjetoPrincipal= jsonRespuestaPrincipal.getJSONObject(0);

            if(jsonObjetoPrincipal.has("error"))
                Toast.makeText(getApplicationContext(),jsonObjetoPrincipal.getString("error"),Toast.LENGTH_LONG).show();
            else
            {
                String cantidadNotificaciones=jsonObjetoPrincipal.getString("resultado"),mensajeInfo="";

                if(cantidadNotificaciones.equals("1"))
                    mensajeInfo=" notificacion nueva";
                else
                    mensajeInfo=" notificaciones nuevas";

                NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                int icono = R.mipmap.ic_launcher;

                Intent intent=new Intent(getApplicationContext(),Principal.class);
                intent.putExtra("cookie",cookie);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder =new NotificationCompat.Builder(getApplicationContext(),"CruzRojaSantaAna")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(icono)
                        .setContentTitle("Notificaciones nuevas")
                        .setContentText("Tienes "+cantidadNotificaciones+mensajeInfo)
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);
                mNotifyMgr.notify(1, mBuilder.build());
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void ejecutar(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MostrarNotificacion();//llamamos nuestro metodo
                handler.postDelayed(this,900000);//se ejecutara cada 10 segundos
            }
        },1000);//empezara a ejecutarse después de 5 milisegundos
    }

    /*Codigo encargado de verificar el estado de la conexion*/

    private BroadcastReceiver networkStateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();
            recargarAplicacion(ni);//Toast.makeText(getApplicationContext(),"holaaaaa",Toast.LENGTH_LONG).show();//doSomethingOnNetworkChange(ni);
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }

    public void recargarAplicacion(NetworkInfo ni)
    {
        if(ni== null)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Principal.this);
            builder1.setMessage("Se ha detectado un cambio de red, por favor recargue la aplicacion")
                    .setTitle("Error de conexion").setPositiveButton("Recargar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intento=new Intent(Principal.this,CapturarCookie.class);
                    startActivity(intento);
                }
            }).setCancelable(false).create().show();
        }
    }
}
