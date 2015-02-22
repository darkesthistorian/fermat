package com.bitdubai.fermat_api.layer._11_middleware.app_runtime;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface Activity {

    public Activities getType();

    public Map<Fragments, Fragment> getFragments();

    public TitleBar getTitleBar() ;

    public SideMenu getSideMenu() ;

    public MainMenu getMainMenu() ;

    public TabStrip getTabStrip() ;

}