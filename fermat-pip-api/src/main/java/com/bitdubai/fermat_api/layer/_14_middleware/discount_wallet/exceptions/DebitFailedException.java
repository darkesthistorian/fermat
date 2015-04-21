package com.bitdubai.fermat_api.layer._14_middleware.discount_wallet.exceptions;

import com.bitdubai.fermat_api.layer._14_middleware.discount_wallet.enums.DebitFailedReasons;

/**
 * Created by ciencias on 3/24/15.
 */
public class DebitFailedException extends Exception  {

    private DebitFailedReasons reason;

    public DebitFailedException (DebitFailedReasons reason){
        this.reason = reason;
    }

    public DebitFailedReasons getReason(){
        return this.reason;
    }
}