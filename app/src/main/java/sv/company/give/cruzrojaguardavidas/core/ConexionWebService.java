package sv.company.give.cruzrojaguardavidas.core;

import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionWebService extends AsyncTask<String, String, String> {

    //La clase recibe 2 parametro en el metodo excute que son la url del script y la otra los parametroa que se enviaran por POST
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection conexionHttp = null;
        URL url = null;
        int cod = 0;
        InputStream inputStr = null;
        BufferedReader lector = null;

        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            conexionHttp = (HttpURLConnection) url.openConnection();
            conexionHttp.setReadTimeout(10000);
            conexionHttp.setConnectTimeout(15000);



            //Esta cadena es la cookie que permite la conexion al servidor web, es necesario renovarla de vez en cuando
            //Todos estos datos se sacan  desde un navegador en informacion de la pagina
            //si se trabajase con un web service local se debe comentar la linea de la cookie
            //el parametro "_test" es en si la informacion que alamcena la cookie llamda _test
            // espires es por ende la fecha de expiracion
            //path es la ruta de la cookie
            conexionHttp.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
            conexionHttp.setRequestProperty("Cookie", "__test=" + strings[2] + "; expires=Thu, 31-Dec-37 17:55:55 GMT; path=/");
            conexionHttp.setRequestMethod("POST");
            conexionHttp.setDoOutput(true);

            OutputStream os = conexionHttp.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //estos de abajo son los parametros que sen envian a la pagina
            writer.write(strings[1]);
            writer.flush();
            writer.close();
            os.close();

            conexionHttp.connect();
            cod = conexionHttp.getResponseCode();
            if (cod == HttpURLConnection.HTTP_OK) {
                inputStr = new BufferedInputStream(conexionHttp.getInputStream());
                lector = new BufferedReader(new InputStreamReader(inputStr));
                String linea = "";
                StringBuffer strBuffer = new StringBuffer();
                while ((linea = lector.readLine()) != null) {
                    strBuffer.append(linea);
                }
                return strBuffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "la conexion fallo";
    }
}
