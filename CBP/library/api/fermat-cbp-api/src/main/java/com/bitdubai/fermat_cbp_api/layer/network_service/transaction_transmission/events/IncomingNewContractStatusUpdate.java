package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events;


import com.bitdubai.fermat_cbp_api.all_definition.events.AbstractCBPFermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 */
public final class IncomingNewContractStatusUpdate extends AbstractCBPFermatEvent {

    private NetworkService destinationNetworkService;

    public IncomingNewContractStatusUpdate(EventType eventType) {
        super(eventType);
    }

    public NetworkService getDestinationNetworkService(){
        return this.destinationNetworkService;
    }

    public void setDestinationNetworkService(NetworkService destinationNetworkService){
        this.destinationNetworkService=destinationNetworkService;
    }

}
