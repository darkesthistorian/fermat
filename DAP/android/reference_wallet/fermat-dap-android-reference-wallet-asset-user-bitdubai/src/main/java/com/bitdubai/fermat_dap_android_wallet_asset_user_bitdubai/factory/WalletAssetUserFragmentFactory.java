package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.AssetDetailActivityFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.AssetRedeemFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.AssetRedeemSelectRedeemPointsFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.UserMainActivityFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.UserHistoryActivityFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * Wallet Asset User Fragment Factory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletAssetUserFragmentFactory extends FermatFragmentFactory<AssetUserSession,WalletResourcesProviderManager, WalletAssetUserFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(WalletAssetUserFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        AbstractFermatFragment currentFragment = null;
        try {

            switch (fragment) {
                case DAP_WALLET_ASSET_USER_MAIN_ACTIVITY:
                    currentFragment = new UserMainActivityFragment();
                    break;
                case DAP_WALLET_ASSET_USER_HISTORY_ACTIVITY:
                    currentFragment = new UserHistoryActivityFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_DETAIL:
                    currentFragment = new AssetDetailActivityFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_REDEEM:
                    currentFragment = new AssetRedeemFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_REDEEM_SELECT_REDEEMPOINTS:
                    currentFragment = new AssetRedeemSelectRedeemPointsFragment();
                    break;
                default:
                    throw new FragmentNotFoundException("Fragment not found", new Exception(), fragment.getKey(), "Swith failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentFragment;
    }

    @Override
    public WalletAssetUserFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletAssetUserFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason = (fragments == null) ? "The parameter 'fragments' is NULL" : "Not found in switch block";
        String context = (fragments == null) ? "Null Value" : fragments.toString();

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}
