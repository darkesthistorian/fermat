package com.bitdubai.wallet_platform_core.layer._2_platform_service.event_manager;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.PlatformServiceSubsystem;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.event_manager.developer.DeveloperBitDubai;

/**
 * Created by ciencias on 23.01.15.
 */
public class EventManagerSubsystem implements PlatformServiceSubsystem {

    Service mEventManager;

    @Override
    public Service getService() {
        return mEventManager;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mEventManager = developerBitDubai.getEventManager();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}