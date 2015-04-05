package com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.discount_wallet.enums.AccountStatus;

/**
 * Created by ciencias on 3/24/15.
 */
class MiddlewareDatabaseConstants {
    /**
     * Fiat Accounts database table definition.
     */
    static final String ACCOUNTS_TABLE_NAME = "FiatAccounts";
    static final String ACCOUNTS_TABLE_ID_COLUMN_NAME = "Id";
    static final String ACCOUNTS_TABLE_LABEL_COLUMN_NAME = "Label";
    static final String ACCOUNTS_TABLE_NAME_COLUMN_NAME = "Name";
    static final String ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME = "FiatCurrency";
    static final String ACCOUNTS_TABLE_STATUS_COLUMN_NAME = "Status";

    static final String ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE = "label";
    static final String ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE = "name";
    static final String ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE = AccountStatus.CREATED.getCode();

    /**
     * Transfers database table definition.
     */
    static final String TRANSFERS_TABLE_NAME = "Transfers";
    static final String TRANSFERS_TABLE_ID_COLUMN_NAME = "Id";
    static final String TRANSFERS_TABLE_MEMO_COLUMN_NAME = "Memo";
    static final String TRANSFERS_TABLE_ID_ACCOUNT_FROM_COLUMN_NAME = "IdAccountFrom";
    static final String TRANSFERS_TABLE_AMOUNT_FROM_COLUMN_NAME = "AmountFrom";
    static final String TRANSFERS_TABLE_ID_ACCOUNT_TO_COLUMN_NAME = "IdAccountTo";
    static final String TRANSFERS_TABLE_AMOUNT_TO_COLUMN_NAME = "AmountTo";
    static final String TRANSFERS_TABLE_TIME_STAMP_COLUMN_NAME = "TimeStamp";

    /**
     * Value Chunks database table definition.
     */
    static final String VALUE_CHUNKS_TABLE_NAME = "ValueChunks";
    static final String VALUE_CHUNKS_TABLE_ID_COLUMN_NAME = "Id";
    static final String VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME = "Status";
    static final String VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME = "IdParent";
    static final String VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME = "FiatCurrency";
    static final String VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME = "FiatAmount";
    static final String VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME = "CryptoCurrency";
    static final String VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME = "CryptoAmount";
    static final String VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME = "IdAccount";
    static final String VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME = "TimeStamp";
    static final String VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME = "IdCredit";
    static final String VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME = "IdDebit";
    static final String VALUE_CHUNKS_TABLE_ID_TRANSFER_COLUMN_NAME = "IdTransfer";

    /**
     * Debits database table definition.
     */
    static final String DEBITS_TABLE_NAME = "Debits";
    static final String DEBITS_TABLE_ID_COLUMN_NAME = "Id";
    static final String DEBITS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME = "IdAccount";
    static final String DEBITS_TABLE_FIAT_AMOUNT_COLUMN_NAME = "FiatAmount";
    static final String DEBITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME = "CryptoAmount";
    static final String DEBITS_TABLE_TIME_STAMP_COLUMN_NAME = "TimeStamp";

    /**
     * Credits database table definition.
     */
    static final String CREDITS_TABLE_NAME = "Credits";
    static final String CREDITS_TABLE_ID_COLUMN_NAME = "Id";
    static final String CREDITS_TABLE_ID_ACCOUNT_COLUMN_NAME = "IdAccount";
    static final String CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME = "FiatAmount";
    static final String CREDITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME = "CryptoAmount";
    static final String CREDITS_TABLE_TIME_STAMP_COLUMN_NAME = "TimeStamp";
    
}