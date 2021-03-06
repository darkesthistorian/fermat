package com.bitdubai.sub_app.crypto_customer_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_community.fragments.MainFragment;
import com.bitdubai.sub_app.crypto_customer_community.preference_settings.CryptoCustomerCommunityPreferenceSettings;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;

import static com.bitdubai.sub_app.crypto_customer_community.fragmentFactory.CryptoCustomerCommunityFragmentsEnumType.MAIN_FRAGMET;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerCommunityFragmentFactory extends FermatFragmentFactory<CryptoCustomerCommunitySubAppSession,SubAppResourcesProviderManager, CryptoCustomerCommunityFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(CryptoCustomerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
        if (fragments == MAIN_FRAGMET) {
            return MainFragment.newInstance();
        }

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public CryptoCustomerCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerCommunityFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}
