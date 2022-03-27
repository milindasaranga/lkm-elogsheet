package com.lkm.elogsheet.services;

import android.content.Context;

import com.lkm.elogsheet.dao.ELogsheetLiteDB;

public class DBService {

    private static DBService instance=null;
    private static ELogsheetLiteDB eLogsheetDB=null;

    public static DBService getInstance(){
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    public void initCouchbaseLite(Context context){
        eLogsheetDB=ELogsheetLiteDB.getInstance();
        eLogsheetDB.initializeDB(context);
    }


}
