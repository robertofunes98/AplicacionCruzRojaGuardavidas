package sv.company.give.cruzrojaguardavidas.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import sv.company.give.cruzrojaguardavidas.R;

public class RecyclerViewAdapterExcepciones extends RecyclerView.Adapter<RecyclerViewAdapterExcepciones.ViewHolder> {

    private ArrayList<String[]> arrayListExcepciones;
    private Context mContext;
    private View fragmentView;

    private SparseBooleanArray seleccionados;

    public RecyclerViewAdapterExcepciones(Context context, ArrayList<String[]> arrayListExcepcionesR, View viewFragmento) {
        arrayListExcepciones = arrayListExcepcionesR;
        mContext = context;
        fragmentView = viewFragmento;
        seleccionados = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_excepciones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return arrayListExcepciones.size();
    }

    /*Devuelve aquellos objetos marcados.*/
    LinkedList<String[]> obtenerSeleccionados(){
        LinkedList<String[]> marcados = new LinkedList<>();
        for (int i = 0; i < arrayListExcepciones.size(); i++) {
            if (seleccionados.get(i)){
                marcados.add(arrayListExcepciones.get(i));
            }
        }
        return marcados;
    }

    int cantidadSeleccionados() {
        int contador=0;
        for (int i = 0; i <= arrayListExcepciones.size(); i++) {
            if (seleccionados.get(i))
                contador++;
        }
        return contador;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemListaGuardavidas, clSeleccionItemGuardavidas;
        TextView tvInformacionExcepcion;
        Button btnEliminarExcepciones;
        View item;


        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemListaGuardavidas = itemView.findViewById(R.id.clItemListaEntrenos);
            clSeleccionItemGuardavidas = itemView.findViewById(R.id.clSeleccionItemGuardavidas);

            tvInformacionExcepcion = itemView.findViewById(R.id.tvInformacionExcepcion);

            btnEliminarExcepciones = fragmentView.findViewById(R.id.btnEliminarExcepciones);

            item = itemView;

        }

        void bindView(final int position) {

            String[] fechaHora=null;
            try {
                fechaHora = Funciones.separarFechaHora(arrayListExcepciones.get(position)[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
            tvInformacionExcepcion.setText(fechaHora[0] + " en " + arrayListExcepciones.get(position)[1]);

            //Aqui se cambia el tipo de borde
            if (position == 0)
                clItemListaGuardavidas.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
            else
                clItemListaGuardavidas.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));


            //Selecciona el objeto si estaba seleccionado
            if (seleccionados.get(getAdapterPosition()))
                item.setSelected(true);
            else
                item.setSelected(false);

            /*Selecciona/deselecciona un ítem*/
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isSelected()) {
                        v.setSelected(true);
                        seleccionados.put(getAdapterPosition(), true);
                    } else {
                        v.setSelected(false);
                        seleccionados.put(getAdapterPosition(), false);
                    }
                    if (haySeleccionados()) {
                        btnEliminarExcepciones.setEnabled(true);
                    } else {
                        btnEliminarExcepciones.setEnabled(false);
                    }

                }
            });

        }


        Boolean haySeleccionados() {
            for (int i = 0; i <= arrayListExcepciones.size(); i++) {
                if (seleccionados.get(i))
                    return true;
            }
            return false;
        }




    }

}

