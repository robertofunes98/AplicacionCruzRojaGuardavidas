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
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.ListadoReuniones;

public class RecyclerViewAdapterAsistencia extends RecyclerView.Adapter<RecyclerViewAdapterAsistencia.ViewHolder> {

    private ArrayList<String[]> listArrayAsistencia;
    private Context mContext;
    private View fragmentView;
    private ConstraintLayout clSeleccionAnterior = null;

    public RecyclerViewAdapterAsistencia(Context context, ArrayList<String[]> arrayListAsistencia, View viewFragmento) {
        listArrayAsistencia = arrayListAsistencia;
        mContext = context;
        fragmentView = viewFragmento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos_recycler_listado_asistencia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Se modifican los aspectos de la vista antes de la creacion de ella en el recycler
        holder.tvNombreCompleto.setText(listArrayAsistencia.get(position)[0]);

        //Aqui se cambia el tipo de borde
        if (position == 0)
            holder.clItemListaAsistencia.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
        else
            holder.clItemListaAsistencia.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));

        //Se establecen los OnClick para los eventos que necesitemos
        holder.clItemListaAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clSeleccionAnterior != null && clSeleccionAnterior == holder.clSeleccionItemAsistencia) {
                    clSeleccionAnterior.setBackgroundResource(0);
                    clSeleccionAnterior = null;
                    holder.btnBorrarAsistente.setEnabled(false);
                } else {
                    if (clSeleccionAnterior != null)
                        clSeleccionAnterior.setBackgroundResource(0);
                    holder.clSeleccionItemAsistencia.setBackgroundColor(Color.parseColor("#B3485FE3"));
                    holder.btnBorrarAsistente.setEnabled(true);
                    Asistencia.itemSeleccionado = position;
                    clSeleccionAnterior = holder.clSeleccionItemAsistencia;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listArrayAsistencia.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        //Se declaran las variables contenedoras de los objetos de la vista
        ConstraintLayout clItemListaAsistencia, clSeleccionItemAsistencia;
        TextView tvNombreCompleto;
        Button btnBorrarAsistente;

        ViewHolder(View itemView) {
            super(itemView);
            //Se vinculan las variables contenedoras con el objeto
            clItemListaAsistencia = itemView.findViewById(R.id.clItemListaAsistencia);
            clSeleccionItemAsistencia = itemView.findViewById(R.id.clSeleccionItemAsistencia);

            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);

            btnBorrarAsistente = fragmentView.findViewById(R.id.btnBorrarAsistente);
        }
    }

}

