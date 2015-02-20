package com.bitdubai.wallet_platform_api.layer._11_middleware.wallet;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public interface CryptoAccount {
    public Double getBalance();

    public CryptoCurrency getCryptoCurrency() ;

    public String getLabel() ;

    public String getName() ;

    public void setLabel(String label) ;

    public void setName(String name) ;
}