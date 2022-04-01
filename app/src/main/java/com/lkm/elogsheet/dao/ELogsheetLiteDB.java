package com.lkm.elogsheet.dao;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Dictionary;
import com.couchbase.lite.ReplicatorChange;
import com.couchbase.lite.ReplicatorConfiguration;
import com.couchbase.lite.Replicator;
import com.couchbase.lite.ReplicatorChangeListener;
import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Expression;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;
import com.couchbase.lite.URLEndpoint;
import com.google.gson.Gson;
import com.lkm.elogsheet.models.ModelBase;

import org.json.JSONObject;

import java.util.Date;
import java.util.Map;


public class ELogsheetLiteDB {

    private static final String DB_NAME = "lkm-elogsheet-db";
    private static ELogsheetLiteDB instance=null;
    private Database database = null;

    public static ELogsheetLiteDB getInstance(){
        if (instance == null) {
            instance = new ELogsheetLiteDB();
        }
        return instance;
    }

    public Database getDBInstance(){
        return database;
    }

    public void initializeDB(Context cntx){
        CouchbaseLite.init(cntx);
        DatabaseConfiguration config = new DatabaseConfiguration();

        try {
            database = new Database(DB_NAME, config);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public void write(ModelBase doc){
        try{
            Document dbDoc= database.getDocument(doc.id);
            Gson gson = new Gson();
            MutableDocument mDBDoc;

            mDBDoc = dbDoc==null? new MutableDocument(doc.id): dbDoc.toMutable();
            JSONObject mJSONObject = new JSONObject(gson.toJson(doc));
            mJSONObject.remove("_id");
            mJSONObject.remove("_rev");

            mDBDoc.setJSON(mJSONObject.toString());
            database.save(mDBDoc);

        }catch (Exception e){
            Log.e("write",e.toString());
        }
    }

    public String getDocumentById(String docId){
        Document doc= database.getDocument(docId);
        return doc==null?null: doc.toJSON();
    }

}
