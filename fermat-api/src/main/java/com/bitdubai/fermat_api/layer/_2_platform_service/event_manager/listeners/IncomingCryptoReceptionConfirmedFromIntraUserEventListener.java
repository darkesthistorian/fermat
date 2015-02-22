package com.bitdubai.fermat_api.layer._2_platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer._1_definition.event.EventMonitor;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventType;

/**
 * Created by loui on 19/02/15.
 */
public class IncomingCryptoReceptionConfirmedFromIntraUserEventListener implements EventListener {

    EventMonitor eventMonitor;
    private EventType eventType;
    private EventHandler eventHandler;

    public IncomingCryptoReceptionConfirmedFromIntraUserEventListener(EventType eventType, EventMonitor eventMonitor){
        this.eventType = eventType;
        this.eventMonitor = eventMonitor;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void getEventHandler() {

    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) {

        try
        {
            this.eventHandler.handleEvent(platformEvent);
        }
        catch (Exception exception)
        {
            eventMonitor.handleEventException(exception, platformEvent);
        }

    }
}