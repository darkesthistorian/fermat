package com.bitdubai.fermat_core.layer._7_world.blockchain_info.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._7_world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 12/03/15.
 */

/**
 * Blockchain Plugin. 
 */

public class BlockchainInfoWorldPluginRoot implements Service, World,DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin{

    /**
     * Service Interface member variables. 
     */
    
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * UsesFileSystem Interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithEvents Interface member variables. 
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables. 
     */
    UUID pluginId;
    
    @Override
    public void start() {
        /**
         * I will initialize the handling od platform events. 
         */
        
        EventListener eventListener;
        EventHandler eventHandler;
        
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
    public void stop(){

        /**
         * I will remove all the event listeners registered with the event manager.
         */
        
        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }
        
        listenersAdded.clear();
        
        this.serviceStatus = ServiceStatus.STOPPED;
    }
    
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;        
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

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


}