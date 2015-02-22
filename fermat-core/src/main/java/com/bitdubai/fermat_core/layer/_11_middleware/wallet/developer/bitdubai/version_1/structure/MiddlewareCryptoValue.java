package com.bitdubai.fermat_core.layer._11_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_middleware.wallet.CryptoValue;
import com.bitdubai.fermat_api.layer._11_middleware.wallet.CryptoValueChunk;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

import java.util.List;

/**
 * Created by ciencias on 2/15/15.
 */
public class MiddlewareCryptoValue implements CryptoValue{

    Double balance;
    CryptoCurrency cryptoCurrency;
    List<CryptoValueChunk> cryptoValueChunks;

    public MiddlewareCryptoValue (CryptoCurrency cryptoCurrency){
        this.cryptoCurrency = cryptoCurrency;
    }

    public Double getBalance() {
        return balance;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }
}