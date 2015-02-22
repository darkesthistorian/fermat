package com.bitdubai.fermat_core.layer._4_user.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._4_user.User;

/**
 * Created by loui on 22/02/15.
 */

/**
 * This addon handles the relationship between one device user and several intra users. That means tha a device user
 * could have many online identities detached from its device identity. In our first implementation it will only have
 * one, but it is expected in the future to enable that functionality.
 * 
 * * * * *
 * * * * * *
 */

public class IntraUserAddonRoot implements Service, User,DealsWithEvents, DealsWithErrors,  Addon {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    @Override
    public void setEventManager(EventManager eventManager) {

    }
}