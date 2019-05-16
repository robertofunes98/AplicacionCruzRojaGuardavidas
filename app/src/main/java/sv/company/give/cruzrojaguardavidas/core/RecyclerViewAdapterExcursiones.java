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

import java.text.ParseException;
import java.util.ArrayList;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.ListadoExcursiones;

public class RecyclerViewAdapterExcursiones extends RecyclerView.Adapter<RecyclerViewAdapterExcursiones.ViewHolder> {

    private ArrayList<String[]> listArraysExcursiones;
    private Context mContext;
    private View fragmentView;
    private ConstraintLayout clSeleccionAnterior = null;

    public RecyclerViewAdapterExcursiones(Context context, ArrayList<String[]> arrayListExcursiones, View viewFragmento) {
        listArraysExcursiones = arrayListExcursiones;
        mContext = context;
        fragmentView = viewFragmento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_excursiones, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        String[] fecha=null;
        try {
            fecha=Funciones.separarFechaHora(listArraysExcursiones.get(position)[1]+" "+listArraysExcursiones.get(position)[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
        holder.tvLugar.setText(listArraysExcursiones.get(position)[0]);
        holder.tvFecha.setText(fecha[0]+" a las "+fecha[1]);
        holder.tvEncargado.setText(listArraysExcursiones.get(position)[3]);
        holder.tvTelEncargado.setText(listArraysExcursiones.get(position)[4]);
        holder.tvAsignada.setText(listArraysExcursiones.get(position)[5]);
        holder.tvEstado.setText(listArraysExcursiones.get(position)[6]);
        holder.tvNumeroGuardavidas.setText(listArraysExcursiones.get(position)[12]);


        if(listArraysExcursiones.get(position)[10].equals("1")) {
            holder.tvCantidadDias.setText(listArraysExcursiones.get(position)[7]);
            holder.tvCantidadDias.setVisibility(View.VISIBLE);
            holder.tvCantidadDiasEstatico.setVisibility(View.VISIBLE);
        }
        else if(listArraysExcursiones.get(position)[11].equals("0")){
            holder.tvCantidadDias.setVisibility(View.GONE);
            holder.tvCantidadDiasEstatico.setVisibility(View.GONE);
        }
        else {
            holder.tvCantidadDias.setVisibility(View.INVISIBLE);
            holder.tvCantidadDiasEstatico.setVisibility(View.INVISIBLE);
        }

        if(listArraysExcursiones.get(position)[11].equals("1")) {
            holder.tvMotivoExtraordinario.setText(listArraysExcursiones.get(position)[7]);
            holder.tvMotivoExtraordinario.setVisibility(View.VISIBLE);
            holder.tvMotivoExtraordinarioEstatico.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvMotivoExtraordinario.setVisibility(View.GONE);
            holder.tvMotivoExtraordinarioEstatico.setVisibility(View.GONE);
        }


        //Aqui se cambia el tipo de borde
        if (position == 0)
            holder.clItemLista.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
        else
            holder.clItemLista.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));

        //Se establecen los OnClick para los eventos que necesitemos
        holder.clItemLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clSeleccionAnterior != null && clSeleccionAnterior == holder.clSeleccionItem) {
                    clSeleccionAnterior.setBackgroundResource(0);
                    clSeleccionAnterior = null;
                    holder.btnAsignarExcursion.setEnabled(false);
                    holder.btnModificarExcursion.setEnabled(false);
                    holder.btnBorrarExcursion.setEnabled(false);
                } else {
                    if (clSeleccionAnterior != null)
                        clSeleccionAnterior.setBackgroundResource(0);
                    holder.clSeleccionItem.setBackgroundColor(Color.parseColor("#B3485FE3"));
                    holder.btnAsignarExcursion.setEnabled(true);
                    holder.btnModificarExcursion.setEnabled(true);
                    holder.btnBorrarExcursion.setEnabled(true);
                    ListadoExcursiones.itemSeleccionado = position;
                    clSeleccionAnterior = holder.clSeleccionItem;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listArraysExcursiones.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemLista, clSeleccionItem;
        TextView tvLugar, tvFecha, tvEncargado, tvTelEncargado, tvAsignada, tvEstado, tvCantidadDias, tvMotivoExtraordinario, tvCantidadDiasEstatico,
                tvMotivoExtraordinarioEstatico, tvNumeroGuardavidas;
        Button btnAsignarExcursion, btnModificarExcursion, btnBorrarExcursion;

        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemLista= itemView.findViewById(R.id.clItemLista);
            clSeleccionItem= itemView.findViewById(R.id.clSeleccionItem);

            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvLugar = itemView.findViewById(R.id.tvLugar);
            tvEncargado = itemView.findViewById(R.id.tvEncargado);
            tvTelEncargado = itemView.findViewById(R.id.tvTelEncargado);
            tvAsignada = itemView.findViewById(R.id.tvAsignada);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvCantidadDias = itemView.findViewById(R.id.tvCantidadDias);
            tvMotivoExtraordinario = itemView.findViewById(R.id.tvMotivoExtraordinario);
            tvCantidadDiasEstatico=itemView.findViewById(R.id.tvCantidadDiasEstatico);
            tvMotivoExtraordinarioEstatico=itemView.findViewById(R.id.tvMotivoExtraordinarioEstatico);
            tvNumeroGuardavidas=itemView.findViewById(R.id.tvNumeroGuardavidas);

            btnAsignarExcursion = fragmentView.findViewById(R.id.btnAsignarExcursion);
            btnModificarExcursion = fragmentView.findViewById(R.id.btnModificarExcursion);
            btnBorrarExcursion = fragmentView.findViewById(R.id.btnBorrarExcursion);
        }
    }

}

