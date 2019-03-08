package sv.company.give.cruzrojaguardavidas.no_usado;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import sv.company.give.cruzrojaguardavidas.core.RecyclerViewAdapter;

public class PosiblesFuturosUsos {

    //empieza funcion para llamar en un periodo de tiempo
    /*final Handler handler = new Handler();
    Timer timer;

    public void llamar(final RecyclerView.ViewHolder holder) {
        timer  = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                @SuppressLint("StaticFieldLeak") AsyncTask mytask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(context, "siii", Toast.LENGTH_SHORT).show();
                                holder.clSeleccionItem.setBackgroundResource(0);

                                timer.cancel();
                            }
                        });

                        return null;
                    }
                };
                mytask.execute();
            }
        };
        timer.schedule(task, 500, 10);
    }*/
    //termina

}
