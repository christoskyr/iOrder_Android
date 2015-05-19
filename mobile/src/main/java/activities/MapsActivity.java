package activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.app.ActionBar.Tab;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import adapters.TabsPagerAdapter;
import fragments.VersionFragment;
import mycompany.iorder.R;
import activities.TopUpActivity;

public class MapsActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabsPagerAdapter mAdapter;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence drawerTitle;
    private CharSequence title;

    String[] andoridVeriosnArray;

    // Tab titles
    private String[] tabs = { "Stores", "Deals"};

    @Override
    public  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getActionBar().setIcon(android.R.color.transparent);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getActionBar().setTitle(Html.fromHtml("<font color='#951A3F'>iOrder</font>"));

//        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getActionBar().setCustomView(R.layout.actionbar);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        title = drawerTitle = getTitle();

        andoridVeriosnArray = new String[] { "Top Up", "Alarm", "Coffee Beans", "Favourite Products",
                "Favourite Stores", "Previous Orders", "Log Out"};

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);

        // Set shadow to drawer
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Set adapter to drawer
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, andoridVeriosnArray));

        // set up click listener on drawer
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // set app icon to behave as action to toggle navigation drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBarDrawerToggle ties together the the interactions the sliding
        // drawer and the app icon
        drawerToggle = new ActionBarDrawerToggle(this, // Host Activity
                drawerLayout, // layout container for navigation drawer
                R.drawable.ic_drawer, // Application Icon
                R.string.drawer_open, // Open Drawer Description
                R.string.drawer_close) // Close Drawer Description
        {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

//        if (savedInstanceState == null) {
//            selectItem(0);
//        }
   }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Listen Toggle state of navigation Drawer
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    // Click Event of navigation drawer item click
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            drawerLayout.closeDrawers();
            Bundle args = new Bundle();
            args.putString("name", andoridVeriosnArray[position]);
            Intent i = new Intent(MapsActivity.this, TopUpActivity.class);
            switch(position){
                case 0:
                    i = new Intent(MapsActivity.this, TopUpActivity.class);
                    break;
                case 1:
                    i = new Intent(MapsActivity.this, AlarmActivity.class);
                    break;
                case 2:
                    i = new Intent(MapsActivity.this, CoffeeBeansActivity.class);
                    break;
            }
            //i.putExtra(args);
            startActivity(i);
            //selectItem(position);
        }
    }

    /*private void selectItem(int position) {

        // Set Fragment text accordingly
        Fragment fragment = new VersionFragment();
        Bundle args = new Bundle();
        args.putString("name", andoridVeriosnArray[position]);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fragment).commit();

            // Update Title on action bar
            drawerList.setItemChecked(position, true);
            //setTitle(andoridVeriosnArray[position]);
            drawerLayout.closeDrawer(drawerList);
    }*/

//    @Override
//    public void setTitle(CharSequence _title) {
//        title = _title;
//        getActionBar().setTitle(title);
//
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        Log.d("onTabReselected", String.valueOf(tab.getPosition()));
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        Log.d("onTabSelected", String.valueOf(tab.getPosition()));
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        Log.d("onTabUnselected", String.valueOf(tab.getPosition()));
    }


}