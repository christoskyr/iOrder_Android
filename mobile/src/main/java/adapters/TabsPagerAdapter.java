package adapters;

/**
 * Created by вягстос on 17/4/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.DealsFragment;
import fragments.MyOrderFragment;
import fragments.ProductsFragment;
import fragments.StoresFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Stores fragment activity
                return new StoresFragment();
            case 1:
                // Deals fragment activity
                return new DealsFragment();
            case 2:
                //My Order fragment Activity
                return new MyOrderFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return NUM_ITEMS;
    }

}
