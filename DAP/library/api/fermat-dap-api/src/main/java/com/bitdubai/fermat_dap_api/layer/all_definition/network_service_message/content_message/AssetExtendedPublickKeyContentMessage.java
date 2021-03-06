package com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;

/**
 * Created by Nerio on 26/11/15.
 */
public class AssetExtendedPublickKeyContentMessage implements DAPContentMessage {
    ExtendedPublicKey extendedPublicKey;
    String actorPublicKey;

    /**
     * Default constructor
     */
    public AssetExtendedPublickKeyContentMessage() {
    }

    /**
     * overloaded constructor
     * @param extendedPublicKey
     */
    public AssetExtendedPublickKeyContentMessage(ExtendedPublicKey extendedPublicKey, String actorPublicKey) {
        this.extendedPublicKey = extendedPublicKey;
        this.actorPublicKey = actorPublicKey;
    }

    /**
     * Gets the Extended Public Key
     * @return
     */
    public ExtendedPublicKey getExtendedPublicKey() {
        return extendedPublicKey;
    }

    /**
     * extended public key setter
     * @param extendedPublicKey
     */
    public void setExtendedPublicKey(ExtendedPublicKey extendedPublicKey) {
        this.extendedPublicKey = extendedPublicKey;
    }

    /**
     * gets the actor public key
     * @return
     */
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    /**
     * sets the actor public key
     * @param actorPublicKey
     */
    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }
}
