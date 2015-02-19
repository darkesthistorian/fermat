package com.bitdubai.wallet_platform_api.layer._3_os.file_system;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */
public interface PluginFileSystem {

    public PluginDataFile getDataFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PluginDataFile createDataFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan);

    void setContext (Object context);

}