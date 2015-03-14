package com.bitdubai.fermat_core.layer._12_middleware.money_request.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._12_middleware.MiddlewareNotStartedException;
import com.bitdubai.fermat_api.layer._12_middleware.money_request.MoneyRequestManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._9_crypto.address_book.exceptions.ExampleException;

/**
 * Created by loui on 23/02/15.
 */
public class IncomingMoneyRequestRejectedEventHandler implements EventHandler {

    MoneyRequestManager moneyRequestManager;

    public void setMoneyRequestManager(MoneyRequestManager moneyRequestManager){
        this.moneyRequestManager = moneyRequestManager;

    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        if (((Service) this.moneyRequestManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.moneyRequestManager.exampleMethod();
            }
            catch (ExampleException exampleException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateCryptoWalletException: "+ exampleException.getMessage());
                exampleException.printStackTrace();

                throw  exampleException;
            }
        }
        else
        {
            throw new MiddlewareNotStartedException();
        }
    }
}