package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetIssuer SubApp Session
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class AssetUserSession extends AbstractFermatSession<InstalledWallet,AssetUserWalletSubAppModuleManager,WalletResourcesProviderManager> {

    private WalletResourcesProviderManager resourceManager;
    private AssetUserWalletSubAppModuleManager walletManager;
    private InstalledWallet installedWallet;
    private ErrorManager errorManager;
    private Map<String, Object> data;

    private WalletSettings settings;

    /**
     * Constructor
     *
     * @param resourceManager Wallet Resource manager
     * @param errorManager    Error Manager
     * @param manager         AssetIssuerWallet Manager
     */
    public AssetUserSession(WalletResourcesProviderManager resourceManager, InstalledWallet installedWallet, ErrorManager errorManager, AssetUserWalletSubAppModuleManager manager) {
        super(installedWallet.getWalletPublicKey(), installedWallet, errorManager, manager, null);
        this.installedWallet = installedWallet;
        data = new HashMap<String, Object>();
        this.errorManager = errorManager;
        this.walletManager = manager;
        this.resourceManager = resourceManager;
    }

    public AssetUserSession() {
        data = new HashMap<String, Object>();
        installedWallet = null;
    }

    public InstalledWallet getWalletSessionType() {
        return installedWallet;
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key, object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }


    public WalletResourcesProviderManager getWalletResourcesProviderManager() {
        return resourceManager;
    }

    public AssetUserWalletSubAppModuleManager getWalletManager() {
        return walletManager;
    }

    public void setSettings(WalletSettings settings) {
        this.settings = settings;
    }
}
