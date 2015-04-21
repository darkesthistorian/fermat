package com.bitdubai.android_core.app.common.version_1.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.SideMenu;
import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.App;
import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.SubApp;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.smartwallet.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;
import com.bitdubai.android_core.app.common.version_1.classes.NavigationDrawerArrayAdapter;
import com.bitdubai.android_core.app.subapp.publisher.version_1.activity.PublisherActivity;
import com.bitdubai.android_core.app.subapp.publisher.version_1.activity.ShopsActivity;
import com.bitdubai.android_core.app.subapp.wallet_factory.version_2.activity.FactoryActivity;


import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {
    private AppRuntimeManager appRuntimeMiddleware;
    private App app;
    private SubApp subApp;
    private com.bitdubai.fermat_api.layer._14_middleware.app_runtime.Activity activity;
    private CorePlatformContext platformContext;
    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.wallet_framework_fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        //create menu option based activity submenu definition
        Platform platform = MyApplication.getPlatform();

        this.platformContext = platform.getCorePlatformContext();

        this.appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);

        String[] menuOption = new String[]{} ;

        mDrawerListView.setAdapter(new NavigationDrawerArrayAdapter(
                getActivity(),
                menuOption));

       /* if (MyApplication.getActivityId() == "DesktopActivity") {



        }
        else if (MyApplication.getActivityId() == "PublisherActivity"){
            mDrawerListView.setAdapter(new NavigationDrawerArrayAdapter(
                    getActivity(),
                    new String[]{
                            getString(R.string.title_section1),
                            getString(R.string.title_section17),

                    }));
        }else{
            mDrawerListView.setAdapter(new NavigationDrawerArrayAdapter(
                    getActivity(),
                    new String[]{
                            getString(R.string.title_section1),
                            getString(R.string.title_section2),
                            getString(R.string.title_section3),
                            getString(R.string.title_section4),
                            getString(R.string.title_section5),
                            getString(R.string.title_section6),
                            getString(R.string.title_section7),
                            getString(R.string.title_section8),
                            getString(R.string.title_section9),
                            getString(R.string.title_section10),
                            getString(R.string.title_section11),
                    }));
        }*/



        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout,SideMenu sideMenu) {

        //create menu option based activity submenu definition


        List<String> menuOption = new ArrayList<String>();
        List<com.bitdubai.fermat_api.layer._14_middleware.app_runtime.MenuItem> menuItem = new ArrayList<>();

            if(sideMenu !=null)
            {
                 menuItem = sideMenu.getMenuItems();
                for (int i = 0; i < menuItem.size(); i++) {

                    com.bitdubai.fermat_api.layer._14_middleware.app_runtime.MenuItem menu = menuItem.get(i);
                    menuOption.add(menu.getLabel());
                }

             }

            mDrawerListView.setAdapter(new NavigationDrawerArrayAdapter(
                    getActivity(),
                    menuOption.toArray(new String[menuItem.size()])));

        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        if(MyApplication.getActivityId()=="DesktopActivity") {

            if (position == 3) {
                Intent intent;
                intent = new Intent(super.getActivity(), FactoryActivity.class);
                startActivity(intent);
            }
            if (position == 4){
                Intent intent;
                intent = new Intent(super.getActivity(), PublisherActivity.class);
                startActivity(intent);
            }
        }else if(MyApplication.getActivityId()=="PublisherActivity") {
            if (position == 1)
            {
                Intent intent;
                intent = new Intent(super.getActivity(), ShopsActivity.class);

                startActivity(intent);
            }
        }else {
            if(MyApplication.getActivityId()=="FactoryActivity") {

                if (position == 3) {
                    Intent intent;
                    intent = new Intent(super.getActivity(), FactoryActivity.class);
                    startActivity(intent);
                }
            }else
            {
                if (position == 1) {
                    Intent intent;
                    //  intent = new Intent(super.getActivity(), ContactsActivity.class);
                    //startActivity(intent);
                }
            }

        }

    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //*** CAST EXCEPTION ! mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }






    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the wallet_framework_activity_framework_drawer_open_menu app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.wallet_framework_activity_framework_drawer_open_menu, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the wallet_framework_activity_framework_drawer_open_menu app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }


    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}