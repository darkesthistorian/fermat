package com.bitdubai.desktop.wallet_manager.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.desktop.InstalledDesktop;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.interfaces.CryptoDesktopSubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class WalletManagerSubAppSession implements FermatSession<SubApp,CryptoDesktopSubAppModuleManager> {

    /**
     * SubApp
     */
    SubApp subApp;


    /**
     * Active objects in wallet session
     */
    Map<String, Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     * Wallet Store Module
     */
    private CryptoDesktopSubAppModuleManager moduleManager;


    /**
     * Create a session for the Wallet Store SubApp
     *
     * @param subApp                  the SubApp type
     * @param errorManager             the error manager
     * @param moduleManager the module of this SubApp
     */
    public WalletManagerSubAppSession(SubApp subApp, ErrorManager errorManager, CryptoDesktopSubAppModuleManager moduleManager) {
        this.subApp = subApp;
        data = new HashMap<String, Object>();
        this.errorManager = errorManager;
        this.moduleManager = moduleManager;
    }

    /**
     * Create a session for the Wallet Store SubApp
     *
     * @param subApp the SubApp type
     */
    public WalletManagerSubAppSession(SubApp subApp) {
        this.subApp = subApp;
    }




    /**
     * Store any data you need to hold between the fragments of the sub app
     *
     * @param key    key to reference the object
     * @param object the object yo want to store
     */
    @Override
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    /**
     * Return the data referenced by the key
     *
     * @param key the key to access de data
     * @return the data you want
     */
    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    @Override
    public void removeData(String key) {
        data.remove(key);
    }

    /**
     * Return the Error Manager
     *
     * @return reference to the Error Manager
     */
    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    @Override
    public String getAppPublicKey() {
        return null;
    }

    @Override
    public List<FermatApp> getPosibleConnections() {
        return null;
    }

    @Override
    public SubApp getFermatApp() {
        return subApp;
    }

    @Override
    public CryptoDesktopSubAppModuleManager getModuleManager() {
        return moduleManager;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WalletManagerSubAppSession that = (WalletManagerSubAppSession) o;

        return subApp == that.subApp;

    }

    @Override
    public int hashCode() {
        return subApp.hashCode();
    }

}
