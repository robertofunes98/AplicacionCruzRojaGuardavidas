package sv.company.give.cruzrojaguardavidas.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.ListadoEntrenos;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.ListadoReuniones;

public class RecyclerViewAdapterReuniones extends RecyclerView.Adapter<RecyclerViewAdapterReuniones.ViewHolder> {

    private ArrayList<String[]> listArraysReuniones;
    private Context mContext;
    private View fragmentView;
    private ConstraintLayout clSeleccionAnterior=null;

    public RecyclerViewAdapterReuniones(Context context, ArrayList<String[]> arrayListEntrenos, View viewFragmento) {
        listArraysReuniones = arrayListEntrenos;
        mContext = context;
        fragmentView=viewFragmento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_reuniones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
        holder.tvFechaReunionLista.setText(listArraysReuniones.get(position)[0]);
        holder.tvHoraReunionLista.setText(listArraysReuniones.get(position)[1]);
        holder.tvLugarReunionLista.setText(listArraysReuniones.get(position)[2]);
        holder.tvTipoReunion.setText(listArraysReuniones.get(position)[3]);

        //Aqui se cambia el tipo de borde
        if (position == 0)
            holder.clItemListaReuniones.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
        else
            holder.clItemListaReuniones.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));

        //Se establecen los OnClick para los eventos que necesitemos
        holder.clItemListaReuniones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clSeleccionAnterior!=null && clSeleccionAnterior==holder.clSeleccionItemReuniones) {
                    clSeleccionAnterior.setBackgroundResource(0);
                    clSeleccionAnterior=null;
                    holder.btnNotificarReunion.setEnabled(false);
                }
                else {
                    if(clSeleccionAnterior!=null)
                        clSeleccionAnterior.setBackgroundResource(0);
                    holder.clSeleccionItemReuniones.setBackgroundColor(Color.parseColor("#B3485FE3"));
                    holder.btnNotificarReunion.setEnabled(true);
                    ListadoReuniones.itemSeleccionado=position;
                    clSeleccionAnterior=holder.clSeleccionItemReuniones;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listArraysReuniones.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemListaReuniones, clSeleccionItemReuniones;
        TextView tvTipoReunion, tvFechaReunionLista, tvLugarReunionLista, tvHoraReunionLista;
        Button btnNotificarReunion;

        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemListaReuniones = itemView.findViewById(R.id.clItemListaReuniones);
            tvTipoReunion = itemView.findViewById(R.id.tvTipoReunion);
            tvFechaReunionLista = itemView.findViewById(R.id.tvFechaReunionLista);
            tvLugarReunionLista = itemView.findViewById(R.id.tvLugarReunionLista);
            tvHoraReunionLista = itemView.findViewById(R.id.tvHoraReunionLista);
            clSeleccionItemReuniones= itemView.findViewById(R.id.clSeleccionItemReuniones);
            btnNotificarReunion=fragmentView.findViewById(R.id.btnNotificarReunion);
        }
    }

}

