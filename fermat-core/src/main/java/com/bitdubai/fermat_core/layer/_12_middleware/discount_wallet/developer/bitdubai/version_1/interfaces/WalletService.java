package com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.exceptions.CantStartWalletServiceException;

/**
 * Created by ciencias on 3/26/15.
 */
public interface WalletService {

    public void start() throws CantStartWalletServiceException;

    public void stop();
}