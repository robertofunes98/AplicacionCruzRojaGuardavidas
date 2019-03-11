package sv.company.give.cruzrojaguardavidas.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    String cookie;

    public Adapter(FragmentManager fm, String cookieR) {
        super(fm);
        cookie=cookieR;
    }

    public void addFragment(Fragment fragment, String title) {
        // Creamos un nuevo Bundle
        Bundle args = new Bundle();

        // Colocamos el String
        args.putString("cookie", cookie);

        //Colocamos este nuevo Bundle como argumento en el fragmento.

        fragment.setArguments(args);

        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}