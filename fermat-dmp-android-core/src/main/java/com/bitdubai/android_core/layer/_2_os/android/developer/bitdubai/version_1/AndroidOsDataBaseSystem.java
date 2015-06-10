package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure.AndroidPlatformDatabaseSystem;
import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure.AndroidPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer._2_os.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;

/**
 * Created by toshiba on 20/05/2015.
 */
public class AndroidOsDataBaseSystem implements DataBaseSystemOs {
    /**
     * DataBaseSystemOs interface member variables.
     */

    PlatformDatabaseSystem platformDatabaseSystem;
    PluginDatabaseSystem pluginDatabaseSystem;
    Context context;

    /**
     * Constructor
     */

    public AndroidOsDataBaseSystem()
    {
        this.pluginDatabaseSystem = new AndroidPluginDatabaseSystem();
        this.platformDatabaseSystem = new AndroidPlatformDatabaseSystem();
    }


    /**
     * DatBaseSystemOs Interface implementation.
     */
    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return this.pluginDatabaseSystem;
    }

    @Override
    public PlatformDatabaseSystem getPlatformDatabaseSystem(){
        return this.platformDatabaseSystem;
    }

    @Override
    public void setContext (Object context)
    {
        this.context = (Context)context;
        this.pluginDatabaseSystem.setContext(context);
        this.platformDatabaseSystem.setContext(context);
    }

}