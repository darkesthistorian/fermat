package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.structure;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.AbstractCommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure.CommunicationRegistrationProcessNetworkServiceAgent</code>
 * <p/>
 * Created by lnacosta - (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Deprecated
public class CommunicationRegistrationProcessNetworkServiceAgent extends AbstractCommunicationRegistrationProcessNetworkServiceAgent {

    public CommunicationRegistrationProcessNetworkServiceAgent(final AbstractNetworkService         networkServicePluginRoot      ,
                                                               final WsCommunicationsCloudClientManager communicationsClientConnection) {

        super(networkServicePluginRoot, communicationsClientConnection);
    }

}
