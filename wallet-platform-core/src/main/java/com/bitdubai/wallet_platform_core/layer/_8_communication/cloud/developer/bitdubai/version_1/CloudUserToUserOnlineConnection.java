package com.bitdubai.wallet_platform_core.layer._8_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._8_communication.CantConnectToUserException;
import com.bitdubai.wallet_platform_api.layer._8_communication.UserToUserOnlineConnection;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudUserToUserOnlineConnection implements UserToUserOnlineConnection {

    User userFrom;
    User userTo;
    
    public CloudUserToUserOnlineConnection(User userFrom, User userTo) {
        
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
    
    @Override
    public User getLocalUser() {
        return this.userFrom;
    }

    @Override
    public User getRemoteUser() {
        return this.userTo;
    }

    @Override
    public void connect() throws CantConnectToUserException {

    }

    @Override
    public void disconnect() {

    }
}