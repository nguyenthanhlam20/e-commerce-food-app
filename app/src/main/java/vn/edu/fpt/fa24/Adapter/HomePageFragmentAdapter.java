package vn.edu.fpt.fa24.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vn.edu.fpt.fa24.Fragments.HomeFragment;
import vn.edu.fpt.fa24.Fragments.ProfileSettingsFragment;


@SuppressWarnings("ALL")
public class HomePageFragmentAdapter extends FragmentPagerAdapter {

    public HomePageFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ProfileSettingsFragment();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
