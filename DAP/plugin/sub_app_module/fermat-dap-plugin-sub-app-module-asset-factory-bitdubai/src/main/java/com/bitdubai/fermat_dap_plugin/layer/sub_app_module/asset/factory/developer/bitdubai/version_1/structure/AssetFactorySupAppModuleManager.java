package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.util.List;

/**
 * Created by franklin on 20/09/15.
 */
public class AssetFactorySupAppModuleManager  {

    private final AssetFactoryManager assetFactoryManager;
    private final IdentityAssetIssuerManager identityAssetIssuerManager;

    /**
     * constructor
     * @param assetFactoryManager
     */
    public AssetFactorySupAppModuleManager(final AssetFactoryManager assetFactoryManager, final IdentityAssetIssuerManager identityAssetIssuerManager) {
        this.assetFactoryManager = assetFactoryManager;
        this.identityAssetIssuerManager = identityAssetIssuerManager;
    }

    public AssetFactory getAssetFactory(String assetPublicKey)  throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByPublicKey(assetPublicKey);
    }

    public AssetFactory newAssetFactoryEmpty()  throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactoryManager.createEmptyAssetFactory();
    }

    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactoryManager.saveAssetFactory(assetFactory);
    }

    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        assetFactoryManager.removeAssetFactory(publicKey);
    }

    public void publishAssetFactory(AssetFactory assetFactory, BlockchainNetworkType blockchainNetworkType)  throws CantSaveAssetFactoryException {
        assetFactoryManager.publishAsset(assetFactory, blockchainNetworkType);
    }

    public List<AssetFactory> getAssetsFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByIssuer(issuerIdentityPublicKey);
    }

    public List<AssetFactory> getAssetsFactoryByState(State state) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByState(state);
    }

    public List<AssetFactory> getAssetsFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryAll();
    }

    public PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryResource(resource);
    }

    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return  assetFactoryManager.getInstallWallets();
    }

    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy
    {
        return assetFactoryManager.isReadyToPublish(assetPublicKey);
    }

    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<IdentityAssetIssuer> identities = assetFactoryManager.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : assetFactoryManager.getActiveIdentities().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<IdentityAssetIssuer> getActiveIdentities() {
        try {
            return identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
