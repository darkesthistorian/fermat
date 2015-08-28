package com.bitdubai.sub_app.wallet_publisher.FragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment;
import com.bitdubai.sub_app.wallet_publisher.preference_settings.WalletPublisherPreferenceSettings;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletPublisherFragmentFactory extends FermatSubAppFragmentFactory<WalletPublisherSubAppSession,WalletPublisherPreferenceSettings,WalletPublisherFragmentsEnumType> {


    public WalletPublisherFragmentFactory(){

    }

    @Override
    public FermatFragment getFermatFragment(WalletPublisherFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments){
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_PUBLISHER_MAIN_FRAGMENT:
                currentFragment =  MainFragment.newInstance(0,null);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found",new Exception(),fragments.toString(),"Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WalletPublisherFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletPublisherFragmentsEnumType.getValue(key);
    }




}
