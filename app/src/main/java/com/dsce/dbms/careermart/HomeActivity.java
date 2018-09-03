package com.dsce.dbms.careermart;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dsce.dbms.careermart.BottomNavigationFragments.HomeFragment;
import com.dsce.dbms.careermart.BottomNavigationFragments.UserFragment;
import com.dsce.dbms.careermart.BottomNavigationFragments.SearchFragment;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private HomeScreenAdapter homescreen;
    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        tabs = (TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Home"));
        tabs.addTab(tabs.newTab().setText("Search"));
        tabs.addTab(tabs.newTab().setText("User"));
        tabs.getTabAt(0).setIcon(R.drawable.home);
        tabs.getTabAt(1).setIcon(R.drawable.search);
        tabs.getTabAt(2).setIcon(R.drawable.user);
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        homescreen = new HomeScreenAdapter(getSupportFragmentManager(),tabs.getTabCount());
        viewPager.setAdapter(homescreen);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    @Override
    public void onBackPressed() {

    }

}

class HomeScreenAdapter extends FragmentPagerAdapter {
    int nmTabs;
    public HomeScreenAdapter(FragmentManager fm , int Numtabs) {
        super(fm);
        this.nmTabs = Numtabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                HomeFragment home = new HomeFragment();
                return  home;
            case 1:
                SearchFragment search = new SearchFragment();
                return search;
            case 2:
                UserFragment user = new UserFragment();
                return user;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return nmTabs;
    }
}
