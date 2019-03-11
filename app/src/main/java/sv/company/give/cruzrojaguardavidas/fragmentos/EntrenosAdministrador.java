package sv.company.give.cruzrojaguardavidas.fragmentos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sv.company.give.cruzrojaguardavidas.R;
import sv.company.give.cruzrojaguardavidas.core.Adapter;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.AgregarEntreno;
import sv.company.give.cruzrojaguardavidas.fragmentos.child_fragments.ListadoEntrenos;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntrenosAdministrador extends Fragment {
    //Variable que guardara la cookie en el fragment al recibirla
    static String cookie = "";

    public EntrenosAdministrador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entrenos_administrador, container, false);

        //Despues de crear la vista se llama al metodo getArguments
        // que obtiene los parametros mandados a la hora de crear el fragment en PRINCIPAL
        //y por ultimo se almacena en la variable local cookie para ser usada como tercer parametro en la conexion
        cookie = getArguments().getString("cookie");


        ViewPager viewPagerEntrenosAdmin = rootView.findViewById(R.id.viewpagerEntrenosAdmin);
        setupViewPager(viewPagerEntrenosAdmin);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabsEntrenosAdmin);
        tabLayout.setupWithViewPager(viewPagerEntrenosAdmin);
        return rootView;
    }

    //Funciones para la carga de fragmentos en las tabs


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager(), cookie);
        adapter.addFragment(new AgregarEntreno(), "Agregar entrenos");
        adapter.addFragment(new ListadoEntrenos(), "Listado de entrenos");
        viewPager.setAdapter(adapter);
    }
}
