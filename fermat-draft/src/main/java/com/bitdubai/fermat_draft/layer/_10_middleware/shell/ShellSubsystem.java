package com.bitdubai.fermat_draft.layer._10_middleware.shell;


import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._11_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._11_middleware.MiddlewareEngine;
import com.bitdubai.fermat_api.layer._11_middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_draft.layer._10_middleware.shell.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 20.01.15.
 */
public class ShellSubsystem implements MiddlewareSubsystem {

    MiddlewareEngine mMiddlewareEngine;

    @Override
    public Plugin getPlugin() {
        return null;
    }
    
    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mMiddlewareEngine = developerBitDubai.getMiddlewareEngine();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}