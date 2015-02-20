package com.bitdubai.wallet_platform_api.layer._11_middleware;


import com.bitdubai.wallet_platform_api.layer._11_middleware.wallet.CantCreateWalletException;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public interface WalletManager {

    public void loadWallet (UUID walletId);

    public void createWallet (UUID walletId) throws CantCreateWalletException;
}