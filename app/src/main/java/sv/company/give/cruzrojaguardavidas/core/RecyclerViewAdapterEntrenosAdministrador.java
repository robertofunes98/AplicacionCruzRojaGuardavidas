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

public class RecyclerViewAdapterEntrenosAdministrador extends RecyclerView.Adapter<RecyclerViewAdapterEntrenosAdministrador.ViewHolder> {

    private ArrayList<String[]> listArraysEntrenos;
    private Context mContext;
    private View fragmentView;
    private int itemSeleccionado;
    private ConstraintLayout clSeleccionAnterior = null;

    public RecyclerViewAdapterEntrenosAdministrador(Context context, ArrayList<String[]> arrayListEntrenos, View viewFragmento) {
        listArraysEntrenos = arrayListEntrenos;
        mContext = context;
        fragmentView = viewFragmento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_entrenos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
        holder.tvDiasEntrenoLista.setText(listArraysEntrenos.get(position)[0]);
        holder.tvHoraEntrenosLista.setText(listArraysEntrenos.get(position)[1]);
        holder.tvLugarEntrenoLista.setText(listArraysEntrenos.get(position)[2]);

        //Aqui se cambia el tipo de borde
        if (position == 0)
            holder.clItemListaEntrenos.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
        else
            holder.clItemListaEntrenos.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));

        //Se establecen los OnClick para los eventos que necesitemos
        holder.clItemListaEntrenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clSeleccionAnterior != null && clSeleccionAnterior == holder.clSeleccionItem) {
                    clSeleccionAnterior.setBackgroundResource(0);
                    clSeleccionAnterior = null;
                    holder.btnNotificarEntreno.setEnabled(false);
                } else {
                    if (clSeleccionAnterior != null)
                        clSeleccionAnterior.setBackgroundResource(0);
                    holder.clSeleccionItem.setBackgroundColor(Color.parseColor("#B3485FE3"));
                    holder.btnNotificarEntreno.setEnabled(true);
                    itemSeleccionado = position;
                    ListadoEntrenos.itemSeleccionado = itemSeleccionado;
                    clSeleccionAnterior = holder.clSeleccionItem;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listArraysEntrenos.size();
    }

    public int getItemSeleccionado() {
        return itemSeleccionado;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemListaEntrenos, clSeleccionItem;
        TextView tvHoraEntrenosLista, tvDiasEntrenoLista, tvLugarEntrenoLista;
        Button btnNotificarEntreno;

        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemListaEntrenos = itemView.findViewById(R.id.clItemListaEntrenos);
            tvHoraEntrenosLista = itemView.findViewById(R.id.tvHoraEntrenosLista);
            tvDiasEntrenoLista = itemView.findViewById(R.id.tvDiasEntrenoLista);
            tvLugarEntrenoLista = itemView.findViewById(R.id.tvLugarEntrenoLista);
            clSeleccionItem = itemView.findViewById(R.id.clSeleccionItemEntrenos);
            btnNotificarEntreno = fragmentView.findViewById(R.id.btnNotificarEntreno);
        }
    }

}

