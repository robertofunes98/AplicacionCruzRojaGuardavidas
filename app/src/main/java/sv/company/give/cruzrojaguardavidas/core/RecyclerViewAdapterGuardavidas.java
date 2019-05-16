package sv.company.give.cruzrojaguardavidas.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import sv.company.give.cruzrojaguardavidas.R;

public class RecyclerViewAdapterGuardavidas extends RecyclerView.Adapter<RecyclerViewAdapterGuardavidas.ViewHolder> {

    private ArrayList<String[]> listArrayGuardavidas;
    private Context mContext;
    private View fragmentView;
    private ConstraintLayout clSeleccionAnterior = null;


    //prueba
    private boolean[] arraySelecciones;
    private SparseBooleanArray seleccionados;

    public RecyclerViewAdapterGuardavidas(Context context, ArrayList<String[]> arrayListGuardavidas, View viewFragmento) {
        listArrayGuardavidas = arrayListGuardavidas;
        mContext = context;
        fragmentView = viewFragmento;
        arraySelecciones = new boolean[arrayListGuardavidas.size()];
        seleccionados = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_guardavidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return listArrayGuardavidas.size();
    }

    /*Devuelve aquellos objetos marcados.*/
    public LinkedList<String[]> obtenerSeleccionados(){
        LinkedList<String[]> marcados = new LinkedList<>();
        for (int i = 0; i < listArrayGuardavidas.size(); i++) {
            if (seleccionados.get(i)){
                marcados.add(listArrayGuardavidas.get(i));
            }
        }
        return marcados;
    }

    public int cantidadSeleccionados() {
        int contador=0;
        for (int i = 0; i <= listArrayGuardavidas.size(); i++) {
            if (seleccionados.get(i))
                contador++;
        }
        return contador;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemListaGuardavidas, clSeleccionItemGuardavidas;
        TextView tvNombreCompleto;
        Button btnConfirmar, btnAsignarForzadamente;
        Integer posicion;
        View item;

        //borrar
        TextView tvPruebas;

        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemListaGuardavidas = itemView.findViewById(R.id.clItemListaGuardavidas);
            clSeleccionItemGuardavidas = itemView.findViewById(R.id.clSeleccionItemGuardavidas);

            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);

            btnConfirmar = fragmentView.findViewById(R.id.btnConfirmar);
            btnAsignarForzadamente=fragmentView.findViewById(R.id.btnAsignarForzadamente);

            item = itemView;

            //borrar
            tvPruebas = fragmentView.findViewById(R.id.tvPruebas);
        }

        public void bindView(final int position) {

            //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
            tvNombreCompleto.setText(listArrayGuardavidas.get(position)[0]);

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
                    if(haySeleccionados())
                    {
                        btnConfirmar.setEnabled(true);
                        btnAsignarForzadamente.setEnabled(true);
                    }
                    else
                    {
                        btnConfirmar.setEnabled(false);
                        btnAsignarForzadamente.setEnabled(false);
                    }

                }
            });

        }


        public Boolean haySeleccionados() {
            for (int i = 0; i <= listArrayGuardavidas.size(); i++) {
                if (seleccionados.get(i))
                    return true;
            }
            return false;
        }




    }

}

