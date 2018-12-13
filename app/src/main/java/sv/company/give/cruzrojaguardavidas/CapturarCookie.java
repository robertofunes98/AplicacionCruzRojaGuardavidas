package sv.company.give.cruzrojaguardavidas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CapturarCookie extends AppCompatActivity {

    WebView wvNavegador;
    String cookie="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar_cookie);

        wvNavegador=(WebView)findViewById(R.id.wvNavegador);
        wvNavegador.getSettings().setJavaScriptEnabled(true);
        wvNavegador.loadUrl("http://hangbor.byethost24.com/WebServiceCruzRoja/obtenerCookie.php");



        wvNavegador.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cookie=wvNavegador.getTitle();
                Intent intent=new Intent(getApplicationContext(),Principal.class);
                intent.putExtra("cookie",cookie);
                startActivity(intent);
            }
        });
    }
}
