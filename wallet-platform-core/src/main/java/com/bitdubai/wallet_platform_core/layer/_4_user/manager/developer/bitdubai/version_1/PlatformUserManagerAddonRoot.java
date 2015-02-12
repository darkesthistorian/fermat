package com.bitdubai.wallet_platform_core.layer._4_user.manager.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.error_manager.ErrorManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.DealsWithFileSystem;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantCreateUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantLoadUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._4_user.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformUserManagerAddonRoot implements Service, UserManager,DealsWithFileSystem, DealsWithEvents, DealsWithErrors, Addon {

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus;
    List<EventListener> listenersAdded = new ArrayList<>();
        
    /**
     * UserManager Interface member variables.
     */
    User mLoggedInUser;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;


    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {

        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

       // eventListener = eventManager.getNewListener(EventType.USER_CREATED);
       // eventHandler = new UserCreatedEventHandler();
       // ((UserCreatedEventHandler) eventHandler).setWalletManager(this);
       // eventListener.setEventHandler(eventHandler);
       // eventManager.addListener(eventListener);
       // listenersAdded.add(eventListener);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    
    /**
     * UserManager Interface implementation.
     */

    @Override
    public User getLoggedInUser() {
        return mLoggedInUser;
    }

    @Override
    public User createUser() throws CantCreateUserException {

        try
        {
            User user = new PlatformUser();
            ((DealsWithFileSystem) user).setPluginFileSystem(this.pluginFileSystem);
            user.createUser();

            return user;
        }
        catch (CantCreateUserException cantCreateUserException)
        {
            /**
             * This is bad, the only thing I can do is to throw the exception again.
             */
            System.err.println("CantPersistUserException: " + cantCreateUserException.getMessage());
            cantCreateUserException.printStackTrace();
            throw cantCreateUserException;
        }

    }

    @Override
    public void loadUser(UUID id) throws CantLoadUserException  {

        try
        {
            User user = new PlatformUser();
            ((DealsWithFileSystem) user).setPluginFileSystem(this.pluginFileSystem);
            user.loadUser(id);

            mLoggedInUser = user;
        }
        catch (CantLoadUserException cantLoadUserException)
        {
            /**
             * This is bad, the only thing I can do is to throw the exception again.
             */
            System.err.println("CantLoadUserException: " + cantLoadUserException.getMessage());
            cantLoadUserException.printStackTrace();
            throw cantLoadUserException;
        }

    }

    
    
    
    
    
    


    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

}