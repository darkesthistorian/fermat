package com.bitdubai.wallet_platform_api.layer._3_os.Database_System;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableFilter {
    
    
    public void setColumn (DatabaseTableColumn column);
    public void setType (DatabaseFilterType type);
    public void setValue (String value);
    public DatabaseTableColumn  getColumn ();
    public String getValue ();
    public DatabaseFilterType getType ();
    
}