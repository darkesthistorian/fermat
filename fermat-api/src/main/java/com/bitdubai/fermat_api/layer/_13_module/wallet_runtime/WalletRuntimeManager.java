package com.bitdubai.fermat_api.layer._13_module.wallet_runtime;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public interface WalletRuntimeManager {
    
    public void recordOpenedWallet (UUID walletId) throws CantRecordOpenedWalletException; 
    
    public void recordClosedWallet (UUID walletId) throws CantRecordClosedWalletException;
    
}