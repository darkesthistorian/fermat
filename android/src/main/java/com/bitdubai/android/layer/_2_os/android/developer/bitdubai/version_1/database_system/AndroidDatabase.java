package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.12.14.
 */
public class AndroidDatabase implements Database {

    public  List<String> getLocalPersonalUsersIds() {

        List<String> LocalPersonalUsersIds = new ArrayList<String>();
        LocalPersonalUsersIds.add ("1");

        return LocalPersonalUsersIds;
    }

    @Override
    public void createTable(String tableName) {
      //  mTableSchema = tableSchema;
       // mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() + "/" + databaseName, null, SQLiteDatabase.OPEN_READWRITE);
       // onCreate(mDatabase);
    }
}