package com.bitdubai.wallet_platform_core.layer._4_user.device_user.developer.bitdubai;


import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.AddonDeveloper;
import com.bitdubai.wallet_platform_core.layer._4_user.device_user.developer.bitdubai.version_1.DeviceUserAddonRoot;


/**
 * Created by ciencias on 22.01.15.
 */
public class DeveloperBitDubai implements AddonDeveloper {

    Addon addon;

    @Override
    public Addon getAddon() {
        return addon;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        addon = new DeviceUserAddonRoot();

    }


}