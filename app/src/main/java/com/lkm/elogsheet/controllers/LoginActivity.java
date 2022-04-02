package com.lkm.elogsheet.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lkm.elogsheet.R;
import com.lkm.elogsheet.common.CommonUtil;
import com.lkm.elogsheet.common.Constants;
import com.lkm.elogsheet.services.DBService;
import com.lkm.elogsheet.services.ReplicationService;

public class LoginActivity extends AppCompatActivity {

    DBService dbService =null;
    public static SharedPreferences sharedPreferences;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView txtLoginError;
    private boolean isValid;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private int accountID;
    private int clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context context = getApplicationContext();
        dbService = DBService.getInstance();
        dbService.initCouchbaseLite(context);

        ReplicationService replService=new ReplicationService();
        replService.StartReplication();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView linkRegister = (TextView) findViewById(R.id.linkRegister);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        txtLoginError = (TextView) findViewById(R.id.txtLoginError);

        sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCES, 0);
        Boolean loggedIn = sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false);//login status

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {

        try {

            isValid = isValidUserInput();

            //filters the user input

            String userId = CommonUtil.filter(inputEmail.getText().toString());
            String filteredPassword = CommonUtil.filter(inputPassword.getText().toString());

            if (isValid) {

                //cursor = FBSQLLiteDB.login(db, filteredEmail, filteredPassword);
                String userDoc=DBService.getInstance().get("USER_"+ userId);

                if (userDoc != null) {
                    //cursor.moveToFirst();

                    String email = "";//cursor.getString(1);
                    clientID = 1;//cursor.getInt(3);

                    //Toast.makeText(getApplicationContext(), "client id " + String.valueOf(clientID), Toast.LENGTH_SHORT).show();

                    sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt(Constants.CLIENT_ID, clientID);
                    editor.putString(Constants.EMAIL, email);
                    editor.putBoolean(Constants.LOGIN_STATUS, true);

                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    txtLoginError.setText("Invalid email or password");
                }
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    //validate user input
    public boolean isValidUserInput() {
        if (CommonUtil.isEmptyOrNull(inputEmail.getText().toString())) {
            txtLoginError.setText("Invalid email or password");
            return false;
        }
        if (CommonUtil.isEmptyOrNull(inputPassword.getText().toString())) {
            txtLoginError.setText("Invalid email or password");
            return false;
        }
        return true;
    }
}