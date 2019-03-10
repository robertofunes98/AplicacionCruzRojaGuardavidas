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

public class RecyclerViewAdapterGuardavidas extends RecyclerView.Adapter<RecyclerViewAdapterGuardavidas.ViewHolder> {

    private ArrayList<String[]> listArrayGuardavidas;
    private Context mContext;
    private View fragmentView;
    private ConstraintLayout clSeleccionAnterior = null;

    public RecyclerViewAdapterGuardavidas(Context context, ArrayList<String[]> arrayListGuardavidas, View viewFragmento) {
        listArrayGuardavidas = arrayListGuardavidas;
        mContext = context;
        fragmentView = viewFragmento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_guardavidas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
        holder.tvNombreCompleto.setText(listArrayGuardavidas.get(position)[0]);

        //Aqui se cambia el tipo de borde
        if (position == 0)
            holder.clItemListaGuardavidas.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
        else
            holder.clItemListaGuardavidas.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));

        //Se establecen los OnClick para los eventos que necesitemos
        holder.clItemListaGuardavidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clSeleccionAnterior != null && clSeleccionAnterior == holder.clSeleccionItemGuardavidas) {
                    clSeleccionAnterior.setBackgroundResource(0);
                    clSeleccionAnterior = null;
                    holder.btnConfirmar.setEnabled(false);
                } else {
                    if (clSeleccionAnterior != null)
                        clSeleccionAnterior.setBackgroundResource(0);
                    holder.clSeleccionItemGuardavidas.setBackgroundColor(Color.parseColor("#B3485FE3"));
                    holder.btnConfirmar.setEnabled(true);
                    Guardavidas.itemSeleccionado = position;
                    clSeleccionAnterior = holder.clSeleccionItemGuardavidas;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listArrayGuardavidas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemListaGuardavidas, clSeleccionItemGuardavidas;
        TextView tvNombreCompleto;
        Button btnConfirmar;

        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemListaGuardavidas= itemView.findViewById(R.id.clItemListaGuardavidas);
            clSeleccionItemGuardavidas= itemView.findViewById(R.id.clSeleccionItemGuardavidas);

            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);

            btnConfirmar = fragmentView.findViewById(R.id.btnConfirmar);
        }
    }

}

