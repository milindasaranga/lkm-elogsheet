package com.lkm.elogsheet.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.couchbase.lite.AbstractReplicatorConfiguration;
import com.couchbase.lite.BasicAuthenticator;
import com.couchbase.lite.Endpoint;
import com.couchbase.lite.Replicator;
import com.couchbase.lite.ReplicatorChange;
import com.couchbase.lite.ReplicatorChangeListener;
import com.couchbase.lite.ReplicatorConfiguration;
import com.couchbase.lite.URLEndpoint;
import com.lkm.elogsheet.dao.ELogsheetLiteDB;
import com.lkm.elogsheet.utils.Constants;

import java.net.URI;
import java.net.URISyntaxException;

public class ReplicationService {
    private static ReplicationService instance=null;
    //private static ELogsheetLiteDB eLogsheetDB=null;
    private Replicator replicator;

    public static ReplicationService getInstance(){
        if (instance == null) {
            instance = new ReplicationService();
        }
        return instance;
    }

    public void StartReplication(){
        URI uri = null;
        try {
            uri = new URI(Constants.sync_gateway_url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Endpoint endpoint = new URLEndpoint(uri);
        BasicAuthenticator basAuth = new BasicAuthenticator("eloguser", "eloguser".toCharArray());
        ReplicatorConfiguration config = new ReplicatorConfiguration(ELogsheetLiteDB.getInstance().getDBInstance(), endpoint);
        //config.setReplicatorType(ReplicatorConfiguration.ReplicatorType.PUSH_AND_PULL);
        config.setAuthenticator(basAuth);
        this.replicator = new Replicator(config);

        replicator.addChangeListener(new ReplicatorChangeListener() {
            @Override
            public void changed(@NonNull ReplicatorChange change) {
                if (change.getStatus().getError() != null) {
                    Log.i("TAG", "Error code ::  ${err.code}");
                }
            }
        });

        this.replicator.start();
    }
}
