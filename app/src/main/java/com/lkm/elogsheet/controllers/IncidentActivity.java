package com.lkm.elogsheet.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lkm.elogsheet.R;
import com.lkm.elogsheet.models.Incident;
import com.lkm.elogsheet.services.IncidentService;
import com.lkm.elogsheet.utils.Constants;
import com.lkm.elogsheet.utils.Messages;

public class IncidentActivity extends AppCompatActivity {

    private EditText incidentDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        incidentDesc = (EditText)findViewById(R.id.txtIncidentDescription);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
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
            this.raiseIncidentSuccessDialog().show();
            this.resetFields();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), Messages.UNEXPECTED_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields(){
        incidentDesc.setText("");
    }

    public Dialog raiseIncidentSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Messages.INCIDENT_MSG)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


}