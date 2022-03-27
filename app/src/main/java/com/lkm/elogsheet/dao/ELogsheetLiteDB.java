package com.lkm.elogsheet.dao;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
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


public class ELogsheetLiteDB {

    private static final String DB_NAME = "lkm-elogsheet-db";
    private static ELogsheetLiteDB instance=null;

    private String TAG = "CBL-GS";
    private ELogsheetLiteDB cntx = this;

    public static ELogsheetLiteDB getInstance(){
        if (instance == null) {
            instance = new ELogsheetLiteDB();
        }
        return instance;
    }

    public void InitlializeDB(Context cntx){
        CouchbaseLite.init(cntx);
    }

//    private static final String DB_NAME = "epsiyan-fb-db";
//    private static final int DB_VERSION = 1;
//
//    public FBSQLLiteDB(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        updateDatabase(db, 0, DB_VERSION);
//    }
}
