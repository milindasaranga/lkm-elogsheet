package com.lkm.elogsheet.services;

import android.content.Context;

import com.google.gson.Gson;
import com.lkm.elogsheet.dao.ELogsheetLiteDB;
import com.lkm.elogsheet.models.ModelBase;
import com.lkm.elogsheet.models.User;

import java.lang.reflect.Type;

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

    public String get(String docId){
        return eLogsheetDB.getDocumentById(docId);
    }

    public ModelBase get(String docId, Class<?> cls){
        String json = eLogsheetDB.getDocumentById(docId);
        ModelBase baseDoc = new Gson().fromJson(json, (Type) cls);
        return baseDoc;
    }

}
