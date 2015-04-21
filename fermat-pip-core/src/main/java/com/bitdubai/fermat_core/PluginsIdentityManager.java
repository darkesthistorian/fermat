package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.CantInitializePluginsManagerException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginNotRecognizedException;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer._11_world.coinbase.developer.bitdubai.version_1.CoinbaseWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._15_transaction.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer._9_communication.cloud.developer.bitdubai.version_1.CloudCommunicationChannelPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._10_network_service.bank_notes.developer.bitdubai.version_1.BankNotesNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._10_network_service.wallet_community.developer.bitdubai.version_1.WalletCommunityNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._10_network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._10_network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fremat_dmp_plugin.layer._15_transaction.incoming_device_user.developer.bitdubai.version_1.IncomingDeviceUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._15_transaction.incoming_extra_user.developer.bitdubai.version_1.IncomingExtraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._15_transaction.incoming_intra_user.developer.bitdubai.version_1.IncomingIntraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._15_transaction.inter_wallet.developer.bitdubai.version_1.InterWalletTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._15_transaction.outgoing_device_user.developer.bitsubai.version_1.OutgoingDeviceUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._15_transaction.outgoing_extra_user.developer.bitdubai.version_1.OutgoingExtraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._15_transaction.outgoing_intra_user.developer.bitdubai.version_1.OutgoingIntraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.CoinapultWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._11_world.blockchain_info.developer.bitdubai.version_1.BlockchainInfoWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._11_world.crypto_index.developer.bitdubai.version_1.CryptoIndexWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._11_world.shape_shift.developer.bitdubai.version_1.ShapeShiftWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._8_crypto.address_book.developer.bitdubai.version_1.AddressBookCryptoPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._14_middleware.app_runtime.developer.bitdubai.version_1.AppRuntimeMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._14_middleware.bank_notes.developer.bitdubai.version_1.BankNotesMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._14_middleware.discount_wallet.developer.bitdubai.version_1.DiscountWalletMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._14_middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._16_module.wallet_manager.developer.bitdubai.version_1.WalletManagerModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._16_module.wallet_runtime.developer.bitdubai.version_1.WalletRuntimeModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 02.02.15.
 */
public class PluginsIdentityManager {

    private PlatformFileSystem platformFileSystem;
    private final Integer AMOUNT_OF_KNOWN_PLUGINS = 27;
    private List<UUID> pluginIds = new ArrayList<>();


    public PluginsIdentityManager(PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {

        this.platformFileSystem = platformFileSystem;

        PlatformTextFile platformTextFile;

        try
        {
            /**
             * First I get the file where all ids are stored. 
             */
            try{
                platformTextFile =  platformFileSystem.getFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            }  catch (CantCreateFileException cantCreateFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("CantCreateFileException: " + cantCreateFileException.getMessage());
                cantCreateFileException.printStackTrace();
                throw new CantInitializePluginsManagerException();
               }

            try
            {
                platformTextFile.persistToMedia();
            }
            catch (CantPersistFileException cantLoadFileException)
            {
                /**
                 * Until we implement some kind of backup of this critical file, there is no possible recovery from this 
                 * situation, 
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();
                throw new CantInitializePluginsManagerException();
            }
            
            /**
             * Then I put the content of the file on an Array String.
             */
            
            String[] stringPluginIds = platformTextFile.getContent().split(";" , -1);

            Integer arrayPosition = 0;

             for (String stringPluginId : stringPluginIds ) {

                    if(stringPluginId != "")
                    {
                        try {
                            pluginIds.add(arrayPosition, UUID.fromString(stringPluginId));
                            arrayPosition++;
                        }
                        catch (IllegalArgumentException e )
                        {
                            /**
                             * This exception occurs when we reach the end of the file. So there is nothing to do here.
                             */
                        }
                        
                    }

                }

            /**
             * Now I check if the amount of plugins on file is the same that the amount of plugin implemented
             */

            if (arrayPosition < AMOUNT_OF_KNOWN_PLUGINS)
            {
                /**
                 * Under this condition, means that since the last time the platform start, new plugins were added to the
                 * platform, and as these new plugins needs new ids, we are going to create one for each of them. 
                 */

                for (int index = arrayPosition; index < AMOUNT_OF_KNOWN_PLUGINS; index++) {

                    UUID newId =  UUID.randomUUID();

                    pluginIds.add(index, newId);
                }

                try
                {
                    savePluginIds(platformTextFile);
                }
                catch (CantPersistFileException cantPersistFileException )
                {
                    /**
                     * If I cannot save this file, It means the Plugin Manager cannot start,
                     */
                    System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                    cantPersistFileException.printStackTrace();
                    cantPersistFileException.getFileName();
                    throw new CantInitializePluginsManagerException();
                }

            }

        }
        catch (FileNotFoundException fileNotFoundException)
        {
            try{
                    platformTextFile =  platformFileSystem.createFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }  catch (CantCreateFileException cantCreateFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("CantCreateFileException: " + cantCreateFileException.getMessage());
                cantCreateFileException.printStackTrace();
                throw new CantInitializePluginsManagerException();
            }

            for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

                UUID newId =  UUID.randomUUID();

                pluginIds.add(arrayPosition, newId);
            }

            try
            {
                savePluginIds(platformTextFile);
            }
            catch (CantPersistFileException cantPersistFileException )
            {
                /**
                 * If I cannot save this file, It means the Plugin Manager cannot start,
                 */
                System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                cantPersistFileException.printStackTrace();
                cantPersistFileException.getFileName();
                throw new CantInitializePluginsManagerException();
            }

        }

    }

    public UUID getPluginId(Plugin plugin) throws PluginNotRecognizedException{

        UUID pluginId = new UUID(0,0);
        Integer pluginIndex = 0;

        if (pluginIndex == 0) {
            try
            {
                CryptoIndexWorldPluginRoot tryType;
                tryType = (CryptoIndexWorldPluginRoot) plugin;
                pluginIndex = 0;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                BitcoinCryptoNetworkPluginRoot tryType;
                tryType = (BitcoinCryptoNetworkPluginRoot) plugin;
                pluginIndex = 1;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                CloudCommunicationChannelPluginRoot tryType;
                tryType = (CloudCommunicationChannelPluginRoot) plugin;
                pluginIndex = 2;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                DiscountWalletMiddlewarePluginRoot tryType;
                tryType = (DiscountWalletMiddlewarePluginRoot) plugin;
                pluginIndex = 3;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }
        if (pluginIndex == 0) {
            try
            {
                WalletManagerModulePluginRoot tryType;
                tryType = (WalletManagerModulePluginRoot) plugin;
                pluginIndex = 4;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }
        if (pluginIndex == 0) {
            try
            {
                WalletRuntimeModulePluginRoot tryType;
                tryType = (WalletRuntimeModulePluginRoot) plugin;
                pluginIndex = 5;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                AppRuntimeMiddlewarePluginRoot tryType;
                tryType = (AppRuntimeMiddlewarePluginRoot) plugin;
                pluginIndex = 6;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                IncomingExtraUserTransactionPluginRoot tryType;
                tryType = (IncomingExtraUserTransactionPluginRoot) plugin;
                pluginIndex = 7;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }


        if (pluginIndex == 0) {
            try
            {
                IncomingIntraUserTransactionPluginRoot tryType;
                tryType = (IncomingIntraUserTransactionPluginRoot) plugin;
                pluginIndex = 8;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }


        if (pluginIndex == 0) {
            try
            {
                InterWalletTransactionPluginRoot tryType;
                tryType = (InterWalletTransactionPluginRoot) plugin;
                pluginIndex = 9;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                CryptoIndexWorldPluginRoot tryType;
                tryType = (CryptoIndexWorldPluginRoot) plugin;
                pluginIndex = 10;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                OutgoingDeviceUserTransactionPluginRoot tryType;
                tryType = (OutgoingDeviceUserTransactionPluginRoot) plugin;
                pluginIndex = 11;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                BankNotesMiddlewarePluginRoot tryType;
                tryType = (BankNotesMiddlewarePluginRoot) plugin;
                pluginIndex = 12;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }
        
        if (pluginIndex == 0) {
            try
            {
                BankNotesNetworkServicePluginRoot tryType;
                tryType = (BankNotesNetworkServicePluginRoot) plugin;
                pluginIndex = 13;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                WalletResourcesNetworkServicePluginRoot tryType;
                tryType = (WalletResourcesNetworkServicePluginRoot) plugin;
                pluginIndex = 14;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                WalletStoreNetworkServicePluginRoot tryType;
                tryType = (WalletStoreNetworkServicePluginRoot) plugin;
                pluginIndex = 15;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                WalletContactsMiddlewarePluginRoot tryType;
                tryType = (WalletContactsMiddlewarePluginRoot) plugin;
                pluginIndex = 16;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                WalletCommunityNetworkServicePluginRoot tryType;
                tryType = (WalletCommunityNetworkServicePluginRoot) plugin;
                pluginIndex = 17;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                AddressBookCryptoPluginRoot tryType;
                tryType = (AddressBookCryptoPluginRoot) plugin;
                pluginIndex = 18;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                IncomingDeviceUserTransactionPluginRoot tryType;
                tryType = (IncomingDeviceUserTransactionPluginRoot) plugin;
                pluginIndex = 19;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                OutgoingExtraUserTransactionPluginRoot tryType;
                tryType = (OutgoingExtraUserTransactionPluginRoot) plugin;
                pluginIndex = 20;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                OutgoingIntraUserTransactionPluginRoot tryType;
                tryType = (OutgoingIntraUserTransactionPluginRoot) plugin;
                pluginIndex = 21;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                BlockchainInfoWorldPluginRoot tryType;
                tryType = (BlockchainInfoWorldPluginRoot) plugin;
                pluginIndex = 22;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                CoinapultWorldPluginRoot tryType;
                tryType = (CoinapultWorldPluginRoot) plugin;
                pluginIndex = 23;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try
            {
                ShapeShiftWorldPluginRoot tryType;
                tryType = (ShapeShiftWorldPluginRoot) plugin;
                pluginIndex = 24;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }

            if (pluginIndex == 0) {
                try {
                    IncomingCryptoTransactionPluginRoot tryType;
                    tryType = (IncomingCryptoTransactionPluginRoot) plugin;
                    pluginIndex = 25;
                } catch (Exception e) {
                    /**
                     * If this fails, is because this is not the index for this plug in.
                     */
                }
            }


            if (pluginIndex == 0) {
                try {
                    CoinbaseWorldPluginRoot tryType;
                    tryType = (CoinbaseWorldPluginRoot) plugin;
                    pluginIndex = 26;
                } catch (Exception e) {
                    /**
                     * If this fails, is because this is not the index for this plug in.
                     */
                }
            }
        }


        if (pluginIndex > 0) {

            return  pluginIds.get(pluginIndex);

        }
        else
        {
            throw new PluginNotRecognizedException();
        }

    }



    private void savePluginIds(PlatformTextFile platformTextFile) throws CantPersistFileException{

        String fileContent = "";

        for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

            fileContent = fileContent + pluginIds.get(arrayPosition).toString() + ";";

        }

        platformTextFile.setContent(fileContent);

        try
        {
            platformTextFile.persistToMedia();
        }
        catch (CantPersistFileException cantPersistFileException )
        {
            /**
             * If I cannot save this file, I cant handle the situation,
             */
            System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
            cantPersistFileException.printStackTrace();
            cantPersistFileException.getFileName();
            throw new CantPersistFileException(cantPersistFileException.getFileName());
        }


    }

}