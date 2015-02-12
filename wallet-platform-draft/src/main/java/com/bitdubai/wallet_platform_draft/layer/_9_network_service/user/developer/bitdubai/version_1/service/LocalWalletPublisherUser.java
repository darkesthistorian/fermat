package com.bitdubai.wallet_platform_draft.layer._9_network_service.user.developer.bitdubai.version_1.service;

/**
 * Created by ciencias on 30.12.14.
 */
public class LocalWalletPublisherUser implements WalletPublisherUser, LocalUser {

    private String mId;
    private LoginType mLoginType;

    public LocalWalletPublisherUser(String userId) {
        mId = userId;
        mLoginType = LoginType.NONE;
    }

    public LoginType getLoginType() {
        return mLoginType;
    }

    @java.lang.Override
    public boolean Login() {
        return false;
    }

    @java.lang.Override
    public boolean Login(int pPIN) {
        return false;
    }

    @java.lang.Override
    public boolean Login(String pPassword) {
        return false;
    }


}