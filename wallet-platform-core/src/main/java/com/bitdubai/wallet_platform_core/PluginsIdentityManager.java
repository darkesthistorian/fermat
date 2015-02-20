package com.bitdubai.wallet_platform_core;

import com.bitdubai.wallet_platform_api.CantInitializePluginsManagerException;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.PluginNotRecognizedException;
import com.bitdubai.wallet_platform_api.layer._3_os.file_system.*;
import com.bitdubai.wallet_platform_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.AppRuntimePluginRoot;
import com.bitdubai.wallet_platform_core.layer._11_middleware.bank_notes.developer.bitdubai.version_1.BankNotespluginRoot;
import com.bitdubai.wallet_platform_core.layer._11_middleware.wallet.developer.bitdubai.version_1.WalletPluginRoot;
import com.bitdubai.wallet_platform_core.layer._11_middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsPluginRoot;
import com.bitdubai.wallet_platform_core.layer._12_transaction.from_extrauser.developer.bitdubai.version_1.FromExtraUserTransactionManagerPluginRoot;
import com.bitdubai.wallet_platform_core.layer._12_transaction.intrauser.developer.bitdubai.version_1.IntraUserTransactionManagerPluginRoot;
import com.bitdubai.wallet_platform_core.layer._12_transaction.interwallet.developer.bitdubai.version_1.InterWalletTransactionManagerPluginRoot;
import com.bitdubai.wallet_platform_core.layer._12_transaction.to_extrauser.developer.bitsubai.version_1.ToExtraUserTransactionManagerPluginRoot;
import com.bitdubai.wallet_platform_core.layer._6_world.crypto_index.developer.bitdubai.version_1.CryptoIndexPluginRoot;
import com.bitdubai.wallet_platform_core.layer._9_communication.cloud.developer.bitdubai.version_1.CloudCommunicationChannelPluginRoot;
import com.bitdubai.wallet_platform_core.layer._10_network_service.bank_notes.developer.bitdubai.version_1.BankNotesPluginRoot;
import com.bitdubai.wallet_platform_core.layer._10_network_service.wallet_community.developer.bitdubai.version_1.WalletCommunityPluginRoot;
import com.bitdubai.wallet_platform_core.layer._10_network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesPluginRoot;
import com.bitdubai.wallet_platform_core.layer._10_network_service.wallet_store.developer.bitdubai.version_1.WalletStorePluginRoot;
import com.bitdubai.wallet_platform_plugin.layer._13_module.wallet_manager.developer.bitdubai.version_1.WalletManagerPluginRoot;
import com.bitdubai.wallet_platform_plugin.layer._13_module.wallet_runtime.developer.bitdubai.version_1.WalletRuntimePluginRoot;
import com.bitdubai.wallet_platform_plugin.layer._7_crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 02.02.15.
 */
public class PluginsIdentityManager {

    private PlatformFileSystem platformFileSystem;
    private final Integer AMOUNT_OF_KNOWN_PLUGINS = 18;
    private List<UUID> pluginIds = new ArrayList<>();


    public PluginsIdentityManager(PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {

        this.platformFileSystem = platformFileSystem;

        PlatformDataFile platformDataFile;

        try
        {


            platformDataFile =  platformFileSystem.getFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            try
            {
                platformDataFile.loadToMemory();
            }
            catch (CantLoadFileException cantLoadFileException)
            {
                /**
                 * Until we implement some kind of backup of this critical file, there is no possible recovery from this 
                 * situation, 
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();
                throw new CantInitializePluginsManagerException();
            }

            String[] stringPluginIds = platformDataFile.getContent().split(";" , -1);

            Integer arrayPosition = 0;

             for (String stringPluginId : stringPluginIds ) {

                    if(stringPluginId != "")
                    {
                        pluginIds.add(arrayPosition, UUID.fromString(stringPluginId));

                        arrayPosition++;
                    }

                }



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
                    savePluginIds(platformDataFile);
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
            platformDataFile =  platformFileSystem.createFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);


            for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

                UUID newId =  UUID.randomUUID();

                pluginIds.add(arrayPosition, newId);
            }

            try
            {
                savePluginIds(platformDataFile);
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

        UUID pluginId;
        Integer pluginIndex = 0;

        if (pluginIndex == 0) {
            try
            {
                CryptoIndexPluginRoot tryType;
                tryType = (CryptoIndexPluginRoot) plugin;
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
                WalletPluginRoot tryType;
                tryType = (WalletPluginRoot) plugin;
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
                WalletManagerPluginRoot tryType;
                tryType = (WalletManagerPluginRoot) plugin;
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
                WalletRuntimePluginRoot tryType;
                tryType = (WalletRuntimePluginRoot) plugin;
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
                AppRuntimePluginRoot tryType;
                tryType = (AppRuntimePluginRoot) plugin;
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
                FromExtraUserTransactionManagerPluginRoot tryType;
                tryType = (FromExtraUserTransactionManagerPluginRoot) plugin;
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
                IntraUserTransactionManagerPluginRoot tryType;
                tryType = (IntraUserTransactionManagerPluginRoot) plugin;
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
                InterWalletTransactionManagerPluginRoot tryType;
                tryType = (InterWalletTransactionManagerPluginRoot) plugin;
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
                CryptoIndexPluginRoot tryType;
                tryType = (CryptoIndexPluginRoot) plugin;
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
                ToExtraUserTransactionManagerPluginRoot tryType;
                tryType = (ToExtraUserTransactionManagerPluginRoot) plugin;
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
                BankNotespluginRoot tryType;
                tryType = (BankNotespluginRoot) plugin;
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
                BankNotesPluginRoot tryType;
                tryType = (BankNotesPluginRoot) plugin;
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
                WalletResourcesPluginRoot tryType;
                tryType = (WalletResourcesPluginRoot) plugin;
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
                WalletStorePluginRoot tryType;
                tryType = (WalletStorePluginRoot) plugin;
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
                WalletContactsPluginRoot tryType;
                tryType = (WalletContactsPluginRoot) plugin;
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
                WalletCommunityPluginRoot tryType;
                tryType = (WalletCommunityPluginRoot) plugin;
                pluginIndex = 17;
            }
            catch (Exception e)
            {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex > 0) {

                return pluginIds.get(pluginIndex);


        }
        else
        {
            throw new PluginNotRecognizedException();
        }

    }



    private void savePluginIds(PlatformDataFile platformDataFile) throws CantPersistFileException{

        String fileContent = "";

        for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

            fileContent = fileContent + pluginIds.get(arrayPosition).toString() + ";";

        }

        platformDataFile.setContent(fileContent);

        try
        {
            platformDataFile.persistToMedia();
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
