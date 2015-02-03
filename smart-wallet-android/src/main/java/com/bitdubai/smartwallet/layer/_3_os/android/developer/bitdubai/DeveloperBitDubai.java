package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.TimeFrequency;
import com.bitdubai.wallet_platform_api.layer._1_definition.license.Licensor;
import com.bitdubai.wallet_platform_api.layer._3_os.Os;
import com.bitdubai.wallet_platform_api.layer._3_os.OsDeveloper;


/**
 * Created by ciencias on 03.01.15.
 */

/**
 * Each developer might have different versions of their own implementation. This object chooses which version to start.
 */

public class DeveloperBitDubai implements OsDeveloper, Licensor {

    Os mAndroidOs;

    @Override
    public Os getAndroidOs() {
        return mAndroidOs;
    }

    public DeveloperBitDubai(){
     //   mAndroidOs = new AndroidOs();
    }

    @Override
    public int getAmountToPay() {
        return 100;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }

    @Override
    public String getName() {
        return "BitDubai";
    }

    @Override
    public String getEmail() {
        return "Android.Component@bitDubai.com";
    }
}