package com.lkm.elogsheet.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lkm.elogsheet.R;
import com.lkm.elogsheet.utils.CommonUtil;
import com.lkm.elogsheet.models.User;
import com.lkm.elogsheet.services.DBService;
import com.lkm.elogsheet.services.ReplicationService;
import com.lkm.elogsheet.utils.Constants;

public class LoginActivity extends AppCompatActivity {

    DBService dbService =null;
    public static SharedPreferences sharedPreferences;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView txtLoginError;
    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context context = getApplicationContext();
        dbService = DBService.getInstance();
        dbService.initCouchbaseLite(context);

        ReplicationService replService=new ReplicationService();
        replService.startReplication();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView linkRegister = (TextView) findViewById(R.id.linkRegister);

        inputEmail = (EditText) findViewById(R.id.user_id);
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

                User userDoc= (User) DBService.getInstance().get("USER_"+ userId, User.class);

                if (userDoc != null) {

                    Toast.makeText(getApplicationContext(), "You have been logged in  successfully " + userDoc.getUserId(), Toast.LENGTH_SHORT).show();

                    sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(Constants.CLIENT_ID, userDoc.getUserId());
                    editor.putBoolean(Constants.LOGIN_STATUS, true);

                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    txtLoginError.setText("Invalid user id or password");
                }
            }

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error occurred while logged in", Toast.LENGTH_SHORT).show();
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