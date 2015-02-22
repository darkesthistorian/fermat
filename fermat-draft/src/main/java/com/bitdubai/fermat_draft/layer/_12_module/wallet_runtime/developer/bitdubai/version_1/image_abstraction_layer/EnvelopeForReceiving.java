package com.bitdubai.fermat_draft.layer._12_module.wallet_runtime.developer.bitdubai.version_1.image_abstraction_layer;

import java.util.List;

/**
 * Created by ciencias on 15.01.15.
 */
public class EnvelopeForReceiving implements Envelope, Movable, Displayable {


    List<BankNote> mBankNotes;
    List<Coin> mCoins;
    EnvelopeStatus mStatus;
    EnvelopeStamp mStamp;


    public EnvelopeStatus getStatus() {
        return mStatus;
    }

    public void setStatus(EnvelopeStatus pStatus) {
        this.mStatus = pStatus;
    }

    @Override
    public EnvelopeStatus getPosition() {
        return null;
    }

    @Override
    public Void moveTo(Position pPosition) {
        return null;
    }
}