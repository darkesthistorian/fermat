package com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventType;

import java.util.UUID;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedOutEvent implements PlatformEvent {

    private UUID userId;
    private EventType eventType;
    private EventSource eventSource;

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public UserLoggedOutEvent(EventType eventType) {
        this.eventType = eventType;
    }


    @Override
    public EventType getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }


}