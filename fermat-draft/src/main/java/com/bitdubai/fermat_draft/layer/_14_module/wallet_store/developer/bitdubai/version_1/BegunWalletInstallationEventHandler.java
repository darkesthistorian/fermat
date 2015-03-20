package com.bitdubai.fermat_plugin.layer._14_module.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._14_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.layer._14_module.wallet_store.CantRecordInstalledWalletException;
import com.bitdubai.fermat_api.layer._14_module.wallet_store.WalletStore;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.WalletInstalledEvent;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class BegunWalletInstallationEventHandler implements EventHandler {
    WalletStore walletStore;

    public void setWalletStore (WalletStore walletStore){
        this.walletStore = walletStore;
    }
    
    @Override
    public void raiseEvent(PlatformEvent platformEvent) throws Exception {
        
        UUID walletId = ((BegunWalletInstallationEvent)platformEvent).getWalletId();
        
        
        if(((Service) this.walletStore).getStatus() == ServiceStatus.STARTED) {
            
            try
            {
                this.walletStore.recordInstalledWallet(walletId);
            }
            catch (CantRecordInstalledWalletException cantRecordInstalledWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantRecordInstalledWalletException: " + cantRecordInstalledWalletException.getMessage());
                cantRecordInstalledWalletException.printStackTrace();

                throw cantRecordInstalledWalletException;
                
            }
            
        }
        else
        {
            throw new ModuleNotRunningException();
        }
    }
}