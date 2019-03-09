package sv.company.give.cruzrojaguardavidas.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sv.company.give.cruzrojaguardavidas.Principal;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.ListadoReuniones;

public class Funciones {
    public static String formatearHora(String horaR) {
        String[] horaSeparada = horaR.split(":");
        String tipoHora, horaFormateada = "";
        int hora = Integer.parseInt(horaSeparada[0]);
        String minutos = horaSeparada[1];
        if (hora == 0) {
            horaFormateada += "12";
            tipoHora = "AM";
        }else if(hora == 12){
            horaFormateada += String.valueOf(hora);
            tipoHora = "PM";
        }else if (hora > 12) {
            horaFormateada += String.valueOf(hora - 12);
            tipoHora = "PM";
        } else {
            horaFormateada = String.valueOf(hora);
            tipoHora = "AM";
        }
        return horaFormateada + ":" + minutos + " " + tipoHora;
    }

    public static String patron_a_dias(String patron) {
        StringBuilder dias = new StringBuilder();
        for (int i = 0; i < patron.length(); i++) {
            if (dias.length() > 0)
                dias.append(", ");
            switch (patron.charAt(i)) {
                case '0':
                    dias.append("Lunes");
                    break;
                case '1':
                    dias.append("Martes");
                    break;
                case '2':
                    dias.append("Miercoles");
                    break;
                case '3':
                    dias.append("Jueves");
                    break;
                case '4':
                    dias.append("Viernes");
                    break;
                case '5':
                    dias.append("Sabado");
                    break;
                case '6':
                    dias.append("Domingo");
                    break;
            }
        }
        return dias.toString();
    }

    public static String obtenerDiaSemana(String fecha) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(fecha);
        Calendar fechaCal = Calendar.getInstance();
        fechaCal.setTime(date);
        String inputDateStr = String.format("%s/%s/%s", fechaCal.get(Calendar.DAY_OF_MONTH),fechaCal.get(Calendar.MONTH)+1,fechaCal.get(Calendar.YEAR));
        String dayOfWeek = ucFirst(fechaCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("es-ES")));
        return dayOfWeek+" "+inputDateStr;
    }

    public static String[] separarFechaHora(String fechaHora) throws ParseException {
        String[] fechaHoraArray=fechaHora.split(" ");
        String[] fechaSeparada=new String[2];
        fechaSeparada[0]=obtenerDiaSemana(fechaHoraArray[0]);
        fechaSeparada[1]=formatearHora(fechaHoraArray[1]);
        return fechaSeparada;
    }

    public static String ucFirst(String str){
        if (str == null || str.isEmpty())
            return str;
        else
            return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
