package com.bitdubai.fermat_api.layer._12_middleware.discount_wallet.exceptions;

import com.bitdubai.fermat_api.layer._12_middleware.discount_wallet.enums.CreditFailedReasons;

/**
 * Created by ciencias on 3/24/15.
 */
public class CreditFailedException extends Exception  {

    private CreditFailedReasons reason;

    public CreditFailedException (CreditFailedReasons reason){
        this.reason = reason;
    }

    public CreditFailedReasons getReason(){
        return this.reason;
    }
}