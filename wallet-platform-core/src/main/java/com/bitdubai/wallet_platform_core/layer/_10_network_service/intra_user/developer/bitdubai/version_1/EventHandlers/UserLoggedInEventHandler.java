package com.bitdubai.wallet_platform_core.layer._10_network_service.intra_user.developer.bitdubai.version_1.EventHandlers;

import com.bitdubai.wallet_platform_api.layer._10_network_service.intra_user.IntraUserManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 19/02/15.
 */
public class UserLoggedInEventHandler implements EventHandler{
    IntraUserManager intraUserManager;
    
    public void setIntraUserManager(IntraUserManager intraUserManager){
        this.intraUserManager = intraUserManager;
        
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
    }
}