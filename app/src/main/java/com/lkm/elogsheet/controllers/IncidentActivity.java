package com.lkm.elogsheet.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lkm.elogsheet.R;
import com.lkm.elogsheet.models.Incident;
import com.lkm.elogsheet.services.IncidentService;

public class IncidentActivity extends AppCompatActivity {

    private EditText incidentDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        incidentDesc = (EditText)findViewById(R.id.txtIncidentDescription);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIncidentDetails();
            }
        });
    }

    public void saveIncidentDetails() {
        try {
            Context context = getApplicationContext();
            Incident incident=new Incident();
            incident.description=incidentDesc.getText().toString();

            IncidentService incidentService=new IncidentService();
            incidentService.saveIncidentDetails(incident,getApplicationContext());

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}