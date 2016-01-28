package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantInitializeRedeemPointWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantSaveRedeemPointStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionSummary;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.database.AssetRedeemPointWalletDao;
import com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.database.AssetRedeemPointWalletDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletImpl implements AssetRedeemPointWallet {
    public static final String PATH_DIRECTORY = "assetredeem-point/assets";
    private static final String ASSET_REDEEM_POINT_WALLET_FILE_NAME = "walletsIds";


    /**
     * AssetIssuerWallet member variables.
     */
    private Database database;

    private Map<String, UUID> walletAssetRedeemPoint = new HashMap<>();

    private AssetRedeemPointWalletDao assetRedeemPointWalletDao;

    private ErrorManager errorManager;

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    private ActorAssetUserManager actorAssetUserManager;

    private AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;

    public AssetRedeemPointWalletImpl(ErrorManager errorManager,
                                      PluginDatabaseSystem pluginDatabaseSystem,
                                      PluginFileSystem pluginFileSystem,
                                      UUID pluginId,
                                      ActorAssetUserManager actorAssetUserManager,
                                      AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager) {

        this.errorManager                           = errorManager;
        this.pluginDatabaseSystem                   = pluginDatabaseSystem;
        this.pluginFileSystem                       = pluginFileSystem;
        this.pluginId                               = pluginId;
        this.actorAssetUserManager                  = actorAssetUserManager;
        this.assetUserActorNetworkServiceManager    = assetUserActorNetworkServiceManager;
    }

    public void initialize(UUID walletId) throws CantInitializeRedeemPointWalletException {
        if (walletId == null)
            throw new CantInitializeRedeemPointWalletException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
            assetRedeemPointWalletDao = new AssetRedeemPointWalletDao(database, pluginFileSystem, pluginId, actorAssetUserManager);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeRedeemPointWalletException("I can't open database", cantOpenDatabaseException, "WalletId: " + pluginId.toString(), "");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantInitializeRedeemPointWalletException("Database does not exists", databaseNotFoundException, "WalletId: " + pluginId.toString(), "");
        } catch (Exception exception) {
            throw new CantInitializeRedeemPointWalletException(CantInitializeRedeemPointWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public UUID create(String walletId) throws CantCreateWalletException {
        try {
            // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
            //       We need to change this in the near future
            UUID internalWalletId = UUID.randomUUID();
            createWalletDatabase(internalWalletId);
            PluginTextFile walletAssetIssuerFile = createAssetIssuerWalletFile();
            loadAssetRedeemPointWalletMap(walletAssetIssuerFile);
            walletAssetRedeemPoint.put(walletId, internalWalletId);
            persistAssetRedeemPointWallet(walletAssetIssuerFile);
            return internalWalletId;
        } catch (CantCreateWalletException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantCreateWalletException(CantCreateWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }

    }

    private void persistAssetRedeemPointWallet(final PluginTextFile pluginTextFile) throws CantCreateWalletException {
        StringBuilder stringBuilder = new StringBuilder(walletAssetRedeemPoint.size() * 72);
        Iterator iterator = walletAssetRedeemPoint.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();

            stringBuilder
                    .append(pair.getKey().toString())
                    .append(",")
                    .append(pair.getValue().toString())
                    .append(";");

            iterator.remove();
        }

        pluginTextFile.setContent(stringBuilder.toString());

        try {
            pluginTextFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            throw new CantCreateWalletException("Could not persist in file", cantPersistFileException, "stringBuilder: " + stringBuilder.toString(), "");
        }
    }

    private PluginTextFile createAssetIssuerWalletFile() throws CantCreateWalletException {
        try {
            return pluginFileSystem.getTextFile(pluginId, "", ASSET_REDEEM_POINT_WALLET_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCreateWalletException("File could not be created (?)", cantCreateFileException, "File Name: " + ASSET_REDEEM_POINT_WALLET_FILE_NAME, "");
        } catch (FileNotFoundException e) {
            throw new CantCreateWalletException("File could not be found", e, "File Name: " + ASSET_REDEEM_POINT_WALLET_FILE_NAME, "");
        }
    }

    private void loadAssetRedeemPointWalletMap(final PluginTextFile loadAssetIssuerWalletMap) throws CantCreateWalletException {
        try {
            loadAssetIssuerWalletMap.loadFromMedia();
            String[] stringAssetIssuerWallet = loadAssetIssuerWalletMap.getContent().split(";", -1);

            for (String stringWalletId : stringAssetIssuerWallet) {

                if (!stringWalletId.equals("")) {
                    String[] idPair = stringWalletId.split(",", -1);
                    walletAssetRedeemPoint.put(idPair[0], UUID.fromString(idPair[1]));
                }
            }
        } catch (CantLoadFileException exception) {
            throw new CantCreateWalletException("Can't load file content from media", exception, "", "");
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateWalletException {
        try {
            AssetRedeemPointWalletDatabaseFactory databaseFactory = new AssetRedeemPointWalletDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateWalletException("Database could not be created", cantCreateDatabaseException, "internalWalletId: " + internalWalletId.toString(), "");
        }
    }

    @Override
    public AssetRedeemPointWalletBalance getBalance() throws CantGetTransactionsException {
        try {
            return new AssetRedeemPointWalletBalanceImpl(database, pluginId, pluginFileSystem, actorAssetUserManager, assetUserActorNetworkServiceManager);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetRedeemPointWalletDao.listsTransactionsByAssets(balanceType, transactionType, max, offset, assetPublicKey);
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetRedeemPointWalletDao.getTransactionsByActor(actorPublicKey, balanceType, max, offset);
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType, TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetRedeemPointWalletDao.getTransactionsByTransactionType(transactionType, max, offset);
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void setTransactionDescription(UUID transactionID, String description) throws CantFindTransactionException, CantStoreMemoException {
        try {
            assetRedeemPointWalletDao.updateMemoField(transactionID, description);
        } catch (CantStoreMemoException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public AssetRedeemPointWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }


    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata(String digitalAssetPublicKey) throws CantGetDigitalAssetFromLocalStorageException {
        return assetRedeemPointWalletDao.getDigitalAssetMetadata(digitalAssetPublicKey);
    }

    @Override
    public void newAssetRedeemed(String userPublicKey, String assetPublicKey) throws CantSaveRedeemPointStatisticException {
        assetRedeemPointWalletDao.newAssetRedeemed(userPublicKey, assetPublicKey);
    }

    @Override
    public List<RedeemPointStatistic> getAllStatistics() throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getAllStatistics();
    }

    @Override
    public List<RedeemPointStatistic> getStatisticsByUser(String userPublicKey) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getStatisticsForUser(userPublicKey);
    }

    @Override
    public List<RedeemPointStatistic> getStatisticsByAssetPublicKey(String assetPk) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getStatisticsForAsset(assetPk);
    }

    @Override
    public List<RedeemPointStatistic> getStatisticsByAssetAndUser(String assetPk, String userPk) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getStatisticForAssetAndUser(userPk, assetPk);
    }
}
