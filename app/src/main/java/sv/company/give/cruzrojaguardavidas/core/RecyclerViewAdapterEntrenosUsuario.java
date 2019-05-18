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
import java.util.Calendar;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.ListadoEntrenos;

public class RecyclerViewAdapterEntrenosUsuario extends RecyclerView.Adapter<RecyclerViewAdapterEntrenosUsuario.ViewHolder> {

    private ArrayList<String[]> listArraysEntrenos;
    private ArrayList<String> listArraysEntrenosFechas;
    private Context mContext;

    public RecyclerViewAdapterEntrenosUsuario(Context context, ArrayList<String[]> arrayListEntrenos, View viewFragmento) {
        listArraysEntrenos = arrayListEntrenos;
        mContext = context;
        listArraysEntrenosFechas=new ArrayList<>();

        crearEntrenos();
    }

    private void crearEntrenos() {
        //creando los datos de los entrenos. Cabe recalcar que esta
        // funcion depende totalmente del telefono por lo tanto es necesario que la fecha y hora sean las correctas
        Calendar fecha=Calendar.getInstance();

        for(int i=1; i<32;i++)
        {
            for (int j=0;j<listArraysEntrenos.size(); j++)
            {
                for(int q=0; q<listArraysEntrenos.get(j)[0].length();q++)
                {
                    if(String.valueOf(fecha.get(Calendar.DAY_OF_WEEK)).equals(String.valueOf(listArraysEntrenos.get(j)[0].charAt(q))))
                    {
                        try {
                            listArraysEntrenosFechas.add("En "+listArraysEntrenos.get(j)[2]+" el "+Funciones.obtenerDiaSemana(fecha.get(Calendar.YEAR)
                                    +"-"+fecha.get(Calendar.MONTH)+"-"+fecha.get(Calendar.DAY_OF_MONTH))+" a las "+listArraysEntrenos.get(j)[1]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            fecha.add(Calendar.DAY_OF_MONTH,1);
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
        holder.tvInformacionEntreno.setText(listArraysEntrenosFechas.get(position));

        //Aqui se cambia el tipo de borde
        if (position == 0)
            holder.clItemListaEntrenos.setBackground(mContext.getResources().getDrawable((int) R.layout.borde_superior));
        else
            holder.clItemListaEntrenos.setBackground(mContext.getResources().getDrawable((int) R.layout.borde));
    }

    @Override
    public int getItemCount() {
        return listArraysEntrenosFechas.size();
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

