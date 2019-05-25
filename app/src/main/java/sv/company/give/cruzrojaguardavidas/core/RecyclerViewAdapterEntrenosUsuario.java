package sv.company.give.cruzrojaguardavidas.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import sv.company.give.cruzrojaguardavidas.R;

public class RecyclerViewAdapterEntrenosUsuario extends RecyclerView.Adapter<RecyclerViewAdapterEntrenosUsuario.ViewHolder> {

    private ArrayList<String[]> arrayListEntrenos, arrayListExcepciones;
    static private ArrayList<String[]> arrayListExcepcionesFinal;
    private ArrayList<String> arrayListEntrenosFechas;
    private Context mContext;

    public RecyclerViewAdapterEntrenosUsuario(Context context, ArrayList<String[]> arrayListEntrenosR, ArrayList<String[]> arrayListExcepcionesR, View viewFragmento) {
        arrayListEntrenos = arrayListEntrenosR;
        mContext = context;
        arrayListEntrenosFechas = new ArrayList<>();
        arrayListExcepcionesFinal = arrayListExcepcionesR;
        crearEntrenos();
        System.out.println("okkk");
    }

    private void crearEntrenos() {
        //creando los datos de los entrenos. Cabe recalcar que esta
        // funcion depende totalmente del telefono por lo tanto es necesario que la fecha y hora sean las correctas
        Calendar fecha = Calendar.getInstance();

        fecha.set(Calendar.SECOND, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        fecha.set(Calendar.MINUTE, 0);
        fecha.set(Calendar.HOUR, 0);

        //es importante usar clone() ya que si no perderiamos la informacion del array principal al pasar su direccion de memoria y no su copia del arraylist
        arrayListExcepciones = (ArrayList<String[]>) arrayListExcepcionesFinal.clone();

        try {
            for (int i = 1; i < 32; i++) {
                for (int j = 0; j < arrayListEntrenos.size(); j++) {
                    for (int q = 0; q < arrayListEntrenos.get(j)[0].length(); q++) {
                        if (String.valueOf(fecha.get(Calendar.DAY_OF_WEEK)).equals(String.valueOf(arrayListEntrenos.get(j)[0].charAt(q)))) {
                            if (arrayListExcepciones.size() <= 0) {
                                arrayListEntrenosFechas.add("En " + arrayListEntrenos.get(j)[2] + " el " + Funciones.obtenerDiaSemana(fecha.get(Calendar.YEAR)
                                        + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH)) + " a las " + arrayListEntrenos.get(j)[1]);
                                System.out.println("ya");
                                break;
                            } else {
                                for (int ex = 0; ex < arrayListExcepciones.size(); ex++) {
                                    Calendar fechaAux = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    fechaAux.setTime(sdf.parse(arrayListExcepciones.get(ex)[1]));
                                    if (!((fecha.equals(fechaAux)) && (arrayListExcepciones.get(ex)[2].equals(arrayListEntrenos.get(j)[3])))) {
                                        if (ex == arrayListExcepciones.size() - 1) {
                                            arrayListEntrenosFechas.add("En " + arrayListEntrenos.get(j)[2] + " el " + Funciones.obtenerDiaSemana(fecha.get(Calendar.YEAR)
                                                    + "-" + (fecha.get(Calendar.MONTH) + 1) + "-" + fecha.get(Calendar.DAY_OF_MONTH)) + " a las " + arrayListEntrenos.get(j)[1]);
                                            System.out.println("ya");
                                            break;
                                        }
                                    } else {
                                        arrayListExcepciones.remove(ex);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                fecha.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_entrenos_usuario, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
        holder.tvInformacionEntreno.setText(arrayListEntrenosFechas.get(position));

        //Aqui se cambia el tipo de borde
        if (position == 0)
            holder.clItemListaEntrenos.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
        else
            holder.clItemListaEntrenos.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));
    }

    @Override
    public int getItemCount() {
        return arrayListEntrenosFechas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemListaEntrenos;
        TextView tvInformacionEntreno;

        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemListaEntrenos = itemView.findViewById(R.id.clItemListaEntrenos);
            tvInformacionEntreno = itemView.findViewById(R.id.tvInformacionEntreno);
        }
    }

}

