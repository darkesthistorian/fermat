package com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoBrokerWalletFragmentsEnumType implements FermatFragmentsEnumType<CryptoBrokerWalletFragmentsEnumType> {

    CBP_CRYPTO_BROKER_WALLET_REQUEST_TAB("CBPCBWHART"),
    CBP_CRYPTO_BROKER_WALLET_OPEN_DEALS_TAB("CBPCBWHAODT"),
    CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB("CBPCBWHAOCT"),
    CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS("CBPCBWHASS"),
    CBP_CRYPTO_BROKER_WALLET_DEAL_DETAILS("CBPCBWDD"),
    CBP_CRYPTO_BROKER_WALLET_CONTRACT_DETAILS("CBPCBWCD"),
    CBP_CRYPTO_BROKER_WALLET_DEALS("CBPCBWD"),
    CBP_CRYPTO_BROKER_WALLET_CONTRACTS("CBPCBWC"),
    CBP_CRYPTO_BROKER_WALLET_STOCK_PREFERENCE("CBPCBWSP"),
    CBP_CRYPTO_BROKER_WALLET_SETTINGS("CBPCBWS");

    private String key;

    CryptoBrokerWalletFragmentsEnumType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public String toString() {
        return key;
    }

    public static CryptoBrokerWalletFragmentsEnumType getValue(String name) {
        for (CryptoBrokerWalletFragmentsEnumType fragments : CryptoBrokerWalletFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
