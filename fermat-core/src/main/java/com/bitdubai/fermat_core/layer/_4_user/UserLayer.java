package com.bitdubai.fermat_core.layer._4_user;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._4_user.UserSubsystem;
import com.bitdubai.fermat_api.layer._4_user.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer._4_user.device_user.DeviceUserSubsystem;
import com.bitdubai.fermat_core.layer._4_user.extra_user.ExtraUserSubsystem;
import com.bitdubai.fermat_core.layer._4_user.intra_user.IntraUserSubsystem;

/**
 * Created by ciencias on 22.01.15.
 */
public class UserLayer implements PlatformLayer {

    Addon userManger;
    Addon intraUser;
    Addon extraUser;
    
    
    public Addon getIntraUser(){
        return intraUser;
    }
    
    public Addon getExtraUser(){
        return extraUser;
    }

    public Addon getUserManager() {
        return userManger;
    }

    public void start() throws CantStartLayerException {

        /**
         * Let's start the Login Subsystem;
         */
        UserSubsystem loginSubsystem = new DeviceUserSubsystem();

        try {
            loginSubsystem.start();
            userManger = ((UserSubsystem) loginSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }


        /**
         * Let's start the Intra User Subsystem;
         */
        UserSubsystem intraUserSubsystem = new IntraUserSubsystem();

        try {
            intraUserSubsystem.start();
            intraUser = ((UserSubsystem) intraUserSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * Let's start the Extra User Subsystem;
         */
        UserSubsystem extraUserSubsystem = new ExtraUserSubsystem();

        try {
            extraUserSubsystem.start();
            extraUser = ((UserSubsystem) extraUserSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }
        
    }



}