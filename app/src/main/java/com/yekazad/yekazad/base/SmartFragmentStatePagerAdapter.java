//Bismillahirrahmanirahim
//Elhamdulillahirabbulalemin
//Esselatu vesselamu alaa seyyidina Muhammedin ve alaa alihi ve sahbihi ecmain

package com.yekazad.yekazad.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohamed El Sayed
 */
/*
Extension of FragmentStatePagerAdapter which intelligently caches
all active fragments and manages the fragment lifecycles.
Usage involves extending from SmartFragmentStatePagerAdapter as you would any other PagerAdapter.
*/

public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private Map<Integer, Fragment> registeredFragments = new HashMap<>();

    public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Register the fragment when the item is instantiated
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}