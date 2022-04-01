package com.lkm.elogsheet.services;

import android.content.Context;

import com.lkm.elogsheet.dao.ELogsheetLiteDB;
import com.lkm.elogsheet.models.ModelBase;

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

    public void write(ModelBase doc){
        eLogsheetDB.write(doc);
    }

    public void get(String docId){
        eLogsheetDB.getDocumentById(docId);
    }

}
