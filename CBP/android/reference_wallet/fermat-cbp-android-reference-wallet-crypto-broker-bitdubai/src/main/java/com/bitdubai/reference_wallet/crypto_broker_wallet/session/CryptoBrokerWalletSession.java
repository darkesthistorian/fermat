package com.bitdubai.reference_wallet.crypto_broker_wallet.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.preference_settings.CryptoBrokerWalletPreferenceSettings;

public class CryptoBrokerWalletSession extends AbstractFermatSession<InstalledWallet, CryptoBrokerWalletModuleManager, WalletResourcesProviderManager> {
    public static final String NEGOTIATION_DATA = "negotiation_data";
    public static final String CONTRACT_DATA = "contract_data";
    public static final String CONFIGURED_DATA = "configured_data";
    public static final String LOCATION_LIST = "list_of_new_location";

    public CryptoBrokerWalletSession() {
    }

}
