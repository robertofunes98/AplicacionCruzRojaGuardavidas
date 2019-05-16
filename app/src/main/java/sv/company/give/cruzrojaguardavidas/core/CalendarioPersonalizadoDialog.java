package sv.company.give.cruzrojaguardavidas.core;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Locale;

import sv.company.give.cruzrojaguardavidas.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CalendarioPersonalizadoDialog extends DialogFragment {

    TextView tvMesAnyo;
    ArrayList<TextView> listTexctViews;
    boolean[] diasSeleccionados;

    public static int EDITAR=1;

    int posicion;
    int funcion;
    static int mesEditando;

    //Dias
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18,
            tv19, tv20, tv21, tv22, tv23, tv24, tv25, tv26, tv27, tv28, tv29, tv30, tv31, tv32, tv33, tv34, tv35, tv36, tv37, tv38, tv39, tv40, tv41, tv42;

    Button btnCancelar, btnAceptar;


    public CalendarioPersonalizadoDialog(int funcionR, int mes) {
        // Required empty public constructor
        posicion=mes;
        funcion=funcionR;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.dialog_fragment_calendario_personalizado, container, false);

        //inicializando variables
        listTexctViews = new ArrayList<>();

        //Vinculando tvs
        tvMesAnyo = rootview.findViewById(R.id.tvMesAnyo);

        tv1 = rootview.findViewById(R.id.tv1);
        tv2 = rootview.findViewById(R.id.tv2);
        tv3 = rootview.findViewById(R.id.tv3);
        tv4 = rootview.findViewById(R.id.tv4);
        tv5 = rootview.findViewById(R.id.tv5);
        tv6 = rootview.findViewById(R.id.tv6);
        tv7 = rootview.findViewById(R.id.tv7);
        tv8 = rootview.findViewById(R.id.tv8);
        tv9 = rootview.findViewById(R.id.tv9);
        tv10 = rootview.findViewById(R.id.tv10);
        tv11 = rootview.findViewById(R.id.tv11);
        tv12 = rootview.findViewById(R.id.tv12);
        tv13 = rootview.findViewById(R.id.tv13);
        tv14 = rootview.findViewById(R.id.tv14);
        tv15 = rootview.findViewById(R.id.tv15);
        tv16 = rootview.findViewById(R.id.tv16);
        tv17 = rootview.findViewById(R.id.tv17);
        tv18 = rootview.findViewById(R.id.tv18);
        tv19 = rootview.findViewById(R.id.tv19);
        tv20 = rootview.findViewById(R.id.tv20);
        tv21 = rootview.findViewById(R.id.tv21);
        tv22 = rootview.findViewById(R.id.tv22);
        tv23 = rootview.findViewById(R.id.tv23);
        tv24 = rootview.findViewById(R.id.tv24);
        tv25 = rootview.findViewById(R.id.tv25);
        tv26 = rootview.findViewById(R.id.tv26);
        tv27 = rootview.findViewById(R.id.tv27);
        tv28 = rootview.findViewById(R.id.tv28);
        tv29 = rootview.findViewById(R.id.tv29);
        tv30 = rootview.findViewById(R.id.tv30);
        tv31 = rootview.findViewById(R.id.tv31);
        tv32 = rootview.findViewById(R.id.tv32);
        tv33 = rootview.findViewById(R.id.tv33);
        tv34 = rootview.findViewById(R.id.tv34);
        tv35 = rootview.findViewById(R.id.tv35);
        tv36 = rootview.findViewById(R.id.tv36);
        tv37 = rootview.findViewById(R.id.tv37);
        tv38 = rootview.findViewById(R.id.tv38);
        tv39 = rootview.findViewById(R.id.tv39);
        tv40 = rootview.findViewById(R.id.tv40);
        tv41 = rootview.findViewById(R.id.tv41);
        tv42 = rootview.findViewById(R.id.tv42);

        btnAceptar=rootview.findViewById(R.id.btnAceptar);
        btnCancelar=rootview.findViewById(R.id.btnCancelar);

        //onclicks
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(funcion==EDITAR)
                {
                    dismiss();
                    diasSelected.guardarDisponibilidadEditada(mesEditando, getDiasSeleccionados());
                }
            }
        });

        //llenando arraylist

        Collections.addAll(listTexctViews, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18,
                tv19, tv20, tv21, tv22, tv23, tv24, tv25, tv26, tv27, tv28, tv29, tv30, tv31, tv32, tv33, tv34, tv35, tv36, tv37, tv38, tv39, tv40, tv41, tv42);


        crearCalendario();


        return rootview;
    }

    private void crearCalendario()
    {
        Calendar calendarioFecha = Calendar.getInstance();

        if(posicion==2)
        {
            calendarioFecha.set(Calendar.MONTH, calendarioFecha.get(Calendar.MONTH)+1);
        }

        mesEditando =(calendarioFecha.get(Calendar.MONTH)+1);

        tvMesAnyo.setText(Funciones.ucFirst(String.valueOf(calendarioFecha.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("es-ES"))))
                + " - " + calendarioFecha.get(Calendar.YEAR));


        Calendar auxFecha = new GregorianCalendar(calendarioFecha.get(Calendar.YEAR), calendarioFecha.get(Calendar.MONTH), 1);

        int j = auxFecha.get(Calendar.DAY_OF_WEEK) - 1, contadorDias = 0;

        for (int i = 0; i < listTexctViews.size(); i++) {
            if (i < j || contadorDias >= (auxFecha.getActualMaximum(Calendar.DAY_OF_MONTH))) {
                listTexctViews.get(i).setVisibility(View.INVISIBLE);
            } else {
                listTexctViews.get(i).setText(String.valueOf(contadorDias + 1));
                j++;
                contadorDias++;
            }
        }

        //este array se inicializa hasta aqui ya que se necesita la cantidad de dias del mes
        diasSeleccionados=new boolean[auxFecha.getActualMaximum(Calendar.DAY_OF_MONTH)];

        for (int i = auxFecha.get(Calendar.DAY_OF_WEEK) - 1; i < auxFecha.getActualMaximum(Calendar.DAY_OF_MONTH)+auxFecha.get(Calendar.DAY_OF_WEEK) - 1; i++) {
            final int I = i;

            final int contador=i-(auxFecha.get(Calendar.DAY_OF_WEEK) -1);
            listTexctViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(diasSeleccionados[contador])
                    {
                        listTexctViews.get(I).setBackground(null);
                        listTexctViews.get(I).setTextColor(Color.BLACK);
                        diasSeleccionados[contador]=false;
                    }
                    else
                    {
                        listTexctViews.get(I).setBackground(getResources().getDrawable(R.drawable.seleccion_dia));
                        listTexctViews.get(I).setTextColor(Color.WHITE);
                        diasSeleccionados[contador]=true;
                    }
                }
            });
        }
    }

    public String getDiasSeleccionados()
    {
        StringBuilder retorno= new StringBuilder();

        for (int i = 0; i<diasSeleccionados.length; i++)
        {
            if(diasSeleccionados[i] && revisarArrayDias(diasSeleccionados, i))
                retorno.append(i + 1).append(",");
            else if(diasSeleccionados[i])
            {
                retorno.append(i + 1);
            }
        }

        return retorno.toString();
    }

    private boolean revisarArrayDias(boolean[] array, int posicion)
    {
        posicion++;
        if(posicion<array.length)
        {
            if(array[posicion])
            {
                return true;
            }
            else
            {
                return revisarArrayDias(array, posicion);
            }
        }
        else {
            return false;
        }
    }


    public interface OnDiasSelected{
        void guardarDisponibilidadEditada(int mes, String dias);
    }

    public OnDiasSelected diasSelected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        diasSelected=(OnDiasSelected) getTargetFragment();
    }

}
