package com.example.faceteknik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.faceteknik.Database.Configuration;
import com.example.faceteknik.Database.RequestHandler;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullnameRegister =(EditText)findViewById(R.id.fullnameRegister);
        final EditText usernameRegister = (EditText) findViewById(R.id.usernameRegister);
        final EditText emailRegister = (EditText) findViewById(R.id.emailRegister);
        final EditText passwordRegister = (EditText) findViewById(R.id.passwordRegister);
//        final EditText lahirRegister = (EditText) findViewById(R.id.lahir);
//        final EditText bioRegister = (EditText) findViewById(R.id.bio);

        final Button buttonRegister = (Button) findViewById(R.id.signup);
        final Button buttonLogin = (Button) findViewById(R.id.login);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = fullnameRegister.getText().toString();
                final String username = usernameRegister.getText().toString();
                final String email = emailRegister.getText().toString();
                final String password = passwordRegister.getText().toString();
//                final String lahir = lahirRegister.getText().toString();
//                final String bio = bioRegister.getText().toString();

//                RegisterRequest temp = new RegisterRequest(fullname, username, email, password, lahir, bio);

                // check if input is null
                if (fullname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please full fill all form needed!", Toast.LENGTH_SHORT).show();
                } else {

                    // password must contains 6 character
                    if (Pattern.matches("\\w{6,}", password)) {
                        addUser(fullname, username, email, password, "", "");
                    } else {
                        Toast.makeText(getApplicationContext(), "Password less than 6 characters!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addUser(final String fullName,final String userName, final String email, final String password, final String tanggalLahir, final String bio){


        class AddUser extends AsyncTask<Void,Void,String>{


            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this,"Mohon tunggu...","",false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // Toast.makeText(AddUserDatabase.this,s,Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Configuration.KEY_FULLNAME, fullName);
                params.put(Configuration.KEY_USERNAME, userName);
                params.put(Configuration.KEY_EMAIL, email);
                params.put(Configuration.KEY_PASSWORD, password);
                params.put(Configuration.KEY_TANGGALLAHIR, tanggalLahir);
                params.put(Configuration.KEY_BIO, bio);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Configuration.URL_ADD_USER, params);
                return res;
            }
        }

        AddUser ae = new AddUser();
        ae.execute();
    }
}