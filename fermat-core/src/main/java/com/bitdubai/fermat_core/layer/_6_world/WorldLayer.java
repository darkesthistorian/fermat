package com.bitdubai.fermat_core.layer._6_world;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._13_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._6_world.WorldSubsystem;
import com.bitdubai.fermat_core.layer._6_world.crypto_index.CryptoIndexSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class WorldLayer implements PlatformLayer {
    
    Plugin mCryptoIndex;
    
    public Plugin getmCryptoIndex(){
        return mCryptoIndex;
    }
    
    
    @Override
    public void start() {

        /**
         * Let's try to start the Crypto Index subsystem.
         */
        WorldSubsystem cryptoIndexSubsystem  = new CryptoIndexSubsystem();
        
        try {
            cryptoIndexSubsystem.start();
            mCryptoIndex = ((WorldSubsystem) cryptoIndexSubsystem).getPlugin();
            
        } catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
    }
}