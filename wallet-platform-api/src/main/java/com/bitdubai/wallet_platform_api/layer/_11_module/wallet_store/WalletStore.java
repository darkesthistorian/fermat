package com.bitdubai.wallet_platform_api.layer._11_module.wallet_store;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public interface WalletStore {
    
    public void recordInstalledWallet (UUID walletId) throws CantRecordInstalledWalletException;
    
    public void recordUninstalledwallet (UUID walletId) throws CantRecordUninstalledWalletException;
    
    
}