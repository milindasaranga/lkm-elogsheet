package com.lkm.elogsheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.lkm.elogsheet.models.User;
import com.lkm.elogsheet.services.DBService;
import com.lkm.elogsheet.services.ReplicationService;

public class LoginActivity extends AppCompatActivity {

    DBService dbService =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        dbService = DBService.getInstance();
        dbService.initCouchbaseLite(context);

        User userDoc= new User();
        userDoc.userId="21004209";
        userDoc.setDocId("USER_"+userDoc.getUserId());

        dbService.write(userDoc);

        ReplicationService replService=new ReplicationService();
        replService.StartReplication();
    }
}