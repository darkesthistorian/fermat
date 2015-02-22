package com.bitdubai.fermat_core.layer._11_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_middleware.wallet.CryptoAccount;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 2/15/15.
 */
public class MiddlewareCryptoAccount implements CryptoAccount{

    Double balance;
    CryptoCurrency cryptoCurrency;
    String label;
    String name;

    public MiddlewareCryptoAccount (CryptoCurrency cryptoCurrency){
        this.cryptoCurrency = cryptoCurrency;
    }

    public Double getBalance() {
        return balance;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }
}