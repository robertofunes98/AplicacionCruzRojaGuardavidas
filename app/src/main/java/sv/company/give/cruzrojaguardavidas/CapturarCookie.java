package sv.company.give.cruzrojaguardavidas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CapturarCookie extends AppCompatActivity {

    WebView wvNavegador;
    String cookie;
    boolean actividadIniciada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar_cookie);
        actividadIniciada=false;
        wvNavegador=(WebView)findViewById(R.id.wvNavegador);
        revisarConexion();
    }

    public void revisarConexion()
    {
        Boolean estadoConexion= isOnlineNet();

        if(!estadoConexion)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(CapturarCookie.this);
            builder1.setMessage("No hay internet disponible")
                    .setTitle("Error de conexion").setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    revisarConexion();
                }
            }).setCancelable(false).create().show();
        }
        else
            obtenerCookie();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void obtenerCookie()
    {

        wvNavegador.getSettings().setJavaScriptEnabled(true);
        wvNavegador.loadUrl(Variables.url+"obtenerCookie.php");
        wvNavegador.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cookie=wvNavegador.getTitle();

                if(cookie.contains("hangbor"))
                    obtenerCookie();
                else
                {
                    if(!actividadIniciada)
                    {
                        Intent intento=new Intent(CapturarCookie.this,Principal.class);
                        intento.putExtra("cookie",cookie);
                        actividadIniciada=true;
                        startActivity(intento);
                    }
                }
            }
        });
    }

    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
