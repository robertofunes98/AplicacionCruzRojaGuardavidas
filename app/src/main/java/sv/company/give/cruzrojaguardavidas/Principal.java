package sv.company.give.cruzrojaguardavidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import sv.company.give.cruzrojaguardavidas.fragmentos.CambiarClaves;
import sv.company.give.cruzrojaguardavidas.fragmentos.InicioSesion;
import sv.company.give.cruzrojaguardavidas.fragmentos.Notificaciones;
import sv.company.give.cruzrojaguardavidas.fragmentos.RegistroUsuarios;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String cookie="";

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

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        cookie=bundle.getString("cookie");
        //Toast.makeText(getApplicationContext(),cookie,Toast.LENGTH_LONG).show();
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
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cargarFragment(Fragment fragmento)
    {

        // Creamos un nuevo Bundle
        Bundle args = new Bundle();

        // Colocamos el String
        args.putString("cookie", cookie);

        //Colocamos este nuevo Bundle como argumento en el fragmento.

        fragmento.setArguments(args);

        FragmentManager manejador=getSupportFragmentManager();
        manejador.beginTransaction().replace(R.id.contenedorFragmento,fragmento).commit();
    }
}
