package sv.company.give.cruzrojaguardavidas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import sv.company.give.cruzrojaguardavidas.core.ConexionWebService;
import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapterAsistencia;
import sv.company.give.cruzrojaguardavidas.core.Variables;

public class CapturarCookie extends AppCompatActivity {

    WebView wvNavegador;
    String cookie;
    boolean actividadIniciada;
    TextView tvVersion;
    ConexionWebService conexion;
    JSONObject jsonObjeto = null;
    JSONArray jsonRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar_cookie);
        tvVersion = findViewById(R.id.tvVersion);
        actividadIniciada = false;
        wvNavegador = (WebView) findViewById(R.id.wvNavegador);
        tvVersion.setText("Versión " + Variables.version);
        revisarConexion();

    }

    private void revisarVersion() {
        //Obteniendo datos de la DB
        conexion = new ConexionWebService();
        try {
            //conexion.execute(url,parametros,cookie)
            String resultado = conexion.execute(Variables.url + "validacion.php",
                    "accion=revisarVersion&version=" + Variables.version, cookie).get();

            //Toast.makeText(getApplicationContext(),resultado,Toast.LENGTH_LONG).show();

            jsonRespuesta = new JSONArray(resultado);
            jsonObjeto = jsonRespuesta.getJSONObject(0);

            if (jsonObjeto.has("error"))
                Toast.makeText(getApplicationContext(), jsonObjeto.getString("error"), Toast.LENGTH_LONG).show();//Si falla la conex o no hay reuniones
            else {
                if (jsonObjeto.getString("version").equals(Variables.version)) {
                    Intent intento = new Intent(CapturarCookie.this, Principal.class);
                    intento.putExtra("cookie", cookie);
                    actividadIniciada = true;
                    startActivity(intento);
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CapturarCookie.this);
                    builder1.setMessage("Su version esta desactualizada. Por favor descargue la nueva version para continuar")
                            .setTitle("Version obsoleta").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).setCancelable(false).create().show();
                }
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void revisarConexion() {
        Boolean estadoConexion = isOnlineNet();

        if (!estadoConexion) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(CapturarCookie.this);
            builder1.setMessage("No hay internet disponible")
                    .setTitle("Error de conexion").setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    revisarConexion();
                }
            }).setCancelable(false).create().show();
        } else
            obtenerCookie();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void obtenerCookie() {

        wvNavegador.getSettings().setJavaScriptEnabled(true);
        wvNavegador.loadUrl(Variables.url + "obtenerCookie.php");
        wvNavegador.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cookie = wvNavegador.getTitle();

                if (cookie.contains("hangbor"))
                    obtenerCookie();
                else {
                    if (!actividadIniciada) {
                        Variables.cookie=cookie;
                        revisarVersion();
                    }
                }
            }
        });
    }

    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
