/*
 * @#RegistrationProcessNetworkServiceAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Deprecated
public class CommunicationRegistrationProcessNetworkServiceAgent<NS extends AbstractNetworkService> extends Thread {

    /*
     * Represent the sleep time for the read or send (5000 milliseconds)
     */
    private static final long SLEEP_TIME = 5000;
    private static final long MAX_SLEEP_TIME = 20000;

    /**
     * Represent the networkService
     */
    private NS networkService;

    /**
     * Represent the communicationsClientConnection
     */
    private WsCommunicationsCloudClientManager communicationsClientConnection;

    /**
     * Represent the active
     */
    private boolean active;

    /**
     * Constructor with parameters
     * @param networkService
     * @param communicationsClientConnection
     */
    public CommunicationRegistrationProcessNetworkServiceAgent(NS networkService, WsCommunicationsCloudClientManager communicationsClientConnection) {
        this.networkService = networkService;
        this.communicationsClientConnection = communicationsClientConnection;
        this.active = Boolean.FALSE;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        while (active){
            try{

                if (communicationsClientConnection.getCommunicationsCloudClientConnection().isRegister() && !networkService.isRegister()){

                    /*
                     * Construct my profile and register me
                     */
                    PlatformComponentProfile platformComponentProfile =  communicationsClientConnection.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(networkService.getIdentityPublicKey(),
                                                                                                                                                 networkService.getAlias().toLowerCase(),
                                                                                                                                                  networkService.getName(),
                                                                                                                                                 networkService.getNetworkServiceType(),
                                                                                                                                                 networkService.getPlatformComponentType(),
                                                                                                                                                 networkService.getExtraData());

                    /*
                     * Register me
                     */
                    communicationsClientConnection.getCommunicationsCloudClientConnection().registerComponentForCommunication(networkService.getNetworkServiceType(), platformComponentProfile);

                    /*
                     * Configure my new profile
                     */
                    networkService.setPlatformComponentProfilePluginRoot(platformComponentProfile);

                    /*
                     * Initialize the connection manager
                     */
                    networkService.initializeCommunicationNetworkServiceConnectionManager();

                    /*
                     * Stop the agent
                     */
                    active = Boolean.FALSE;

                }else if (!networkService.isRegister()){

                    try {
                        sleep(CommunicationRegistrationProcessNetworkServiceAgent.SLEEP_TIME);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        active = Boolean.FALSE;
                    }

                }else if (!networkService.isRegister()){
                    active = Boolean.FALSE;
                }

            }catch (Exception e){
                try {
                    System.out.println(e.getMessage());
                    sleep(CommunicationRegistrationProcessNetworkServiceAgent.MAX_SLEEP_TIME);
                } catch (InterruptedException e1) {
                    System.out.println(e1.getMessage());
                    active = Boolean.FALSE;
                }
            }
        }
    }

    /**
     * (non-javadoc)
     * @see Thread#start()
     */
    @Override
    public synchronized void start() {
        this.active = Boolean.TRUE;
        super.start();
    }

    /**
     * Get the IsRunning
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }
}
