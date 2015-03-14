package com.bitdubai.fermat_api.layer._2_os.file_system;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantPersistFileException;

/**
 * Created by Natalia on 11/02/2015.
 */
public interface PluginImageFile {
    public byte[] getContent ();

    public void setContent (byte[] content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadToMemory() throws CantLoadFileException;

    public void loadFromMedia() throws CantLoadFileException;
}