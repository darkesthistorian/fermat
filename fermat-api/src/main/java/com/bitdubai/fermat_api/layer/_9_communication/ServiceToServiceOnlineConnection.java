package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.layer._4_user.DeviceUser;

/**
 * Created by ciencias on 2/12/15.
 */
public interface ServiceToServiceOnlineConnection {


    
    public void connect() throws CantConnectToRemoteServiceException;
    
    public void disconnect();
    
}