package com.bitdubai.wallet_platform_draft.layer._7_crypto_network.litecoin;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworkSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class LitecoinSubsystem implements CryptoNetworkSubsystem {
    @Override
    public void start() throws CantStartSubsystemException {
        throw new CantStartSubsystemException();
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

}