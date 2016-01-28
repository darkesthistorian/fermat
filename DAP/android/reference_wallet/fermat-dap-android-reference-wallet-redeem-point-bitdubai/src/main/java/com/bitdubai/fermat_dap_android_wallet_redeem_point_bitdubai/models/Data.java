package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 12/9/15.
 */
public class Data {
    public static List<DigitalAsset> getAllDigitalAssets(AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {
        List<AssetRedeemPointWalletList> assets = moduleManager.getAssetRedeemPointWalletBalances("walletPublicKeyTest");
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        DigitalAsset digitalAsset;

        for (AssetRedeemPointWalletList asset : assets) {
            digitalAsset = new DigitalAsset();
            digitalAsset.setAssetPublicKey(asset.getDigitalAsset().getPublicKey());
            digitalAsset.setName(asset.getDigitalAsset().getName());
            digitalAsset.setAvailableBalanceQuantity(asset.getQuantityAvailableBalance());
            digitalAsset.setBookBalanceQuantity(asset.getQuantityBookBalance());
            digitalAsset.setAvailableBalance(asset.getDigitalAsset().getGenesisAmount());
            digitalAsset.setExpDate((Timestamp) asset.getDigitalAsset().getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue());

            digitalAssets.add(digitalAsset);

            List<Resource> resources = asset.getDigitalAsset().getResources();
            if (resources != null && !resources.isEmpty()) {
                digitalAsset.setImage(resources.get(0).getResourceBinayData());
            }
        }
        return digitalAssets;
    }

    public static List<UserRedeemed> getUserRedeemedPointList(String walletPublicKey, DigitalAsset digitalAsset, AssetRedeemPointWalletSubAppModule moduleManager) throws Exception {

        AssetRedeemPointWallet wallet = moduleManager.loadAssetRedeemPointWallet(walletPublicKey);
        List<RedeemPointStatistic> all = wallet.getStatisticsByAssetPublicKey(digitalAsset.getAssetPublicKey());
        List<UserRedeemed> userRedeemeds = new ArrayList<>();

        for (RedeemPointStatistic stadistic : all) {
            UserRedeemed user = new UserRedeemed(stadistic.userThatRedeemed().getName(), new Timestamp(stadistic.redemptionTime().getTime()));
            userRedeemeds.add(user);
        }

        /*List<UserRedeemed> userRedeemeds = new ArrayList<>();
        UserRedeemed user= new UserRedeemed("Penny Quintero",new Timestamp(new Date().getTime()));
        userRedeemeds.add(user);
        user= new UserRedeemed("Nerio Indriago",new Timestamp(new Date().getTime()));
        userRedeemeds.add(user);
        user= new UserRedeemed("Jinmy Bohorquez",new Timestamp(new Date().getTime()));
        userRedeemeds.add(user);*/

        return userRedeemeds;
    }

}