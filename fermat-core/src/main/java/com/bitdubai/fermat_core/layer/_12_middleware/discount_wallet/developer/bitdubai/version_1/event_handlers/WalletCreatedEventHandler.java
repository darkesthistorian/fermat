package com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._12_middleware.MiddlewareNotStartedException;
import com.bitdubai.fermat_api.layer._12_middleware.discount_wallet.interfaces.DiscountWalletManager;
import com.bitdubai.fermat_api.layer._12_middleware.discount_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.WalletCreatedEvent;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public class WalletCreatedEventHandler implements EventHandler {

    DiscountWalletManager discountWalletManager;

    public void setWalletmanager ( DiscountWalletManager discountWalletManager){
        this.discountWalletManager = discountWalletManager;
    }

    @Override
    public  void handleEvent(PlatformEvent platformEvent) throws Exception {
        UUID walletId = ((WalletCreatedEvent) platformEvent).getWalletId();


        if (((Service) this.discountWalletManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.discountWalletManager.createWallet(walletId);
            }
            catch (CantCreateWalletException cantCreateWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateCryptoWalletException: "+ cantCreateWalletException.getMessage());
                cantCreateWalletException.printStackTrace();

                throw  cantCreateWalletException;
            }
        }
        else
        {
            throw new MiddlewareNotStartedException();
        }
    }
    
}