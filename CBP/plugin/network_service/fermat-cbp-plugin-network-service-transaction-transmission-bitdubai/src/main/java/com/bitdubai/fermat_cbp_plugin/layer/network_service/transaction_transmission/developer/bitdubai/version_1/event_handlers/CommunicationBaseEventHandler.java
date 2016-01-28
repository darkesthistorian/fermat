package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 */
public class CommunicationBaseEventHandler extends AbstractCommunicationBaseEventHandler<CompleteComponentConnectionRequestNotificationEvent> {
    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public CommunicationBaseEventHandler(AbstractNetworkService networkService) {
        super(networkService);
    }

    @Override
    public void processEvent(CompleteComponentConnectionRequestNotificationEvent event) {
        //TODO: to process
        //System.out.println("Transaction Transmission - CommunicationBaseEventHandler:\n"+event);
    }
}
